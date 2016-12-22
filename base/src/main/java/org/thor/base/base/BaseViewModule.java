package org.thor.base.base;

import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;

import org.thor.base.net.ApiException;
import org.thor.base.net.Result;
import org.thor.base.utils.logger.Logger;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.Schedulers;


/**
 * 项目名称:  MSHB
 * 类描述:
 * 创建人:    ICOGN
 * 创建时间:  2016/11/2 9:16
 * 修改人:    ICOGN
 * 修改时间:  2016/11/2 9:16
 * 备注:
 * 版本:
 */

public abstract class BaseViewModule implements Observable {
    private transient PropertyChangeRegistry mCallbacks;
    private OnProgress progress;

    public BaseViewModule(OnProgress progress) {
        this.progress = progress;
    }


    private void onComplete() {
        dismissProgress();
        onNavigate();
        progress.onComplete();
    }

    private void showProgress() {
        progress.showProgress();
    }

    private void onNavigate() {
        progress.onNavigate();
    }

    private void dismissProgress() {
        progress.dismissProgress();
    }

    protected void onError(String message) {
        dismissProgress();
        progress.onError(message);
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        if (mCallbacks == null) {
            mCallbacks = new PropertyChangeRegistry();
        }
        mCallbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        if (mCallbacks != null) {
            mCallbacks.remove(callback);
        }
    }

    /**
     * 通知侦听器,所有这个实例的属性已经改变了
     */
    public synchronized void notifyChange() {
        if (mCallbacks != null) {
            mCallbacks.notifyCallbacks(this, 0, null);
        }
    }

    /**
     * 通知侦听器,一个特定的属性已经改变了。属性的getter变化应标有{@link Bindable}来生成一个字段
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with {@link Bindable} to generate a field in
     * <code>BR</code> to be used as <code>fieldId</code>.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    public void notifyPropertyChanged(int fieldId) {
        if (mCallbacks != null) {
            mCallbacks.notifyCallbacks(this, fieldId, null);
        }
    }


    public <T> Configure<T> request(Flowable<? extends Result<T>> observable) {
        return new Configure<>(observable);
    }

    public <T> T apply(Result<T> result) {
        if (!result.isSuccess()) throw new ApiException(result.getMessage());
        return result.getData();
    }

    public class Configure<T> {
        private Consumer<T> onNext;
        private Consumer<Throwable> onError;
        private Action onComplete;
        private Flowable<T> observable;

        Configure(Flowable<? extends Result<T>> observable) {
            this.observable = observable
                    .map(BaseViewModule.this::apply)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io());
        }

        public Configure<T> onNext(Consumer<T> onNext) {
            this.onNext = onNext;
            return this;
        }

        public Configure<T> onError(Consumer<Throwable> onError) {
            this.onError = onError;
            return this;
        }

        public Configure<T> onComplete(Action onComplete) {
            this.onComplete = onComplete;
            return this;
        }

        public void start() {
            showProgress();
            if (onNext == null) onNext = Functions.emptyConsumer();
            if (onError == null) onError = e -> BaseViewModule.this.onError(e.getMessage());
            if (onComplete == null) onComplete = BaseViewModule.this::onComplete;
            progress.addDisposable(observable
                    .subscribe(onNext,
                            onError,
                            onComplete,
                            s -> s.request(Long.MAX_VALUE)
                    )
            );
        }
    }
}

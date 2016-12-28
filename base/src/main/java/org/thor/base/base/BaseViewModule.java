package org.thor.base.base;


import org.thor.base.net.ApiException;
import org.thor.base.net.Result;

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

public abstract class BaseViewModule {
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


    public <T> Configure<T> request(Flowable<? extends Result<T>> observable) {
        return new Configure<>(observable);
    }

    public <T> T apply(Result<T> result) {
        if (!result.isSuccess()) throw new ApiException(result.getMessage());
        return result.getData();
    }

    public class Configure<T> {
        private Consumer<T>         onNext;
        private Consumer<Throwable> onError;
        private Action              onComplete;
        private Flowable<T>         observable;

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

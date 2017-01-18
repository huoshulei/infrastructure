package org.thor.base.base;



import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.ListCompositeDisposable;

public abstract class RxLifeActivity extends BaseActivity implements OnProgress {

    private ListCompositeDisposable disposable = new ListCompositeDisposable();

    @Override
    public void addDisposable(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed())
            this.disposable.add(disposable);
    }

    @Override
    public void remove(Disposable disposable) {
        if (disposable != null) this.disposable.remove(disposable);
    }

    @Override
    public void clear() {
        if (!disposable.isDisposed()) disposable.clear();
    }


    /**
     * 网络请求开始回调
     */
    @Override
    public void showProgress() {
        showDialog();
    }


    /**
     * 网络请求完成 或失败回调
     */
    @Override
    public void dismissProgress() {
        hideDialog();
    }

    /**
     * 网络请求错误回调
     */
    @Override
    public void onError(String message) {
    }

    @Override
    protected void onDestroy() {
        clear();
        dismissDialog();
        super.onDestroy();
    }
}

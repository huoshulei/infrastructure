package org.thor.base.base;

import io.reactivex.disposables.Disposable;

/**
 */

public interface OnProgress {
    void onComplete();

    void showProgress();

    void onNavigate();

    void dismissProgress();

    void onError(String message);

    void addDisposable(Disposable disposable);

    void remove(Disposable disposable);

    void clear();
}

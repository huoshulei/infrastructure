package org.thor.base.base;

import io.reactivex.disposables.Disposable;

/**
 */

public interface OnProgress {

    void showProgress();

    void dismissProgress();

    void onError(String message);

    void addDisposable(Disposable disposable);

    void remove(Disposable disposable);

    void clear();
}

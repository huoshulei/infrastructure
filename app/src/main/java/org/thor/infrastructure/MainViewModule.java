package org.thor.infrastructure;

import org.thor.base.base.OnProgress;

import io.reactivex.functions.Consumer;

/**
 * Created by caihong on 2016/12/22.
 */

public class MainViewModule extends TestViewModule {
    public MainViewModule(OnProgress progress) {
        super(progress);
    }

    public void a() {
        request(api.aa()).onNext(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
            }
        }).start();
    }
}

package org.thor.infrastructure;

import org.thor.base.base.OnProgress;
import org.thor.base.utils.logger.Logger;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

/**
 * 创建人: 霍述雷
 * 时 间:2016/12/22 14:04.
 */

public class MainViewModule extends TestViewModule {
    public MainViewModule(OnProgress progress) {
        super(progress);
    }

    public void a() {
        request(api.aa()).onNext(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
             Logger.d("MainViewModule accept: "+o);
            }
        }).start();
    }


}

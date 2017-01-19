package org.thor.infrastructure;

import org.thor.base.base.BaseViewModule;
import org.thor.base.base.OnProgress;
import org.thor.base.net.Result;
import org.thor.base.net.ServeException;

/**
 * 创建人: 霍述雷
 * 时 间:2016/12/21 9:16.
 */

public class TestViewModule extends BaseViewModule<Api> {

//    protected static final Api api = Net.getInstance(Api.class);

    public TestViewModule(OnProgress progress) {
        super(progress,Api.class);
    }

    @Override
    protected <T> T converter(Result<T> result) {
        if (!result.isSuccess()) throw new ServeException(result.getMessage());
        if (result.getData() == null) return (T) new Object();
        return result.getData();
    }
}

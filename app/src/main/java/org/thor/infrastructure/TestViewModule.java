package org.thor.infrastructure;

import org.thor.base.base.BaseViewModule;
import org.thor.base.base.OnProgress;
import org.thor.base.net.Result;
import org.thor.base.net.Net;

/**
 * Created by caihong on 2016/12/21.
 */

public class TestViewModule extends BaseViewModule {

    protected static final Api api = Net.NET.getRetrofit().create(Api.class);

    public TestViewModule(OnProgress progress) {
        super(progress);
    }

    @Override
    public <T> T apply(Result<T> result) {
        return result.getData();
    }

}

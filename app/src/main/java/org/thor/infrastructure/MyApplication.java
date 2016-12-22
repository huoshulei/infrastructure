package org.thor.infrastructure;

import android.support.annotation.NonNull;

import org.thor.base.BaseApplication;

/**
 * Created by caihong on 2016/12/22.
 */

public class MyApplication extends BaseApplication {


    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    @NonNull
    @Override
    public String getBaseUrl() {
        return "http://gank.io/api/";
    }

    @NonNull
    @Override
    public String refreshToken(String token) {
        return "asd";
    }
}

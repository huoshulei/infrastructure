package org.thor.infrastructure;

import android.support.annotation.NonNull;

import org.thor.base.BaseApplication;
import org.thor.base.net.Net;
import org.thor.base.net.NetOptions;

/**
 * 创建人: 霍述雷
 * 时 间:2016/12/22 9:48.
 */

public class MyApplication extends BaseApplication {


    @Override
    protected void initNet(NetOptions options) {
        options.setBaseUrl("http://gank.io/api/");
        Net.init(options);
    }

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }
//
//    @NonNull
//    @Override
//    public String getBaseUrl() {
//        return "http://gank.io/api/";
//    }
//
//    @NonNull
//    @Override
//    public String refreshToken(String token) {
//        return "asd";
//    }
}

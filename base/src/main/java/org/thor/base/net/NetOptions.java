package org.thor.base.net;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;


/**
 * 创建人: 霍述雷
 * 时 间:2017/1/18 16:44.
 */

public class NetOptions {
    private String  baseUrl;
    private boolean debug;
    private Authenticator authenticator = new Authenticator() {
        @Override
        public Request authenticate(Route route, Response response) throws IOException {
            return response.request();
        }
    };

    private Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            return chain.proceed(request);
        }
    };

    Authenticator getAuthenticator() {
        return authenticator;
    }

    String getBaseUrl() {
        return baseUrl;
    }

    boolean isDebug() {
        return debug;
    }

    Interceptor getInterceptor() {
        return interceptor;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    public void setTokenInterceptor(Interceptor interceptor) {
        this.interceptor = interceptor;
    }
}

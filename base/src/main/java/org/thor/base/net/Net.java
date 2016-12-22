package org.thor.base.net;

import android.text.TextUtils;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.thor.base.App;
import org.thor.base.utils.SPUtils;
import org.thor.base.utils.interceptor.HttpLoggingInterceptor;
import org.thor.base.utils.interceptor.HttpLoggingInterceptor.Level;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by caihong on 2016/12/5.
 */

public enum Net {
    NET;
    private String token;
    private final Retrofit retrofit;

    Net() {
        retrofit = new Retrofit.Builder()
                .baseUrl(App.application.getBaseUrl())
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new HttpLoggingInterceptor()
                                .setLevel(App.application.isDebug() ? Level.BODY : Level.NONE))
                        .retryOnConnectionFailure(true)
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .addNetworkInterceptor(tokenInterceptor)
                        .authenticator(authenticator)
                        .build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * token拦截
     */
    Interceptor tokenInterceptor = chain -> {
        Request request = chain.request();
        if (getToken() == null || alreadyHasAuthorizationHeader(request))
            return chain.proceed(request);
        Request authorised = request.newBuilder()
                .header("Authorization", getToken())
                .build();
        return chain.proceed(authorised);
    };
    /**
     * 刷新token
     */
    Authenticator authenticator = (route, response) -> {
        setToken(refreshToken(getToken()));
        return response.request().newBuilder()
                .addHeader("Authorization", getToken())
                .build();
    };

    /**
     * @param request
     * @return Authorization 是否为空
     */
    boolean alreadyHasAuthorizationHeader(Request request) {
        String header = request.header("Authorization");
        return header != null && !header.isEmpty();
    }

    /**
     * @param token
     * @return 刷新Token
     * @throws IOException
     */
    String refreshToken(String token) throws IOException {
        return App.application.refreshToken(token);
    }

    private String getToken() {
        if (TextUtils.isEmpty(token)) token = SPUtils.getString("token");
        return token;
    }

    private void setToken(String token) {
        this.token = token;
        SPUtils.putString("token", token);
    }


    public Retrofit getRetrofit() {
        return retrofit;
    }
}

package org.thor.base.net;

import android.text.TextUtils;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.thor.base.utils.interceptor.HttpLoggingInterceptor;
import org.thor.base.utils.interceptor.HttpLoggingInterceptor.Level;

import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 创建人: 霍述雷
 * 时 间:2016/12/5 15:31.
 */

public class Net {
    private volatile static Retrofit retrofit;

    private static String BASE_URL;

    private static boolean debug;

    /**
     * token拦截
     */
    private static Interceptor   tokenInterceptor;
    /**
     * 刷新token
     */
    private static Authenticator authenticator;

    public static void init(String baseUrl) {
        NetOptions options = new NetOptions();
        options.setBaseUrl(baseUrl);
        init(options);
    }

    public static void init(NetOptions options) {
        BASE_URL = options.getBaseUrl();
        debug = options.isDebug();
        authenticator = options.getAuthenticator();
        tokenInterceptor = options.getInterceptor();
    }

    public static <T> T getInstance(Class<T> clazz) {
        if (TextUtils.isEmpty(BASE_URL)) throw new RuntimeException("请先在Application中配置");
        if (retrofit == null) {
            synchronized (Retrofit.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .client(new OkHttpClient.Builder()
                                    .addInterceptor(new HttpLoggingInterceptor()
                                            .setLevel(debug ? Level.BODY : Level.NONE))
                                    .retryOnConnectionFailure(true)
                                    .connectTimeout(15, TimeUnit.SECONDS)
                                    .addNetworkInterceptor(tokenInterceptor)
                                    .authenticator(authenticator)
                                    .build())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return retrofit.create(clazz);
    }

}

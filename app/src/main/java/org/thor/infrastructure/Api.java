package org.thor.infrastructure;

import io.reactivex.Flowable;
import retrofit2.http.GET;

/**
 * 创建人: 霍述雷
 * 时 间:2016/12/21 14:03.
 */

public interface Api {
    @GET("data/Android/10/1")
    Flowable<HttpResult<Object>> aa();
}

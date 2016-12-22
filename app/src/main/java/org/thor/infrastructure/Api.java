package org.thor.infrastructure;

import io.reactivex.Flowable;
import retrofit2.http.GET;

/**
 * Created by caihong on 2016/12/21.
 */

public interface Api {
    @GET("data/福利/10/1")
    Flowable<HttpResult<Object>> aa();
}

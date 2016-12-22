package org.thor.infrastructure;

import com.google.gson.annotations.SerializedName;

import org.thor.base.net.Result;

/**
 * Created by caihong on 2016/12/22.
 */

public class HttpResult<T> implements Result<T> {
    @SerializedName("error")
    private boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("results")
    private T data;

    @Override
    public boolean isSuccess() {
        return !success;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public T getData() {
        return data;
    }
}

package org.thor.infrastructure;

import com.google.gson.annotations.SerializedName;

import org.thor.base.net.Result;

/**
 * 创建人: 霍述雷
 * 时 间:2016/12/22 14:03.
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

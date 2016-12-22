package org.thor.base.net;

/**
 * Created by caihong on 2016/12/5.
 */

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}

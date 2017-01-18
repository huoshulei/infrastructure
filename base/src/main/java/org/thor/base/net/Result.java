package org.thor.base.net;

/**
 * 项目名称:  MSHB
 * 类描述:
 * 创建人:    ICOGN
 * 创建时间:  2016/9/26 14:05
 * 修改人:    ICOGN
 * 修改时间:  2016/9/26 14:05
 * 备注:
 * 版本:
 */

public interface Result<T> {
    //    @SerializedName("success")
//    private boolean success;
//    @SerializedName("message")
//    private String message;
//    @SerializedName("data")
//    private T data;
//
    boolean isSuccess();

    String getMessage();

    T getData();
}

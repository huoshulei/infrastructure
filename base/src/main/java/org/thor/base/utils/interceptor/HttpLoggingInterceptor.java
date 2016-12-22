/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thor.base.utils.interceptor;


import com.android.annotations.NonNull;

import org.thor.base.utils.logger.Logger;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;


/**
 * 项目名称:  MSHB
 * 类描述:
 * 创建人:    ICOGN
 * 创建时间:  2016/9/23 15:49
 * 修改人:    ICOGN
 * 修改时间:  2016/9/23 15:49
 * 备注:
 * 版本:
 */
public final class HttpLoggingInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    public enum Level {

        NONE,

        BASIC,

        HEADERS,

        BODY
    }


    public HttpLoggingInterceptor() {
    }


    private volatile Level level = Level.NONE;


    public HttpLoggingInterceptor setLevel(@NonNull Level level) {
        if (level == null) throw new NullPointerException("level == null. Use Level.NONE instead.");
        this.level = level;
        return this;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Level level = this.level;

        Request request = chain.request();
        if (level == Level.NONE) {
            return chain.proceed(request);
        }

        boolean logBody    = level == Level.BODY;
        boolean logHeaders = logBody || level == Level.HEADERS;

        RequestBody requestBody    = request.body();
        boolean     hasRequestBody = requestBody != null;

        Connection connection          = chain.connection();
        Protocol   protocol            = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
        String     requestStartMessage = "--> " + request.method() + ' ' + request.url() + ' ' + protocol;
        if (!logHeaders && hasRequestBody) {
            requestStartMessage += " (" + requestBody.contentLength() + "-请求体长度)";
        }
        Logger.t("请求地址").d(requestStartMessage);
        if (logHeaders) {
            if (hasRequestBody) {
                // Request body headers are only present when installed as a network interceptor. Force
                // them to be included (when available) so there values are known.
                if (requestBody.contentType() != null) {
                    Logger.t("内容格式").d("Content-Type: " + requestBody.contentType());
                }
                if (requestBody.contentLength() != -1) {
                    Logger.t("请求体长度").d("Content-Length: " + requestBody.contentLength());
                }
            }

            Headers headers = request.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                String name = headers.name(i);
                if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                    Logger.t("请求信息").d(name + ": " + headers.value(i));
                }
            }

            if (!logBody || !hasRequestBody) {
                Logger.t("空请求体").d("--> 请求结束 " + request.method());
            } else if (bodyEncoded(request.headers())) {
                Logger.t("未知").d("--> 请求结束 " + request.method() + " (encoded body omitted)");
            } else {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);

                Charset   charset     = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                if (isPlaintext(buffer)) {
//                    log.log(buffer.readString(charset));
                    Logger.t("请求体").json(buffer.readString(charset));
                    Logger.t("请求结束").d("--> 请求结束 " + request.method()
                            + " (" + requestBody.contentLength() + "-字节 body)");
                } else {
                    Logger.t("请求结束").d("--> 请求结束 " + request.method() + " (二进制 "
                            + requestBody.contentLength() + "-字节 body omitted)");
                }
            }
        }

        long     startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            Logger.t("网络连接失败").d("<-- 网络连接失败: " + e);
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody  = response.body();
        long         contentLength = responseBody.contentLength();
        String       bodySize      = contentLength != -1 ? contentLength + "-字节" : "未知长度";
        Logger.t("请求结束").d("<-- " + response.code() + ' ' + response.message() + ' '
                + response.request().url() + " (" + tookMs + "ms" + (!logHeaders ? ", "
                + bodySize + " body" : "") + ')');

        if (logHeaders) {
            Headers headers = response.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                Logger.t("响应信息").d(headers.name(i) + ": " + headers.value(i));
            }

            if (!logBody || !HttpHeaders.hasBody(response)) {
                Logger.t("空响应体").d("<-- 网络请求结束");
            } else if (bodyEncoded(response.headers())) {
                Logger.t("未知").d("<-- 网络请求结束 (encoded body omitted)");
            } else {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();

                Charset   charset     = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    try {
                        charset = contentType.charset(UTF8);
                    } catch (UnsupportedCharsetException e) {
                        Logger.t("响应体解码失败").d("不能解码的响应体.<-- 网络请求结束");
                        return response;
                    }
                }

                if (!isPlaintext(buffer)) {
                    Logger.t("响应体加密").d("<-- 网络请求结束 (二进制 " + buffer.size() + "-字节 body omitted)");
                    return response;
                }

                if (contentLength != 0) {
                    Logger.t("响应体").json(buffer.clone().readString(charset));
                }

                Logger.t("本次网络请求结束").d("<-- 网络请求结束 (" + buffer.size() + "-字节 body)");
            }
        }

        return response;
    }

    /**
     * 是否加密
     */
    private static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix    = new Buffer();
            long   byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
}

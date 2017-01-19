package org.thor.base.base;


import com.google.gson.JsonParseException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONException;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import org.thor.base.App;
import org.thor.base.net.ApiException;
import org.thor.base.net.Net;
import org.thor.base.net.Result;
import org.thor.base.net.ServeException;
import org.thor.base.net.TokenException;
import org.thor.base.utils.logger.Logger;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.text.ParseException;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.Schedulers;


/**
 * 项目名称:  MSHB
 * 类描述:
 * 创建人:    ICOGN
 * 创建时间:  2016/11/2 9:16
 * 修改人:    ICOGN
 * 修改时间:  2016/11/2 9:16
 * 备注:
 * 版本:
 */

public abstract class BaseViewModule<API> {
    private   OnProgress progress;
    protected API          api;

    public BaseViewModule(OnProgress progress, Class<API> clazz) {
        this.progress = progress;
        api = Net.getInstance(clazz);
    }

    private void showProgress() {
        progress.showProgress();
    }

    private void dismissProgress() {
        progress.dismissProgress();
    }
    protected abstract  <T>T converter(Result<T> result);
    protected final <T> Configure<T> request(Flowable<? extends Result<T>> observable) {
        return new Configure<>(observable);
    }

    public class Configure<T> {
        private Consumer<T>         onNext;
        private Flowable<T>         observable;
        private Consumer<Throwable> onError;
        private Action              onComplete;

        Configure(Flowable<? extends Result<T>> observable) {
            this.observable = observable
                    .map( new Function<Result<T>, T>() {
                        @Override
                        public T apply(Result<T> result) throws Exception {
                            return Configure.this.apply(result);
                        }
                    })
                    .onErrorResumeNext(new Function<Throwable, Publisher<T>>() {
                        @Override
                        public Publisher<T> apply(Throwable e) throws Exception {
                            return handleException(e);
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io());
        }


        public Configure<T> onNext(Consumer<T> onNext) {
            this.onNext = onNext;
            return this;
        }

        public Configure<T> onError(Consumer<Throwable> onError) {
            this.onError = onError;
            return this;
        }

        public Configure<T> onComplete(Action onComplete) {
            this.onComplete = onComplete;
            return this;
        }

        public void start() {
            showProgress();
            if (onNext == null) onNext = Functions.emptyConsumer();
            progress.addDisposable(observable
                    .subscribe(onNext,
                            new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable e) throws Exception {
                                    onError(e);
                                }
                            },
                            new Action() {
                                @Override
                                public void run() throws Exception {
                                    Configure.this.onComplete();
                                }
                            },
                            new Consumer<Subscription>() {
                                @Override
                                public void accept(Subscription s) throws Exception {
                                    s.request(Long.MAX_VALUE);
                                }
                            }
                    )
            );
        }

        private void onError(Throwable e) throws Exception {
            App.toast(e.getMessage());
            dismissProgress();
            progress.onError(e.getMessage());
            if (onError != null) onError.accept(e);
        }

        private void onComplete() throws Exception {
            dismissProgress();
            if (onComplete != null) onComplete.run();
        }

        private Flowable<T> handleException(Throwable e) {
            Logger.d("BaseViewModule:" + e.toString());
            ApiException ex;
            if (e instanceof HttpException) {
                HttpException exception = (HttpException) e;
                switch (exception.code()) {
                    case 400:
                        ex = new ApiException("请求错误!请求中有语法错误");
                        break;
                    case 402:
                        ex = new ApiException("需要付费");
                        break;
                    case 403:
                        ex = new ApiException("禁止访问!");
                        break;
                    case 404:
                        ex = new ApiException("访问目标不存在或已被删除");
                        break;
                    case 405:
                        ex = new ApiException("资源被禁止");
                        break;
                    case 406:
                        ex = new ApiException("请求错误!请求中有语法错误");
                        break;
                    case 407:
                        ex = new ApiException("需要代理身份认证");
                        break;
                    case 410:
                        ex = new ApiException("资源不可用");
                        break;
                    case 414:
                        ex = new ApiException("请求路径过长");
                        break;
                    case 500:
                        ex = new ApiException("服务器繁忙!暂不接受访问");
                        break;
                    case 502:
                        ex = new ApiException("网关错误");
                        break;
                    default:
                        ex = new ApiException("网络错误");
                        break;
                }
            } else if (e instanceof JSONException
                    || e instanceof JsonParseException
                    || e instanceof ParseException) {
                ex = new ApiException("数据解析异常");
            } else if (e instanceof ConnectException) {
                ex = new ApiException("服务器连接失败");
            } else if (e instanceof TokenException
                    || e instanceof ServeException) {
                ex = new ApiException(e.getMessage());
            } else if (e instanceof SocketException) {
                ex = new ApiException("无法连接到服务器");
            } else if (e instanceof SocketTimeoutException) {
                ex = new ApiException("连接超时");
            } else {
                ex = new ApiException("未知错误");
            }
            return Flowable.error(ex);
        }

        private  T apply(Result<T> result) {
            return converter(result);
        }


    }
}

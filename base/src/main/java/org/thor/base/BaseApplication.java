package org.thor.base;

import android.app.Application;
import android.support.annotation.NonNull;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.thor.base.base.BaseActivity;
import org.thor.base.utils.logger.LogLevel;
import org.thor.base.utils.logger.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by caihong on 2016/12/5.
 */

public abstract class BaseApplication extends Application {
    private List<BaseActivity> activities = new ArrayList<>();

    public List<BaseActivity> getActivities() {
        return activities;
    }

    public void addActivities(BaseActivity activity) {
        activities.add(activity);
    }

    public void removeActivity(BaseActivity activity) {
        activities.remove(activity);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
        initLogger();
    }


    /**
     * 初始化日志信息
     */
    protected void initLogger() {
        Logger.init("OneMedia")
                .methodCount(2)
                .hideThreadInfo()
                .logLevel(isDebug() ? LogLevel.FULL : LogLevel.NONE)
                .methodOffset(0);
    }

    public abstract boolean isDebug();

    /**
     * 初始化image loader
     */
    protected void initImageLoader() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
//默认图片 加载中图片 错误图片 圆角
//                .showImageForEmptyUri(R.mipmap.ic_avatar)
//                .showImageOnLoading(R.mipmap.ic_avatar)
//                .showImageOnFail(R.mipmap.ic_avatar)
//                .displayer(new RoundedBitmapDisplayer(getResources().getDimensionPixelOffset(R
//                        .dimen.dp_80)))
                .cacheInMemory(true) // 打开内存缓存
                .cacheOnDisk(true) // 打开硬盘缓存
                .resetViewBeforeLoading(true)// 在ImageView加载前清除它上面之前的图片
                .build();
        // ImageLoader的配置
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder
                (getApplicationContext())
                .memoryCacheSize(5 * 1024 * 1024)// 设置内存缓存为5M
                .defaultDisplayImageOptions(options)// 设置默认的显示选项
                .denyCacheImageMultipleSizesInMemory()//禁止缓存多图
                .threadPriority(3)
                .diskCacheSize(200 * 1024 * 1024)//磁盘缓存大小
                .build();
        // 初始化ImageLoader
        ImageLoader.getInstance().init(config);
    }

    @NonNull
    public abstract String getBaseUrl();

    @NonNull
    public abstract String refreshToken(String token);


}

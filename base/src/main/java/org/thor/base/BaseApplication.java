package org.thor.base;

import android.app.Application;

//import org.thor.base.base.BaseActivity;
import org.thor.base.base.BaseActivity;
import org.thor.base.net.Net;
import org.thor.base.net.NetOptions;
import org.thor.base.utils.logger.LogLevel;
import org.thor.base.utils.logger.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * 创建人: 霍述雷
 * 时 间:2016/12/5 13:45.
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
        initLogger();
        initNet();
    }

    /**
     * 配置BaseUrl
     */
    private void initNet() {
        NetOptions options = new NetOptions();
        options.setDebug(isDebug());
        initNet(options);
        Net.init(options);
    }

    protected abstract void initNet(NetOptions options);

    /**
     * 模式
     */
    protected abstract boolean isDebug();

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

}
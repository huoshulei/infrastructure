package org.thor.base;

import android.widget.Toast;

import org.thor.base.utils.logger.Logger;


/**
 * 项目名称:  MSHB
 * 类描述:
 * 创建人:    ICOGN
 * 创建时间:  2016/10/22 8:47
 * 修改人:    ICOGN
 * 修改时间:  2016/10/22 8:47
 * 备注:
 * 版本:
 */

public final class App {
    public static final BaseApplication application;

    static {
        BaseApplication app = null;
        try {
            app = (BaseApplication) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication").invoke(null);
            if (app == null)
                throw new IllegalStateException("Static initialization of Applications must be on main thread.");
        } catch (Exception e) {
            Logger.d("Failed to get current application from AppGlobals." + e.getMessage());
            try {
                app = (BaseApplication) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null);
            } catch (Exception e1) {
                Logger.d("Failed to get current application from ActivityThread." + e.getMessage());
            }
        } finally {
            application = app;
        }
    }

    private App() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    public static void toast(String msg) {
        Toast.makeText(application, msg, Toast.LENGTH_SHORT).show();
    }

    public static void toast(int msgId) {
        Toast.makeText(application, msgId, Toast.LENGTH_SHORT).show();
    }

    public static void longToast(String mag) {
        Toast.makeText(application, mag, Toast.LENGTH_LONG).show();
    }
}

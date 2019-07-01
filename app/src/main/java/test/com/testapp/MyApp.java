package test.com.testapp;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import java.lang.reflect.Method;

import test.com.commenlib.service.GlobalService;

/**
 * Created by zhangjian on 2019/5/15 11:35
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initService();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private void initService() {
        for (String str : GlobalService.COMPONETS) {

            try {
                Class clazz = Class.forName(str);
                Object o = clazz.newInstance();
                Method initialService = o.getClass().getDeclaredMethod("initialService", Application.class);
                initialService.invoke(o, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

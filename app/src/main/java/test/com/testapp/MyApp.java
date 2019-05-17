package test.com.testapp;

import android.app.Application;

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

package test.com.logincomponent;

import android.app.Application;

import test.com.commenlib.service.GlobalService;

/**
 * Created by zhangjian on 2019/5/15 11:18
 */
public class MyApp extends Application {
    private Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        initialService(this);
    }

    public void initialService(Application app) {
        GlobalService.getInstance().setiLoginService(new LoginService());
        application = app;
    }
}

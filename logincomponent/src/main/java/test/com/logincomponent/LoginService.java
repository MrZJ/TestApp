package test.com.logincomponent;

import android.content.Context;
import android.content.Intent;

import test.com.commenlib.service.ILoginService;

/**
 * Created by zhangjian on 2019/5/15 11:09
 */
public class LoginService implements ILoginService {
    @Override
    public void startAcitivity(Context context, Intent intent, String className) {
        intent.setClassName(context, className);
        context.startActivity(intent);
    }
}

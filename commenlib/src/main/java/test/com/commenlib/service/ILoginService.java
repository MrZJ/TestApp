package test.com.commenlib.service;

import android.content.Context;
import android.content.Intent;

/**
 * Created by zhangjian on 2019/5/15 11:07
 */
public interface ILoginService {
    void startAcitivity(Context context, Intent intent,String className);
}

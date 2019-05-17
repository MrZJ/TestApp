package test.com.commenlib.service;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by zhangjian on 2019/5/15 11:02
 */
public interface IMineService {
    Fragment instanceFragment();
    void startMineActivity(Context context);
}

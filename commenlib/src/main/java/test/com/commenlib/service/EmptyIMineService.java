package test.com.commenlib.service;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by zhangjian on 2019/5/15 11:06
 */
public class EmptyIMineService implements IMineService {
    @Override
    public Fragment instanceFragment() {
        return new Fragment();
    }

    @Override
    public void startMineActivity(Context context) {

    }
}

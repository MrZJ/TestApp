package test.com.minecomponent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import test.com.commenlib.service.IMineService;

/**
 * Created by zhangjian on 2019/5/15 11:02
 */
public class MineService implements IMineService {
    @Override
    public Fragment instanceFragment() {
        return new UserFragment();
    }

    @Override
    public void startMineActivity(Context context) {
        Intent intent = new Intent(context, MineActivity.class);
        context.startActivity(intent);
    }

}

package test.com.testapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangjian on 2019/6/25 13:44
 */
public class FlowActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow);
        FlowLayout flowLayout = findViewById(R.id.flowLayout);
        flowLayout.addTags(getList());
    }

    List<String> getList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("flowLayout" + i);
        }
        return list;
    }
}

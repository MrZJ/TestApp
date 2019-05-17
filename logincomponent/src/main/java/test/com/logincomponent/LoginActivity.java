package test.com.logincomponent;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import test.com.commenlib.anotation.PeopleBean;
import test.com.commenlib.eventbus.EventBus;
import test.com.commenlib.service.GlobalService;

public class LoginActivity extends AppCompatActivity {

    private TextView text1;
    private TextView text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getInstance().postEvent(new PeopleBean("老王", 55));
            }
        });
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getInstance().postEvent(new PeopleBean("小李", 22));
            }
        });
    }

    public void getFragment(View view) {
        Fragment fragment = GlobalService.getInstance().getiMineService().instanceFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commitAllowingStateLoss();
    }
}

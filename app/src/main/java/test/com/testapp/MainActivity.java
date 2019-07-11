package test.com.testapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.ImageView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import test.com.commenlib.anotation.ContentView;
import test.com.commenlib.anotation.InjectManager;
import test.com.commenlib.anotation.InjectView;
import test.com.commenlib.anotation.PeopleBean;
import test.com.commenlib.eventbus.EventBus;
import test.com.commenlib.eventbus.Receive;
import test.com.commenlib.eventbus.ThreadMode;
import test.com.commenlib.service.GlobalService;


@ContentView(getLayoutId = R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @InjectView(getViewId = R.id.et)
    private EditText et;
    ObjectAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectManager.inject(this);
        EventBus.getInstance().regist(this);

    }

    public void login(View view) {
//        test.com.logincomponent.LoginActivity
        GlobalService.getInstance().getiLoginService().startAcitivity(this, new Intent(), "test.com.logincomponent.LoginActivity");
    }

    public void mine(View view) {
        GlobalService.getInstance().getiMineService().startMineActivity(this);
    }

    public void customViewClick(View view) {
        Log.e("jianzhang", "customViewClick");
    }

    public void goSplashView(View view) {
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
    }

    public void goErazerView(View view) {
        Intent intent = new Intent(this, ErazerActivity.class);
        startActivity(intent);
    }

    public void goBezier(View view) {
        Intent intent = new Intent(this, BezierTestActivity.class);
        startActivity(intent);
    }

    public void goPathMeasure(View view) {
        Intent intent = new Intent(this, PathMeasureActivity.class);
        startActivity(intent);
    }

    public void goPercentLayout(View view) {
        Intent intent = new Intent(this, PercentActivity.class);
        startActivity(intent);
    }

    public void paintTest(View view) {
        Intent intent = new Intent(this, PaintActivity.class);
        startActivity(intent);
    }

    public void goFlowLayout(View view) {
        Intent intent = new Intent(this, FlowActivity.class);
        startActivity(intent);
    }

    public void goTaoBao(View view) {
        Intent intent = new Intent(this, VLayoutActivity.class);
        startActivity(intent);
    }

    class MyFilter implements InputFilter {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Log.e("filter", source + ",start=" + start + ",end=" + end + ",Spanned=" + dest + ",dend=" + dend);
            if (source != null && source.equals("o")) {
                return "哦";
            }
            return null;
        }
    }

    @Receive(getThreadMode = ThreadMode.THREAD_MAIN)
    public void onReceive(PeopleBean bean) {
        Log.e("Main", bean.toString() + "," + Thread.currentThread().getName());
    }

    @Receive(getThreadMode = ThreadMode.THREAD_BACK)
    public void onReceive2(PeopleBean bean) {
        Log.e("Main", bean.toString() + "," + Thread.currentThread().getName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getInstance().unRegist(this);
    }
}

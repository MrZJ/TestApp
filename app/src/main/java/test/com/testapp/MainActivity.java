package test.com.testapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectManager.inject(this);
        EventBus.getInstance().regist(this);
        MyFilter[] filters = new MyFilter[1];
        filters[0] = new MyFilter();
//        et = findViewById(R.id.et);
        et.setFilters(filters);
        Disposable disposable1 = Observable.just("name", "sex", "age", "skil")
                .doOnSubscribe(disposable -> {
                    Log.e("rxjava", "subscribe" + disposable);
                })
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((String s) -> {
                    Log.e("rxjava", "subscribe" + s);
                });

//        Disposable subscribe = Observable
//                .create((ObservableOnSubscribe<Integer>) e -> {
//                    int i = 0;
//                    while (true) {
//                        i++;
//                        e.onNext(i);
//                    }
//                })
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.newThread())
//                .subscribe(integer -> {
//                    Thread.sleep(5000);
//                    System.out.println(integer);
//                });
    }

    public void login(View view) {
//        test.com.logincomponent.LoginActivity
        GlobalService.getInstance().getiLoginService().startAcitivity(this, new Intent(), "test.com.logincomponent.LoginActivity");
    }

    public void mine(View view) {
        GlobalService.getInstance().getiMineService().startMineActivity(this);
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

package test.com.minecomponent;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by zhangjian on 2019/5/15 10:55
 */
public class UserFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getContext());
        textView.setText("我是UserFragment");
        textView.setBackgroundColor(Color.parseColor("#ffffff"));
//        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_user,null);
        return textView;
    }
}

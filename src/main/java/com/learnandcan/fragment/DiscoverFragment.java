package com.learnandcan.fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.learnandcan.R;
import com.learnandcan.activity.DiscoverdetailActivity;


/**
 * Created by lsr on 2015/11/18.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DiscoverFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_discover, container, false);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onStart() {

        super.onStart();

        Button testBtn = (Button) getView().findViewById(R.id.testBtn);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), DiscoverdetailActivity.class);
                startActivity(intent);
            }
        });
    }

    /*
      * 函数名：initWidget()
      * 说明：用于初始化控件,包括ListView, linearlayout
      * */

}

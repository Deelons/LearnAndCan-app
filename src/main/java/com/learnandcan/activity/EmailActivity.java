package com.learnandcan.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.learnandcan.R;

public class EmailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_email);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title_email);
    }
}

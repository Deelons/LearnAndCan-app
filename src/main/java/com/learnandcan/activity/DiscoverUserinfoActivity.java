package com.learnandcan.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import com.learnandcan.R;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by lsr on 2015/11/18.
 */
public class DiscoverUserinfoActivity extends ActionBarActivity {

    private ListView userdetail_userinfoLv, userdetail_lessoninfoLv;
    private LinkedList<HashMap<String, Object>> userdetail_userinfoLl, userdetail_lessoninfoLl;
    private TextView returnTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetail);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        initWidget();
    }

    private void initWidget() {
        //返回按钮初始化
        returnTv=(TextView)findViewById(R.id.userdetail_returnTv);
        returnTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //listview的初始化
        userdetail_userinfoLv = (ListView) findViewById(R.id.userdetail_userinfoLv);
        userdetail_lessoninfoLv = (ListView) findViewById(R.id.userdetail_lessoninfoLv);
        userdetail_userinfoLl = new LinkedList<HashMap<String, Object>>();
        userdetail_lessoninfoLl = new LinkedList<HashMap<String, Object>>();
        getUserdetail_lessoninfoLl();
        getUserdetail_userinfoLl();
        SimpleAdapter lesson_simpleAdapter = new SimpleAdapter(this, userdetail_lessoninfoLl, R.layout.lessonoverview_adp_item, new String[]{"portrait", "name", "desp"}, new int[]{R.id.lessonadp_portraitIv, R.id.lessonadp_nameTv, R.id.lessonadp_despTv});
        userdetail_lessoninfoLv.setAdapter(lesson_simpleAdapter);
        SimpleAdapter user_simpleAdapter=new SimpleAdapter(this,userdetail_userinfoLl,R.layout.userdetailinfo_adp_item,new String[]{"attr","value"},new int[]{R.id.userinfo_adp_attrTv,R.id.userinfo_adp_valueTv});
        userdetail_userinfoLv.setAdapter(user_simpleAdapter);
    }

    public void getUserdetail_userinfoLl(){
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("attr", "城市");
        map.put("value", "广东深圳");
        userdetail_userinfoLl.add(map);

        map = new HashMap<String, Object>();
        map.put("attr", "爱好");
        map.put("value", "钢琴，吉他，音乐");
        userdetail_userinfoLl.add(map);

        map = new HashMap<String, Object>();
        map.put("attr", "星座");
        map.put("value", "狮子座");
        userdetail_userinfoLl.add(map);


    }
    public void getUserdetail_lessoninfoLl(){
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("portrait", R.drawable.ic_miya);
        map.put("name", "miya");
        map.put("desp", "吉他入门教学，学生自备吉他，可线上提供指导，线下交流学习......");
        userdetail_lessoninfoLl.add(map);

        map = new HashMap<String, Object>();
        map.put("portrait", R.drawable.ic_miya);
        map.put("name", "miya");
        map.put("desp", "吉他入门教学，学生自备吉他，可线上提供指导，线下交流学习......");
        userdetail_lessoninfoLl.add(map);
    }
}

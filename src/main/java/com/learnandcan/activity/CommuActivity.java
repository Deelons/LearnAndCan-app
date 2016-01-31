package com.learnandcan.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import com.learnandcan.R;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by lsr on 2016/1/30.
 */
public class CommuActivity extends ActionBarActivity {

    private ListView commu_friend_Lv;
    private LinkedList<HashMap<String,Object>> commu_friend_Li;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_commu);

        initWidget();
    }

    void initWidget(){
        commu_friend_Lv=(ListView)findViewById(R.id.commu_friend_Lv);
        commu_friend_Li=new LinkedList<HashMap<String,Object>>();
        setLi();
        SimpleAdapter simpadapter=new SimpleAdapter(this,commu_friend_Li,R.layout.communicate_adp_item,new String[]{"portrait","name","history","time"},new int[]{R.id.commuadp_portrait,R.id.commuadp_name,R.id.commuadp_history,R.id.commuadp_time});
        commu_friend_Lv.setAdapter(simpadapter);
    }

    void setLi(){
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("portrait",R.drawable.ic_miya);
        map.put("name", "mike");
        map.put("history",":)");
        map.put("time","昨天");
    }


}

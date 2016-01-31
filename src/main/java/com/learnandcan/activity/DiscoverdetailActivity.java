package com.learnandcan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import com.learnandcan.R;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by lsr on 2015/11/18.
 */
public class DiscoverdetailActivity extends ActionBarActivity {

    private LinearLayout discover_userinfoLl;//用户基本信息栏linearlayout
    private ListView discover_commentLv;//网友评论listview
    private LinkedList<HashMap<String, Object>> discover_commentLl;
    private TextView discover_returnTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discoverdetail);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        initWidget();
    }

    private void initWidget(){
        //初始化网友评论 listview
        discover_commentLv=(ListView)findViewById(R.id.discover_commentLv);
        discover_commentLl=new LinkedList<HashMap<String, Object>>();
        getlessondetail_commentLl();
        SimpleAdapter simpleAdapter=new SimpleAdapter(this,discover_commentLl,R.layout.lessoncomment_adp_item,new String[]{"portrait","comment"},new int[]{R.id.lessondetail_adp_portraitIv,R.id.lessondetail_adp_commentTv});
        discover_commentLv.setAdapter(simpleAdapter);

        //初始化用户基本信息栏并加监听
        discover_userinfoLl=(LinearLayout)findViewById(R.id.discover_userinfoLl);
        discover_userinfoLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(DiscoverdetailActivity.this,DiscoverUserinfoActivity.class);
                startActivity(intent);
            }
        });

        //初始化返回按钮
        discover_returnTv=(TextView)findViewById(R.id.discover_returnTv);
        discover_returnTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    /*
   * 函数名：getlessondetail_commentLl
   * 说明：用于得到课程概览中每个课程的具体数据（这里先写死）
   *
   * */
    public void getlessondetail_commentLl(){
        HashMap<String,Object> map=new HashMap<String,Object>();

        map.put("portrait",R.drawable.ic_miya);
        map.put("comment","赞~");
        discover_commentLl.add(map);

        map=new HashMap<String,Object>();

        map.put("portrait",R.drawable.ic_miya);
        map.put("comment","赞~");
        discover_commentLl.add(map);

        map=new HashMap<String,Object>();

        map.put("portrait",R.drawable.ic_miya);
        map.put("comment","赞~");
        discover_commentLl.add(map);

    }
}

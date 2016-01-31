package com.learnandcan.fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import com.learnandcan.R;
import com.learnandcan.activity.LessondetailActivity;

import java.util.HashMap;
import java.util.LinkedList;


/**
 * Created by lsr on 2015/11/17.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class LessonFragment extends Fragment implements View.OnClickListener{

    private ListView lesson_overviewLv;//课程概览的listview
    private LinkedList<HashMap<String, Object>> lesson_overviewLl;//与ListView对应的linkedlist



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_lesson, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        initWidget();//控件初始化工作
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

    /*
        * 函数名：initWidget()
        * 说明：用于初始化控件,包括ListView
        * */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void initWidget() {
        //init ListView

        lesson_overviewLv = (ListView) getView().findViewById(R.id.lesson_overviewLv);
        lesson_overviewLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),LessondetailActivity.class);
                startActivity(intent);

                Log.v("lsr", "list clicked!");
            }
        });
        lesson_overviewLl = new LinkedList<HashMap<String, Object>>();
        getLesson_overviewLl();


        SimpleAdapter simpleAdapter = new SimpleAdapter(this.getActivity(), lesson_overviewLl, R.layout.lessonoverview_adp_item, new String[]{"portrait", "name", "desp"}, new int[]{R.id.lessonadp_portraitIv, R.id.lessonadp_nameTv, R.id.lessonadp_despTv});
        lesson_overviewLv.setAdapter(simpleAdapter);
        //init LisView

    }

    /*
    * 函数名：getLesson_overviewLl
    * 说明：用于得到课程概览中每个课程的具体数据（这里先写死）
    *
    * */
    public void getLesson_overviewLl() {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("portrait", R.drawable.ic_miya);
        map.put("name", "miya");
        map.put("desp", "吉他入门教学，学生自备吉他，可线上提供指导，线下交流学习......");
        lesson_overviewLl.add(map);

        map = new HashMap<String, Object>();
        map.put("portrait", R.drawable.ic_miya);
        map.put("name", "miya");
        map.put("desp", "吉他入门教学，学生自备吉他，可线上提供指导，线下交流学习......");
        lesson_overviewLl.add(map);

        map = new HashMap<String, Object>();
        map.put("portrait", R.drawable.ic_miya);
        map.put("name", "miya");
        map.put("desp", "吉他入门教学，学生自备吉他，可线上提供指导，线下交流学习......");
        lesson_overviewLl.add(map);

        map = new HashMap<String, Object>();
        map.put("portrait", R.drawable.ic_miya);
        map.put("name", "miya");
        map.put("desp", "吉他入门教学，学生自备吉他，可线上提供指导，线下交流学习......");
        lesson_overviewLl.add(map);

    }

}

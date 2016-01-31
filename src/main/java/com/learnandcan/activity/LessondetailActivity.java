package com.learnandcan.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import com.learnandcan.R;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by lsr on 2015/11/17.
 */
public class LessondetailActivity extends ActionBarActivity {

    private ListView lessondetail_commentLv;//网友评论listview；
    private LinkedList<HashMap<String,Object>> lessondetail_commentLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessondetail);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        initWidget();
    }
    /*
       * 函数名：initWidget()
       * 说明：用于初始化控件,包括ListView
       * */
    private void initWidget(){
        lessondetail_commentLv=(ListView)findViewById(R.id.lessondetail_commentLv);
        lessondetail_commentLl=new LinkedList<HashMap<String, Object>>();
        getlessondetail_commentLl();
        SimpleAdapter simpleAdapter=new SimpleAdapter(this,lessondetail_commentLl,R.layout.lessoncomment_adp_item,new String[]{"portrait","comment"},new int[]{R.id.lessondetail_adp_portraitIv,R.id.lessondetail_adp_commentTv});
        lessondetail_commentLv.setAdapter(simpleAdapter);

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
        lessondetail_commentLl.add(map);

        map=new HashMap<String,Object>();

        map.put("portrait",R.drawable.ic_miya);
        map.put("comment","赞~");
        lessondetail_commentLl.add(map);

        map=new HashMap<String,Object>();

        map.put("portrait",R.drawable.ic_miya);
        map.put("comment","赞~");
        lessondetail_commentLl.add(map);

    }
}

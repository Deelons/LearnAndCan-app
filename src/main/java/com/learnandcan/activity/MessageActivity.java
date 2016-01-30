package com.learnandcan.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;


import com.learnandcan.R;
import com.learnandcan.adapter.MessageAdapter;
import com.learnandcan.entity.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends Activity {

    private List<Message> messageList = new ArrayList<Message>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_message);
        initMessage();//初始化ListView数据
        MessageAdapter messageAdapter = new MessageAdapter(MessageActivity.this,
                R.layout.message_item,messageList);
        ListView listView = (ListView) findViewById(R.id.message);
        listView.setAdapter(messageAdapter);
    }

    private void initMessage(){
        Message two = new Message(R.drawable.message_two);
        messageList.add(two);
        Message three = new Message(R.drawable.message_three);
        messageList.add(three);
        Message four = new Message(R.drawable.message_four);
        messageList.add(four);
        Message five = new Message(R.drawable.message_five);
        messageList.add(five);
        Message six = new Message(R.drawable.message_six);
        messageList.add(six);
        Message seven = new Message(R.drawable.message_seven);
        messageList.add(seven);
    }
}

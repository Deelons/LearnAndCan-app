package com.learnandcan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;


import com.learnandcan.R;
import com.learnandcan.entity.Message;

import java.util.List;

/**
 * Created by ryc on 2016/1/29.
 */
public class MessageAdapter extends ArrayAdapter<Message> {

    private int resourceId;

    public MessageAdapter(Context context, int resource, List<Message> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = getItem(position);//获取当前项的message实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        ImageView image = (ImageView) view.findViewById(R.id.message_image);
        image.setImageResource(message.getImageId());
        return view;
    }
}

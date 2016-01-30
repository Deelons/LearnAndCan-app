package com.learnandcan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;


import com.learnandcan.R;
import com.learnandcan.entity.Account;

import java.util.List;

/**
 * Created by ryc on 2016/1/30.
 */
public class AccountAdapter extends ArrayAdapter<Account> {

    private int resourceId;

    public AccountAdapter(Context context, int resource, List<Account> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Account account = getItem(position);//获取当前项的message实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        ImageView image = (ImageView) view.findViewById(R.id.account_image);
        image.setImageResource(account.getImageId());
        return view;
    }
}

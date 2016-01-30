package com.learnandcan.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;


import com.learnandcan.R;
import com.learnandcan.adapter.AccountAdapter;
import com.learnandcan.entity.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryc on 2016/1/30.
 */
public class AccountActivity extends Activity{
    private List<Account> accountList = new ArrayList<Account>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_account);
        initAccount();//初始化数据
        //第一个参数指定在哪里加载
        //第二个参数指定子项的布局
        //第三个参数指定
        AccountAdapter accountAdapter = new AccountAdapter(AccountActivity.this,
                R.layout.account_item,accountList);
        ListView listView = (ListView) findViewById(R.id.account);
        listView.setAdapter(accountAdapter);
    }

    private void initAccount(){
        Account one = new Account(R.drawable.account_one);
        accountList.add(one);
        Account two = new Account(R.drawable.account_two);
        accountList.add(two);
        Account three = new Account(R.drawable.account_three);
        accountList.add(three);
    }
}

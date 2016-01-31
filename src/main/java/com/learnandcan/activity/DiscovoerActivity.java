package com.netease.nim.demo.main.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.netease.nim.demo.R;
import com.netease.nim.demo.main.activity.waterfall.WaterFall;

import java.io.File;

/**
 * Created by lsr on 2016/1/30.
 */
public class DiscovoerActivity extends ActionBarActivity{

    private static final String filepathDir = "/sdcard/waterfall";
    private WaterFall waterFall;
    String[] pics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPath();
        waterFall = (WaterFall) findViewById(R.id.waterfall);
        // waterFall.setup(pics);
        waterFall.setup(filepathDir,pics);
    }

    private void initPath() {
        File fileDir = new File(filepathDir);

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            if (!fileDir.exists()) {
                fileDir.mkdirs();

                Log.v("lsr", "no");
            } else if (fileDir.exists()) {
                Log.v("lsr", "yes");
            }

            pics = fileDir.list();
            for (String str : pics) {
                Log.v("lsr", "-->pics" + str);
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

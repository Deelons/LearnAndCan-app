package com.learnandcan.activity.waterfall;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.learnandcan.activity.waterfall.FlowingView;

/**
 * ?????
 * ?��?????????????????????????????????????
 *
 * @author carrey
 */
public class WaterFall extends ScrollView {

    private String textBlewImage="kj hg";

    /**
     * ??????message??handler
     */
    private DelayHandler delayHandler;
    /**
     * ?????????????��?Handler
     */
    private AddItemHandler addItemHandler;

    /**
     * ScrollView????????LinearLayout
     */
    private LinearLayout containerLayout;
    /**
     * ??????��???Layout
     */
    private ArrayList<LinearLayout> colLayoutArray;

    /**
     * ???????????��???????????��?
     */
    private int currentPage;

    /**
     * ?��??????????????��??????bitmap????????��?��?
     */
    private int[] currentTopLineIndex;
    /**
     * ?��?????????��????��??????bitmap??????????��?
     */
    private int[] currentBomLineIndex;
    /**
     * ?��?????????????????��????????��?
     */
    private int[] bomLineIndex;
    /**
     * ?��???��???
     */
    private int[] colHeight;

    private String filepathDir;

    /**
     * ???��??????��??
     */
    private String[] imageFilePaths;

    /**
     * ??????????????
     */
    private int colCount;
    /**
     * ????????��??????????
     */
    private int pageCount;
    /**
     * ???????????
     */
    private int capacity;

    private Random random;

    /**
     * ?��???
     */
    private int colWidth;

    private boolean isFirstPage;

    public WaterFall(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public WaterFall(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaterFall(Context context) {
        super(context);
        init();
    }

    /**
     * ?????????????
     */
    private void init() {
        delayHandler = new DelayHandler(this);
        addItemHandler = new AddItemHandler(this);
        colCount = 2;//??????????4??
        pageCount = 30;//?????��???30??????????
        capacity = 10000;//???????10000???
        random = new Random();
        colWidth = getResources().getDisplayMetrics().widthPixels / colCount;

        colHeight = new int[colCount];
        currentTopLineIndex = new int[colCount];
        currentBomLineIndex = new int[colCount];
        bomLineIndex = new int[colCount];
        colLayoutArray = new ArrayList<LinearLayout>();

    }

    /**
     * ???????? ??????????? ???????
     */
    public void setup(String fileDirparam,String[] imgpaths) {
        containerLayout = new LinearLayout(getContext());
        containerLayout.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        addView(containerLayout, layoutParams);

        for (int i = 0; i < colCount; i++) {
            LinearLayout colLayout = new LinearLayout(getContext());
            LinearLayout.LayoutParams colLayoutParams = new LinearLayout.LayoutParams(
                    colWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
            colLayout.setPadding(2, 2, 2, 2);
            colLayout.setOrientation(LinearLayout.VERTICAL);

            containerLayout.addView(colLayout, colLayoutParams);
            colLayoutArray.add(colLayout);
        }


        filepathDir=fileDirparam;
        imageFilePaths=imgpaths.clone();
        //??????
        addNextPageContent(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                //???????????????DelayHandler??????????????????DelayHandler
                //??????��????????��??????��???????
                delayHandler.sendMessageDelayed(delayHandler.obtainMessage(), 200);
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        //??????????��??????????????bitmap,???OOM
        /*---???????????
         * ??????????��???
		 * ?????????????????????????????????????��????? ???5???????Bitmap,
		 * ?????????��????Bitmap?????????
		 * ????????????????????????????????????????????
		 * ????????
		 * ?????????????????????????????Bitmap,????????��?????????Bitmap
		 * ???????????????????��?????????bitmao,??????????????????bitmap
		 * ---*/
        int viewHeight = getHeight();
        if (t > oldt) {//???????
            if (t > 2 * viewHeight) {
                for (int i = 0; i < colCount; i++) {
                    LinearLayout colLayout = colLayoutArray.get(i);
                    //???????????????bitmap
                    FlowingView topItem = (FlowingView) colLayout.getChildAt(currentTopLineIndex[i]);
                    if (topItem.getFootHeight() < t - 2 * viewHeight) {
                        topItem.recycle();
                        currentTopLineIndex[i]++;
                    }
                    //?????��?????(+1)????????bitmap
                    FlowingView bomItem = (FlowingView) colLayout.getChildAt(Math.min(currentBomLineIndex[i] + 1, bomLineIndex[i]));
                    if (bomItem.getFootHeight() <= t + 3 * viewHeight) {
                        bomItem.reload();
                        currentBomLineIndex[i] = Math.min(currentBomLineIndex[i] + 1, bomLineIndex[i]);
                    }
                }
            }
        } else {//???????
            for (int i = 0; i < colCount; i++) {
                LinearLayout colLayout = colLayoutArray.get(i);
                //?????��?????????bitmap
                FlowingView bomItem = (FlowingView) colLayout.getChildAt(currentBomLineIndex[i]);
                if (bomItem.getFootHeight() > t + 3 * viewHeight) {
                    bomItem.recycle();
                    currentBomLineIndex[i]--;
                }
                //???????????(-1)????????bitmap
                FlowingView topItem = (FlowingView) colLayout.getChildAt(Math.max(currentTopLineIndex[i] - 1, 0));
                if (topItem.getFootHeight() >= t - 2 * viewHeight) {
                    topItem.reload();
                    currentTopLineIndex[i] = Math.max(currentTopLineIndex[i] - 1, 0);
                }
            }
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    /**
     * ???????????????Handler????????????????????message?????
     * ????��???????????????????????????????????????????????????
     * ?????????????????????????????????????????????????????????
     *
     * @author carrey
     */
    private static class DelayHandler extends Handler {
        private WeakReference<WaterFall> waterFallWR;
        private WaterFall waterFall;

        public DelayHandler(WaterFall waterFall) {
            waterFallWR = new WeakReference<WaterFall>(waterFall);
            this.waterFall = waterFallWR.get();
        }

        @Override
        public void handleMessage(Message msg) {
            //?��???????????��??????��???????
            if (waterFall.getScrollY() + waterFall.getHeight() >=
                    waterFall.getMaxColHeight() - 20) {
                //??????????????????????
                waterFall.addNextPageContent(false);
            } else if (waterFall.getScrollY() == 0) {
                //???????????
            } else {
                //???????��?��??
            }
            super.handleMessage(msg);
        }
    }

    /**
     * ?????????????��?Handler
     *
     * @author carrey
     */
    private static class AddItemHandler extends Handler {
        private WeakReference<WaterFall> waterFallWR;
        private WaterFall waterFall;

        public AddItemHandler(WaterFall waterFall) {
            waterFallWR = new WeakReference<WaterFall>(waterFall);
            this.waterFall = waterFallWR.get();
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x00:
                    FlowingView flowingView = (FlowingView) msg.obj;
                    waterFall.addItem(flowingView);
                    break;
            }
            super.handleMessage(msg);
        }
    }

    /**
     * ??????????????
     *
     * @param flowingView
     */
    private void addItem(FlowingView flowingView) {
        int minHeightCol = getMinHeightColIndex();
        colLayoutArray.get(minHeightCol).addView(flowingView);
        colHeight[minHeightCol] += flowingView.getViewHeight();
        flowingView.setFootHeight(colHeight[minHeightCol]);

        if (!isFirstPage) {
            bomLineIndex[minHeightCol]++;
            currentBomLineIndex[minHeightCol]++;
        }
    }

    /**
     * ????????????????
     */
    private void addNextPageContent(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;

        //????????????pageCount?????????
        for (int i = pageCount * currentPage;
             i < pageCount * (currentPage + 1) && i < capacity; i++) {
            new Thread(new PrepareFlowingViewRunnable(i)).start();
        }
        currentPage++;
    }

    /**
     * ???????????FlowingView
     *
     * @author carrey
     */
    private class PrepareFlowingViewRunnable implements Runnable {
        private int id;

        public PrepareFlowingViewRunnable(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            FlowingView flowingView = new FlowingView(getContext(), id, colWidth,textBlewImage);
            String imageFilePath = filepathDir+"/"+imageFilePaths[random.nextInt(imageFilePaths.length)];
            flowingView.setImageFilePath(imageFilePath);
            flowingView.loadImage();
            addItemHandler.sendMessage(addItemHandler.obtainMessage(0x00, flowingView));
        }

    }

    /**
     * ??????????��??????
     *
     * @return
     */
    private int getMaxColHeight() {
        int maxHeight = colHeight[0];
        for (int i = 1; i < colHeight.length; i++) {
            if (colHeight[i] > maxHeight)
                maxHeight = colHeight[i];
        }
        return maxHeight;
    }

    /**
     * ??????????��???��?????
     *
     * @return
     */
    private int getMinHeightColIndex() {
        int index = 0;
        for (int i = 1; i < colHeight.length; i++) {
            if (colHeight[i] < colHeight[index])
                index = i;
        }
        return index;
    }
}

package com.netease.nim.demo.main.activity.waterfall;

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

/**
 * �ٲ���
 * ĳЩ�������˹̶����ã��������չ���ܣ��������޸�
 *
 * @author carrey
 */
public class WaterFall extends ScrollView {

    private String textBlewImage="kj hg";

    /**
     * �ӳٷ���message��handler
     */
    private DelayHandler delayHandler;
    /**
     * ��ӵ�Ԫ���ٲ����е�Handler
     */
    private AddItemHandler addItemHandler;

    /**
     * ScrollViewֱ�Ӱ�����LinearLayout
     */
    private LinearLayout containerLayout;
    /**
     * ������е���Layout
     */
    private ArrayList<LinearLayout> colLayoutArray;

    /**
     * ��ǰ������ҳ�棨�Ѿ������˼��Σ�
     */
    private int currentPage;

    /**
     * �洢ÿһ�������Ϸ����δ������bitmap�ĵ�Ԫ����С�к�
     */
    private int[] currentTopLineIndex;
    /**
     * �洢ÿһ�������·����δ������bitmap�ĵ�Ԫ������к�
     */
    private int[] currentBomLineIndex;
    /**
     * �洢ÿһ�����Ѿ����ص����·��ĵ�Ԫ���к�
     */
    private int[] bomLineIndex;
    /**
     * �洢ÿһ�еĸ߶�
     */
    private int[] colHeight;

    private String filepathDir;

    /**
     * ���е�ͼƬ��Դ·��
     */
    private String[] imageFilePaths;

    /**
     * �ٲ�����ʾ������
     */
    private int colCount;
    /**
     * �ٲ���ÿһ�μ��صĵ�Ԫ����
     */
    private int pageCount;
    /**
     * �ٲ���������
     */
    private int capacity;

    private Random random;

    /**
     * �еĿ��
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
     * ������ʼ������
     */
    private void init() {
        delayHandler = new DelayHandler(this);
        addItemHandler = new AddItemHandler(this);
        colCount = 2;//Ĭ���������4��
        pageCount = 30;//Ĭ��ÿ�μ���30���ٲ�����Ԫ
        capacity = 10000;//Ĭ������10000��ͼ
        random = new Random();
        colWidth = getResources().getDisplayMetrics().widthPixels / colCount;

        colHeight = new int[colCount];
        currentTopLineIndex = new int[colCount];
        currentBomLineIndex = new int[colCount];
        bomLineIndex = new int[colCount];
        colLayoutArray = new ArrayList<LinearLayout>();

    }

    /**
     * ���ⲿ���� ��һ��װ��ҳ�� �������
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
        //��ӵ�һҳ
        addNextPageContent(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                //��ָ�뿪��Ļ��ʱ����DelayHandler��ʱ����һ����Ϣ��Ȼ��DelayHandler
                //��ʱ���жϵ�ǰ�Ļ���λ�ã����в�ͬ�Ĵ���
                delayHandler.sendMessageDelayed(delayHandler.obtainMessage(), 200);
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        //�ڹ��������У����չ����˺�Զ��bitmap,��ֹOOM
        /*---�����㷨˵����
         * ���յ�����˼·�ǣ�
		 * ����ֻ���ֵ�ǰ�ֻ���ʾ����һ���Լ��Ϸ��������·����� һ��5�����ݵ�Bitmap,
		 * ���������Χ�ĵ�ԪBitmap�������ա�
		 * �������ְ�����һ���������֮ǰ���չ��ĵ�Ԫ�����¼��ء�
		 * ��ϸ�Ľ��⣺
		 * ���¹�����ʱ�򣺻��ճ����Ϸ������ĵ�ԪBitmap,���ؽ����·���������Bitmap
		 * ���Ϲ�����ʱ�򣺻��ճ����·������ĵ�Ԫbitmao,���ؽ����Ϸ���������bitmap
		 * ---*/
        int viewHeight = getHeight();
        if (t > oldt) {//���¹���
            if (t > 2 * viewHeight) {
                for (int i = 0; i < colCount; i++) {
                    LinearLayout colLayout = colLayoutArray.get(i);
                    //�����Ϸ���������bitmap
                    FlowingView topItem = (FlowingView) colLayout.getChildAt(currentTopLineIndex[i]);
                    if (topItem.getFootHeight() < t - 2 * viewHeight) {
                        topItem.recycle();
                        currentTopLineIndex[i]++;
                    }
                    //�����·�����(+1)��������bitmap
                    FlowingView bomItem = (FlowingView) colLayout.getChildAt(Math.min(currentBomLineIndex[i] + 1, bomLineIndex[i]));
                    if (bomItem.getFootHeight() <= t + 3 * viewHeight) {
                        bomItem.reload();
                        currentBomLineIndex[i] = Math.min(currentBomLineIndex[i] + 1, bomLineIndex[i]);
                    }
                }
            }
        } else {//���Ϲ���
            for (int i = 0; i < colCount; i++) {
                LinearLayout colLayout = colLayoutArray.get(i);
                //�����·���������bitmap
                FlowingView bomItem = (FlowingView) colLayout.getChildAt(currentBomLineIndex[i]);
                if (bomItem.getFootHeight() > t + 3 * viewHeight) {
                    bomItem.recycle();
                    currentBomLineIndex[i]--;
                }
                //�����Ϸ�����(-1)��������bitmap
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
     * ����֮����Ҫ��һ��Handler����Ϊ��ʹ�������ӳٷ���message�ĺ���
     * �ӳٵ�Ч�����ڣ�����û����ٻ�������ָ�����뿪��Ļ��Ȼ�󻬶����˵ײ���ʱ��
     * ��Ϊ��Ϣ�Ժ��ͣ�����ָ�뿪��Ļ���������ײ������ʱ����ڣ���Ȼ�ܹ�����ͼƬ
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
            //�жϵ�ǰ��������λ�ã����в�ͬ�Ĵ���
            if (waterFall.getScrollY() + waterFall.getHeight() >=
                    waterFall.getMaxColHeight() - 20) {
                //�������ײ��������һҳ����
                waterFall.addNextPageContent(false);
            } else if (waterFall.getScrollY() == 0) {
                //�������˶���
            } else {
                //�������м�λ��
            }
            super.handleMessage(msg);
        }
    }

    /**
     * ��ӵ�Ԫ���ٲ����е�Handler
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
     * ��ӵ�Ԫ���ٲ�����
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
     * �����һ��ҳ�������
     */
    private void addNextPageContent(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;

        //�����һ��ҳ���pageCount����Ԫ����
        for (int i = pageCount * currentPage;
             i < pageCount * (currentPage + 1) && i < capacity; i++) {
            new Thread(new PrepareFlowingViewRunnable(i)).start();
        }
        currentPage++;
    }

    /**
     * �첽����Ҫ��ӵ�FlowingView
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
     * ����������е����߶�
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
     * ���Ŀǰ�߶���С���е�����
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

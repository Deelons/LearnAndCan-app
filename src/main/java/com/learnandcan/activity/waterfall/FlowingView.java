package com.learnandcan.activity.waterfall;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
/**
 * 瀑布流中流动的单元
 * @author carrey
 *
 */
public class FlowingView extends View implements View.OnClickListener, View.OnLongClickListener {

	/** 单元的编号,在整个瀑布流中是唯一的,可以用来标识身份 */
	private int index;

	/** 单元中要显示的图片Bitmap和文字text */
	private Bitmap imageBmp;
	private String textBlewimage;

	private int textSize=30;
	private int paddingleft=5;
	private int paddingright=5;
	private int getPaddingtop=10;

	/** 图像文件的路径 */
	private String imageFilePath;
	/** 单元的宽度 */
	private int width;
	/*图像除去padding之后的宽度*/
	private int alterWidth;
	/** 单元的高度 */
	private int height;
	/*图像的高度*/
	private int alterHeight;
	/** 画笔 */
	private Paint paint;
	/** 图像绘制区域 */
	private Rect rect;

	/** 这个单元的底部到它所在列的顶部之间的距离 */
	private int footHeight;

	public FlowingView(Context context, int index, int width,String textBlewimage) {
		super(context);
		this.index = index;
		this.width = width;
		this.textBlewimage=textBlewimage;
		init();
	}

	/**
	 * 基本初始化工作
	 */
	private void init() {
		setOnClickListener(this);
		setOnLongClickListener(this);
		paint = new Paint();
		paint.setAntiAlias(true);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(width, height);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		//绘制图像
		canvas.drawColor(Color.rgb(242, 242, 242));
		if (imageBmp != null && rect != null) {
			canvas.drawBitmap(imageBmp, null, rect, paint);
			imageBmp.recycle();
		}

		paint.setColor(Color.rgb(255, 255, 255));
		Rect textRect=new Rect(0,alterHeight,alterWidth,alterHeight+textSize);
		canvas.drawRect(textRect,paint);

		paint.setColor(Color.rgb(0,0,0));
		paint.setTextSize(textSize);
		canvas.drawText(textBlewimage,0,alterHeight+5*textSize/6,paint);
		super.onDraw(canvas);
	}

	/**
	 * 被WaterFall调用异步加载图片数据
	 */
	public void loadImage() {
		InputStream inStream = null;
		try {
			inStream =new FileInputStream(imageFilePath);

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 6;
			imageBmp = BitmapFactory.decodeStream(inStream,null,options);
			inStream.close();
			inStream = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (imageBmp != null) {
			int bmpWidth = imageBmp.getWidth();
			int bmpHeight = imageBmp.getHeight();

			alterWidth=width-paddingleft-paddingright;
			height = (int) (bmpHeight * width / bmpWidth)+textSize+getPaddingtop;
			alterHeight=(int) (bmpHeight * alterWidth / bmpWidth);

			rect = new Rect(0, 0,alterWidth, alterHeight);
		}
	}

	/**
	 * 重新加载回收了的Bitmap
	 */
	public void reload() {
		if (imageBmp == null) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					InputStream inStream = null;
					try {
						inStream = new FileInputStream(imageFilePath);
						BitmapFactory.Options options = new BitmapFactory.Options();
						options.inSampleSize = 6;
						imageBmp = BitmapFactory.decodeStream(inStream,null,options);
						inStream.close();
						inStream = null;
						postInvalidate();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}

	/**
	 * 防止OOM进行回收
	 */
	public void recycle() {
		if (imageBmp == null || imageBmp.isRecycled())
			return;
		new Thread(new Runnable() {

			@Override
			public void run() {
				imageBmp.recycle();
				imageBmp = null;
				postInvalidate();
			}
		}).start();
	}

	@Override
	public boolean onLongClick(View v) {
		Toast.makeText(getContext(), "long click : " + index, Toast.LENGTH_SHORT).show();
		return true;
	}

	@Override
	public void onClick(View v) {
		Toast.makeText(getContext(), "click : " + index, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 获取单元的高度
	 * @return
	 */
	public int getViewHeight() {
		return height;
	}
	/**
	 * 设置图片路径
	 * @param imageFilePath
	 */
	public void setImageFilePath(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}

	public Bitmap getImageBmp() {
		return imageBmp;
	}

	public void setImageBmp(Bitmap imageBmp) {
		this.imageBmp = imageBmp;
	}

	public int getFootHeight() {
		return footHeight;
	}

	public void setFootHeight(int footHeight) {
		this.footHeight = footHeight;
	}

	public void setTextSize(int textSize){this.textSize=textSize;}

	public void setPaddingleft(int len){this.paddingleft=len;}
	public void setPaddingright(int len){this.paddingright=len;}
}

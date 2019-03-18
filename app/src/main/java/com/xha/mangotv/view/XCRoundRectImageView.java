package com.xha.mangotv.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.xha.mangotv.R;

public class XCRoundRectImageView extends ImageView {
	private static String TYPE_ROUND="round";

private String type;

private int mCircular_bead=20;

private int mWidth;

private int mRadius ;//半径

private BitmapShader shader;

private Matrix matrix;

private Paint mBitmapPaint;



private RectF mRoundRect,mRect;

	private Paint Roundpaint;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public XCRoundRectImageView(Context context, AttributeSet attrs, int defStyleAttr,
							int defStyleRes) {
	super(context, attrs, defStyleAttr, defStyleRes);
	// TODO Auto-generated constructor stub
	initView(attrs);
}

public XCRoundRectImageView(Context context, AttributeSet attrs, int defStyleAttr) {
	super(context, attrs, defStyleAttr);
	// TODO Auto-generated constructor stub
	initView(attrs);
}

public XCRoundRectImageView(Context context, AttributeSet attrs) {
	super(context, attrs);
	// TODO Auto-generated constructor stub
	initView(attrs);
}

public XCRoundRectImageView(Context context) {
	super(context);
	// TODO Auto-generated constructor stub
	initView(null);
}

private void initView(AttributeSet attrs){
	matrix = new Matrix();
	mBitmapPaint = new Paint();
	mBitmapPaint.setAntiAlias(true);

	Roundpaint = new Paint();
	Roundpaint.setAntiAlias(true);
	Roundpaint.setColor(getResources().getColor(R.color.black_san));
	Roundpaint.setAlpha(50);
}

@Override
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	// TODO Auto-generated method stub
	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	
			setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
	
	
	
}


@Override
protected void onDraw(Canvas canvas) {
	// TODO Auto-generated method stub
	
	if (getDrawable() == null)  
    {  
        return;  
    }  
	
	setUpShader();
	canvas.drawRoundRect(mRoundRect, mCircular_bead, mCircular_bead, mBitmapPaint);

//	canvas.drawRoundRect(mRect,mCircular_bead,mCircular_bead,Roundpaint);
	
//	super.onDraw(canvas);
}


 @Override  
    protected void onSizeChanged(int w, int h, int oldw, int oldh)  
    {  
        super.onSizeChanged(w, h, oldw, oldh);  
        // 圆角图片的范围  
        mRoundRect = new RectF(0, 0, getWidth(), getHeight());
		mRect = new RectF(0,getHeight()-20,getWidth(),getHeight());
    }  

/** 
 * 初始化BitmapShader 
 */  
private void setUpShader() {
	
	Drawable drawable = getDrawable();
	
	if(drawable==null){
		return ;
	}
	
	Bitmap bitmap = drawableToBitamp(drawable);//获取bitmap
	
	shader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
	
	float scale = 1.0f;
	
	
	/*
	 * 
	 * 圆形时：取bitmap的宽或者高的小值作为基准，如果采用大值，缩放后肯定不能填满我们的圆形区域。然后，view的mWidth/bSize ; 得到的就是scale。

	        圆角时：因为设计到宽/高比例，我们分别getWidth() * 1.0f / bmp.getWidth() 和 getHeight() * 1.0f / bmp.getHeight() ；最终取大值，因为我们要让最终缩放完成的图片一定要大于我们的view的区域，有点类似centerCrop；

                   比如：view的宽高为10*20；图片的宽高为5*100 ； 最终我们应该按照宽的比例放大，而不是按照高的比例缩小；因为我们需要让缩放后的图片，自定大于我们的view宽高，并保证原图比例。

	        有了scale，就可以设置给我们的matrix；
	 * 
	 */
		
//	scale= Math.max(getWidth()*1.0f/bitmap.getWidth(), getHeight()*1.0f/bitmap.getHeight());
	// shader的变换矩阵，我们这里主要用于放大或者缩小 
	matrix.setScale(getWidth()*1.0f/bitmap.getWidth(), getHeight()*1.0f/bitmap.getHeight());
	// 设置变换矩阵
	shader.setLocalMatrix(matrix);
	// 设置shader 
	mBitmapPaint.setShader(shader);
	
	
}


private Bitmap drawableToBitamp(Drawable drawable){
	
	
//	if(drawable instanceof BitmapDrawable){
//		
//		BitmapDrawable bitmapDrawable  = (BitmapDrawable) drawable;
//		return bitmapDrawable.getBitmap();
//	}
	
	int w = drawable.getIntrinsicWidth();
	int h = drawable.getIntrinsicHeight();
	Bitmap bitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
	Canvas canvas = new Canvas(bitmap);
	drawable.setBounds(0, 0, w, h);
	drawable.draw(canvas);
	return bitmap;
}}

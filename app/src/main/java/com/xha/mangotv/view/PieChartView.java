package com.xha.mangotv.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.github.lzyzsd.randomcolor.RandomColor;
import com.xha.mangotv.R;
import com.xha.mangotv.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 饼状统计图，带有标注线，都可以自行设定其多种参数选项
 * <p/>
 * Created By: Seal.W
 */
public class PieChartView extends View {

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;
    private int select;
    private boolean isClick;
    private static int[] colors={ 0xFFCA3334,0xFF2D4553,0xFF58A0A7,0xFFDA8168,0xFF8AC7AF,0xFF6E9F85,0xFFCF8532,0xFFBFA29B,0xFF6E7074,0xFF52656F,0xFFC3CCD3};
    private TouchClickListener touchClickListener;
    /**
     * 饼图半径
     */
    private float pieChartCircleRadius = 100;

    private float textBottom;
    /**
     * 记录文字大小
     */
    private float mTextSize = 14;

    /**
     * 饼图所占矩形区域（不包括文字）
     */
    private RectF pieChartCircleRectF = new RectF();

    /**
     * 饼状图信息列表
     */
    private List<PieceDataHolder> pieceDataHolders = new ArrayList<>();

    private float distance;
    /**
     * 标记线长度
     */
    private float markerLineLength = 10f;

    private Context context;

    public PieChartView(Context context) {
        super(context);
        this.context=context;
        init(null, 0);
    }

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init(attrs, 0);
    }

    public PieChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.PieChartView, defStyle, 0);

        pieChartCircleRadius = a.getDimension(
                R.styleable.PieChartView_circleRadius,
                pieChartCircleRadius);
        isClick=a.getBoolean(R.styleable.PieChartView_isClick,false);
        mTextSize = a.getDimension(R.styleable.PieChartView_textSize, mTextSize)/getResources().getDisplayMetrics().density;

        a.recycle();

        // Set up a default_tp TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        distance = DensityUtil.dp2px(context,16);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {

        mTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mTextSize, getContext().getResources().getDisplayMetrics()));

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.descent - fontMetrics.ascent;
        textBottom = fontMetrics.bottom;
    }

    /**
     * 设置饼状图的半径
     *
     * @param pieChartCircleRadius 饼状图的半径（px）
     */
    public void setPieChartCircleRadius(int pieChartCircleRadius) {

        this.pieChartCircleRadius = pieChartCircleRadius;

        invalidate();
    }

    /**
     * 设置标记线的长度
     *
     * @param markerLineLength 标记线的长度（px）
     */
    public void setMarkerLineLength(int markerLineLength) {
        this.markerLineLength = markerLineLength;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPieChartCircleRectF();

        drawAllSectors(canvas);
    }
    private int count;

    private void drawAllSectors(Canvas canvas) {
        float sum = 0f;
        for (PieceDataHolder pieceDataHolder : pieceDataHolders) {
            sum += pieceDataHolder.value;
        }

        float sum2 = 0f;
        int i=0;
        count=pieceDataHolders.size();
        for (PieceDataHolder pieceDataHolder : pieceDataHolders) {
            float startAngel = sum2 / sum * 360;
            sum2 += pieceDataHolder.value;
            double sweepAngel = pieceDataHolder.value / sum * 360;

            drawSector(canvas, pieceDataHolder.color, startAngel, sweepAngel);
            pieceDataHolder.x = startAngel;
            pieceDataHolder.y = startAngel + sweepAngel;
            drawMarkerLineAndText(canvas, pieceDataHolder.color, startAngel + sweepAngel / 2, pieceDataHolder.marker,i,sweepAngel);
            i++;
        }
    }

    private void initPieChartCircleRectF() {

        pieChartCircleRectF.left = getWidth()/2  - pieChartCircleRadius;
        pieChartCircleRectF.top =  getHeight()/2  - pieChartCircleRadius;
        pieChartCircleRectF.right = pieChartCircleRectF.left + pieChartCircleRadius *2;
        pieChartCircleRectF.bottom = pieChartCircleRectF.top + pieChartCircleRadius *2;
        Log.e("TAG","左边的数值===="+(getWidth()/2  - pieChartCircleRadius)+",右边的数值=="+(pieChartCircleRectF.left + pieChartCircleRadius * 2));
        Log.e("TAG","中心的数值===="+getWidth()/2+",半径的数值=="+pieChartCircleRadius);


    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.(sp)
     */
    public float getTextSize() {
        return mTextSize;
    }

    /**
     * Sets the view's text dimension attribute value. In the PieChartView view, this dimension
     * is the font size.
     *
     * @param textSize The text dimension attribute value to use.(sp)
     */
    public void setTextSize(float textSize) {
        mTextSize = textSize;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * 设置饼状图要显示的数据
     *
     * @param data 列表数据
     */
    public void setData(List<PieceDataHolder> data) {

        if (data != null) {
            pieceDataHolders.clear();
            pieceDataHolders.addAll(data);
        }
        requestLayout();
        invalidate();
    }

    /**
     * 绘制扇形
     *
     * @param canvas     画布
     * @param color      要绘制扇形的颜色
     * @param startAngle 起始角度
     * @param sweepAngle 结束角度
     */
    protected void drawSector(Canvas canvas, int color, double startAngle, double sweepAngle) {

        Paint paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);

        canvas.drawArc(pieChartCircleRectF, (float)startAngle, (float)sweepAngle, true, paint);

    }

    /**
     * 绘制标注线和标记文字
     *
     * @param canvas      画布
     * @param color       标记的颜色
     * @param rotateAngel 标记线和水平相差旋转的角度
     */
    protected void drawMarkerLineAndText(Canvas canvas, int color, double rotateAngel, String text,int pos,double sweepAngel) {
        Paint paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);

        Path path= new Path();
        path.close();
        path.moveTo(getWidth() / 2, getHeight() / 2);
//        final float x = (float) (getWidth() / 2 + (markerLineLength + pieChartCircleRadius) * Math.cos(Math.toRadians(rotateAngel)));
//        final float y = (float) (getHeight() / 2 + (markerLineLength + pieChartCircleRadius) * Math.sin(Math.toRadians(rotateAngel)));



        final float x = (float) (getWidth() / 2 + pieChartCircleRadius * Math.cos(Math.toRadians(rotateAngel)));
        final float y = (float) (getHeight() / 2 + pieChartCircleRadius * Math.sin(Math.toRadians(rotateAngel)));
        path.lineTo(x, y);
        float x1 = (float) (x + markerLineLength*(pos+1) * Math.cos(Math.toRadians(360/count*pos)));
        float y1 = (float) (y + markerLineLength*(pos+1) * Math.sin(Math.toRadians(360/count*pos)));

//        float lineStartX   =   pieChartCircleRectF.right-pieChartCircleRectF.left/2   +   (pieChartCircleRadius- distance)   *  (float) Math.cos(rotateAngel *   3.14   /180 );
//        float lineStartY   =   (barHeight-lengedHeight)/2   +   (radius- distance)  *   (float) Math.sin(lineAngle   *   3.14/180);
//        float x1,y1;
//        if(Math.abs(sweepAngel) <= 20) { //当偏转角度小于30°时，增加指示线的长度，避免描述文字重叠
//            float num = (count - pos) % 3;
//            x1 = pieChartCircleRectF.right-pieChartCircleRectF.left / 2 + (pieChartCircleRadius + distance * num * 1f) * (float) Math.cos(rotateAngel * 3.14 / 180);
//            y1 = pieChartCircleRectF.bottom-pieChartCircleRectF.top / 2 + (pieChartCircleRadius + distance * num * 1f) * (float) Math.sin(rotateAngel * 3.14 / 180);
//        }else{
//            x1 = pieChartCircleRectF.right-pieChartCircleRectF.left/2 + (pieChartCircleRadius+ distance) * (float) Math.cos(rotateAngel * 3.14 /180 );
//            y1 = pieChartCircleRectF.bottom-pieChartCircleRectF.top/2 + (pieChartCircleRadius+ distance) * (float) Math.sin(rotateAngel * 3.14 /180);
//        }

        path.lineTo(x1, y1);

        float landLineX;
        if (270f > rotateAngel && rotateAngel > 90f) {
            landLineX = x1 - 30;
        } else {
            landLineX = x1 + 30;
        }
        path.lineTo(landLineX, y1);
        canvas.drawPath(path, paint);
        mTextPaint.setColor(color);
        if (270f > rotateAngel && rotateAngel > 90f) {
            float textWidth = mTextPaint.measureText(text);
            String[] split = text.split("\n");
            canvas.drawText(split[0], landLineX - textWidth/2, y1 + mTextHeight / 2 - textBottom, mTextPaint);
            canvas.drawText(split[1], landLineX - textWidth/2, y1 + mTextHeight / 2 - textBottom+mTextHeight, mTextPaint);
        } else {
            String[] split = text.split("\n");
            canvas.drawText(split[0], landLineX, y1 + mTextHeight / 2 - textBottom, mTextPaint);
            canvas.drawText(split[1], landLineX, y1 + mTextHeight / 2 - textBottom+mTextHeight, mTextPaint);
        }
    }

    /**
     * 饼状图每块的信息持有者
     */
    public static final class PieceDataHolder {

        /**
         * 每块扇形的值的大小
         */
        private double value;

        /**
         * 扇形的颜色
         */
        private int color;

        /**
         * 每块的标记
         */
        private String marker;

        private String name;

        private  double x,y;
        private boolean isqb;
        private String channelGroupCode;
        public PieceDataHolder(double value, int color, String marker,String channelGroupCode,String name,boolean isqb) {
            this.value = value;
            this.channelGroupCode=channelGroupCode;
            this.name=name;
            this.isqb=isqb;
            this.color=colors[color];

//             随机颜色值
//            RandomColor randomColor = new RandomColor();
//            this.color = randomColor.randomColor();

            this.marker = marker;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (isClick){

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    float x = event.getX();
                    float y = event.getY();
                    int radius = 0;
                    // 第一象限
                    if (x >= getMeasuredWidth() / 2 && y >= getMeasuredHeight() / 2) {
                        radius = (int) (Math.atan((y - getMeasuredHeight() / 2) * 1.0f
                                / (x - getMeasuredWidth() / 2)) * 180 / Math.PI);
                    }

                    // 第二象限
                    if (x <= getMeasuredWidth() / 2 && y >= getMeasuredHeight() / 2) {
                        radius = (int) (Math.atan((getMeasuredWidth() / 2 - x)
                                / (y - getMeasuredHeight() / 2))
                                * 180 / Math.PI + 90);
                    }
                    // 第三象限
                    if (x <= getMeasuredWidth() / 2 && y <= getMeasuredHeight() / 2) {
                        radius = (int) (Math.atan((getMeasuredHeight() / 2 - y)
                                / (getMeasuredWidth() / 2 - x))
                                * 180 / Math.PI + 180);
                    }

                    // 第四象限
                    if (x >= getMeasuredWidth() / 2 && y <= getMeasuredHeight() / 2) {
                        radius = (int) (Math.atan((x - getMeasuredWidth() / 2)
                                / (getMeasuredHeight() / 2 - y))
                                * 180 / Math.PI + 270);
                    }

                    for (int i = 0; i < pieceDataHolders.size(); i++) {
                        PieceDataHolder point = pieceDataHolders.get(i);
                        if (point.x <= radius && point.y >= radius) {
                            select = i;
                            if (point.isqb){
                                touchClickListener.OnTouchClickListener(point.channelGroupCode,point.name,"0%");
                            }else{
                                touchClickListener.OnTouchClickListener(point.channelGroupCode,point.name,point.value+"");
                            }
//                            Toast.makeText(getContext(), "点击了" + point.marker,
//                                    Toast.LENGTH_SHORT)
//                                    .show();
                            invalidate();
                            return true;
                        }
                    }

                    break;
            }
            return true;

        }else{
            return super.onTouchEvent(event);
        }

    }

    public interface TouchClickListener{

        void OnTouchClickListener(String channelGroupCode,String name,String bf);

    }

    public void setOnTouchClickListener(TouchClickListener touchClickListener){
        this.touchClickListener=touchClickListener;
    }
}

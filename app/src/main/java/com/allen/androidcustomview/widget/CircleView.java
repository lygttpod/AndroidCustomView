package com.allen.androidcustomview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by allen on 2016/12/8.
 */

public class CircleView extends View {

    private Path mPath;
    private Canvas mCanvas;
    private Paint mPaint;

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();

    }

    private void init() {
        mPath = new Path();
        mCanvas = new Canvas();
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        mPath.moveTo(100,500);
//        mPath.quadTo(300,100,600,500);
//
////        mCanvas.drawPath(mPath,mPaint);
//        mCanvas.drawCircle(200,200,50,mPaint);


        drawLine();
        mPaint.setColor(Color.RED); //画笔颜色
        mPaint.setStrokeWidth(10); //画笔宽度
        mPaint.setStyle(Paint.Style.STROKE);
        mPath.reset();
        //起点
        mPath.moveTo(300, 150);
        //mPath
        mPath.cubicTo(500, 450, 700, 450, 900, 150);
        //画path
        canvas.drawPath(mPath, mPaint);
        //画控制点
        canvas.drawPoint(500, 450, mPaint);
        canvas.drawPoint(700, 450, mPaint);

        //画线
//        canvas.drawLine(startPoint.x, startPoint.y, assistPoint1.x, assistPoint1.y, mPaint);
//        canvas.drawLine(endPoint.x, endPoint.y, assistPoint2.x, assistPoint2.y, mPaint);
//        canvas.drawLine(assistPoint1.x, assistPoint1.y, assistPoint2.x, assistPoint2.y, mPaint);
    }

    private void drawLine() {

    }
}

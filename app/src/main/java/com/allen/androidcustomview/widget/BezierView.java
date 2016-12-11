package com.allen.androidcustomview.widget;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.allen.androidcustomview.R;
import com.allen.androidcustomview.bean.CircleBean;
import com.allen.androidcustomview.bean.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by allen on 2016/12/9.
 */

public class BezierView extends View {


    private static final String TAG = "allen";
    private Paint paint;
    private List<CircleBean> mCircleBeen = new ArrayList<>();
    private ValueAnimator animator;

    public BezierView(Context context) {
        super(context);
        init();
    }

    public BezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        initPaint();
        createInAnimation();

    }

    private void initPaint() {
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.circle));
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(60);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCircleBeen != null) {
            for (CircleBean circleBean : getCircleBeen()) {
                paint.setAlpha(circleBean.getAlpha());
                canvas.drawCircle(circleBean.getP().getX(), circleBean.getP().getY(), circleBean.getRadius(), paint);
            }
        }
//        draw2Bezier(canvas);
//        draw2Bezier2(canvas);
//        drawHxbText(canvas);

    }

    private void drawHxbText(Canvas canvas) {

        Path path = new Path();
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.circle));
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        for (CircleBean circleBean : getCircleBeen()) {
            if (circleBean.getP2().getX() == circleBean.getP().getX()) {
                drawText(canvas);
            }
        }
    }


    private void draw2Bezier(Canvas canvas) {

        Path path = new Path();
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.circle));
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        for (CircleBean circleBean : getCircleBeen()) {
            path.moveTo(circleBean.getP0().getX(), circleBean.getP0().getY());
            path.quadTo(circleBean.getP1().getX(), circleBean.getP1().getY(), circleBean.getP2().getX(), circleBean.getP2().getY());
            canvas.drawPath(path, paint);

            if (circleBean.getP2().getX() == circleBean.getP().getX()) {
                drawText(canvas);
            }
        }
    }

    private void draw2Bezier2(Canvas canvas) {

        Path path = new Path();
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.circle));
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        for (CircleBean circleBean : getCircleBeen()) {
            path.moveTo(circleBean.getP2().getX(), circleBean.getP2().getY());
            path.quadTo(circleBean.getP3().getX(), circleBean.getP3().getY(), circleBean.getP4().getX(), circleBean.getP4().getY());
            canvas.drawPath(path, paint);

            if (circleBean.getP2().getX() == circleBean.getP().getX()) {
                drawText(canvas);
            }
        }
    }

    private void drawText(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(2);
        paint.setTextSize(50);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawText("红小宝", 470, 650, paint);
    }

    public void createInAnimation() {

        animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float t = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                for (int i = 0; i < mCircleBeen.size(); i++) {
                    CircleBean c = mCircleBeen.get(i);
                    float x = (float) (Math.pow((1 - t), 2) * c.getP0().getX() + 2 * t * (1 - t) * c.getP1().getX() + Math.pow(t, 2) * c.getP2().getX());
                    float y = (float) (Math.pow((1 - t), 2) * c.getP0().getY() + 2 * t * (1 - t) * c.getP1().getY() + Math.pow(t, 2) * c.getP2().getY());

                    if (1f == t) {
                        c.setAlpha(100);
                        animator.cancel();
                        createFloatAnimation();
//                        createOutAnimation();
                    } else {
                        c.setAlpha(60);
                    }
                    mCircleBeen.get(i).setP(new Point(x, y));

                }
                invalidate();
            }
        });

        animator.start();
    }

    private void createFloatAnimation() {


        final ValueAnimator floatAnimator = ValueAnimator.ofFloat(0f, 1f);
        floatAnimator.setDuration(3000);
        floatAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float t = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                for (int i = 0; i < mCircleBeen.size(); i++) {
                    CircleBean c = mCircleBeen.get(i);
                    float x = (float) (Math.pow((1 - t), 2) * c.getP2().getX() - 50 + 2 * t * (1 - t) * (c.getP2().getX() + 50) + Math.pow(t, 2) * (c.getP2().getX() + 100));
                    float y = (float) (Math.pow((1 - t), 2) * c.getP2().getY() + 2 * t * (1 - t) * (c.getP2().getY() - 200) + Math.pow(t, 2) * c.getP2().getY());

                    if (1f == t) {
                        c.setAlpha((int) (100 * t));
                        floatAnimator.cancel();
                        createFloatAnimation2();
                    } else {
                        c.setAlpha(60);
                    }
                    mCircleBeen.get(i).setP(new Point(x, y));

                }
                invalidate();

            }
        });


        floatAnimator.start();
    }

    private void createFloatAnimation2() {


        final ValueAnimator floatAnimator = ValueAnimator.ofFloat(0f, 1f);
        floatAnimator.setDuration(3000);
        floatAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float t = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                for (int i = 0; i < mCircleBeen.size(); i++) {
                    CircleBean c = mCircleBeen.get(i);
                    float x = (float) (Math.pow((1 - t), 2) * (c.getP2().getX() + 100) + 2 * t * (1 - t) * (c.getP2().getX() + 50) + Math.pow(t, 2) * c.getP2().getX());
                    float y = (float) (Math.pow((1 - t), 2) * c.getP2().getY() + 2 * t * (1 - t) * (c.getP2().getY() + 200) + Math.pow(t, 2) * c.getP2().getY());

                    if (1f == t) {
                        c.setAlpha((int) (100 * t));
                        floatAnimator.cancel();
                        createOutAnimation();
                    } else {
                        c.setAlpha(60);
                    }
                    mCircleBeen.get(i).setP(new Point(x, y));

                }
                invalidate();

            }
        });


        floatAnimator.start();
    }


    private void createOutAnimation() {
        final ValueAnimator outAnimator = ValueAnimator.ofFloat(0f, 1f);
        outAnimator.setDuration(1000);
        outAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float t = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                for (int i = 0; i < mCircleBeen.size(); i++) {
                    CircleBean c = mCircleBeen.get(i);
//                    float x = (float) (Math.pow((1 - t), 2) * c.getP2().getX() + 2 * t * (1 - t) * c.getP3().getX() + Math.pow(t, 2) * c.getP4().getX());
//                    float y = (float) (Math.pow((1 - t), 2) * c.getP2().getY() + 2 * t * (1 - t) * c.getP3().getY() + Math.pow(t, 2) * c.getP4().getY());
                    float x = (float) (Math.pow((1 - t), 2) * c.getP2().getX() + 2 * t * (1 - t) * c.getP3().getX() + Math.pow(t, 2) * c.getP4().getX());
                    float y = (float) (Math.pow((1 - t), 2) * c.getP2().getY() + 2 * t * (1 - t) * c.getP3().getY() + Math.pow(t, 2) * c.getP4().getY());

                    if (1f == t) {
                        c.setAlpha(0);
                        outAnimator.cancel();
                    } else {
                        c.setAlpha(60);
                    }
                    mCircleBeen.get(i).setP(new Point(x, y));

                }
                invalidate();
            }
        });
        outAnimator.start();
    }

    public void startAnimation() {
        animator.start();
    }

    public List<CircleBean> getCircleBeen() {
        return mCircleBeen;
    }

    public void setCircleBeen(List<CircleBean> circleBeen) {
        this.mCircleBeen = circleBeen;
    }
}

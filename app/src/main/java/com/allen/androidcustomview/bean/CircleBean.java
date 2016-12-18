package com.allen.androidcustomview.bean;

import android.graphics.PointF;

/**
 * Created by allen on 2016/12/9.
 */

public class CircleBean {

    /**
     * 真实轨迹坐标点
     */
    PointF p;
    /**
     * 起点坐标
     */
    PointF p0;
    /**
     * 进入动画的控制点坐标
     */
    PointF p1;
    /**
     * 到达中心点做坐标
     */
    PointF p2;
    /**
     * 飞出动画控制点的坐标
     */
    PointF p3;
    /**
     * 结束位置坐标
     */
    PointF p4;

    /**
     * 小球半径
     */
    private float radius;
    /**
     * 圆圈的透明度
     */
    private int alpha;


    public CircleBean(PointF p0, PointF p1, PointF p2, PointF p3, PointF p4, float radius, int alpha) {
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.radius = radius;
        this.alpha = alpha;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public PointF getP4() {
        return p4;
    }

    public void setP4(PointF p4) {
        this.p4 = p4;
    }

    public PointF getP3() {
        return p3;
    }

    public void setP3(PointF p3) {
        this.p3 = p3;
    }

    public PointF getP2() {
        return p2;
    }

    public void setP2(PointF p2) {
        this.p2 = p2;
    }

    public PointF getP1() {
        return p1;
    }

    public void setP1(PointF p1) {
        this.p1 = p1;
    }

    public PointF getP0() {
        return p0;
    }

    public void setP0(PointF p0) {
        this.p0 = p0;
    }

    public PointF getP() {
        return p;
    }

    public void setP(PointF p) {
        this.p = p;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}

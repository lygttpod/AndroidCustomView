package com.allen.androidcustomview.bean;

/**
 * Created by allen on 2016/12/9.
 */

public class CircleBean {

    /**
     * 真实轨迹坐标点
     */
    Point p;
    /**
     * 起点坐标
     */
    Point p0;
    /**
     * 进入动画的控制点坐标
     */
    Point p1;
    /**
     * 到达中心点做坐标
     */
    Point p2;
    /**
     * 飞出动画控制点的坐标
     */
    Point p3;
    /**
     * 结束位置坐标
     */
    Point p4;

    /**
     * 小球半径
     */
    private float radius;
    /**
     * 圆圈的透明度
     */
    private int alpha;

    public CircleBean(Point p0, Point p1, Point p2, float radius, int alpha) {
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
        this.radius = radius;
        this.alpha = alpha;
    }

    public CircleBean(Point p0, Point p1, Point p2, Point p4, float radius, int alpha) {
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
        this.p4 = p4;
        this.radius = radius;
        this.alpha = alpha;
    }

    public CircleBean(Point p0, Point p1, Point p2, Point p3, Point p4, float radius, int alpha) {
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.radius = radius;
        this.alpha = alpha;
    }

    public CircleBean() {
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public Point getP() {
        return p;
    }

    public void setP(Point p) {
        this.p = p;
    }

    public Point getP0() {
        return p0;
    }

    public void setP0(Point p0) {
        this.p0 = p0;
    }

    public Point getP1() {
        return p1;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }

    public Point getP2() {
        return p2;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
    }

    public Point getP3() {
        return p3;
    }

    public void setP3(Point p3) {
        this.p3 = p3;
    }

    public Point getP4() {
        return p4;
    }

    public void setP4(Point p4) {
        this.p4 = p4;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}

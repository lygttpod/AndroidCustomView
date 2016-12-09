package com.allen.androidcustomview.bean;

/**
 * Created by allen on 2016/12/9.
 */

public class CircleBean {

    Point p;
    Point p0;
    Point p1;
    Point p2;

    /**
     * 小球半径
     */
    private int radius;
    /**
     * 圆圈的透明度
     */
    private int alpha;

    public CircleBean(Point p0, Point p1, Point p2, int radius, int alpha) {
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
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

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}

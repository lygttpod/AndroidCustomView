package com.allen.androidcustomview.bean;

/**
 * Created by allen on 2016/12/9.
 *
 */

public class Point {


    /**
     * 小球x坐标
     */
    private float x;
    /**
     * 小球y坐标
     */
    private float y;

    public Point() {
    }

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}

package com.allen.androidcustomview.utils;

import android.graphics.PointF;

/**
 * Created by allen on 2016/12/15.
 * <p>
 * 贝塞尔曲线计算工具类
 */

public class BezierUtil {


    /**
     * 二阶贝塞尔曲线
     * B(t) = Po*(1-t)^2 + 2*p1*t*(1-t)+t^2*p2
     *
     * @param t  曲线长度比例
     * @param p0 起始点
     * @param p1 控制点
     * @param p2 终止点
     * @return t对应的点
     */
    public static PointF CalculateBezierPointForQuadratic(float t, PointF p0, PointF p1, PointF p2) {
        PointF point = new PointF();
        float temp = 1 - t;
//      point.x = temp * temp * p0.x + 2 * t * temp * p1.x + t * t * p2.x;
//      point.y = temp * temp * p0.y + 2 * t * temp * p1.y + t * t * p2.y;
        point.x = (float) (Math.pow(temp, 2) * p0.x + 2 * t * temp * p1.x + Math.pow(t, 2) * p2.x);
        point.y = (float) (Math.pow(temp, 2) * p0.y + 2 * t * temp * p1.y + Math.pow(t, 2) * p2.y);
        return point;
    }

    /**
     * 三
     * 阶贝塞尔曲线
     * B(t) = Po*(1-t)^3 + 3*p1*t*(1-t)^2+3*p2*t^2*(1-t)+p3*t^3,
     *
     * @param t  曲线长度比例
     * @param p0 起始点
     * @param p1 控制点1
     * @param p2 控制点2
     * @param p3 终止点
     * @return t对应的点
     */
    public static PointF CalculateBezierPointForCubic(float t, PointF p0, PointF p1, PointF p2, PointF p3) {
        PointF point = new PointF();
        float temp = 1 - t;
//      point.x = p0.x * temp * temp * temp + 3 * p1.x * t * temp * temp + 3 * p2.x * t * t * temp + p3.x * t * t * t;
//      point.y = p0.y * temp * temp * temp + 3 * p1.y * t * temp * temp + 3 * p2.y * t * t * temp + p3.y * t * t * t;
        point.x = (float) (p0.x * Math.pow(temp, 3) + 3 * p1.x * t * Math.pow(temp, 2) + 3 * p2.x * Math.pow(t, 2) * temp + p3.x * Math.pow(t, 3));
        point.y = (float) (p0.y * Math.pow(temp, 3) + 3 * p1.y * t * Math.pow(temp, 2) + 3 * p2.y * Math.pow(t, 2) * temp + p3.y * Math.pow(t, 3));
        return point;
    }

}

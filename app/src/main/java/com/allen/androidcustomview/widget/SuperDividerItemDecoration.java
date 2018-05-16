package com.allen.androidcustomview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;


/**
 * <pre>
 *      @author : Allen
 *      e-mail  : lygttpod@163.com
 *      date    : 2018/04/15
 *      desc    : 万能分割线
 *      version : 1.0
 * </pre>
 */
public class SuperDividerItemDecoration extends RecyclerView.ItemDecoration {


    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;

    private static Context context;


    /**
     * 默认分割线的颜色
     */
    private int dividerDefaultColor = 0xFFE1E5E8;

    /**
     * 分割线的颜色
     */
    private int dividerColor;
    /**
     * 分割线的宽度
     */
    private int dividerWidth;
    /**
     * 分割线距离左右两边的距离
     */
    private int dividerPadding;
    /**
     * 分割线距离左边的距离
     */
    private int dividerPaddingLeft;
    /**
     * 分割线距离右边的距离
     */
    private int dividerPaddingRight;

    /**
     * 分割线距离上边的距离
     */
    private int dividerPaddingTop;
    /**
     * 分割线距离下边的距离
     */
    private int dividerPaddingBottom;
    /**
     * 是否显示列表最后一条分割线
     */
    private boolean dividerIsShowLastDivide;


    /**
     * 分割线item的画笔
     */
    private Paint dividerPaint;

    /**
     * 分割线开始的位置（解决recyclerView添加头布局的时候，要从header下边的position位置算起）
     */
    private int dividerFromPosition = 0;


    /**
     * recyclerView布局方式（水平或者垂直）
     */
    private int orientation;


    public SuperDividerItemDecoration(Builder builder) {

        context = builder.context;

        dividerColor = builder.dividerColor == 0 ? dividerDefaultColor : builder.dividerColor;
        dividerPadding = dp2px(builder.dividerPadding);
        dividerPaddingLeft = dp2px(builder.dividerPaddingLeft);
        dividerPaddingRight = dp2px(builder.dividerPaddingRight);
        dividerPaddingTop = dp2px(builder.dividerPaddingTop);
        dividerPaddingBottom = dp2px(builder.dividerPaddingBottom);
        dividerWidth = builder.dividerWidth == 0 ? dp2px(0.5f) : dp2px(builder.dividerWidth);
        dividerFromPosition = builder.dividerFromPosition;
        dividerIsShowLastDivide = builder.dividerIsShowLastDivide;
        orientation = builder.orientation;
        dividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dividerPaint.setColor(dividerColor);

        if (dividerPadding != 0) {
            dividerPaddingLeft = dividerPaddingRight = dividerPadding;
            dividerPaddingTop = dividerPaddingBottom = dividerPadding;
        }
    }

    public static class Builder {

        private Context context;
        private int dividerColor;
        private int dividerWidth;
        private int dividerPadding;
        private int dividerPaddingLeft;
        private int dividerPaddingRight;
        private int dividerPaddingTop;
        private int dividerPaddingBottom;
        private int dividerFromPosition;
        private boolean dividerIsShowLastDivide;
        private int orientation = VERTICAL;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setDividerColor(int dividerColor) {
            this.dividerColor = dividerColor;
            return this;
        }

        public Builder setDividerWidth(int dividerWidth) {
            this.dividerWidth = dividerWidth;
            return this;
        }

        public Builder setDividerPadding(int dividerPadding) {
            this.dividerPadding = dividerPadding;
            return this;
        }

        public Builder setDividerPaddingLeft(int dividerPaddingLeft) {
            this.dividerPaddingLeft = dividerPaddingLeft;
            return this;
        }

        public Builder setDividerPaddingRight(int dividerPaddingRight) {
            this.dividerPaddingRight = dividerPaddingRight;
            return this;
        }

        public Builder setDividerPaddingTop(int dividerPaddingTop) {
            this.dividerPaddingTop = dividerPaddingTop;
            return this;
        }

        public Builder setDividerPaddingBottom(int dividerPaddingBottom) {
            this.dividerPaddingBottom = dividerPaddingBottom;
            return this;
        }

        public Builder setDividerFromPosition(int dividerFromPosition) {
            this.dividerFromPosition = dividerFromPosition;
            return this;
        }

        public Builder setIsShowLastDivide(boolean dividerIsShowLastDivide) {
            this.dividerIsShowLastDivide = dividerIsShowLastDivide;
            return this;
        }

        public Builder setOrientation(int orientation) {
            this.orientation = orientation;
            return this;
        }

        public SuperDividerItemDecoration build() {
            return new SuperDividerItemDecoration(this);
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        if (orientation == VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }

    }


    private void drawVertical(Canvas c, RecyclerView parent) {
        int count = parent.getChildCount();

        if (count > 0) {
            int showCount = dividerIsShowLastDivide ? count : count - 1;
            for (int i = dividerFromPosition; i < showCount; i++) {
                View view = parent.getChildAt(i);
                //可见item的底部
                int itemBottom = view.getBottom();

                c.drawRect(parent.getPaddingLeft() + dividerPaddingLeft,
                        itemBottom,
                        parent.getWidth() - parent.getPaddingRight() - dividerPaddingRight,
                        itemBottom + dividerWidth,
                        dividerPaint);
            }
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int count = parent.getChildCount();
        if (count > 0) {
            int showCount = dividerIsShowLastDivide ? count : count - 1;
            for (int i = dividerFromPosition; i < showCount; i++) {
                View view = parent.getChildAt(i);
                //可见item的底部
                int itemRight = view.getRight();

                c.drawRect(itemRight,
                        parent.getPaddingTop() + dividerPaddingTop,
                        itemRight + dividerWidth,
                        parent.getHeight() - parent.getPaddingBottom() - dividerPaddingBottom,
                        dividerPaint);
            }
        }
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (orientation == VERTICAL) {
            outRect.bottom = dividerWidth;
        } else {
            outRect.right = dividerWidth;
        }
    }

    /**
     * dp 2 px
     *
     * @param dpVal
     */
    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

}

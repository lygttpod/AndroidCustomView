package com.allen.androidcustomview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;


/**
 * <pre>
 *      @author : Allen
 *      e-mail  : lygttpod@163.com
 *      date    : 2018/04/15
 *      desc    : 悬停吸顶效果的ItemDecoration
 *      version : 1.0
 * </pre>
 */
public class HoverItemDecoration extends RecyclerView.ItemDecoration {

    private Context context;

    private int width;

    /**
     * 分组item的高度
     */
    private int itemHeight;
    /**
     * 分割线的高度
     */
    private int itemDivideHeight;
    /**
     * 分组text距离左边的距离
     */
    private int itemTextPaddingLeft;

    /**
     * 分组item的画笔
     */
    private Paint itemPaint;
    /**
     * 分组item的颜色
     */
    private int itemHoverPaintColor=0xFFf4f4f4;
    /**
     * 分组文字的颜色
     */
    private int textPaintColor=0xFF999999;

    /**
     * 悬停item的画笔
     */
    private Paint itemHoverPaint;
    /**
     * 文字的画笔
     */
    private Paint textPaint;
    /**
     * 绘制文字的矩形边框
     */
    private Rect textRect = new Rect();
    /**
     * 分组字母的回调（一般是取的分组的大写字母）
     */
    private BindItemTextCallback bindItemTextCallback;


    public HoverItemDecoration(Context content, BindItemTextCallback bindItemTextCallback) {
        this.context = content;
        this.bindItemTextCallback = bindItemTextCallback;

        width = content.getResources().getDisplayMetrics().widthPixels;

        itemPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        itemPaint.setColor(itemHoverPaintColor);

        itemHoverPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        itemHoverPaint.setColor(itemHoverPaintColor);

        itemHeight = dp2px(30);
        itemTextPaddingLeft = dp2px(20);
        itemDivideHeight = dp2px(1);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textPaintColor);
        textPaint.setTextSize(sp2px(15));


    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = parent.getChildAt(i);
            //分组item的顶部和底部
            int itemTop = view.getTop() - itemHeight;
            int itemBottom = view.getTop();

            //可见item在adapter中真实的位置
            int position = parent.getChildAdapterPosition(view);

            //获取回调的分组文字（一般是字母）
            String text = bindItemTextCallback.getItemText(position);

            //如果是一组中第一个的话绘制出分组的item和文字，否则绘制分割线
            if (isFirstInGroup(position)) {
                c.drawRect(0, itemTop, width, itemBottom, itemPaint);
                drawText(c, itemTop, itemBottom, text);
            } else {
                c.drawRect(0, view.getTop() - itemDivideHeight, width, view.getTop(), itemHoverPaint);
            }

        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        //绘制悬停的view

        int count = parent.getChildCount();

        if (count > 0) {
            //悬停只是第一个位置悬停，所以只取第一个view进行设置
            View firstView = parent.getChildAt(0);

            int position = parent.getChildAdapterPosition(firstView);
            String text = bindItemTextCallback.getItemText(position);

            //如果悬停view的底部小于悬停布局的高度说明正在上滑，就让他随着滑动逐渐滑进去，否则就固定悬停位置不边
            //isFirstInGroup(position+1)是下一个item是另外分组第一个的时候当前item才滚动上去
            if (firstView.getBottom() <= itemHeight && isFirstInGroup(position + 1)) {
                c.drawRect(0, 0, width, firstView.getBottom(), itemHoverPaint);
                drawText(c, firstView.getBottom() - itemHeight, firstView.getBottom(), text);
            } else {
                c.drawRect(0, 0, width, itemHeight, itemHoverPaint);
                drawText(c, 0, itemHeight, text);
            }
        }

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        //如果是分组第一个就留出绘制item的高度
        if (isFirstInGroup(position)) {
            outRect.top = itemHeight;
        }else {
            outRect.top = itemDivideHeight;
        }

    }


    /**
     * 绘制文字
     *
     * @param canvas 画布
     */
    private void drawText(Canvas canvas, int itemTop, int itemBottom, String textString) {

        textRect.left = itemTextPaddingLeft;
        textRect.top = itemTop;
        textRect.right = textString.length();
        textRect.bottom = itemBottom;

        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline = (textRect.bottom + textRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        //文字绘制到整个布局的中心位置
        canvas.drawText(textString, textRect.left, baseline, textPaint);
    }


    private boolean isFirstInGroup(int position) {
        if (position == 0) {
            return true;
        } else {
            String prevItemText = bindItemTextCallback.getItemText(position - 1);
            String currentItemText = bindItemTextCallback.getItemText(position);
            //上一个和当前位置的值一样说明是同一个组的否则就是新的一组
            if (prevItemText.equals(currentItemText)) {
                return false;
            } else {
                return true;
            }

        }
    }

    public interface BindItemTextCallback {
        String getItemText(int position);
    }

    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    protected int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());

    }

}

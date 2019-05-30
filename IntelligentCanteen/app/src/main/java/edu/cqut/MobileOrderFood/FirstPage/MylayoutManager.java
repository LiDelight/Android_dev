package edu.cqut.MobileOrderFood.FirstPage;

import android.support.v7.widget.RecyclerView;
import android.view.View;


public class MylayoutManager extends RecyclerView.LayoutManager {
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    private int offsetX = 0; //水平偏移
    private int mLeftX = 0; //卡片左端点的位置

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //没有数据就不进行处理
        if (getItemCount() == 0) {
            removeAndRecycleAllViews(recycler);
            return;
        }

        //将所有的子view临时移除并且回收
        detachAndScrapAttachedViews(recycler);

        //进行布局
        offsetX = 0;
        for (int i = 0; i < getItemCount(); i++) {
            View child = recycler.getViewForPosition(i);
            addView(child);
            measureAndLayout(child, i);
        }

    }

    /**
     * 计算view的大小并且设置位置
     *
     * @param view     目标view
     * @param position 在数据里面的位置
     */
    private void measureAndLayout(View view, int position) {
        //开始计算大小
        measureChildWithMargins(view, 0, 0);

        //计算宽度
        int width = getDecoratedMeasuredWidth(view);
        //计算高度
        int height = getDecoratedMeasuredHeight(view);

        //将view放置在RecyclerView里面
        layoutDecoratedWithMargins(view, mLeftX , 0, mLeftX + width, height);

        //更新水平位移
        offsetX += width;    }
}

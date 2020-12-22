package utils.kkutils.ui.recycleview;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/***
 * 滚动工具
 */
public class KKRecycleViewScrollTool {

    RecyclerView mRecyclerView;
    boolean isSmooth;

    public KKRecycleViewScrollTool(RecyclerView recyclerView, boolean isSmooth) {
        this.mRecyclerView = recyclerView;
        this.isSmooth = isSmooth;
        if (isSmooth) {
            initSmooth();
        }
    }

    /***
     * 滚动到指定位置并且置顶
     * @param recycleView
     * @param position
     */
    public static void scrollToPositionToTop(RecyclerView recycleView, int position) {
        if(position<0)return;
        LinearLayoutManager l = (LinearLayoutManager) recycleView.getLayoutManager();
        l.scrollToPositionWithOffset(position, 0);
    }

    protected void initSmooth() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mShouldScroll && RecyclerView.SCROLL_STATE_IDLE == newState) {
                    mShouldScroll = false;
                    smoothMoveToPosition(mToPosition);
                }
            }
        });
    }

    //目标项是否在最后一个可见项之后
    protected boolean mShouldScroll;
    //记录目标项位置
    protected int mToPosition;

    /**
     * 滑动到指定位置
     */
    public void smoothMoveToPosition(int position) {
        if (position < 0) position = 0;

        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后
            mRecyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }
}

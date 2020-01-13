package utils.kkutils.ui.recycleview;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.UiTool;

/***
 * 用于9宫格的
 */
public class JiuGongGeTool {
    public static void initJiuGongGe(RecyclerView recyclerView, int spanCount, int paddingDp){
        final int padding= CommonTool.dip2px(paddingDp);
        RecycleViewTool.initRecycleViewStaggeredGrid(recyclerView, spanCount);
        recyclerView.setPadding(0,-padding,-padding,0);
        RecycleViewTool.removeAllDecoration(recyclerView);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top=padding;
            }
        });
    }

    /***
     *
     * @param itemView  不要直接传itemview ， 传itemView 里面的子控件
     * @param spanCount
     * @param paddingDp
     * @param recyclerViewWidth
     */
    public static void initJiuGongGeItemView(View itemView,int spanCount,int paddingDp,int recyclerViewWidth){
        int w = (recyclerViewWidth - CommonTool.dip2px(paddingDp) * (spanCount-1)) / spanCount;
        UiTool.setWH(itemView,w,w);
    }
}

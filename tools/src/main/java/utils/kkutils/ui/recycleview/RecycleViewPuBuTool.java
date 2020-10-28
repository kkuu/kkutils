package utils.kkutils.ui.recycleview;


import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import utils.kkutils.common.CommonTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.UiTool;
import utils.kkutils.ui.KKSimpleRecycleView;

public class RecycleViewPuBuTool {
    public static void init(KKSimpleRecycleView recycleView, int spanCount, int headCount){
        {//头部铺满
            recycleView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
                public void onChildViewAttachedToWindow(@NonNull View itemView) {
                    int childAdapterPosition = recycleView.getChildAdapterPosition(itemView);
                    if (childAdapterPosition < headCount) {
                        if (itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
                            ((StaggeredGridLayoutManager.LayoutParams)itemView.getLayoutParams()).setFullSpan(true);
                        }
                    } else {
                        if (itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
                            ((StaggeredGridLayoutManager.LayoutParams)itemView.getLayoutParams()).setFullSpan(false);
                        }
                    }
                }
                public void onChildViewDetachedFromWindow(@NonNull View view) {
                }
            });
        }


        if (recycleView.getLayoutManager() == null || !(recycleView.getLayoutManager() instanceof StaggeredGridLayoutManager)) {
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(spanCount, 1);
            layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//防止左右交错边距不对

            recycleView.setLayoutManager(layoutManager);

            //防止第一行到顶部有空白区域
            recycleView.setItemViewCacheSize(0);//不加这一行 下面代码刷新的时候会跳动
            recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);

                    try {
                        if(newState==RecyclerView.SCROLL_STATE_IDLE){
                            RecyclerView.LayoutManager layoutManager1 = recyclerView.getLayoutManager();
                            if(layoutManager1!=null&&layoutManager1 instanceof StaggeredGridLayoutManager){
                                StaggeredGridLayoutManager staggeredGridLayoutManager= (StaggeredGridLayoutManager) layoutManager1;
                                int[] firstVisibleItem = null;
                                firstVisibleItem = staggeredGridLayoutManager.findFirstVisibleItemPositions(firstVisibleItem);
                                if (firstVisibleItem != null && firstVisibleItem[0] <2) {
                                    if(recyclerView.getAdapter()!=null){
                                        recyclerView.getAdapter().notifyItemRangeChanged(0,recyclerView.getAdapter().getItemCount());
                                    }
                                }
                            }
                        }
                    }catch (Exception e){
                        LogTool.ex(e);
                    }



                }
            });

        }




        StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recycleView.getLayoutManager();
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                //防止第一行到顶部有空白区域
                if(newState==RecyclerView.SCROLL_STATE_IDLE){
                    StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                    int[] firstVisibleItem = null;
                    firstVisibleItem = layoutManager.findFirstVisibleItemPositions(firstVisibleItem);
                    if (firstVisibleItem != null && firstVisibleItem[0] <2) {
                        if(recyclerView.getAdapter()!=null){
                            recycleView.getAdapter().notifyDataSetChanged();
                        }
                    }
                }


            }
        });
    }


//    /**
//     * 暂时两列  需要在initData 中调用  initPuBuLiuSpanItemPadding 设置间距
//     * @param recycleView
//     * @param itemPaddingDp
//     * @param onItemSizeChange
//     */
//    public static void initPuBuLiuSpan2(RecyclerView recycleView, int itemPaddingDp, RecycleViewTool.OnItemSizeChange onItemSizeChange){
//        int spanCount=2;
//        int padding=CommonTool.dip2px(itemPaddingDp);
//        RecycleViewTool.initPuBuLiu(recycleView, spanCount, 0, null, null);
//
//         recycleView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
//            @Override
//            public void onChildViewAttachedToWindow(@NonNull View itemView) {
//                int childAdapterPosition = recycleView.getChildAdapterPosition(itemView);
//                if(childAdapterPosition<spanCount){//head 铺满
//                    if(itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams){
//                        ((StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams()).setFullSpan(true);
//                    }
//                }else {
//                    initPuBuLiuSpanItemPadding(itemView, itemPaddingDp);
//
//                    int  width=(CommonTool.getWindowSize().x-padding*3)/2;
//                    onItemSizeChange.onItemSizeChange(recycleView, childAdapterPosition, itemView, width);
//                }
//            }
//            @Override
//            public void onChildViewDetachedFromWindow(@NonNull View view) {
//
//            }
//        });
//    }
//
//    public static void initPuBuLiuSpanItemPadding(View itemView ,int paddingDp){
//        if(itemView.getLayoutParams()==null)return;
//        if(!(itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams))return;
//
//        itemView.post(new Runnable() {
//            @Override
//            public void run() {
//                StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams();
//                int padding = CommonTool.dip2px(paddingDp);
//                if (layoutParams.getSpanIndex() == 0) {
//                    itemView.setPadding(padding, 0, padding / 2, padding);
//                } else {
//                    itemView.setPadding(padding / 2, 0, padding, padding);
//                }
//            }
//        });
//    }

}

package utils.kkutils.ui.recycleview;


import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import utils.kkutils.common.CommonTool;
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
        //瀑布流 用缓存会导致 下一页 的时候 闪烁
        recycleView.setItemViewCacheSize(0);

        if (recycleView.getLayoutManager() == null || !(recycleView.getLayoutManager() instanceof StaggeredGridLayoutManager)) {
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(spanCount, 1);
            recycleView.setLayoutManager(layoutManager);
        }

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

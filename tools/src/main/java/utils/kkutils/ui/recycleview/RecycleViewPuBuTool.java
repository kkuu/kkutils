package utils.kkutils.ui.recycleview;


import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import utils.kkutils.common.CommonTool;
import utils.kkutils.common.UiTool;

public class RecycleViewPuBuTool {

    public static void initPuBuLiuSpan2(RecyclerView recycleView, int itemPaddingDp, RecycleViewTool.OnItemSizeChange onItemSizeChange){
        int spanCount=2;
        int padding=CommonTool.dip2px(itemPaddingDp);
        RecycleViewTool.initPuBuLiu(recycleView, spanCount, 0, null, null);
         recycleView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View itemView) {
                int childAdapterPosition = recycleView.getChildAdapterPosition(itemView);
                if(childAdapterPosition<2){//head 铺满
                    if(itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams){
                        ((StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams()).setFullSpan(true);
                    }
                }else {
                    ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
                    if(layoutParams instanceof StaggeredGridLayoutManager.LayoutParams){
                        if(childAdapterPosition%2==0){
                            itemView.setPadding(padding, 0, padding/2, padding);
                        }else {
                            itemView.setPadding(padding/2, 0, padding, padding);
                        }
                    }
                    int  width=(CommonTool.getWindowSize().x-padding*3)/2;
                    onItemSizeChange.onItemSizeChange(recycleView, childAdapterPosition, itemView, width);
                }
            }
            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {

            }
        });
    }



}

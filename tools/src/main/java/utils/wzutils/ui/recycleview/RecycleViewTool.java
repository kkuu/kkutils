package utils.wzutils.ui.recycleview;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import utils.wzutils.R;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.UiTool;

public class RecycleViewTool {
    public static interface OnItemSizeChange{
        public void onItemSizeChange(RecyclerView recyclerView, int position,View itemView, int width);
    }
    public static void removeAllDecoration(RecyclerView recyclerView){
        try {
            for(int i=0;i<recyclerView.getItemDecorationCount();i++){//删掉以前的
                recyclerView.removeItemDecorationAt(i);
                i--;
            }
        }catch (Exception e){
            LogTool.ex(e);
        }

    }
    public static void initRecycleViewLinearlayout(RecyclerView recyclerView,int paddingDp ){
        removeAllDecoration(recyclerView);
        final int padding= CommonTool.dip2px(paddingDp);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                try {
                    int position = parent.getChildAdapterPosition(view);
                    boolean isLastItem=parent.getAdapter().getItemCount()==position+1;
                    int paddingBottom=isLastItem?padding:0;//最后一条才有
                    outRect.set(padding,padding,padding,paddingBottom);
                }catch (Exception e){
                    LogTool.ex(e);
                }
            }
        });
    }
    /***
     * 用于  多列 的 列表
     * @param recyclerView
     * @param spanCount
     * @param headCount  占据一行的  head 数量
     * @param paddingDp  间距
     * @param onItemSizeChange 用于可能需要根据item 宽来动态设置view 宽的情况
     */
    public static void initRecycleViewGrid(final RecyclerView recyclerView, final int spanCount, final int headCount, final int paddingDp, final OnItemSizeChange onItemSizeChange){
        GridLayoutManager layoutManager=new GridLayoutManager(recyclerView.getContext(),spanCount);

        if(headCount>0){
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if(position<headCount){
                        return spanCount;
                    }
                    return 1;
                }
            });
        }
        recyclerView.setLayoutManager(layoutManager);

        {//设置间隔
            removeAllDecoration(recyclerView);

            final double jiangeCount=spanCount+1;
            final int padding= CommonTool.dip2px(paddingDp);
            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    try {
                        int position = parent.getChildAdapterPosition(view);
                        boolean isLastItem=parent.getAdapter().getItemCount()==position+1;
                        int paddingBottom=isLastItem?padding:0;//最后一条才有,  paddingTop 一直有， paddingBottom 只有最后一条才有
                        boolean isHead=position<=headCount-1;//当前条是否是head
                        if(isHead){
                            outRect.set(padding,padding,padding,paddingBottom);
                        }else {


                            if(onItemSizeChange!=null){
                                int w=recyclerView.getWidth();
                                if(w==0)w=recyclerView.getMeasuredWidth();
                                int itemW= (int) ((w-padding*jiangeCount)/spanCount);
                                if(view.getWidth()!=itemW){
                                    onItemSizeChange.onItemSizeChange(recyclerView,position,view,itemW);
                                }
                            }

                            int index=position-headCount+1;

                            if(index%spanCount==1){//最左边的
                                outRect.set(padding,padding,padding/2,paddingBottom);
                            }else if(index%spanCount==0){//最右边的
                                outRect.set(padding/2,padding,padding,paddingBottom);
                            }else {//中间的
                                outRect.set(padding/2,padding,padding/2,paddingBottom);
                            }
                        }
                    }catch (Exception e){
                        LogTool.ex(e);
                    }
                }
            });

        }
    }
}

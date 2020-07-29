package kk.kktools.recycleview_test;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import kk.kktools.R;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.UiTool;
import utils.kkutils.ui.recycleview.RecycleViewTool;

@Deprecated  //有错， 待修复
public class KKDecoration extends RecyclerView.ItemDecoration {

    public KKDecoration(int spanCount, int headCount,double paddingBoderDp,double paddingItemDp) {
        this.spanCount = spanCount;
        this.headCount = headCount;
        paddingBoder=CommonTool.dip2px(paddingBoderDp);
        paddingItem=CommonTool.dip2px(paddingItemDp);

        paintLine.setColor(Color.BLACK);
        paintLine.setAntiAlias(true);
    }
    public Paint paintLine=new Paint();
    public int spanCount;
    public int headCount;
    public int paddingBoder;//父边距
    public int paddingItem;//子项边距
    public  void initHeadItem(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state,boolean isFirstLine, boolean isLastLine){

    }
    public void initLeftItem(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state,boolean isFirstLine, boolean isLastLine){


        outRect.set(paddingBoder, isFirstLine?paddingBoder:paddingItem/2, paddingItem / 2, isLastLine ? paddingBoder : paddingItem/2);
        view.setPadding(outRect.left, outRect.top, outRect.right, outRect.bottom);
        outRect.set(0, 0, 0, 0);
    }
    public void initRightItem(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state,boolean isFirstLine, boolean isLastLine){
        outRect.set(paddingItem/2, isFirstLine?paddingBoder:paddingItem/2, paddingBoder, isLastLine ? paddingBoder : paddingItem/2);
        view.setPadding(outRect.left, outRect.top, outRect.right, outRect.bottom);
        outRect.set(0, 0, 0, 0);
    }
    public void initCenterItem(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state,boolean isFirstLine, boolean isLastLine){
        outRect.set(paddingItem/2, isFirstLine?paddingBoder:paddingItem/2, paddingItem / 2, isLastLine ? paddingBoder : paddingItem / 2);
        view.setPadding(outRect.left, outRect.top, outRect.right, outRect.bottom);
        outRect.set(0, 0, 0, 0);
    }

    /***
     * 设置view 为正方形
     * @param view
     */
    public  void setViewSquare(View view){
        UiTool.setWHEqual(view);
    }


    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);

    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int lineWidth=1;
//        {//水平
//            int count=(int)(parent.getChildCount()*1.0/spanCount+0.9999999999);
//            if(count>1){
//                for(int i=1;i<count;i++){
//                    int preLineIndex=(i-1)*spanCount+1;//用上一行的第一个来作为线的top
//                    int top=parent.getChildAt(preLineIndex).getBottom()+padding/2;
//                    c.drawRect(padding, top, parent.getWidth()-padding, (float) (top+lineWidth), paintLine);
//                }
//            }
//        }
//        {//竖直
//            int itemW=parent.getWidth()/spanCount;
//            int step=itemW;
//            int top=padding;
//            c.drawRect(itemW, top, (float) (itemW+lineWidth), parent.getHeight()-top, paintLine);
//            itemW+=step;
//            c.drawRect(itemW, top, (float) (itemW+lineWidth), parent.getHeight()-top, paintLine);
//            itemW+=step;
//            c.drawRect(itemW, top, (float) (itemW+lineWidth), parent.getHeight()-top, paintLine);
//        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        try {
            int position = parent.getChildAdapterPosition(view);//数据索引

            // 计算这个child 处于第几列
            int column = (position) % spanCount;
            int dividerWidth=CommonTool.dip2px(10);

            outRect.left = (column * dividerWidth / spanCount)+26;
            outRect.right = dividerWidth - (column + 1) * dividerWidth / spanCount;


            if (position >= spanCount)
                outRect.top = dividerWidth;


            if(true)return;

            boolean isLastLine= RecycleViewTool.isLastLine(parent.getAdapter().getItemCount()-headCount,position-headCount,spanCount);
            boolean isFirstLine=position<spanCount+headCount;

            int spanIndex=-1;//在一行中得索引，StaggeredGridLayoutManager GridLayoutManager这个才用
            {//有span
                if(view.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams){
                    spanIndex=((StaggeredGridLayoutManager.LayoutParams)view.getLayoutParams()).getSpanIndex();
                }
                if(view.getLayoutParams() instanceof GridLayoutManager.LayoutParams){
                    spanIndex=((GridLayoutManager.LayoutParams)view.getLayoutParams()).getSpanIndex();
                }
            }
            boolean isHead=position<=headCount-1;//当前条是否是head
            if(isHead){
                initHeadItem(outRect, view, parent, state,isFirstLine, isLastLine);
            }else {
                if(spanIndex<0){//普通得
                    int index=position-headCount+1;
                    if(index%spanCount==1){//最左边的
                        initLeftItem(outRect, view, parent, state, isFirstLine,isLastLine);
                    }else if(index%spanCount==0){//最右边的
                        initRightItem(outRect, view, parent, state,isFirstLine, isLastLine);
                    }else {//中间的
                        initCenterItem(outRect, view, parent, state,isFirstLine, isLastLine);
                    }
                }else {//格子类型得 需要spanindex 大于0
                    if(spanIndex==0){//最左边的
                        initLeftItem(outRect, view, parent, state,isFirstLine, isLastLine);
                    }else if(spanIndex==spanCount-1){//最右边的
                        initRightItem(outRect, view, parent, state,isFirstLine, isLastLine);
                    }else {//中间的
                        initCenterItem(outRect, view, parent, state, isFirstLine,isLastLine);
                    }
                }
            }
        }catch (Exception e){
            LogTool.ex(e);
        }
    }



    /***
     * 四边相等，并且设置正方形item
     */
    public static class KKDecorationEqualSquare extends KKDecoration{

        public KKDecorationEqualSquare(int spanCount, int headCount, double paddingBoderDp, double paddingItemDp) {
            super(spanCount, headCount, paddingBoderDp, paddingItemDp);
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            setViewSquare(view);
        }
    }

}

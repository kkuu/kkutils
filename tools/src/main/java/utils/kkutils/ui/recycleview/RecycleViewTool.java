package utils.kkutils.ui.recycleview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import utils.kkutils.common.CommonTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.UiTool;
import utils.kkutils.parent.KKParentRecycleView;

public class RecycleViewTool {
    /**
     * 线性布局  的间隔设置
     * @param recyclerView
     * @param dividerLeftDp
     * @param dividerRightDp
     * @param dividerVerticalDp
     */
    public static void initRecycleViewLinearlayout(final KKParentRecycleView recyclerView,int dividerLeftDp,int dividerRightDp, int dividerVerticalDp) {
        removeAllDecoration(recyclerView);
        final int dividerLeft= CommonTool.dip2px(dividerLeftDp);
        final int dividerRight= CommonTool.dip2px(dividerRightDp);
        final int dividerVertical= CommonTool.dip2px(dividerVerticalDp);
        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if(recyclerView.isEmptyView(view))return;//如果是空数据， 就不要间隔了
                if(parent.getChildAdapterPosition(view)==0){
                    outRect.set(dividerLeft, dividerVertical, dividerRight, dividerVertical);
                }else {
                    outRect.set(dividerLeft, 0, dividerRight, dividerVertical);
                }
            }
        };
        recyclerView.addItemDecoration(itemDecoration);
    }
    /***
     * 线性布局  的间隔设置
     * @param recyclerView
     * @param paddingDp
     */
    public static void initRecycleViewLinearlayout(KKParentRecycleView recyclerView,int paddingDp ){
        initRecycleViewLinearlayout(recyclerView,paddingDp,paddingDp,paddingDp);
    }






    public static abstract class RecycleViewBarOnChangeListener{
        public boolean show;
        public void onBarChange(boolean showIn){
            if(this.show!=showIn){
                onChange(showIn);
            }
            this.show=showIn;
        }
        public abstract void onChange(boolean show);
    }
    /**
     * 主要用于首页 title 滑动 改变显示的
     * 用于以下情况
     * recycleview 的第一条 有一个bar（比如搜索框）
     * 当recycle滚动的时候 这个bar 是处于显示的还是隐藏的 监听
     *
     * @param recyclerView
     * @param barHeightDp
     * @param onChangeListener
     */
    public static void initScrlloBarChange(RecyclerView recyclerView, final int barHeightDp, final RecycleViewBarOnChangeListener onChangeListener){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int barHeight= CommonTool.dip2px(barHeightDp);
            boolean isShowingTitleSearch=false;//title 上面的搜索框是否显示
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View firstChildView = recyclerView.getChildAt(0);
                int currPosition= recyclerView.getChildAdapterPosition(firstChildView);// 当前第一个view 在adaper 里面的position
                int top = firstChildView.getTop();//第一个View 的上边距

                if(currPosition==0){//是第一个View
                    if(-top>=barHeight){//bar 被隐藏了
                        if(onChangeListener!=null){
                            onChangeListener.onBarChange(false);
                        }
                        return;
                    }else {//bar  显示了
                        if(onChangeListener!=null){
                            onChangeListener.onBarChange(true);
                        }
                    }

                }
            }
        });
    }
























    /***
     * 设置某一项铺满
     * 必须用的 StaggeredGridLayoutManager  这个才能调用
     * @param itemView
     */
    public static void setStaggeredGridFullSpan(View itemView) {
        try {
            StaggeredGridLayoutManager.LayoutParams layoutParams =
                    new StaggeredGridLayoutManager.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setFullSpan(true);
            itemView.setLayoutParams(layoutParams);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }



    /***
     * 初始化 网格线
     * @param spanCount
     * @param lineWidth
     * @param color
     */
    public static RecyclerView.ItemDecoration initGridLineSimple( final int paddingIn, final int spanCount, final int lineWidth, final int color){
        return  new RecyclerView.ItemDecoration() {
            Paint paint=new Paint();
            int padding=CommonTool.dip2px(paddingIn);

            {
                paint.setColor(color);
                paint.setAntiAlias(true);
            }

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            }

            @Override
            public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDrawOver(c, parent, state);

                {//水平
                    int count=(int)(parent.getChildCount()*1.0/spanCount+0.9999999999);
                    if(count>1){
                        for(int i=1;i<count;i++){
                            int preLineIndex=(i-1)*spanCount+1;//用上一行的第一个来作为线的top
                            int top=parent.getChildAt(preLineIndex).getBottom()+padding/2;
                            c.drawRect(padding, top, parent.getWidth()-padding, top+lineWidth, paint);
                        }
                    }
                }
                {//竖直
                    int itemW=parent.getWidth()/spanCount;
                    int step=itemW;
                    int top=padding;
                    c.drawRect(itemW, top, itemW+lineWidth, parent.getHeight()-top, paint);
                    itemW+=step;
                    c.drawRect(itemW, top, itemW+lineWidth, parent.getHeight()-top, paint);
                    itemW+=step;
                    c.drawRect(itemW, top, itemW+lineWidth, parent.getHeight()-top, paint);
                }
            }
        };
    }







    public static void initRecycleViewGrid(final RecyclerView recyclerView, final int spanCount, final int headCount,final int itemPaddingDp, final RecycleViewTool.OnItemSizeChange onItemSizeChange, final RecyclerView.ItemDecoration itemDecorationEnd){
        initRecycleViewGrid(recyclerView,spanCount,headCount,itemPaddingDp,itemPaddingDp,onItemSizeChange,itemDecorationEnd);
    }

    /***
     * 用于  多列 的 列表
     * @param recyclerView
     * @param spanCount
     * @param headCount  占据一行的  head 数量
     * @param paddingDp  间距
     * @param onItemSizeChange 用于可能需要根据item 宽来动态设置view 宽的情况
     * @param itemDecorationEnd 可用于自定义间隔， 默认可不传
     */
    public static void initRecycleViewGrid(final RecyclerView recyclerView, final int spanCount, final int headCount,final int headPaddingDp, final int itemPaddingDp, final RecycleViewTool.OnItemSizeChange onItemSizeChange, final RecyclerView.ItemDecoration itemDecorationEnd){
        GridLayoutManager layoutManager=null;

        {//判断是否设置过， 设置过就不要设置了，不然ui 刷新会出问题
            RecyclerView.LayoutManager layoutManagerOld= recyclerView.getLayoutManager();
            if(layoutManagerOld!=null&&layoutManagerOld instanceof GridLayoutManager){
                layoutManager= (GridLayoutManager) layoutManagerOld;
            }else {
                layoutManager=new GridLayoutManager(recyclerView.getContext(),spanCount);
                recyclerView.setLayoutManager(layoutManager);
            }
        }


        layoutManager.setSpanCount(spanCount);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(position<headCount){
                    return spanCount;
                }
                return 1;
            }
        });

        initDecoration(recyclerView,spanCount,headCount,headPaddingDp,itemPaddingDp,onItemSizeChange,itemDecorationEnd);

    }
    /***
     * 瀑布流
     */
    public static void initPuBuLiu(final RecyclerView recycleView, final int spanCount, final int headCount,final int headPaddingDp, final int itemPaddingDp, final RecycleViewTool.OnItemSizeChange onItemSizeChange, final RecyclerView.ItemDecoration itemDecorationEnd){
        if(recycleView.getLayoutManager()==null||!(recycleView.getLayoutManager() instanceof StaggeredGridLayoutManager)){
            StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(spanCount,StaggeredGridLayoutManager.VERTICAL);
            recycleView.setLayoutManager(layoutManager);

            RecycleViewTool.initDecoration(recycleView, spanCount, headCount, headPaddingDp, itemPaddingDp, onItemSizeChange, itemDecorationEnd);

        }
    }
    /***
     * 一般用于瀑布流
     * @param recyclerView
     * @param spanCount
     * 如果需要head
     *
     *  StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams();
     *         layoutParams.setFullSpan(true);//将 StaggeredGrid的某个item直接占满宽
     *
     * 图片不固定大小的时候，高度随时在变，所以在initdata里面
     *
     *  int w=(recycler_view.getMeasuredWidth()-CommonTool.dip2px(padding*(count+1)))/count;
     *                             UiTool.setWH(itemView.findViewById(R.id.imgv_shipin_tupian_item),w,w);
     *
     * 间隔 需要在item xml 里面配置margin 5， 在recycleview 里面设置padding 5   最后就是 10 的间距
     */
    public static void initRecycleViewStaggeredGrid(final RecyclerView recyclerView, final int spanCount){
        StaggeredGridLayoutManager layoutManager=null;
        {//判断是否设置过， 设置过就不要设置了，不然ui 刷新会出问题
            RecyclerView.LayoutManager layoutManagerOld= recyclerView.getLayoutManager();
            if(layoutManagerOld!=null&&layoutManagerOld instanceof StaggeredGridLayoutManager){
                layoutManager= (StaggeredGridLayoutManager) layoutManagerOld;
            }else {
                layoutManager=new StaggeredGridLayoutManager(spanCount,StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
            }
        }
        layoutManager.setSpanCount(spanCount);
    }



    public static void initDecoration(final RecyclerView recyclerView, final int spanCount, final int headCount,final int headPaddingDp, final int itemPaddingDp, final RecycleViewTool.OnItemSizeChange onItemSizeChange, final RecyclerView.ItemDecoration itemDecorationEnd){
        {//设置间隔
            RecycleViewTool.removeAllDecoration(recyclerView);

            final double jiangeCount=spanCount+1;
            final int padding= CommonTool.dip2px(itemPaddingDp);
            final int headPadding= CommonTool.dip2px(headPaddingDp);
            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                    super.onDrawOver(c, parent, state);
                    try {
                        if(itemDecorationEnd!=null)itemDecorationEnd.onDrawOver(c,parent,state);
                    }catch (Exception e){
                        LogTool.ex(e);
                    }
                }

                @Override
                public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    try {
                        int position = parent.getChildAdapterPosition(view);

                        boolean isLastLine=isLastLine(parent.getAdapter().getItemCount()-headCount,position-headCount,spanCount);

                        int paddingBottom=isLastLine?padding:0;//最后一行才有,  paddingTop 一直有， paddingBottom 只有最后一条才有



                        boolean isHead=position<=headCount-1;//当前条是否是head
                        if(isHead){
                            outRect.set(headPadding,headPadding,headPadding,headPadding);
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

                    try {
                        if(itemDecorationEnd!=null)itemDecorationEnd.getItemOffsets(outRect,view,parent,state);
                    }catch (Exception e){
                        LogTool.ex(e);
                    }
                }
            });

        }
    }

    /***
     * 是否最后一行
     * @param count  总数
     * @param position  当前索引，0开始
     * @param spanCount 几列
     * @return
     */
    public static boolean isLastLine(int count,int position,int spanCount){
        boolean isLastLine=false;
        {
            int line=0;
            if(count%spanCount==0){
                line=count/spanCount;
            }else {
                line=count/spanCount+1;
            }
            isLastLine=position>=(line-1)*spanCount;
        }
        return isLastLine;
    }


























    /***
     * 删除所有间隔线
     * @param recyclerView
     */
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
    public static interface OnItemSizeChange{
        public void onItemSizeChange(RecyclerView recyclerView, int position,View itemView, int width);
    }

}

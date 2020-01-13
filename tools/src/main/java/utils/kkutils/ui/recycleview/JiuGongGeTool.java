package utils.kkutils.ui.recycleview;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.UiTool;

/***
 * 用于9宫格的
 *
 *  recycleView.setData(TestData.getTestStrList(10), R.layout.jianwen_item, new KKSimpleRecycleView.KKRecycleAdapter() {
 *             @Override
 *             public void initData(int position, int type, View itemView) {
 *                 super.initData(position, type, itemView);
 *
 *                 final KKSimpleRecycleView recycleView_jianwen_tupian=itemView.findViewById(R.id.recycleView_jianwen_tupian);
 *
 *                 JiuGongGeTool.initJiuGongGe(recycleView_jianwen_tupian,3,10);
 *
 *                 recycleView_jianwen_tupian.setData(TestData.getTestStrList(9), R.layout.jianwen_item_img, new KKSimpleRecycleView.KKRecycleAdapter() {
 *                     @Override
 *                     public void initData(int position, int type, View itemView) {
 *                         super.initData(position, type, itemView);
 *                         ImageView imgv_jianwen_item=itemView.findViewById(R.id.imgv_jianwen_item);
 *                         JiuGongGeTool.initJiuGongGeItemView(imgv_jianwen_item,3,10,CommonTool.getWindowSize().x-CommonTool.dip2px(26));
 *                         loadImage(R.drawable.logo,imgv_jianwen_item);
 *                     }
 *                 });
 *             }
 *         });
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
     * @param parentClickView  父容器可点击的 就传入
     * @param recyclerView
     * @param spanCount
     * @param paddingDp
     */
    public static void initJiuGongGe(final View parentClickView, RecyclerView recyclerView, int spanCount, int paddingDp){
        initJiuGongGe(recyclerView,spanCount,paddingDp);
        parentClickView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return parentClickView.onTouchEvent(event);
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

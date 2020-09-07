package utils.kkutils.ui.recycleview;

import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.ViewTool;

public class RecycleViewAutoScroolTool {
    /***
     * 自动滚动Y
     * @param recyclerView
     * @param delayMillis
     * @param stepY
     */
    public static void startAutoScrollY(final RecyclerView recyclerView, final int delayMillis, final int stepY) {
        setCanNotTouch(recyclerView);
        stopAutoScroll(recyclerView);

        Runnable runnable = new Runnable() {
            public void run() {
                recyclerView.scrollBy(0,stepY);
                LogTool.s("滚动"+recyclerView.getScrollY());
                recyclerView.postDelayed(this,delayMillis);
            }
        };

        ViewTool.setTag(recyclerView,runnable,key_recycleview_auto_scroll);
        recyclerView.post(runnable);
    }




    /***
     * 自动滚动Item
     * 自动间隔时间滚动 多少item，
     *
     * 如果要实现无限循环就设置 数量  Integer.MAX_VALUE  ， position = position % list.size();
     * @param recyclerView
     * @param delayMillis
     * @param stepPosition
     */
    public static void startAutoScrollPositon(RecyclerView recyclerView, int delayMillis, int stepPosition){
        setCanNotTouch(recyclerView);
        stopAutoScroll(recyclerView);

        Runnable runnable = new Runnable() {
            int position = -1;

            @Override
            public void run() {
                position += stepPosition;
                recyclerView.smoothScrollToPosition(position);
                LogTool.s("自动滚动：" + position + "    " + recyclerView.getAdapter().getItemCount());
                if (recyclerView.getAdapter().getItemCount() > position) {
                    recyclerView.postDelayed(this, delayMillis);
                }
            }
        };
        recyclerView.postDelayed(runnable,1);
        ViewTool.setTag(recyclerView,runnable,key_recycleview_auto_scroll);
    }


    public static void setCanNotTouch(View view){
        //禁止点击
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    static  final  int key_recycleview_auto_scroll= ViewTool.initKey();
    /***
     * 停止自动滚动
     * @param recyclerView
     */
    public static void stopAutoScroll(RecyclerView recyclerView){
        Object tag = ViewTool.getTag(recyclerView,key_recycleview_auto_scroll);
        if(tag!=null&& tag instanceof Runnable){
            recyclerView.removeCallbacks((Runnable) tag);
        }
    }

}

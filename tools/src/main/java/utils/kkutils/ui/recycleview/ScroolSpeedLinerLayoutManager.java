package utils.kkutils.ui.recycleview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

/***
 * 控制 smoothScrollToPosition 的滑动速度的
 *
 */
public class ScroolSpeedLinerLayoutManager extends LinearLayoutManager {

    public ScroolSpeedLinerLayoutManager(Context context) {
        super(context);
    }

    public ScroolSpeedLinerLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public ScroolSpeedLinerLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public float speed=1.2f;//滑动一个pixel需要多少毫秒
    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        startSmoothScroll(this,recyclerView.getContext(),speed,position);
    }


    /***
     * 自动间隔时间滚动 多少item，
     *
     * 如果要实现无限循环就设置 数量  Integer.MAX_VALUE  ， position = position % list.size();
     * @param recyclerView
     * @param delayMillis
     * @param stepPosition
     */
    public static void startAutoScrollPositon(RecyclerView recyclerView,int delayMillis,int stepPosition){
        recyclerView.postDelayed(new Runnable() {
            int position=-1;
            @Override
            public void run() {
                position=stepPosition;
                recyclerView.smoothScrollToPosition(position);
                if(recyclerView.getAdapter().getItemCount()>position){
                    recyclerView.postDelayed(this,delayMillis);
                }
            }
        },delayMillis);
    }
    /***
     * 控制 smoothScrollToPosition 的滑动速度的
     * @param layoutManager
     * @param context
     * @param speed
     * @param position
     */
    public static void startSmoothScroll(RecyclerView.LayoutManager layoutManager,Context context, float speed, int position){
        LinearSmoothScroller linearSmoothScroller =
                new LinearSmoothScroller(context) {
                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        return speed; //返回滑动一个pixel需要多少毫秒
                    }

                };
        linearSmoothScroller.setTargetPosition(position);
        layoutManager.startSmoothScroll(linearSmoothScroller);

    }
}

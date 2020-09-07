package utils.kkutils.ui.recycleview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.ViewTool;

/***
 * 控制 smoothScrollToPosition 的滑动速度的
 *
 */
public class SpeedLinerLayoutManager extends LinearLayoutManager {

    public SpeedLinerLayoutManager(Context context) {
        super(context);
    }

    public SpeedLinerLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public SpeedLinerLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public float speed=1.2f;//滑动一个pixel需要多少毫秒
    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        startSmoothScroll(this,recyclerView.getContext(),speed,position);
    }


    /***
     * 控制 smoothScrollToPosition 的滑动速度的
     * @param layoutManager
     * @param context
     * @param speed
     * @param position
     */
    public static void startSmoothScroll(RecyclerView.LayoutManager layoutManager, Context context, float speed, int position){
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

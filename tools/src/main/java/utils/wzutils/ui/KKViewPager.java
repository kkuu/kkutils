package utils.wzutils.ui;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by ishare on 2016/9/18.
 */
public class KKViewPager extends ViewPager {
    private boolean canScroll = true;

    public KKViewPager(Context context) {
        super(context);
    }


    public KKViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (!canScroll) {
            return false;
        }

        return super.onInterceptTouchEvent(ev);
    }

    public boolean isCanScroll() {
        return canScroll;
    }

    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }
}

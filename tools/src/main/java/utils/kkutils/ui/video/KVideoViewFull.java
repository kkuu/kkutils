package utils.kkutils.ui.video;

import android.content.Context;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.VideoView;

import utils.kkutils.ui.video.douyin2.views.KVideoView;

/***
 * 全屏播放
 */
public class KVideoViewFull extends KVideoView {
    public KVideoViewFull(Context context) {
        super(context);
    }

    public KVideoViewFull(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KVideoViewFull(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        setMeasuredDimension(width, height);
    }
}

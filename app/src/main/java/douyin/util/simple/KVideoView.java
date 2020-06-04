package douyin.util.simple;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.dueeeke.videoplayer.controller.BaseVideoController;
import com.dueeeke.videoplayer.player.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import utils.kkutils.common.LogTool;

public class KVideoView extends VideoView {
    public KVideoView(@NonNull Context context) {
        super(context);
    }

    public KVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public KVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        setScreenScaleType(VideoView.SCREEN_SCALE_CENTER_CROP);
    }
    @Override
    public void onVideoSizeChanged(int videoWidth, int videoHeight) {
        super.onVideoSizeChanged(videoWidth, videoHeight);
        LogTool.s(mUrl);
        LogTool.s("onVideoSizeChanged: "+videoWidth+"  "+videoHeight);
       if(mRenderView!=null) LogTool.s(    "计算后高度"+    mRenderView.getView().getWidth()+"  "+mRenderView.getView().getHeight());
    }

    public BaseVideoController getController(){
        return mVideoController;
    }
}

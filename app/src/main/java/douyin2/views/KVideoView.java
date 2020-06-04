package douyin2.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dueeeke.videoplayer.controller.BaseVideoController;
import com.dueeeke.videoplayer.player.VideoView;

import utils.kkutils.common.LogTool;

public class KVideoView extends VideoView{
    public KVideoView(@NonNull Context context) {
        super(context);
    }

    public KVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public KVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public void pause() {
        try {
            super.pause();
        }catch (Exception e){
            LogTool.ex(e);
        }
    }

    @Override
    public void resume() {
        try {
            super.resume();

        }catch (Exception e){
            LogTool.ex(e);
        }
    }

    @Override
    public void start() {
        try {
            super.start();
        }catch (Exception e){
            LogTool.ex(e);
        }
    }

    @Override
    public void stopFullScreen() {

        try {
            super.stopFullScreen();
        }catch (Exception e){
            LogTool.ex(e);
        }
    }

    @Override
    public void stopNestedScroll() {

        try {
            super.stopNestedScroll();
        }catch (Exception e){
            LogTool.ex(e);
        }
    }

    @Override
    public void stopTinyScreen() {

        try {
            super.stopTinyScreen();
        }catch (Exception e){
            LogTool.ex(e);
        }
    }


    public BaseVideoController getController(){
        return mVideoController;
    }
}

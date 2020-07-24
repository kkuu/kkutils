package utils.kkutils.ui.video.douyin2.views;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.dueeeke.videoplayer.BuildConfig;
import com.dueeeke.videoplayer.controller.BaseVideoController;
import com.dueeeke.videoplayer.ijk.IjkPlayerFactory;
import com.dueeeke.videoplayer.player.VideoView;
import com.dueeeke.videoplayer.player.VideoViewConfig;
import com.dueeeke.videoplayer.player.VideoViewManager;

import utils.kkutils.AppTool;
import utils.kkutils.common.BroadcastReceiverTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.ui.video.douyin2.library.cache.PreloadManager;

public class KVideoView extends VideoView {
    public KVideoView(@NonNull Context context) {
        super(context);
        init();
    }

    public KVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }
    public void init(){
        initAutoPause();
        addOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onPlayerStateChanged(int playerState) {

            }

            @Override
            public void onPlayStateChanged(int playState) {

                /***
                 *  //播放器的各种状态
                 *     public static final int STATE_ERROR = -1;
                 *     public static final int STATE_IDLE = 0;
                 *     public static final int STATE_PREPARING = 1;
                 *     public static final int STATE_PREPARED = 2;
                 *     public static final int STATE_PLAYING = 3;
                 *     public static final int STATE_PAUSED = 4;
                 *     public static final int STATE_PLAYBACK_COMPLETED = 5;
                 *     public static final int STATE_BUFFERING = 6;
                 *     public static final int STATE_BUFFERED = 7;
                 *     public static final int STATE_START_ABORT = 8;//开始播放中止
                 */

                if(playState==STATE_PREPARING||playState==STATE_PLAYING){
                    try {
                        if(currVideoView!=null&&currVideoView!=KVideoView.this){
                            currVideoView.pause();
                        }
                        currVideoView=KVideoView.this;
                    }catch (Exception e){
                        LogTool.ex(e);
                    }
                }
            }
        });
    }

    public static VideoView currVideoView;

    public static void pauseAll(){
        if(currVideoView!=null){
            currVideoView.pause();
        }
    }

    public BaseVideoController getController() {
        return mVideoController;
    }

    static {
        //播放器配置，注意：此为全局配置，按需开启
        VideoViewManager.setConfig(VideoViewConfig.newBuilder()
                .setLogEnabled(BuildConfig.DEBUG)//调试的时候请打开日志，方便排错
                .setPlayerFactory(IjkPlayerFactory.create())
//                .setPlayerFactory(ExoMediaPlayerFactory.create())
//                .setRenderViewFactory(SurfaceRenderViewFactory.create())
//                .setEnableOrientation(true)
//                .setEnableAudioFocus(false)
//                .setScreenScaleType(VideoView.SCREEN_SCALE_MATCH_PARENT)
//                .setAdaptCutout(false)
//                .setPlayOnMobileNetwork(true)
//                .setProgressManager(new ProgressManagerImpl())
                .build());
    }









    /***
     * 设置部分生命周期
     */
    public void initAutoPause() {
        Lifecycle lifecycle=null;
        if(AppTool.currActivity instanceof FragmentActivity){
            lifecycle= ((FragmentActivity) AppTool.currActivity).getLifecycle();
        }
        if(AppTool.currActivity instanceof AppCompatActivity){
            lifecycle= ((AppCompatActivity) AppTool.currActivity).getLifecycle();
        }

        if(lifecycle!=null){
            lifecycle.addObserver(new LifecycleEventObserver() {
                @Override
                public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                    try {
                        LogTool.s(source+""+  event);
                        if (Lifecycle.Event.ON_PAUSE == event) {
                            pause();
                        }
                        if (Lifecycle.Event.ON_RESUME == event) {
                            resume();
                        }
                        //
                        if (Lifecycle.Event.ON_DESTROY == event) {
                            release();
                            PreloadManager.getInstance(getContext()).removeAllPreloadTask();
                        }
                    }catch (Exception e){
                        LogTool.ex(e);
                    }
                }
            });
        }

    }


    @Override
    public void pause() {
        try {
            super.pause();
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    @Override
    public void resume() {
        try {
            super.resume();
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    @Override
    public void start() {
        try {

            super.start();
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }




    @Override
    public void stopFullScreen() {

        try {
            super.stopFullScreen();
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    @Override
    public void stopNestedScroll() {

        try {
            super.stopNestedScroll();
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    @Override
    public void stopTinyScreen() {

        try {
            super.stopTinyScreen();
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }



}

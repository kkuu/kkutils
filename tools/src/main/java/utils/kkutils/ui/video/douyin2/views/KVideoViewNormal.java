package utils.kkutils.ui.video.douyin2.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.bumptech.glide.Glide;
import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.BuildConfig;
import com.dueeeke.videoplayer.controller.BaseVideoController;
import com.dueeeke.videoplayer.ijk.IjkPlayerFactory;
import com.dueeeke.videoplayer.player.VideoView;
import com.dueeeke.videoplayer.player.VideoViewConfig;
import com.dueeeke.videoplayer.player.VideoViewManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.kkutils.AppTool;
import utils.kkutils.ImgTool;
import utils.kkutils.R;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.UiTool;
import utils.kkutils.ui.lunbo.LunBoTool;
import utils.kkutils.ui.video.douyin2.library.cache.PreloadManager;

/***
 * 普通播放用的
 */
public class KVideoViewNormal extends VideoView {
    /***
     * 播放按钮图片
     */
    public static int res_play_img=R.drawable.kk_video_icon_play;

    public KVideoViewNormal(@NonNull Context context) {
        super(context);
        init();
    }

    public KVideoViewNormal(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KVideoViewNormal(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public OnStateChangeListener getOnStateChangeListener(){
        return new OnStateChangeListener() {
            @Override
            public void onPlayerStateChanged(int playerState) {
            }

            @Override
            public void onPlayStateChanged(int playState) {
                LogTool.s("playstate" + playState+"   "+playStateMap.get(playState));
            }
        };
    }
    public void init(){
        weakReferenceList.add(new WeakReference(this));
        initAutoPause();
        addOnStateChangeListener(getOnStateChangeListener());
        initDefalutController();
    }

    /***
     * 设置默认controller
     */
    public void initDefalutController() {
        StandardVideoController standardVideoController = new StandardVideoController(getContext());
        standardVideoController.addDefaultControlComponent("", false);
        setVideoController(standardVideoController);
        setPlayImg();

    }

    /***
     * 设置播放按钮图片
     */
    public void setPlayImg(){
        ImageView start_play = findViewById(R.id.start_play);
        start_play.setBackgroundResource(res_play_img);

        ImageView iv_replay=findViewById(R.id.iv_replay);
        iv_replay.setPadding(0, 0, 0, 0);
        iv_replay.setBackgroundResource(0);
        iv_replay.setImageResource(res_play_img);
    }

    @Override
    public void setUrl(String url, Map headers) {
        super.setUrl(url, headers);
        autoLoadThumb(url);
    }
    public void autoLoadThumb(String url){
        post(new Runnable() {
            @Override
            public void run() {
                ImageView thumb = getController().findViewById(R.id.thumb);
                thumb.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                UiTool.setWH(thumb, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                Glide.with(AppTool.currActivity)
                        .load(ImgTool.convertByAliYun(url,thumb.getWidth(),0))
                        .into(thumb);
            }
        });
    }


    static List<WeakReference<KVideoViewNormal>> weakReferenceList=new ArrayList<>();
    public static void pauseAll(){
        for (WeakReference<KVideoViewNormal> kVideoViewParentWeakReference : weakReferenceList) {
            KVideoViewNormal kVideoViewParent = kVideoViewParentWeakReference.get();
            if(kVideoViewParent!=null){
                try {
                    kVideoViewParent.pause();
                }catch (Exception e){
                    LogTool.ex(e);
                }
            }
        }
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

    public BaseVideoController getController() {
        return mVideoController;
    }

    HashMap<Object,Object> playStateMap=new HashMap<Object,Object>(){
        {
            put(-1,"STATE_ERROR");
            put(0,"STATE_IDLE");
            put(1,"STATE_PREPARING");
            put(2,"STATE_PREPARED");
            put(3,"STATE_PLAYING");
            put(4,"STATE_PAUSED");
            put(5,"STATE_PLAYBACK_COMPLETED");
            put(6,"STATE_BUFFERING");
            put(7,"STATE_BUFFERED");
            put(8,"STATE_START_ABORT");
        }
    };
    @Override
    public void release(){
        try {
            LogTool.s("播放release");
            super.release();
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    @Override
    public void pause() {
        try {
            LogTool.s("播放暂停");
            super.pause();
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    @Override
    public void resume() {
        try {
            LogTool.s("播放resume");
            super.resume();
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    @Override
    public void start() {
        try {
            LogTool.s("播放start");
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

}

package utils.kkutils.ui.video.douyin2.views;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.dueeeke.videoplayer.controller.BaseVideoController;
import com.dueeeke.videoplayer.player.VideoView;

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
        post(new Runnable() {
            @Override
            public void run() {
                initAutoPause();
            }
        });
    }






    public BaseVideoController getController() {
        return mVideoController;
    }














    /***
     * 设置部分生命周期
     */
    public void initAutoPause() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof FragmentActivity) {
                FragmentActivity activity = (FragmentActivity) context;
                String tag = activity.toString();
                Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(tag);
                if (fragment == null) {
                    Lifecycle lifecycle = activity.getLifecycle();
                    lifecycle.addObserver(new LifecycleEventObserver() {
                        @Override
                        public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                            try {
                                if (Lifecycle.Event.ON_PAUSE == event) {
                                    pause();
                                }
                                if (Lifecycle.Event.ON_RESUME == event) {
                                    resume();
                                }
                                if (Lifecycle.Event.ON_START == event) {
                                    start();
                                }
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
            context = ((ContextWrapper) context).getBaseContext();
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

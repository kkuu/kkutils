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

public class KVideoView extends KVideoViewNormal {


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
    public OnStateChangeListener getOnStateChangeListener() {
        return new OnStateChangeListener() {
            @Override
            public void onPlayerStateChanged(int playerState) {

            }

            @Override
            public void onPlayStateChanged(int playState) {
                if(playState==STATE_PREPARING||playState==STATE_PLAYING){
                    try {
                        if(currVideoView!=null&&currVideoView!=KVideoView.this){//自动停止上一个
                            currVideoView.pause();
                        }
                        currVideoView=KVideoView.this;
                    }catch (Exception e){
                        LogTool.ex(e);
                    }
                }
            }
        };
    }

    public static VideoView currVideoView;

    @Override
    public void initDefalutController() {
        //不要默认controller
    }

    @Override
    public void autoLoadThumb(String url) {
        //不要自动加载， 主要是图片缩放
    }
}

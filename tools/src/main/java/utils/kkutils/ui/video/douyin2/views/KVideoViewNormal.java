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

import java.util.Map;

import utils.kkutils.AppTool;
import utils.kkutils.ImgTool;
import utils.kkutils.R;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.UiTool;
import utils.kkutils.ui.video.douyin2.library.cache.PreloadManager;

/***
 * 普通播放用的
 */
public class KVideoViewNormal extends KVideoView {


    public KVideoViewNormal(@NonNull Context context) {
        super(context);
    }

    public KVideoViewNormal(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public KVideoViewNormal(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public void init() {
        super.init();
        StandardVideoController standardVideoController = new StandardVideoController(getContext());
        standardVideoController.addDefaultControlComponent("", false);
        setVideoController(standardVideoController);
    }

    @Override
    public void setUrl(String url, Map headers) {
        super.setUrl(url, headers);
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
}

package douyin;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dueeeke.videoplayer.controller.BaseVideoController;
import com.dueeeke.videoplayer.controller.MediaPlayerControl;
import com.dueeeke.videoplayer.player.VideoView;
import com.dueeeke.videoplayer.util.L;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import douyin.util.simple.VideoBean;
import kk.kktools.R;
import utils.kkutils.ImgTool;

/**
 * 抖音  一般改这个就好了
 * Created by xinyu on 2018/1/6.
 */

public class DouYinController extends BaseVideoController<MediaPlayerControl> implements Serializable {


    private ImageView thumb;
    private ImageView mPlayBtn;

    public DouYinController(@NonNull Context context) {
        super(context);
    }

    public DouYinController(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DouYinController(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.kk_douyin_fragment_control;
    }

    @Override
    public void initView() {
        super.initView();
        thumb = mControllerView.findViewById(R.id.kk_iv_thumb);
        mPlayBtn = mControllerView.findViewById(R.id.kk_play_btn);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                doPauseResume();
            }
        });
    }

    public ImageView getThumb() {
        return thumb;
    }

    public ImageView getmPlayBtn() {
        return mPlayBtn;
    }

    public void initData(VideoBean item) {
        ImgTool.loadImage(item.getThumb(), thumb);

//        new ImgToolGlide().initBuilder(getContext(),thumb,item.getThumb(),0,0).into()
    }

    @Override
    public void setPlayState(int playState) {
        super.setPlayState(playState);

        switch (playState) {
            case VideoView.STATE_IDLE:
                L.e("STATE_IDLE " + hashCode());
                thumb.setVisibility(VISIBLE);
                break;
            case VideoView.STATE_PLAYING:
                L.e("STATE_PLAYING " + hashCode());

                thumb.setVisibility(GONE);
                mPlayBtn.setVisibility(GONE);
                break;
            case VideoView.STATE_PAUSED:
                L.e("STATE_PAUSED " + hashCode());
                thumb.setVisibility(GONE);
                mPlayBtn.setVisibility(VISIBLE);
                break;
            case VideoView.STATE_PREPARED:
                L.e("STATE_PREPARED " + hashCode());
                break;
            case VideoView.STATE_ERROR:
                L.e("STATE_ERROR " + hashCode());
                Toast.makeText(getContext(), R.string.dkplayer_error_message, Toast.LENGTH_SHORT).show();
                break;
        }

        /***
         * 需要进度就要打开这个歌
         */
        if (showProgress) {
            if (playState == VideoView.STATE_PLAYING) {
                removeCallbacks(mShowProgress);
                post(mShowProgress);
            }
        }

    }

    /***
     * 释放回调进度，可以通过setProgress 来获取
     */
    public boolean showProgress = false;

    /**
     * 需要就打开可以设置进度， 返回950 代表 间隔 1000-950 调用一次
     *
     * @return
     */
    @Override
    protected int setProgress() {
//        showProgress=true;
        long duration = mMediaPlayer.getDuration();
        long currentPosition = mMediaPlayer.getCurrentPosition();
//        seekBarShouYe.setMax(duration);
//        seekBarShouYe.setProgress(currentPosition);
//        LogTool.s(""+currentPosition+"  "+duration);
        return 950;
    }


    @Override
    public boolean showNetWarning() {
        //不显示移动网络播放警告
        return false;
    }
}

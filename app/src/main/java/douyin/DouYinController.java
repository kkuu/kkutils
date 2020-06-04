package douyin;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dueeeke.videoplayer.controller.BaseVideoController;
import com.dueeeke.videoplayer.player.VideoView;
import com.dueeeke.videoplayer.util.L;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import douyin.util.simple.KVideoView;
import douyin.util.simple.VideoBean;
import kk.kktools.R;
import utils.kkutils.ImgTool;
import utils.kkutils.common.LogTool;

/**
 * 抖音  一般改这个就好了
 * Created by xinyu on 2018/1/6.
 */

public class DouYinController extends BaseVideoController implements Serializable {


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
        return R.layout.kk_douyin_control;
    }

    @Override
    public void initView() {
        super.initView();
        thumb = findViewById(R.id.kk_iv_thumb);
        mPlayBtn = findViewById(R.id.kk_play_btn);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mControlWrapper.togglePlay();
//                doPauseResume();
            }
        });
    }

    public View getView(){
        return this;
    }
    public ImageView getThumb() {
        return thumb;
    }

    public ImageView getmPlayBtn() {
        return mPlayBtn;
    }

    protected List<VideoBean> mVideoList;
    protected int position;
    protected VideoBean item;
    public void initData(List<VideoBean> mVideoList, int position, VideoBean item) {
        ImgTool.loadImage(item.getThumb(), thumb);
        this.mVideoList=mVideoList;
        this.position=position;
        this.item=item;
    }

    @Override
    public void setPlayState(int playState) {
        super.setPlayState(playState);
        if(item!=null) LogTool.s(item.getUrl()+"   "+playState);
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


    @Override
    protected void setProgress(int duration, int position) {
        super.setProgress(duration, position);
    }

    @Override
    public boolean showNetWarning() {
        //不显示移动网络播放警告
        return false;
    }

    /***
     * 调用了start ， 但是不一定播放了,   加载下一页可以在这里判断
     * if(shiPinData.pageControl.hasMoreData()&&position==videoBeanList.size()-1)
     * @param videoView
     * @param position
     */
    public void onCommitStartPlay(KVideoView videoView, int position) {

    }
}

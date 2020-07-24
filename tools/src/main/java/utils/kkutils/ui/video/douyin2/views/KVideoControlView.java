package utils.kkutils.ui.video.douyin2.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.dueeeke.videoplayer.controller.ControlWrapper;
import com.dueeeke.videoplayer.controller.IControlComponent;
import com.dueeeke.videoplayer.player.VideoView;
import com.dueeeke.videoplayer.util.L;

import utils.kkutils.R;
import utils.kkutils.common.UiTool;
import utils.kkutils.ui.video.douyin2.library.cache.PreloadManager;


public  class KVideoControlView extends RelativeLayout implements IControlComponent {

    public ImageView thumb;
    public ImageView playBtn;

    public ControlWrapper mControlWrapper;
    public int mScaledTouchSlop;
    public int mStartX, mStartY;

    public KVideoControlView(@NonNull Context context) {
        super(context);
        init();
    }

    public KVideoControlView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KVideoControlView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
       initViews();
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mControlWrapper.togglePlay();
            }
        });
        mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    /***
     * 有需要可覆盖这个
     */
    public void initViews(){
//        LayoutInflater.from(getContext()).inflate(getLayoutId(), this, true);
//        thumb = findViewById(getThumbId());
//        playBtn = findViewById(getPlayBtnId());

        thumb=new ImageView(getContext());
        UiTool.setWH(thumb, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        thumb.setScaleType(ImageView.ScaleType.CENTER_CROP);
        addView(thumb);



        playBtn=new ImageView(getContext());
        playBtn.setVisibility(GONE);
        playBtn.setBackgroundResource(R.drawable.dkplayer_shape_play_bg);
        playBtn.setImageResource(R.drawable.dkplayer_selector_play_button);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(CENTER_IN_PARENT);
        addView(playBtn,layoutParams);


    }



    /***
     * 预加载
     * @param coverImgUrl
     * @param videoDownloadUrl
     * @param position
     */
    public void preLoad(String coverImgUrl,String videoDownloadUrl,int position){
        //开始预加载
        PreloadManager.getInstance(getContext()).addPreloadTask(videoDownloadUrl, position);
        Glide.with(getContext())
                .load(coverImgUrl)
                .into(thumb);
    }

    /**
     * 解决点击和VerticalViewPager滑动冲突问题
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mStartX = (int) event.getX();
                mStartY = (int) event.getY();
                return true;
            case MotionEvent.ACTION_UP:
                int endX = (int) event.getX();
                int endY = (int) event.getY();
                if (Math.abs(endX - mStartX) < mScaledTouchSlop
                        && Math.abs(endY - mStartY) < mScaledTouchSlop) {
                    performClick();
                }
                break;
        }
        return false;
    }

    @Override
    public void attach(@NonNull ControlWrapper controlWrapper) {
        mControlWrapper = controlWrapper;
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onVisibilityChanged(boolean isVisible, Animation anim) {

    }

    @Override
    public void onPlayStateChanged(int playState) {
        switch (playState) {
            case VideoView.STATE_IDLE:
                L.e("STATE_IDLE " + hashCode());
                thumb.setVisibility(VISIBLE);
                break;
            case VideoView.STATE_PLAYING:
                L.e("STATE_PLAYING " + hashCode());
                thumb.setVisibility(GONE);
                playBtn.setVisibility(GONE);
                break;
            case VideoView.STATE_PAUSED:
                L.e("STATE_PAUSED " + hashCode());
                thumb.setVisibility(GONE);
                playBtn.setVisibility(VISIBLE);
                break;
            case VideoView.STATE_PREPARED:
                L.e("STATE_PREPARED " + hashCode());
                break;
            case VideoView.STATE_ERROR:
                L.e("STATE_ERROR " + hashCode());
                Toast.makeText(getContext(), "STATE_ERROR", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onPlayerStateChanged(int playerState) {

    }

    @Override
    public void setProgress(int duration, int position) {

    }

    @Override
    public void onLockStateChanged(boolean isLocked) {

    }
}

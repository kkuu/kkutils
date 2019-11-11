package douyin.util.simple;


import android.view.View;
import android.view.ViewGroup;

import com.dueeeke.videoplayer.player.VideoView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import utils.kkutils.AppTool;
import utils.kkutils.common.LogTool;


/**
 *使用 recyclerView+ pagerSnapHelper实现
 */
public class DouYinAdapter extends DouYinAdapterParent {


    public RecyclerView recyclerView;

    public DouYinAdapter(Lifecycle lifecycle, ViewGroup container, List<VideoBean> mVideoList) {
        super(lifecycle, container, mVideoList);
    }

    @Override
    public void initViewsImp() {
        recyclerView=new RecyclerView(AppTool.getApplication());
        recyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        container.addView(recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(AppTool.getApplication(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(this);

        final PagerSnapHelper pagerSnapHelper = new PagerSnapHelper() {
            @Override
            public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
                int position = super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
                if (position > mPlayingPosition) {
                    mIsReverseScroll = false;
                } else if (position < mPlayingPosition) {
                    mIsReverseScroll = true;
                }
                mCurrentPosition = position;
                return position;
            }


        };
        pagerSnapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setItemViewCacheSize(5);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mPreloadManager.resumePreload(mCurrentPosition, mIsReverseScroll);
                } else {
                    mPreloadManager.pausePreload(mCurrentPosition, mIsReverseScroll);
                }

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    View viewIdle = pagerSnapHelper.findSnapView(recyclerView.getLayoutManager());
                    int position = recyclerView.getChildAdapterPosition(viewIdle);
                    startPlay( position);
                }

            }
        });

        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                try {
                    int position=recyclerView.getChildAdapterPosition(view);
                    LogTool.s("释放： "+position);
                    VideoView videoView = view.findViewWithTag(position);
                    videoView.release();
                }catch (Exception e){
                    LogTool.ex(e);
                }

            }
        });
    }

    @Override
    public void setCurrentPosition(final int position) {
        recyclerView.scrollToPosition(position);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                startPlay( position);
            }
        });
    }



}
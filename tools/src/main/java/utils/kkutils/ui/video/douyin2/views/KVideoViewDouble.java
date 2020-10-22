package utils.kkutils.ui.video.douyin2.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dueeeke.videoplayer.render.TextureRenderView;

/***
 * 镜像播放
 */
public class KVideoViewDouble extends KVideoViewNormal {
    public KVideoViewDouble(@NonNull Context context) {
        super(context);
    }

    public KVideoViewDouble(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public KVideoViewDouble(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /***
     * 设置镜像view
     * @param copyImageView
     */
    public void setCopyImageView(ImageView copyImageView) {
        this.copyImageView = copyImageView;
    }

    public ImageView copyImageView;

    /**
     * 初始化视频渲染View
     */
    protected void addDisplay() {
        if (mRenderView != null) {
            mPlayerContainer.removeView(mRenderView.getView());
            mRenderView.release();
        }
        mRenderView =new TextureRenderView(getContext()){
            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
                super.onSurfaceTextureUpdated(surface);
                if(copyImageView!=null){
                    Bitmap bitmap = getBitmap();
                    copyImageView.setImageBitmap(bitmap);
                }
            }
        };

        mRenderView.attachToPlayer(mMediaPlayer);
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        mPlayerContainer.addView(mRenderView.getView(), 0, params);


    }
}

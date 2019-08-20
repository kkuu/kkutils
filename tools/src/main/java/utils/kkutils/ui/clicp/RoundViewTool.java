package utils.kkutils.ui.clicp;

import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import java.util.Arrays;

import utils.kkutils.R;
import utils.kkutils.common.CommonTool;


/**
 * 圆角图片
 *
 * @author
 */
public class RoundViewTool {
    public float[] radii = new float[8];   // top-left, top-right, bottom-right, bottom-left
    public Path mClipPath;                 // 剪裁区域路径
    public Path mClipPath28;//api 大于27 的时候用这个裁剪

    public Paint mPaint;                   // 画笔
    public boolean mRoundAsCircle = true; // 圆形
    public RectF mLayer;                   // 画布图层大小

    public View targetView;

    public RoundViewTool(View targetView, AttributeSet attrs) {
        this.targetView = targetView;

        mClipPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);


        int round = CommonTool.dip2px(10);
        radii = new float[]{round, round, round, round, round, round, round, round};


        if (targetView != null && targetView.getContext() != null && attrs != null) {
            TypedArray typedArray = targetView.getContext().obtainStyledAttributes(attrs, R.styleable.RoundImageView);
            float radius = typedArray.getDimension(R.styleable.RoundImageView_radius, 0);

            if (radius != 0) {
                setRoundCornerPx((int) radius);
            } else {
                int kk_radius_topLeftRadius = (int) typedArray.getDimension(R.styleable.RoundImageView_topLeftRadius, 0);
                int kk_radius_topRightRadius = (int) typedArray.getDimension(R.styleable.RoundImageView_topRightRadius, 0);
                int kk_radius_bottomLeftRadius = (int) typedArray.getDimension(R.styleable.RoundImageView_bottomLeftRadius, 0);
                int kk_radius_bottomRightRadius = (int) typedArray.getDimension(R.styleable.RoundImageView_bottomRightRadius, 0);
                if (
                        kk_radius_topLeftRadius != 0
                                || kk_radius_topRightRadius != 0
                                || kk_radius_bottomLeftRadius != 0
                                || kk_radius_bottomRightRadius != 0
                ) {
                    setRoundCornerPx(kk_radius_topLeftRadius, kk_radius_topRightRadius, kk_radius_bottomLeftRadius, kk_radius_bottomRightRadius);
                }

            }

        }


    }


    /**
     * 设置圆角
     *
     * @param cornerDp
     */
    public void setRoundCornerDp(int cornerDp) {
        setRoundCornerPx(CommonTool.dip2px(cornerDp));
    }

    public void setRoundCornerDp(int cornerDpTopLeft, int cornerDpTopRight, int cornerDpBottomLeft, int cornerDpBottomRight) {
        setRoundCornerPx(CommonTool.dip2px(cornerDpBottomLeft),CommonTool.dip2px(cornerDpTopRight),CommonTool.dip2px(cornerDpBottomLeft),CommonTool.dip2px(cornerDpBottomRight));
    }

    public void setRoundCornerPx(int cornerPx) {
        Arrays.fill(radii, cornerPx);
        refreshRound();
    }

    public void setRoundCornerPx(int cornerPxTopLeft, int cornerPxTopRight, int cornerPxBottomLeft, int cornerPxBottomRight) {
        radii = new float[]{cornerPxTopLeft,cornerPxTopLeft,cornerPxTopRight,cornerPxTopRight,cornerPxBottomLeft,cornerPxBottomLeft,cornerPxBottomRight,cornerPxBottomRight};
        refreshRound();
    }





    public void refreshRound() {
        mRoundAsCircle = false;
        refreshSize();

    }

    public void refreshSize() {
        int w = targetView.getWidth();
        int h = targetView.getHeight();
        mLayer = new RectF(0, 0, w, h);
        mClipPath.reset();
        if (mRoundAsCircle) {
            PointF center = new PointF(w / 2, h / 2);
            mClipPath.addCircle(center.x, center.y, w / 2, Path.Direction.CW);
        } else {
            mClipPath.addRoundRect(mLayer, radii, Path.Direction.CW);
        }

        mClipPath28 = new Path();
        mClipPath28.addRect(mLayer, Path.Direction.CW);
        mClipPath28.op(this.mClipPath, Path.Op.DIFFERENCE);
        targetView.invalidate();
    }

    public void onDrawPre(Canvas canvas) {
        canvas.saveLayer(mLayer, null, Canvas.ALL_SAVE_FLAG);//必须， 否则效果不对
    }

    public void onDrawAfter(Canvas canvas) {
        if (Build.VERSION.SDK_INT <= 27) {
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));//设置合成模式
            canvas.drawPath(mClipPath, mPaint);//不能直接画， 一定用 drawPath
        } else {
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));//设置合成模式
            canvas.drawPath(mClipPath28, mPaint);//不能直接画， 一定用 drawPath
        }
        canvas.restore();
    }


}

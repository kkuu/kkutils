package utils.kkutils.ui.progress;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import utils.kkutils.R;
import utils.kkutils.common.ResourcesTool;
import utils.kkutils.common.UiTool;

/**
 * abc on 2017/4/6.
 */

public class KKProgressYuan extends View {
    /**
     * 圆形进度条控件
     * Author: JueYes jueyes_1024@163.com
     * Time: 2019-08-07 15:38
     */


        public Paint mBackPaint, mProgPaint;   // 绘制画笔
    public RectF mRectF;       // 绘制区域
    public int[] mColorArray;  // 圆环渐变色
    public int mProgress;      // 圆环进度(0-100)



    public int angleStart;//开始角度  0


    public int angleEnd;//结束角度  360

    public int backColor;
    public int progressColor;

    public float backWidth;
    public float progressWidth;

        public KKProgressYuan(Context context) {
            this(context, null);
        }

        public KKProgressYuan(Context context, @Nullable AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public KKProgressYuan(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            @SuppressLint("Recycle")
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.KKProgressYuan);
            // 初始化进度
            mProgress = typedArray.getInteger(R.styleable.KKProgressYuan_kk_progress_progress, 90);

            angleStart=typedArray.getInteger(R.styleable.KKProgressYuan_kk_progress_angleStart, 0);
            angleEnd=typedArray.getInteger(R.styleable.KKProgressYuan_kk_progress_angleStart, 360);




            backWidth=typedArray.getDimension(R.styleable.KKProgressYuan_kk_progress_backWidth, 5);
            backColor=typedArray.getColor(R.styleable.KKProgressYuan_kk_progress_backColor, Color.LTGRAY);

            progressWidth=typedArray.getDimension(R.styleable.KKProgressYuan_kk_progress_progressWidth, 10);
            progressColor=typedArray.getColor(R.styleable.KKProgressYuan_kk_progress_progressColor, Color.BLUE);

            // 初始化进度圆环渐变色
            int startColor = typedArray.getColor(R.styleable.KKProgressYuan_kk_progress_progressStartColor, -1);
            int endColor = typedArray.getColor(R.styleable.KKProgressYuan_kk_progress_progressEndColor, -1);
            if (startColor != -1 && endColor != -1) mColorArray = new int[]{startColor, endColor};
            else mColorArray = null;
            typedArray.recycle();



            initPaint();




        }

        public void initPaint(){
            // 初始化背景圆环画笔
            mBackPaint = new Paint();
            mBackPaint.setStyle(Paint.Style.STROKE);    // 只描边，不填充
            mBackPaint.setStrokeCap(Paint.Cap.ROUND);   // 设置圆角
            mBackPaint.setAntiAlias(true);              // 设置抗锯齿
            mBackPaint.setDither(true);                 // 设置抖动


            mBackPaint.setStrokeWidth(backWidth);
            mBackPaint.setColor(ResourcesTool.getColor(backColor));

            // 初始化进度圆环画笔
            mProgPaint = new Paint();
            mProgPaint.setStyle(Paint.Style.STROKE);    // 只描边，不填充
            mProgPaint.setStrokeCap(Paint.Cap.ROUND);   // 设置圆角
            mProgPaint.setAntiAlias(true);              // 设置抗锯齿
            mProgPaint.setDither(true);                 // 设置抖动

            mProgPaint.setStrokeWidth(progressWidth);
            mProgPaint.setColor(ResourcesTool.getColor(progressColor));

           if(mColorArray!=null&&mColorArray.length>1) mProgPaint.setShader(new LinearGradient(0, 0, 0, getMeasuredWidth(), mColorArray, null, Shader.TileMode.MIRROR));

            invalidate();
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int viewWide = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
            int viewHigh = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
            int mRectLength = (int) ((viewWide > viewHigh ? viewHigh : viewWide) - (mBackPaint.getStrokeWidth() > mProgPaint.getStrokeWidth() ? mBackPaint.getStrokeWidth() : mProgPaint.getStrokeWidth()));
            int mRectL = getPaddingLeft() + (viewWide - mRectLength) / 2;
            int mRectT = getPaddingTop() + (viewHigh - mRectLength) / 2;
            mRectF = new RectF(mRectL, mRectT, mRectL + mRectLength, mRectT + mRectLength);

            // 设置进度圆环渐变色
            if (mColorArray != null && mColorArray.length > 1)
                mProgPaint.setShader(new LinearGradient(0, 0, 0, getMeasuredWidth(), mColorArray, null, Shader.TileMode.MIRROR));
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int angle=angleEnd-angleStart;
            if(angle<0){
                angle+=360;
            }

            canvas.drawArc(mRectF, angleStart, angle, false, mBackPaint);
            canvas.drawArc(mRectF, angleStart, angle * mProgress / 100, false, mProgPaint);
        }

        // ---------------------------------------------------------------------------------------------

    /**
     *
     * @param angleStart  进度条 的开始角度
     */
    public void setAngleStart(int angleStart) {
        this.angleStart = angleStart;
        initPaint();
    }

    /***
     *
     * @param angleEnd 进度条的结束角度
     */
    public void setAngleEnd(int angleEnd) {
        this.angleEnd = angleEnd;
        initPaint();
    }

        /**
         * 获取当前进度
         *
         * @return 当前进度（0-100）
         */
        public int getProgress() {
            return mProgress;
        }

        /**
         * 设置当前进度
         *
         * @param progress 当前进度（0-100）
         */
        public void setProgress(int progress) {
            this.mProgress = progress;
            invalidate();
        }

        /**
         * 设置当前进度，并展示进度动画。如果动画时间小于等于0，则不展示动画
         *
         * @param progress 当前进度（0-100）
         * @param animTime 动画时间（毫秒）
         */
        public void setProgress(int progress, long animTime) {
            if (animTime <= 0) setProgress(progress);
            else {
                ValueAnimator animator = ValueAnimator.ofInt(mProgress, progress);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mProgress = (int) animation.getAnimatedValue();
                        invalidate();
                    }
                });
                animator.setInterpolator(new OvershootInterpolator());
                animator.setDuration(animTime);
                animator.start();
            }
        }

        /**
         * 设置背景圆环宽度
         *
         * @param width 背景圆环宽度
         */
        public void setBackWidth(int width) {
            this.backWidth=width;
            initPaint();

        }

        /**
         * 设置背景圆环颜色
         *
         * @param color 背景圆环颜色
         */
        public void setBackColor( int color) {
            this.backColor=color;
            initPaint();

        }

        /**
         * 设置进度圆环宽度
         *
         * @param width 进度圆环宽度
         */
        public void setProgWidth(int width) {
            this.progressWidth=width;
            initPaint();

        }



        /**
         * 设置进度圆环颜色(支持渐变色)
         *
         * @param colorArray 渐变色集合
         */
        public void setProgColor( int ...colorArray) {
            if(colorArray==null)return;

            if(colorArray.length==1){
                this.progressColor=colorArray[0];
            }else {
                mColorArray = new int[colorArray.length];
                for (int index = 0; index < colorArray.length; index++)
                    mColorArray[index] = ResourcesTool.getColor( colorArray[index]);
            }
            initPaint();

        }
}

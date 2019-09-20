package utils.kkutils.ui.animation;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import java.util.Random;

import utils.kkutils.common.CommonTool;
import utils.kkutils.parent.KKViewOnclickListener;


public class FloatView extends RelativeLayout {
    private static final long ANIMATION_TIME = 1000;
    private static final long ANIMATION_DEFAULT_TIME = 2000;
    float dDistance= CommonTool.dip2px(5);
    private OnItemClickListener mListener=new OnItemClickListener() {
        @Override
        public void itemClick(View view) {

        }
    };
    public FloatView(Context context) {
        super(context);
        init();
    }

    public FloatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FloatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public void init(){
        setClipChildren(true);
    }




    //添加小球,需要在getwidth 不为0的时候添加， 不如用post方法统一添加
    public void addFloatView(View floatview) {
        floatview.measure(0,0);
        addView(floatview);


        setChildViewPosition(floatview);
      //  initAnim(floatview);
        initFloatAnim(floatview);

        floatview.setOnClickListener(new KKViewOnclickListener() {
            @Override
            public void onClickKK(View view) {
                //设置接口回调
                if(animOnEnd)animRemoveView(view);
                if(mListener!=null)mListener.itemClick(view);
            }
        });

    }

    /***
     * 完成收是否播放动画
     * @param animOnEnd
     */
    public void setAnimOnEnd(boolean animOnEnd) {
        this.animOnEnd = animOnEnd;
    }

    public boolean animOnEnd=true;

    //FloatView上下抖动的动画
    private void initFloatAnim(View view) {
        floatAnim(view,dDistance,ANIMATION_TIME);

    }

    public static void floatAnim(View view,float distance,long time){
        Animation anim = new TranslateAnimation(0,0,0,distance);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(time);
        anim.setRepeatCount(Integer.MAX_VALUE);
        anim.setRepeatMode(Animation.REVERSE);//反方向执行
        view.startAnimation(anim);
    }

    @Override
    public void removeAllViews() {//删掉动画
        for(int i=0;i<getChildCount();i++){
            getChildAt(i).clearAnimation();
        }
        super.removeAllViews();
    }

    //FloatView初始化时动画
    private void initAnim(View view) {
        view.setAlpha(0);
        view.setScaleX(0);
        view.setScaleY(0);
        view.animate().alpha(1).scaleX(1).scaleY(1).setDuration(ANIMATION_DEFAULT_TIME).start();
    }



    //设置子view的位置
    private void setChildViewPosition(View childView) {
        //设置随机位置
        Random randomX = new Random();
        Random randomY = new Random();
        float x = randomX.nextFloat() * (getWidth() - childView.getMeasuredWidth());
        float y = randomY.nextFloat() * (getHeight() - childView.getMeasuredHeight()-dDistance);

        if(y<dDistance)y=dDistance;

        childView.setX(x);
        childView.setY(y);
    }


    private void animRemoveView(final View view) {
//
        final float y=view.getY();

        ValueAnimator animator = ValueAnimator.ofFloat(y,0);
        animator.setDuration(1000);
        animator.setInterpolator(new AccelerateInterpolator());

        //动画更新的监听
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float Value = (float) animation.getAnimatedValue();
                view.setAlpha(Value/y);
                view.setTranslationY(Value);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                removeView(view);
            }
        });
        animator.start();
    }

    public interface OnItemClickListener{
        void  itemClick(View view);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
}
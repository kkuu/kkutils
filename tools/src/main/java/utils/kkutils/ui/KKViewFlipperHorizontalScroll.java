package utils.kkutils.ui;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ViewFlipper;

/***
 * 可以竖向滚动的同时  如果控件过长 还可以水平滚动
 *
 *  添加完子控件后 调用这个方法开始
 *  {@link #beginStartScroll()}
 */
public class KKViewFlipperHorizontalScroll extends ViewFlipper {
    OnShowListener onShowListener;


    public KKViewFlipperHorizontalScroll(Context context) {
        super(context);
        init();
    }

    public KKViewFlipperHorizontalScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        setAutoStart(true);

        initDefaultAnim();


        setOnShowListener(new KKViewFlipperHorizontalScroll.OnShowListener() {
            @Override
            public void onShow(View view) {
                if(view instanceof KKHorizontalScroll){
                    ((KKHorizontalScroll) view).startScroll();
                }
            }
        });
    }
    public void initDefaultAnim(){
        if(getInAnimation()==null){

            int type=Animation.RELATIVE_TO_SELF;
            TranslateAnimation animationIn = new TranslateAnimation(type,0,type,0,type,1,type,0);
            animationIn.setDuration(500);
            setInAnimation(animationIn);

            TranslateAnimation animationOut = new TranslateAnimation(type,0,type,0,type,0,type,-1);
            animationOut.setDuration(500);
            setOutAnimation(animationOut);
        }

    }

    public interface OnShowListener {
        public void onShow(View view);
    }

    public void setOnShowListener(OnShowListener onShowListener) {
        this.onShowListener = onShowListener;
    }

    @Override
    public void setDisplayedChild(int whichChild) {
        super.setDisplayedChild(whichChild);
        if (onShowListener != null) onShowListener.onShow(getChildAt(getDisplayedChild()));
    }

    /***
     * 添加完子控件后 调用这个方法开始
     */
    public void beginStartScroll(){
        try {
            KKHorizontalScroll scroll = (KKHorizontalScroll) getChildAt(0);
            scroll.startScroll();
        }catch (Exception e){
            e.printStackTrace();
        }
        startFlipping();
    }


    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        addViewInHorizontalScroll(child,index,params);
    }

     void addViewInHorizontalScroll(View child, int index, ViewGroup.LayoutParams params) {
        KKHorizontalScroll scroll=new KKHorizontalScroll(getContext());
        ViewGroup.LayoutParams lp=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        scroll.setLayoutParams(lp);
        scroll.setFillViewport(true);
        scroll.addView(child);

        scroll.setKKScrollChangeListener(new KKHorizontalScroll.ScrollChangeListener() {
            @Override
            public void onScrollChange(KKHorizontalScroll KKHorizontalScroll, KKHorizontalScroll.ScrollState scrollState) {
                if(scrollState== KKViewFlipperHorizontalScroll.KKHorizontalScroll.ScrollState.stop){
                    showNext();
                    startFlipping();
                }
                if(scrollState== KKViewFlipperHorizontalScroll.KKHorizontalScroll.ScrollState.running){
                    stopFlipping();
                }
            }

        });

        super.addView(scroll,index,params);
    }




    public static class KKHorizontalScroll extends HorizontalScrollView {
        public KKHorizontalScroll(Context context) {
            super(context);
            init();
        }

        public KKHorizontalScroll(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public KKHorizontalScroll(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }


        Handler handler;

        public void init() {
            handler = new Handler();
            setScrollBarSize(0);
        }

        public void setKKScrollChangeListener(ScrollChangeListener scrollChangeListener) {
            this.scrollChangeListener = scrollChangeListener;
        }

        ScrollChangeListener scrollChangeListener;
        ScrollState scrollState = ScrollState.stop;

        public static enum ScrollState {
            running, stop
        }

        public static interface ScrollChangeListener {
            public void onScrollChange(KKHorizontalScroll KKHorizontalScroll, ScrollState scrollState);
        }

        public void notifyState(ScrollState scrollState) {
            this.scrollState = scrollState;
            if (scrollChangeListener != null) {
                scrollChangeListener.onScrollChange(this, scrollState);
            }
        }

        Runnable scrollRunnable = new Runnable() {
            int x = 5;
            @Override
            public void run() {
                scrollBy(x, 0);
                if (getScrollX() != getMaxScrollX()) {
                    handler.postDelayed(this, 30);
                } else {
                    stopScroll();
                }
            }
        };


        public int getMaxScrollX() {
            int maxScroll = getChildAt(0).getWidth() - getWidth();
            return maxScroll;
        }

        public void startScroll() {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (getScrollX() == getMaxScrollX()) {//不能滚动了
                        return;
                    }
                    notifyState(ScrollState.running);
                    handler.post(scrollRunnable);
                }
            }, 1000);
        }

        public void stopScroll() {
            post(new Runnable() {
                @Override
                public void run() {
                    handler.removeCallbacksAndMessages(null);
                    notifyState(ScrollState.stop);
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setScrollX(0);
                        }
                    }, 1000);
                }
            });
        }

    }
}

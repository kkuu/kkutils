package utils.kkutils.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import utils.kkutils.common.CommonTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.ui.graphics.KKDrawableTool;

public class KKQuickIndexBar extends LinearLayout {
    /**
     * 显示数据
     */
    public String[] dataList = new String[]{"A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    public int backgroundColorDown = Color.TRANSPARENT;
    public float textSizeDp = 10;
    public int textColor = Color.parseColor("#333333");
    public int centerBgColor= Color.parseColor("#e8e8e8");
    public int index = 0;
    public static final String tagChild="child";
    /***
     * 最里面的控件的w ，正方形的  textview
     */
    public int itemChildW= CommonTool.dip2px(16);



    public OnIndexChangedListener mListener;

    public KKQuickIndexBar(Context context) {
        this(context, null);
    }

    public KKQuickIndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KKQuickIndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    protected void init(AttributeSet attrs) {
        setOrientation(VERTICAL);
        initTouch();
        for (int i = 0; i < dataList.length; i++) {
            addView(getItem(i));
        }
    }

    /***
     * 初始化itemView
     * @param index
     * @return
     */
    public View getItem(int index) {
        String str = dataList[index];
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);

        RelativeLayout parent=new RelativeLayout(getContext());
        parent.setGravity(Gravity.CENTER);
        parent.setLayoutParams(params);

        TextView text = new TextView(getContext());
        text.setText(str);
        text.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSizeDp);
        text.setTextColor(textColor);
        text.setGravity(Gravity.CENTER);
        text.setLayoutParams(new ViewGroup.LayoutParams(itemChildW, itemChildW));
        text.setTag(tagChild);
        text.setIncludeFontPadding(false);

        parent.addView(text);

        return parent;
    }
    public void setSelect(String indexStr){
        for (int i = 0; i < dataList.length; i++) {
            if(dataList[i].equals(indexStr)){
                setSelect(i);
            }
        }
    }
    public void setSelect(int index){
        View child = getChildAt(index).findViewWithTag(tagChild);
        for(int i=0;i<getChildCount();i++){
            getChildAt(i).findViewWithTag(tagChild).setBackground(null);
        }
        ShapeDrawable shapeDrawable=new ShapeDrawable(new OvalShape()){
            Paint fillPaint;
            {
                fillPaint=new Paint();
                fillPaint.setColor(Color.parseColor("#e8e8e8"));
            }
            @Override
            public void draw(Canvas canvas) {
                getShape().draw(canvas,fillPaint);
            }
        };
        int w=child.getWidth() > child.getHeight() ? child.getHeight() : child.getWidth();
        shapeDrawable.setIntrinsicWidth(w);
        shapeDrawable.setIntrinsicHeight(w);
        shapeDrawable.setBounds(0,0,w,w);
        child.setBackground(shapeDrawable);
    }

    RelativeLayout centerView;
    int centerViewW= CommonTool.dip2px(100);
    Runnable centerViewHideRunnable=new Runnable() {
        @Override
        public void run() {
            if(centerView!=null){
                centerView.setAlpha(1);
                centerView.animate().alpha(0).setDuration(1000).start();
            }
        }
    };
    /***
     * 居中的大的
     */
    public void showCenterView(String textStr){
        if(centerView==null){
            centerView=new RelativeLayout(getContext());
            centerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            centerView.setGravity(Gravity.CENTER);
            ViewGroup rootView = (ViewGroup) getRootView();
            rootView.addView(centerView);
            centerView.setClickable(false);
            centerView.setFocusable(false);
            centerView.setAlpha(0);


            TextView textView = new TextView(getContext());
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 40);
            textView.setTextColor(textColor);
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(new ViewGroup.LayoutParams(centerViewW, centerViewW));
            textView.setTag(tagChild);



            textView.setBackground(KKDrawableTool.getCircleDrawable(centerBgColor));

            centerView.addView(textView);


        }
        TextView textView=centerView.findViewWithTag(tagChild);
        textView.setText(textStr);

        centerView.removeCallbacks(centerViewHideRunnable);


        centerView.animate().alpha(1).setDuration(100).start();

        centerView.postDelayed(centerViewHideRunnable,500);

    }

    public void initTouch() {

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        setBackgroundColor(backgroundColorDown);
                        handle(v, event);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        handle(v, event);
                        return true;
                    case MotionEvent.ACTION_UP:
                        setBackgroundColor(Color.TRANSPARENT);
                        handle(v, event);
                        return true;
                }
                return false;
//                return IndexBar.super.onTouchEvent(event);
            }
        });
    }


    protected void handle(View v, MotionEvent event) {
        int y = (int) event.getY();
        int height = getChildAt(0).getHeight();

        int position = y / height;

        if (position < 0) {
            position = 0;
        } else if (position >= dataList.length) {
            position = dataList.length - 1;
        }
        LogTool.s("y:" + y + "  index" + position);
        if (position != index) {
            index = position;
            String selectStr = dataList[position];
            boolean showIndicator = event.getAction() != MotionEvent.ACTION_UP;
            setSelect(index);
            if (mListener != null) {
                mListener.onIndexChanged(index, selectStr, showIndicator);
            }
            if(showIndicator){
                showCenterView(selectStr);
            }
        }

    }

    public void setOnIndexChangedListener(OnIndexChangedListener listener) {
        mListener = listener;
    }

    public interface OnIndexChangedListener {
        void onIndexChanged(int selectIndex, String selectStr, boolean showIndicator);
    }
}
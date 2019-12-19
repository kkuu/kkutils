package kk.kktools.tuya;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class EditViewParent extends FrameLayout {

    public static int mode_huabi=1;//画笔模式
    public static int mode_wenzi=2;//文字模式
    public int mode=mode_huabi;

    public EditViewParent(Context context) {
        super(context);
        init();
    }

    public EditViewParent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditViewParent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public void init(){
        setBackgroundColor(Color.parseColor("#333333"));
        EditViewCanvasView editViewCanvasView = new EditViewCanvasView(getContext());
        editViewCanvasView.parent=this;
        addView(editViewCanvasView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(mode==mode_wenzi){
            if(ev.getAction()== MotionEvent.ACTION_DOWN){
                addEditText(ev.getX(),ev.getY());
            }
        }
        return mode!=mode_huabi;
    }
    public void setMode(int mode) {
        this.mode=mode;
    }
    public ArrayList<Runnable> dongzuo=new ArrayList<>();
    public void  addDongZuo(Runnable o){
        dongzuo.add(o);
    }

    public void addViewWithDongZuo(final View view){
        addView(view);
        addDongZuo(new Runnable() {
            @Override
            public void run() {
                removeView(view);
            }
        });
    }
    public void addEditText(float x, float y){
        EditText editText=new EditText(getContext());
        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        editText.setLayoutParams(layoutParams);
        editText.setHint("请输入文字");
        editText.setTranslationX(x);
        editText.setTranslationY(y);
        addViewWithDongZuo(editText);
    }
    public void back(){
        if(dongzuo.size()>0){
            Runnable o = dongzuo.get(dongzuo.size()-1);
            o.run();
        }
    }
}

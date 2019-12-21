package kk.kktools.tuya;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import utils.kkutils.common.LogTool;


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
    CheckBox checkBoxWenZi;
    EditViewCanvasView editViewCanvasView;
    ViewGroup.LayoutParams lp_wrap=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    public void initBtns(){
        LinearLayout btnParent=new LinearLayout(getContext());
        addView(btnParent,lp_wrap);



        {//上一步
            Button btn_back=new Button(getContext());
            btn_back.setText("上一步");
            btn_back.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    back();
                }
            });
            btnParent.addView(btn_back);
        }
        {//文字 选择
            checkBoxWenZi=new CheckBox(getContext());
            checkBoxWenZi.setText("文字");
            checkBoxWenZi.setLayoutParams(new ViewGroup.LayoutParams(lp_wrap));
            checkBoxWenZi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        setMode(mode_wenzi);
                    }else {
                        setMode(mode_huabi);
                    }
                }
            });
            btnParent.addView(checkBoxWenZi);
        }


    }
    public void init(){

        setBackgroundColor(Color.parseColor("#333333"));
        editViewCanvasView = new EditViewCanvasView(getContext());
        editViewCanvasView.parent=this;
        addView(editViewCanvasView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        initBtns();

    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogTool.s("onInterceptTouchEvent"+ev.getAction()+"  "+ev.getX()+"  "+ev.getY());
        if(mode==mode_wenzi){
            if(ev.getAction()== MotionEvent.ACTION_DOWN){
                addEditText(ev.getX(),ev.getY());
            }
            if(ev.getAction()== MotionEvent.ACTION_UP){
                checkBoxWenZi.setChecked(false);
            }
        }
        return false;
    }
    public void setMode(int mode) {
        this.mode=mode;
    }
    public ArrayList<Runnable> dongzuo=new ArrayList<>();
    public void  addDongZuo(Runnable o){
        dongzuo.add(o);
    }


    public void addEditText(float x, float y){
        EditText editText=new EditText(getContext());
        editText.setHint("请输入文字");
        addView(editText,-2,-2,x,y);
    }
    public void addView(View view,int w,int h,float x,float y){
        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(w, h);
        view.setLayoutParams(layoutParams);
        view.setTranslationX(x);
        view.setTranslationY(y);
        addViewWithDongZuo(view);
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


    public void back(){
        LogTool.s("back:"+dongzuo.size());
        if(dongzuo.size()>0){
            Runnable o = dongzuo.get(dongzuo.size()-1);
            o.run();
            dongzuo.remove(o);
            editViewCanvasView.invalidate();
        }
    }
}

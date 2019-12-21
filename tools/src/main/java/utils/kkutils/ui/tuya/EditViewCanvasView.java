package utils.kkutils.ui.tuya;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import utils.kkutils.R;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.ShareTool;
import utils.kkutils.ui.dialog.DialogTool;

public class EditViewCanvasView extends FrameLayout {
    public EditViewCanvasView(Context context) {
        super(context);
        init();
    }

    public EditViewCanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditViewCanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public Paint paint=new Paint();

    public ArrayList<Runnable> dongzuo=new ArrayList<>();
    public ImageView imageViewBg;//背景图片控件，


    public void init(){
        initPaint();
        setBackgroundColor(Color.WHITE);
        initBgImage();//图片面板
        initPenPanel();//画笔面板
    }

    /**
     * 设置图片
     */
    public void initBgImage(){
        if(imageViewBg==null){
            imageViewBg=new ImageView(getContext());
            imageViewBg.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            addView(imageViewBg);
        }
    }

    /**
     * 初始化画笔
     */
    public void initPaint(){
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
    }

    /***
     * 初始化画笔面板路径和事件相关
     */
    public void initPenPanel(){
        final List<Path> pathList=new ArrayList<>();//画笔路径
        final View pathView=new View(getContext()){
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                for(Path path:pathList){
                    canvas.drawPath(path,paint);
                }
            }
        };
        addView(pathView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        pathView.setOnTouchListener(new OnTouchListener() {
            public Path currDownPath;
            public void addPath(final Path path){
                pathList.add(0,path);
                addDongZuo(new Runnable() {
                    @Override
                    public void run() {
                        pathList.remove(path);
                        pathView.invalidate();
                    }
                });
            }
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.v("kk","event"+event.getX()+"  "+event.getY());
                if(event.getAction()== MotionEvent.ACTION_DOWN){
                    currDownPath=new Path();
                    currDownPath.moveTo(event.getX(),event.getY());
                }
                if(currDownPath==null)return true;
                if(event.getAction()== MotionEvent.ACTION_MOVE){
                    currDownPath.lineTo(event.getX(),event.getY());
                    if(!pathList.contains(currDownPath)){
                        addPath(currDownPath);
                    }
                }
                if(event.getAction()== MotionEvent.ACTION_UP){
                    currDownPath.moveTo(event.getX(),event.getY());
                }
                pathView.invalidate();
                return true;
            }
        });

    }



    /***
     * 添加一个可拖动的TextView
     * @param text
     */
    public void addDragTextView(String text){
        final TextView textView=new TextView(getContext());
        textView.setText(text);
        new DragTool().bindView(this, textView, new OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogTool.initNormalInputDialog("请输入文字", "", "确定", new DialogTool.OnDialogInputEnd() {
                    @Override
                    public void onInputEnd(final EditText editText) {
                        textView.setText(editText.getText().toString().trim());
                    }
                },"取消",textView.getText().toString(),-1).show();
            }
        });
        addViewWithDongZuo(textView,-2,-2,300,300);
    }

    /***
     * 可以回退删除的 添加控件
     * @param view
     * @param w
     * @param h
     * @param x
     * @param y
     */
    public void addViewWithDongZuo(View view,int w,int h,float x,float y){
        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(w, h);
        view.setLayoutParams(layoutParams);
        view.setTranslationX(x);
        view.setTranslationY(y);
        addViewWithDongZuo(view);
    }
    /***
     * 可以回退删除的 添加控件
     * @param view
     */
    public void addViewWithDongZuo(final View view){
        addView(view);
        addDongZuo(new Runnable() {
            @Override
            public void run() {
                removeView(view);
            }
        });
    }

    /***
     * 保存到相册
     */
    public void baocun(){
        clearFocus();
        ShareTool.saveView2Gallery(this,"图片1");
    }

    /***
     * 退回上一步操作
     */
    public void back(){
        LogTool.s("back:"+dongzuo.size());
        if(dongzuo.size()>0){
            Runnable o = dongzuo.get(dongzuo.size()-1);
            o.run();
            dongzuo.remove(o);
        }
    }

    public void  addDongZuo(Runnable o){
        dongzuo.add(o);
    }



}

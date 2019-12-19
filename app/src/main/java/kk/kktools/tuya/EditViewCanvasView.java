package kk.kktools.tuya;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EditViewCanvasView extends View {


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
    Paint paint=new Paint();
    public void init(){
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
    }
    List<Path> pathList=new ArrayList<>();
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.v("kk","onDraw");
        for(Path path:pathList){
            canvas.drawPath(path,paint);
        }
    }
    public Path getPath(){
        if(pathList.size()>0){
            return pathList.get(0);
        }
        return new Path();
    }
    public EditViewParent parent;
    public EditViewParent getEditParent(){
        if(parent==null){
            throw new RuntimeException("需要设置父窗口为：EditViewParent");
        }
        return parent;
    }
    public void addPath(final Path path){
        pathList.add(0,path);
        getEditParent().addDongZuo(new Runnable() {
            @Override
            public void run() {
                pathList.remove(path);
            }
        });
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.v("kk","event"+event.getX()+"  "+event.getY());
        if(event.getAction()== MotionEvent.ACTION_DOWN){
            addPath(new Path());
            getPath().moveTo(event.getX(),event.getY());
        }
        if(event.getAction()== MotionEvent.ACTION_MOVE){
            getPath().lineTo(event.getX(),event.getY());
        }
        if(event.getAction()== MotionEvent.ACTION_UP){
            getPath().moveTo(event.getX(),event.getY());
        }
        invalidate();
        return true;
    }

}

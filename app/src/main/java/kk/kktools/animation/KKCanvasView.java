package kk.kktools.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import utils.kkutils.parent.KKParentFragment;

public class KKCanvasView extends View {

    public KKCanvasView(Context context) {
        super(context);
        init();
    }

    public KKCanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KKCanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public KKCanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    Paint paint=new Paint();
    Path path=new Path();
    public void init(){
        paint.setColor(Color.BLUE);
        path.reset();
        path.moveTo(0, 0);
        path.lineTo(100, 100);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
    }
}

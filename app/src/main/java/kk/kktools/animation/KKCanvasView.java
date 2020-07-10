package kk.kktools.animation;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import utils.kkutils.common.UiTool;
import utils.kkutils.parent.KKParentFragment;

public class KKCanvasView extends RelativeLayout {

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
        paint.setStrokeWidth(10);
        paint.setTextSize(20);
        paint.setStyle(Paint.Style.STROKE);
        path.reset();
//        path.moveTo(0, 0);
//        path.lineTo(100, 100);
//        path.lineTo(300, 200);


        path.moveTo(0,0);
        path.lineTo(300,300);
        path.quadTo(50,500,300,700);
        path.cubicTo(600,600,500,250,50,800);
        path.quadTo(500,0,0,0);

        View v=new View(getContext());
        UiTool.setWH(v, 100, 100);
        v.setBackgroundColor(Color.RED);
        addView(v);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator mAnimator = ObjectAnimator.ofFloat(v, View.X, View.Y, path);
                mAnimator.setDuration(5000);
                mAnimator.start();

            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
        canvas.drawText("fadsf", 100, 100, paint);
    }
}

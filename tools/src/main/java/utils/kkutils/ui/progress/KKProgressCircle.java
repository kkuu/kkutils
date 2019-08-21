package utils.kkutils.ui.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * abc on 2017/4/6.
 */

public class KKProgressCircle extends View {
    protected int maxProgess = 100;
    protected int currProgess = 67;
    protected int colorBg = Color.parseColor("#ffffff");//整个背景， 也就是内圆背景
    protected int colorBgIn = Color.parseColor("#dcdcdc");//圆环缺口bg
    protected int colorBegin = Color.parseColor("#e61f5b");//圆环覆盖 背景开始， 可以和下面一样就是纯色
    protected int colorEnd = Color.parseColor("#f19d40");//圆环覆盖 背景结束， 可以和下面一样就是纯色
    protected Paint paintIn;
    protected Paint paintClear;
    protected  Paint paintHuan;
    protected  int xCenter = 0;
    protected int yCenter = 0;
    protected  int neiYuanWidth = 20;
    protected int waiyuanWidth = 40;

    public KKProgressCircle(Context context) {
        super(context);
        init();
    }

    public KKProgressCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public KKProgressCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
    }
    public void setColorAndWidth(String colorBgStr,String colorBgInStr,String colorBeginStr,String colorEndStr,int neiYuanWidthPx,int waiyuanWidthPx){
        colorBg = Color.parseColor(colorBgStr);
        colorBgIn = Color.parseColor(colorBgInStr);
        colorBegin = Color.parseColor(colorBeginStr);
        colorEnd = Color.parseColor(colorEndStr);
        this.neiYuanWidth=neiYuanWidthPx;
        this.waiyuanWidth=waiyuanWidthPx;
        initPaint();
        invalidate();
    }
    public void initPaint() {
        paintIn = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintIn.setColor(colorBgIn);

        paintClear = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintClear.setColor(colorBg);

        paintHuan = new Paint(Paint.ANTI_ALIAS_FLAG);
        Shader mShader = new LinearGradient(0, 0, 0, getWidth(), new int[]{colorBegin, colorEnd}, null, Shader.TileMode.CLAMP);
        paintHuan.setShader(mShader);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        xCenter = w / 2;
        yCenter = w / 2;

        if (paintClear == null) {
            initPaint();
        }

        //清屏
        canvas.drawRect(new Rect(0, 0, getWidth(), getHeight()), paintClear);

        //画内圆
        canvas.drawCircle(xCenter, yCenter, xCenter - (waiyuanWidth - neiYuanWidth) / 2, paintIn);
        canvas.drawCircle(xCenter, yCenter, xCenter - (waiyuanWidth - neiYuanWidth) / 2 - neiYuanWidth, paintClear);

        //画外园
        canvas.drawArc(new RectF(0, 0, w, w), -90, currProgess * 360 / maxProgess, true, paintHuan);
        canvas.drawCircle(xCenter, yCenter, xCenter - waiyuanWidth, paintClear);
    }

    public void setProgress(int currProgess) {
        this.currProgess = currProgess;
        postInvalidate();
    }
}

package utils.kkutils.ui.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import utils.kkutils.common.CommonTool;

/**
 * 暂时按照宽高300dp 做的
 */
public class KKProgressKeDuSimple extends View {

    public KKProgressKeDuSimple(Context context) {
        super(context);
        init();
    }

    public KKProgressKeDuSimple(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KKProgressKeDuSimple(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }
    public void init(){
        {
            setRotation(startAngle);

            paint=new Paint();
            paint.setAntiAlias(true);
            paint.setColor(colorLine);

        }
    }

    Paint paint;
    int colorLine=Color.parseColor("#67FFA6");
    int smallLineHeight= CommonTool.dip2px(0.75f);
    int smallLineWidth= CommonTool.dip2px(3);

    int step=2;//多少度 画一条线
    int startAngle=90+50;//开始角度
    int endAngle=90-50+360;//结束角度





    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX=getWidth()/2;
        for(int progress=0;progress<=endAngle-startAngle;progress++){
            canvas.rotate(1,centerX,centerX);
           if(progress%step==0) drawProgress(canvas);
        }
    }

    public void drawProgress(Canvas canvas){
        int w=getWidth();
        int centerX=getWidth()/2;
        paint.setColor(colorLine);
        paint.setStrokeWidth(smallLineHeight);
        int startXSmall=w- smallLineWidth;
        canvas.drawLine(startXSmall,centerX,startXSmall+smallLineWidth,centerX,paint);
    }




}

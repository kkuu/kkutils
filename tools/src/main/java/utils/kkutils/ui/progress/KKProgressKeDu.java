package utils.kkutils.ui.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import utils.kkutils.common.CommonTool;

/**
 * 暂时按照宽高300dp 做的
 */
public class KKProgressKeDu extends View {
    public int lineCount=120;//总刻度 总共有多少格进度,不改格子大小就不改这个
    public int startProgress=0;//开始刻度 0就是右边水平位置
    public int endProgress=startProgress+lineCount;//结束的刻度位置， 比如 startProgress+lineCount  就是所有格子都画，否则只画一部分
    public int currProgress=30;//当前进度位置


    public KKProgressKeDu(Context context) {
        super(context);
        init();
    }

    public KKProgressKeDu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KKProgressKeDu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }
    public void init(){
        {
            paint=new Paint();
            paint.setAntiAlias(true);
            paint.setColor(colorLineBg);
            setBottomNotShow();
            setProgress(75);
        }
    }
    /***
     * 显示动画
     */
    public void showAnim(){
        final int endProgress=currProgress;
        currProgress=startProgress;
        postDelayed(new Runnable() {
            @Override
            public void run() {


                if(currProgress<endProgress){
                    currProgress+=1;
                    invalidate();
                    postDelayed(this,20);
                }

            }
        },20);//没20毫秒刷新一次
    }

    /***
     * 设置底部一部分不显示，就是缺一块
     */
    public void setBottomNotShow(){
        startProgress=44;
        endProgress=136;
    }

    /***
     * 设置进度， 百分比 比如 50
     * @param progress
     */
    public void setProgress(float progress){
        currProgress= startProgress+(int) ((endProgress-startProgress)*progress/100.0);
        invalidate();
    }

    Paint paint;
    int colorLineBg=Color.parseColor("#E3E7EB");
    int colorLine=Color.parseColor("#F84658");
    int smallLineHeight=CommonTool.dip2px(2);
    int smallLineWidth=CommonTool.dip2px(5);
    int bigLineWidth=CommonTool.dip2px(16);
    int bigLineWidthLong=CommonTool.dip2px(26);
    int bigLineHeight=CommonTool.dip2px(3);




    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX=getWidth()/2;

        for(int progress=0;progress<=lineCount*2;progress++){
            drawProgress(canvas,progress);
            canvas.rotate(360.0f/lineCount,centerX,centerX);
        }
    }

    public void drawProgress(Canvas canvas, int progress){
        int w=getWidth();
        int centerX=getWidth()/2;

        if(progress>=startProgress&&progress<=endProgress){
            {//画外圆
                int stopX = drawWaiYuan(canvas, colorLineBg, progress);//外部灰色圆
                if(progress==currProgress||progress==0)drawSanJiao(canvas,stopX+5,centerX-bigLineHeight/5);//外部三角

                if(progress<=currProgress){//外部红色圆
                    drawWaiYuan(canvas,colorLine,progress);
                }
            }
            {//画内圆
                paint.setColor(colorLineBg);
                paint.setStrokeWidth(smallLineHeight);
                int startXSmall=w-CommonTool.dip2px(68);
                canvas.drawLine(startXSmall,centerX,startXSmall+smallLineWidth,centerX,paint);
            }
        }
    }








    public int drawWaiYuan(Canvas canvas, int color,int progress){

        paint.setColor(color);
        paint.setStrokeWidth(bigLineHeight);
        int centerX=getWidth()/2;
        int startX=getWidth()-CommonTool.dip2px(50);
        int stopX=startX+(isLongProgress(progress)?bigLineWidthLong:bigLineWidth);

        canvas.drawLine(startX,centerX,stopX,centerX,paint);
        return stopX;
    }

    int sanJiaoW=CommonTool.dip2px(10);
    int sanJiaoH=CommonTool.dip2px(15);
    Path pathSanJiao=null;
    public void drawSanJiao(Canvas canvas, int x, int y){
        paint.setAntiAlias(true);
        paint.setColor(colorLine);
        if(pathSanJiao==null){
            pathSanJiao=new Path();
        }
        pathSanJiao.reset();
        pathSanJiao.moveTo(x, y);// 此点为多边形的起点
        pathSanJiao.lineTo(x+sanJiaoH, y-sanJiaoW/2);
        pathSanJiao.lineTo(x+sanJiaoH, y+sanJiaoW/2);
        canvas.drawPath(pathSanJiao, paint);
    }
    /**
     * 是否长的进度条
     * @param progress
     * @return
     */
    public boolean isLongProgress(int progress){
        int longProgress=(endProgress-startProgress)/4;
        int realProgress= (progress-startProgress);
        if(progress==startProgress||progress==endProgress){
            return false;
        }
        return realProgress%longProgress==0;
    }



}

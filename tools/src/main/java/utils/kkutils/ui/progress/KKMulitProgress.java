package utils.kkutils.ui.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;


import java.util.ArrayList;
import java.util.List;

import utils.kkutils.common.CommonTool;


/**
 * 一个进度条里面包含了多个进度
 */
public class KKMulitProgress extends View {
    public KKMulitProgress(Context context) {
        super(context);
        init();
    }

    public KKMulitProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KKMulitProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }



    public void setProgress(float progress){
        List<MulitProgressData> listProgress=new ArrayList<>();
        listProgress.add(new KKMulitProgress.MulitProgressData(0,progress));
        setProgressList(listProgress);
    }
    public void setProgressList(List<MulitProgressData> listProgress) {
        this.listProgress = listProgress;
    }
    //进度数据
    public List<MulitProgressData> listProgress = new ArrayList<>();
    //进度条颜色
    public int colorProgress = Color.parseColor("#484848");
    //背景色
    public int colorBackground = Color.parseColor("#cccccc");
    //最小刻度
    public int minProgress = 0;
    //最大刻度
    public int maxProgress = 100;
    //背景用的
    public MulitProgressData mulitProgressDataBackground = new MulitProgressData(0, maxProgress);
    //进度画笔
    public Paint paint = new Paint();
    //背景画笔
    public Paint paintBackground = new Paint();
    //圆角大小
    public int radius = CommonTool.dip2px(4);


    public static class MulitProgressData {
        public float beginProgress;
        public float width;

        /***
         *
         * @param beginProgress  大于等于 minprogress
         * @param endProgress    小于等于  maxprogress
         */
        public MulitProgressData(float beginProgress, float endProgress) {
            this.beginProgress = beginProgress;
            this.width = endProgress - beginProgress;
        }

        float viewWidth;
        float viewHeight;
        float allProgress;

        public MulitProgressData init(KKMulitProgress mulitProgress, float allProgress) {
            this.viewWidth = mulitProgress.getWidth();
            this.viewHeight = mulitProgress.getHeight();
            this.allProgress = allProgress;
            return this;
        }

        public float getBeginReal() {
            return beginProgress / allProgress * viewWidth;
        }

        public float getWidthReal() {
            return width / allProgress * viewWidth;
        }

        RectF rectF = new RectF();

        public RectF getRectF() {
            if (rectF == null) {
                rectF = new RectF();
            }
            rectF.left = getBeginReal();
            rectF.top = 0;
            rectF.right = rectF.left + getWidthReal();
            rectF.bottom = viewHeight;
            return rectF;
        }
    }


    public void init() {
//        listProgress.add(new MulitProgressData(0, 10));
//        listProgress.add(new MulitProgressData(20, 40));
//        listProgress.add(new MulitProgressData(50, 90));
//        listProgress.add(new MulitProgressData(95, 99));
        setProgress(0);
        paint.setColor(colorProgress);
        paintBackground.setColor(colorBackground);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int all = maxProgress - minProgress;
        mulitProgressDataBackground.init(this, all);
        canvas.drawRoundRect(mulitProgressDataBackground.getRectF(), radius, radius, paintBackground);
        for (MulitProgressData data : listProgress) {
            data.init(this, all);
            canvas.drawRoundRect(data.getRectF(), radius, radius, paint);
        }
    }
}

package utils.kkutils.ui.graphics;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.RoundRectShape;

import java.util.Arrays;

import utils.kkutils.common.CommonTool;

public class KKDrawableTool {
    /**
     * 获取一个圆角矩形
     * @param color
     * @param radius
     * @return
     */
    public static Drawable getRoundRectDrawable(int color, int radius){
        GradientDrawable drawable=new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(color);
        drawable.setCornerRadius(radius);
        return drawable;
    }

    /**
     * 获取一个圆形
     * @param color
     * @return
     */
    public static Drawable getCircleDrawable(int color){
        GradientDrawable drawable=new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setColor(color);
        return drawable;
    }

    /***
     * 获取一个五角星
     * @param color
     * @return
     */
    public static Drawable getStarDrawable(int color){
        int size= CommonTool.dip2px(1000);
        Path path=new Path();
        path.addPath(KKPathTool.regularStarPath(5,size/2));
        path.close();
        return getPathDrawable(path,size,size,color);
    }

    /***
     * https://blog.csdn.net/xiangzhihong8/article/details/78278931/
     * @param color
     * @return
     */
    public static Drawable getPathDrawable(Path path, int w, int h, int color){
        ShapeDrawable shapeDrawable=new ShapeDrawable(new PathShape(path,w,h));
        shapeDrawable.getPaint().setColor(color);
        return shapeDrawable;
    }


    @Deprecated
    /***
     * 只是备份有这种方式， 但是不用这个
     */
    public static Drawable getRoundRectDrawableOld(int color, int radius){
        float[] radiusArray= new float[8];
        Arrays.fill(radiusArray,radius);
        /**
         * ArcShape：扇形shape
         * OvalShape：椭圆形shape
         * PathShape：构造一个可根据Path绘制的shape
         * RectShape：矩形shape
         * RoundRectShape：圆角矩形shape
         *
         * 作者：code希必地
         * 链接：https://www.jianshu.com/p/07d38715c0b4
         * 来源：简书
         * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
         */
        ShapeDrawable drawable=new ShapeDrawable(new RoundRectShape(radiusArray,null,null)){
            Paint fillPaint;
            {
                fillPaint=new Paint();
                fillPaint.setColor(color);
            }
            @Override
            public void draw(Canvas canvas) {
                getShape().draw(canvas,fillPaint);
            }
        };
        return drawable;
    }

}

package utils.kkutils.ui.imageview;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import utils.kkutils.common.CommonTool;

public class ImageViewMatrix extends androidx.appcompat.widget.AppCompatImageView {
    public ImageViewMatrix(Context context) {
        super(context);
    }
    public ImageViewMatrix(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ImageViewMatrix(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        initCrop();
    }

    /**
     * 上下边距
     */
    int  boundY= CommonTool.dip2px(200);
    public void initCrop(){
        if(getDrawable()==null)return;

        float scale;
        float dx = 0, dy = 0;

        {//   ScaleType.CENTER_CROP 用的这个里面的代码
            final int dwidth = getDrawable().getIntrinsicWidth();
            final int dheight = getDrawable().getIntrinsicHeight();

            final int vwidth = getWidth() - getPaddingLeft() - getPaddingRight();
            final int vheight = getHeight() - getPaddingTop() - getPaddingBottom()+boundY;

            if (dwidth * vheight > vwidth * dheight) {
                scale = (float) vheight / (float) dheight;
                dx = (vwidth - dwidth * scale) * 0.5f;
            } else {
                scale = (float) vwidth / (float) dwidth;
                dy = (vheight - dheight * scale) * 0.5f;
            }
        }




        setScaleType(ScaleType.MATRIX);
        matrixBegin=new Matrix();
        matrixBegin.postScale(scale,scale);
        matrixBegin.postTranslate(Math.round(dx), Math.round(dy)-boundY/2);
        setImageMatrix(matrixBegin);
    }
    Matrix matrixBegin=getImageMatrix();

    public void translate(float x,float y){
        Matrix imageMatrix = new Matrix(matrixBegin);
        imageMatrix.postTranslate(x,y);
        setImageMatrix(imageMatrix);
    }
    public void translateAdd(float x,float y){
        Matrix imageMatrix = new Matrix(getImageMatrix());
        imageMatrix.postTranslate(x,y);
        setImageMatrix(imageMatrix);
    }


}

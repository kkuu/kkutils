package utils.kkutils.ui.imageview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import utils.kkutils.common.CommonTool;

/***
 * view 滚动 里面图片也滚动的控件
 *
 *  recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
 *             @Override
 *             public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
 *                 super.onScrollStateChanged(recyclerView, newState);
 *             }
 *             @Override
 *             public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
 *                 super.onScrolled(recyclerView, dx, dy);
 *                 for(int i=0;i<recyclerView.getChildCount();i++){
 *                     ImageViewScroll imgv_shouye_big=recyclerView.getChildAt(i).findViewById(R.id.imgv_shouye_big);
 *                     if(imgv_shouye_big!=null){
 *                         imgv_shouye_big.translateRefresh();
 *                     }
 *                 }
 *             }
 *         });
 *
 *
 */
public class ImageViewScroll extends androidx.appcompat.widget.AppCompatImageView {
    public ImageViewScroll(Context context) {
        super(context);
    }

    public ImageViewScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewScroll(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        post(new Runnable() {
            @Override
            public void run() {
                initCrop();
            }
        });
    }

    float scale=1;
    float centerX;//显示图片中间，需要移动的x  比如-100
    float scrollAll;//最打滚动高度，  最小是0 贴合顶部， 最大是 -(drawableH-viewH)
    float defaultScrollHeightScale=1.8f;//默认可移动高比 view 高  多少倍，就是包含看不见的部分
    public void initCrop(){
        if(getDrawable()==null)return;

         int drawableW = getDrawable().getIntrinsicWidth();
         int drawableH = getDrawable().getIntrinsicHeight();

         int viewW = getWidth() - getPaddingLeft() - getPaddingRight();
         int viewH = getHeight() - getPaddingTop() - getPaddingBottom();

        {//初始话 是否需要拉伸
            float minDrawableW=viewW;
            float minDrawableH=viewH*defaultScrollHeightScale;
            if(drawableW<minDrawableW||drawableH<minDrawableH){//需要拉伸
                float scale1=minDrawableW/drawableW;
                float scale2=minDrawableH/drawableH;
                scale=Math.max(scale1,scale2);
                drawableW*=scale;
                drawableH*=scale;
            }
        }

        centerX=-(drawableW-viewW)/2;
        scrollAll=-(drawableH-viewH);
        translateRefresh();
    }


    /***
     * 当前在屏幕中的比
     * @return
     */
    public float getPersentInWindow(){
        int [] point=new int[2];
        getLocationInWindow(point);
        float y=point[1];//当前y
        float h=CommonTool.getWindowSize().y;//总共可显示高
        float bi =  y / h;
        if(bi>1)bi=1;
        if(bi<0)bi=0;
        return bi;
    }

    /***
     * 滚动的时候调用
     */
    public void translateRefresh(){
        float persentInWindow = getPersentInWindow();
        float v = persentInWindow * scrollAll;
        if(v>0)v=0;
        if(v<scrollAll)v=scrollAll;
        translate(centerX,v);
    }








    Matrix matrixBegin=getImageMatrix();
    Matrix imageMatrixCurr=new Matrix();

    /**
     * 直接移动y
     * @param x
     * @param y
     */
    public void translate(float x,float y){
        imageMatrixCurr.set(matrixBegin);
        imageMatrixCurr.postScale(scale,scale);
        imageMatrixCurr.postTranslate(x,y);
        setImageMatrix(imageMatrixCurr);
    }

    /***
     * 在上次的移动基础上 增加移动y
     * @param x
     * @param y
     */
    public void translateAdd(float x,float y){
        imageMatrixCurr.set(getImageMatrix());
        imageMatrixCurr.postScale(scale,scale);
        imageMatrixCurr.postTranslate(x,y);
        setImageMatrix(imageMatrixCurr);
    }

}

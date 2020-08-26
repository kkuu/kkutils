package utils.kkutils.ui.textview;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.HashMap;
import java.util.Map;

import utils.kkutils.AppTool;
import utils.kkutils.R;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.UiTool;
import utils.kkutils.common.ViewTool;

/***\
 * 使用方法
 *  text.setText(spanTextTool.builder);
 *
 *  /
 *         new SpanTextTool("").addString("年时奋进砺奋进", R.color.kk_tv_h0)
 *                 .addStringSpan("1", SpanTextTool.getImageGifSpan(R.drawable.kk_test_gif, 30, 30,tv_span)).addString("年时砥年时砥砺奋进年时砥砺奋进年时年时砥年时砥砺奋进年时砥砺奋进年时", R.color.kk_tv_h0)
 *                 .setTextView(tv_span);
 */
public class SpanTextTool {
    public SpannableStringBuilder builder;
    public SpanTextTool(CharSequence text){
        builder=new SpannableStringBuilder(text);
    }
    public  SpanTextTool addString(String textAdd,int colorResId){
        if(textAdd==null)return this;
        int color= UiTool.getColorByResId(colorResId);
        addStringSpan(textAdd,new ForegroundColorSpan(color));
        return this;
    }
    public  SpanTextTool addString(String textAdd,int colorResId,int textSizeDp){
        if(textAdd==null)return this;


        int color= UiTool.getColorByResId(colorResId);
        ForegroundColorSpan colorSpan=new ForegroundColorSpan(color);
        AbsoluteSizeSpan sizeSpan=new AbsoluteSizeSpan(textSizeDp,true);
        addStringSpan(textAdd,colorSpan,sizeSpan);
        return this;
    }

    public  SpanTextTool addStringSpan(String textAdd, Object ...spans){
        if(textAdd==null)return this;
        int begin=builder.length();
        int end=begin+textAdd.length();
        builder.append(textAdd);
        for(int i=0;i<spans.length;i++){
            builder.setSpan(spans[i],begin,end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return this;
    }


    /***
     * 获取imagespan  w 不传用图片本身大小，传了就用传的
     * @param drawableId
     * @param w
     * @param h
     * @return
     */
    public static ImageSpan getImageSpan(int drawableId,int w, int h ){
        final Drawable drawable = AppTool.getApplication().getResources().getDrawable(drawableId);
        if(w>0){
            drawable.setBounds(0,0, w, h);
        }else {
            drawable.setBounds(0,0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }

        return  getImageSpan(drawable);
    }

    /***
     * 图片比文字大  一定要传w ，h
     * @param drawableId
     * @param w
     * @param h
     * @param textView
     * @return
     */
    static Map<Object ,SpanAsyncDrawable> map=new HashMap<>();
    static Map<Object ,Drawable.Callback> mapCallback=new HashMap<>();
    static int gif_key= ViewTool.initKey();

    /***
     *  这里一个drawableId  对应 一个SpanAsyncDrawable  ， 也就是 底层只加载一次，加载多了会加载失败
     * @param drawableId
     * @param w
     * @param h
     * @param textView
     * @return
     */
    public static ImageSpan getImageGifSpan(Object drawableId,int w, int h ,TextView textView){
        Object tag = ViewTool.getTag(textView, gif_key);
        if(tag==null){
            tag=new Drawable.Callback() {
                @Override
                public void invalidateDrawable(@NonNull Drawable who) {
                    try {
                        if(textView!=null)textView.postInvalidate();
                    }catch (Exception e){
                        LogTool.ex(e);
                    }
                }

                @Override
                public void scheduleDrawable(@NonNull Drawable who, @NonNull Runnable what, long when) {

                }

                @Override
                public void unscheduleDrawable(@NonNull Drawable who, @NonNull Runnable what) {

                }
            };
            ViewTool.setTag(textView, tag, gif_key);

        }
        Drawable.Callback callback = (Drawable.Callback) ViewTool.getTag(textView, gif_key);
        SpanAsyncDrawable spanDrawable  = map.get(drawableId);
        if(spanDrawable!=null&&spanDrawable.getmDrawable()!=null){
            spanDrawable.addCallBack( callback);
            return getImageSpan(spanDrawable);
        }
        spanDrawable=new SpanAsyncDrawable();
        spanDrawable.setCallback(callback);// 第一次不能用add  这里面是弱引用， 要在下面保持强引用
        mapCallback.put(drawableId, callback);//保持强引用， 免得被低内存时候销毁了


        map.put(drawableId, spanDrawable);
        if(w>0){
            spanDrawable.setBounds(0,0,w,h);
        }
        SpanAsyncDrawable finalSpanDrawable = spanDrawable;
        Glide.with(AppTool.getApplication()).asGif().load(drawableId).into(new CustomViewTarget(textView) {
            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
            }
            @Override
            public void onResourceReady(@NonNull Object o, @Nullable Transition transition) {
                GifDrawable resource= (GifDrawable) o;
                if(w>0){
                    resource.setBounds(0,0,w,h);
                }else {
                    resource.setBounds(0,0,resource.getIntrinsicWidth(),resource.getIntrinsicHeight());
                }
                finalSpanDrawable.setDrawable(resource);
                resource.setLoopCount(GifDrawable.LOOP_FOREVER);
                resource.start();
            }
            @Override
            protected void onResourceCleared(@Nullable Drawable placeholder) {

            }
        });

        return getImageSpan(spanDrawable);
    }

    public static ImageSpan getImageSpan(Drawable drawable){
        //设置图片
        ImageSpan imageSpan=new ImageSpan(drawable){
            public int getSize(Paint paint, CharSequence text, int start, int end,
                               Paint.FontMetricsInt fm) {
                Drawable d = getDrawable();
                Rect rect = d.getBounds();
                if (fm != null) {
                    Paint.FontMetricsInt fmPaint=paint.getFontMetricsInt();
                    int fontHeight = fmPaint.bottom - fmPaint.top;
                    int drHeight=rect.bottom-rect.top;

                    int top= drHeight/2 - fontHeight/4;
                    int bottom=drHeight/2 + fontHeight/4;

                    fm.ascent=-bottom;
                    fm.top=-bottom;
                    fm.bottom=top;
                    fm.descent=top;
                }
                return rect.right;
            }
            //剧中设置
            @Override
            public void draw(Canvas canvas, CharSequence text, int start, int end,
                             float x, int top, int y, int bottom, Paint paint) {
                Drawable b = getDrawable();
                canvas.save();
                int fontHeight = (int) paint.getTextSize();
                int drHeight=b.getBounds().bottom-b.getBounds().top;
                int transY = (fontHeight-drHeight)/2-paint.getFontMetricsInt().descent/2;
                canvas.translate(x, y-drHeight-transY);
                b.draw(canvas);
                canvas.restore();

            }
        };
        return imageSpan;
    }

    public void setTextView(TextView textView){
        if(textView!=null)textView.setText(builder);
    }


    public void test(){

        //字体颜色
        ForegroundColorSpan foregroundColorSpan=new ForegroundColorSpan(Color.parseColor("#FF4040"));
        //背景颜色
        BackgroundColorSpan backgroundColorSpan=new BackgroundColorSpan(Color.parseColor("#FF4040"));

        //模糊效果(BlurMaskFilter)
        MaskFilter filter=new BlurMaskFilter(4.0f, BlurMaskFilter.Blur.OUTER);
        MaskFilterSpan maskFilterSpan=new MaskFilterSpan(filter);


        //删除线效果
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        //下划线效果
        UnderlineSpan un=new UnderlineSpan();

        //文本字体绝对的大小
        AbsoluteSizeSpan  ab=new AbsoluteSizeSpan(30,true);
        //文本字体相对的大小
        RelativeSizeSpan relativeSizeSpan=new RelativeSizeSpan(3.0f);
        //基于X的缩放（ScaleXSpan ）
        ScaleXSpan scaleXSpan=new ScaleXSpan(3.0f);

        //字体样式：粗体、斜体等（StyleSpan ）
        StyleSpan styleSpan=new StyleSpan(Typeface.BOLD);

        //上标（SubscriptSpan ）
        SuperscriptSpan superscriptSpan=new SuperscriptSpan();
        //下标
        SubscriptSpan subscriptSpan=new SubscriptSpan();

        //字体、大小、样式和颜色（TextAppearanceSpan）
        //TextAppearanceSpan ab=new TextAppearanceSpan(this, android.R.style.TextAppearance_Medium);


        //文字字体
        TypefaceSpan typefaceSpan=new TypefaceSpan ("serif");

        // 文本超链接（URLSpan ）
        URLSpan urlSpan=new URLSpan("http://www.baidu.com");





        final Drawable drawable = AppTool.getApplication().getResources().getDrawable(android.R.drawable.btn_star);
        //设置图片
        ImageSpan imageSpan=new ImageSpan(drawable);
        //设置图片 基于底部
        DynamicDrawableSpan dynamicDrawableSpan=new DynamicDrawableSpan() {
            @Override
            public Drawable getDrawable() {
                drawable.setBounds(0,0,50,50);
                return drawable;
            }
        };



        { //文本可点击，有点击事件

            ClickableSpan clickableSpan=new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    CommonTool.showToast("点击");
                }
                @Override
                public void updateDrawState(TextPaint ds) {
                    //去掉可点击文字的下划线
                    ds.setUnderlineText(false);
                }
            };
//            stringBuilder.setSpan(clickableSpan,0,3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            textView.setMovementMethod(LinkMovementMethod.getInstance());
//            textView.setText(stringBuilder);
        }
    }


}

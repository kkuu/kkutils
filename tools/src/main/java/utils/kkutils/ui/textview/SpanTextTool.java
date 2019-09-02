package utils.kkutils.ui.textview;

import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.MaskFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.ParcelableSpan;
import android.text.Spannable;
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
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import utils.kkutils.AppTool;
import utils.kkutils.R;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.UiTool;

/***\
 * 使用方法
 *  text.setText(spanTextTool.builder);
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

    public  SpanTextTool addStringSpan(String textAdd, ParcelableSpan ...spans){
        if(textAdd==null)return this;
        int begin=builder.length();
        int end=begin+textAdd.length();
        builder.append(textAdd);
        for(int i=0;i<spans.length;i++){
            builder.setSpan(spans[i],begin,end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return this;
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

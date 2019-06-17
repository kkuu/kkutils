package utils.kkutils.ui.textview;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import utils.kkutils.common.UiTool;

public class SpanTextTool {
    public SpannableStringBuilder builder;
    public SpanTextTool(CharSequence text){
        builder=new SpannableStringBuilder(text);
    }
    public  SpanTextTool addString(String textAdd,int colorResId){
        if(textAdd==null)return this;
        int color= UiTool.getColorByResId(colorResId);

        int begin=builder.length();
        int end=begin+textAdd.length();
        builder.append(textAdd);

        ForegroundColorSpan colorSpan=new ForegroundColorSpan(color);
        builder.setSpan(colorSpan,begin,end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return this;
    }
}

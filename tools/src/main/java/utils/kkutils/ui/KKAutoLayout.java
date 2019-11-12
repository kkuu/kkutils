package utils.kkutils.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.flexbox.AlignContent;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;

/**
 * Created by ishare on 2016/6/28.
 * 自动换行的
 */

public class KKAutoLayout extends FlexboxLayout {


    public KKAutoLayout(Context context) {
        super(context);
        init();
    }

    public KKAutoLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KKAutoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        setFlexWrap(FlexWrap.WRAP);
        setAlignItems(AlignItems.STRETCH);
        setAlignContent(AlignContent.STRETCH);
    }

}

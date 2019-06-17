package utils.kkutils.ui.textview;


import android.content.Context;
import android.util.AttributeSet;

/**
 * abc on 2017/11/16.
 */

public class BoldTextView extends androidx.appcompat.widget.AppCompatTextView {

    public BoldTextView(Context context) {
        super(context);
        init();
    }

    public BoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        getPaint().setFakeBoldText(true);
    }

}

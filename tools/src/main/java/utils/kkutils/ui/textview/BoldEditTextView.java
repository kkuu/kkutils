package utils.kkutils.ui.textview;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * abc on 2017/11/16.
 */

public class BoldEditTextView extends androidx.appcompat.widget.AppCompatEditText {

    public BoldEditTextView(Context context) {
        super(context);
        init();
    }

    public BoldEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BoldEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        getPaint().setFakeBoldText(true);
    }

}

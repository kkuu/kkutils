package utils.kkutils.ui;

import android.content.Context;
import android.util.AttributeSet;

public class KKRadioButton extends androidx.appcompat.widget.AppCompatRadioButton {
    public KKRadioButton(Context context) {
        super(context);
    }

    public KKRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KKRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        getPaint().setFakeBoldText(checked);
    }
}

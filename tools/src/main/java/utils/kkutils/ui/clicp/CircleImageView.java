package utils.kkutils.ui.clicp;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import utils.kkutils.ui.KKImageView;

public class CircleImageView extends RoundImageView {


    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init() {
        super.init();
        roundViewTool.mRoundAsCircle=true;
        setScaleType(ScaleType.CENTER_CROP);
    }
}

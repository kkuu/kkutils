package utils.kkutils.ui.clicp;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import utils.kkutils.ui.KKImageView;

public class RoundImageView extends KKImageView {
    public RoundViewTool roundViewTool;
    public RoundImageView(Context context) {
        super(context);
        init(null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    public void init(AttributeSet attrs){
        roundViewTool=new RoundViewTool(this, attrs);
        setScaleType(ScaleType.CENTER_CROP);

    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        roundViewTool.refreshSize();
    }
    @Override
    protected void dispatchDraw(Canvas canvas) {
        roundViewTool.onDrawPre(canvas);
        super.dispatchDraw(canvas);
        roundViewTool.onDrawAfter(canvas);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        roundViewTool.onDrawPre(canvas);
        super.onDraw(canvas);
        roundViewTool.onDrawAfter(canvas);
    }
}

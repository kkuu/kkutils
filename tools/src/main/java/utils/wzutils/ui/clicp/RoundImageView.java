package utils.wzutils.ui.clicp;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

import utils.wzutils.ui.WzImageView;

public class RoundImageView extends WzImageView {
    public RoundViewTool roundViewTool;
    public RoundImageView(Context context) {
        super(context);
        init();
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void init(){
        roundViewTool=new RoundViewTool(this);

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

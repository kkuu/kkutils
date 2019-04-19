package utils.wzutils.ui.clicp;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class RoundLinearLayoutSimple extends LinearLayout {
    public RoundViewTool roundViewTool;
    public RoundLinearLayoutSimple(Context context) {
        super(context);
        init();
    }

    public RoundLinearLayoutSimple(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundLinearLayoutSimple(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public RoundLinearLayoutSimple(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }



    public void init(){
        roundViewTool=new RoundViewTool(this);
        //roundViewTool.setRoundCornerDp(20);

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

package utils.kkutils.ui.clicp;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class RoundRelativeLayoutSimple extends RelativeLayout {
    public RoundViewTool roundViewTool;
    public RoundRelativeLayoutSimple(Context context) {
        super(context);
        init(null);
    }

    public RoundRelativeLayoutSimple(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RoundRelativeLayoutSimple(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public RoundRelativeLayoutSimple(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public void init(AttributeSet attrs){
        roundViewTool=new RoundViewTool(this, attrs);
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

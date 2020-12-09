package utils.kkutils.ui.clicp;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RoundFrameLayoutSimple extends FrameLayout  {
    public RoundViewTool roundViewTool;
    public RoundFrameLayoutSimple(@NonNull Context context) {
        super(context);
        init(null);
    }

    public RoundFrameLayoutSimple(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RoundFrameLayoutSimple(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }
    public RoundFrameLayoutSimple(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }



    public void init(AttributeSet attrs) {
        this.roundViewTool = new RoundViewTool(this, attrs);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.roundViewTool.refreshSize();
    }

    protected void dispatchDraw(Canvas canvas) {
        this.roundViewTool.onDrawPre(canvas);
        super.dispatchDraw(canvas);
        this.roundViewTool.onDrawAfter(canvas);
    }

    protected void onDraw(Canvas canvas) {
        this.roundViewTool.onDrawPre(canvas);
        super.onDraw(canvas);
        this.roundViewTool.onDrawAfter(canvas);
    }

}

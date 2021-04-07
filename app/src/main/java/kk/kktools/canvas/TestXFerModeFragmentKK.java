package kk.kktools.canvas;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.shixia.colorpickerview.ColorPickerView;
import com.shixia.colorpickerview.OnColorChangeListener;

import kk.kktools.R;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.UiTool;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.StatusBarTool;
import utils.kkutils.ui.dialog.DialogTool;

public class TestXFerModeFragmentKK extends KKParentFragment {

    @Override
    public int initContentViewId() {
        return 0;
    }

    @Override
    public View initContentView() {
        TestCanvasView testCanvasView=new TestCanvasView(getContext());
        UiTool.setWH(testCanvasView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return testCanvasView;
    }

    @Override
    public void initData() {

    }

    public static class TestCanvasView extends View {



        public TestCanvasView(@NonNull Context context) {
            super(context);
            init();
        }

        public TestCanvasView(@NonNull Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public TestCanvasView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        public void init(){

        setLayerType(LAYER_TYPE_SOFTWARE,null);
        setBackgroundColor(Color.TRANSPARENT);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            Paint paint=new Paint();
            paint.setAntiAlias(true);

            paint.setColor(Color.RED);
            canvas.drawRect(0,0,getWidth(),getHeight(),paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

            paint.setColor(Color.BLUE);
            canvas.drawCircle(getWidth()/2,getHeight()/2,200,paint);

        }
        public void drawTest(Canvas  canvas){
            Paint paint=new Paint();
            paint.setAntiAlias(true);


            paint.setColor(Color.BLUE);
            canvas.drawCircle(50,50,50,paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

            paint.setColor(Color.YELLOW);
            canvas.drawRect(50,50,150,150,paint);
        }
    }
}

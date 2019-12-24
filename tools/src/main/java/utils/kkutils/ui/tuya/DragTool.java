package utils.kkutils.ui.tuya;

import android.graphics.Canvas;
import android.graphics.Point;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import utils.kkutils.common.LogTool;

public class DragTool {
    long timeDragStart=0;

    /***
     *
     * @param parent  拖动到的容器
     * @param dragView  可拖动的控件
     * @param onClickListener
     */
    public void bindView( View dragView, final View.OnClickListener onClickListener){
        dragView.setOnTouchListener(new View.OnTouchListener() {
            float downX;
            float downY;
            float x;
            float y;
            long timeDown=0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction() & MotionEvent.ACTION_MASK )
                {
                    case MotionEvent.ACTION_DOWN:
                        x=v.getX();
                        y=v.getY();
                        downX = event.getRawX();
                        downY = event.getRawY();
                        timeDown=System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_MOVE://随手移动，getRawX()与getX()有区别
                        if(onClickListener!=null&&(System.currentTimeMillis()-timeDown<100))break;//这样可以做点击
                        v.setTranslationX(x+(event.getRawX()-downX));
                        v.setTranslationY(y+(event.getRawY()-downY));
                        v.invalidate();
                        break;
                    case MotionEvent.ACTION_UP:
                        if(onClickListener!=null){
                            if(System.currentTimeMillis()-timeDown<300) {
                                    onClickListener.onClick(v);
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

    /***
     *这个实现不好，用上面的 简单些
     * @param parent  拖动到的容器
     * @param dragView  可拖动的控件
     * @param onClickListener
     */
    @Deprecated
    public void bindViewOld(ViewGroup parent, View dragView, final View.OnClickListener onClickListener){

        parent.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                LogTool.s("onDragEvent  "+event.getAction()+"  "+event.getX());
                DragData localState = (DragData) event.getLocalState();
                if(event.getAction()==DragEvent.ACTION_DRAG_STARTED){
                    timeDragStart=System.currentTimeMillis();
                }
                if(event.getAction()!=DragEvent.ACTION_DRAG_ENDED){
                    if(System.currentTimeMillis()-timeDragStart<100)return true;//防止点击的时候拖动
                    localState.view.setTranslationX(event.getX()-localState.point.x);
                    localState.view.setTranslationY(event.getY()-localState.point.y);
                }else {
                    if(System.currentTimeMillis()-timeDragStart<300){//控件拖动的时候不能获得焦点 ，所以在这里设置 点击
                        localState.view.performClick();
                    }
                }
                return true;
            }
        });

        dragView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    v.startDrag(null,new View.DragShadowBuilder(v){
                        @Override
                        public void onDrawShadow(Canvas canvas) {

                        }
                    },new DragData(v,new Point((int)event.getX(),(int)event.getY())),0);
                }
                return false;
            }
        });
       if(onClickListener!=null) dragView.setOnClickListener(onClickListener);

    }

    public static class DragData{
        public DragData(View view, Point pointIn) {
            this.view = view;
            if(pointIn==null){
                pointIn=new Point(view.getWidth()/2,view.getHeight()/2);
            }
            this.point = pointIn;
        }
        public View view;
        public Point point;
    }
}

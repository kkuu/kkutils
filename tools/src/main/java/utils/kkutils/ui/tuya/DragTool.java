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
    public void bindView(ViewGroup parent, View dragView, View.OnClickListener onClickListener){
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

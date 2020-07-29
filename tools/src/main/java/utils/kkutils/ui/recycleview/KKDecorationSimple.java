package utils.kkutils.ui.recycleview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import utils.kkutils.common.CommonTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.ResourcesTool;
import utils.kkutils.common.UiTool;

public class KKDecorationSimple extends RecyclerView.ItemDecoration {

    public KKDecorationSimple(int spanCount, int headCount, double paddingDp, int lineWidth, int lineColor) {
        this.spanCount = spanCount;
        this.headCount = headCount;
        padding=CommonTool.dip2px(paddingDp);

        if(lineWidth>0){
            this.lineWidth=lineWidth;
            paintLine.setColor(ResourcesTool.getColor(lineColor));
            paintLine.setAntiAlias(true);
        }

    }
    public Paint paintLine=new Paint();
    public int spanCount;
    public int headCount;
    public int padding;//子项边距
    public int lineWidth;


    /***
     * 设置view 为正方形
     * @param view
     */
    public  void setViewSquare(View view){
        UiTool.setWHEqual(view);
    }


    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if(paintLine!=null){
            int lineWidth=1;
            {//水平
                int count=(int)(parent.getChildCount()*1.0/spanCount+0.9999999999);
                if(count>1){
                    for(int i=1;i<count;i++){
                        int preLineIndex=(i-1)*spanCount+1;//用上一行的第一个来作为线的top
                        int top=parent.getChildAt(preLineIndex).getBottom()+padding/2;
                        c.drawRect(0, top, parent.getWidth(), (float) (top+lineWidth), paintLine);
                    }
                }
            }
            {//竖直
                for(int i=0;i<parent.getChildCount();i++){
                    View childAt = parent.getChildAt(i);
                    boolean lastLine = RecycleViewTool.isLastLine(parent.getChildCount(), i, spanCount);
                    if(lastLine){
                        c.drawRect(childAt.getRight()+padding/2, childAt.getTop()-padding, (float) (childAt.getRight()+padding/2+lineWidth),childAt.getBottom(), paintLine);
                    }else {
                        c.drawRect(childAt.getRight()+padding/2, childAt.getTop()-padding, (float) (childAt.getRight()+padding/2+lineWidth),childAt.getBottom()+padding/2, paintLine);
                    }
                }
            }
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        try {
            int position = parent.getChildAdapterPosition(view);//数据索引
            boolean isHead=position<headCount;//当前条是否是head
            if(!isHead){
                // 计算这个child 处于第几列
                int column = (position) % spanCount;
                outRect.left = (column * padding / spanCount);
                outRect.right = padding - (column + 1) * padding / spanCount;
                if (position >= spanCount)
                    outRect.top = padding;
            }

        }catch (Exception e){
            LogTool.ex(e);
        }
    }


}

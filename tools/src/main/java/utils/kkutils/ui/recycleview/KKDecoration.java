package utils.kkutils.ui.recycleview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import utils.kkutils.common.CommonTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.ResourcesTool;
import utils.kkutils.common.UiTool;

/***
 * RecyclerView 的间隔线
 *
 recycleView.setLayoutManager(new GridLayoutManager(getContext(),3));
 recycleView.addItemDecoration(new KKDecoration(3,0,10,20,3,Color.BLACK){

 {
 initAllShow();
 //                setBoder(false, 0);
 showItemVLineAll=true;
 }
 @Override
 public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
 super.getItemOffsets(outRect, view, parent, state);
 UiTool.setWHEqual(view);
 }
 });

 *
 *
 *
 */
public class KKDecoration extends RecyclerView.ItemDecoration {
    /***
     * 可调节参数
     */
    public int boderLinePadding=0;//边框线距离内容得间距
    public boolean showBoder=false;//是否显示边框
    public boolean showItemVLineAll;//是否显示所有得竖向得线，false 最后一排 空白得地方不划线， ture 要画

    public Paint paintLine;//画笔
    public int spanCount;//列数
    public int headCount;//头部数量， 暂时没用到
    public int paddingItem;//子项边距
    public int paddingBoder;//容器边距
    public int lineWidth;//线宽
    List<Point> itemPadding;//存放每个item 的 outRect left，right





    public KKDecoration(int spanCount, int headCount, double paddingItemDp, double paddingBoderDp, int lineWidth, int lineColor) {
        this.spanCount = spanCount;
        this.headCount = headCount;
        paddingItem =CommonTool.dip2px(paddingItemDp);
        paddingBoder=CommonTool.dip2px(paddingBoderDp);
         itemPadding = getItemPadding(spanCount, paddingItem, paddingBoder);
        setLine(lineWidth, lineColor);

    }

    /***
     * 所有内容都显示, 测试专用
     */
    public void initAllShow(){
        setLine(3, Color.BLACK);
        setBoder(true, 5);
        paddingItem=CommonTool.dip2px(10);
        paddingBoder=CommonTool.dip2px(20);
    }




    /***
     * 设置边框
     * @param showBoder 是否显示边框
     * @param boderLinePaddingDp 边框线距离内容得间距
     */
    public void setBoder(boolean showBoder,int boderLinePaddingDp){
        this.showBoder=showBoder;
        this.boderLinePadding=CommonTool.dip2px(boderLinePaddingDp);
    }

    public void setLine( int lineWidth, int lineColor){
        if(lineWidth>0){
            this.lineWidth=lineWidth;
            paintLine=new Paint();
            paintLine.setStrokeWidth(lineWidth);
            paintLine.setColor(ResourcesTool.getColor(lineColor));
            paintLine.setAntiAlias(true);
        }
    }






    /***
     * 设置view 为正方形
     * @param view
     */
    public  void setViewSquare(View view){
        UiTool.setWHEqual(view);
    }

    /***
     * 画边框
     * @param c
     * @param parent
     */
    public void drawBoderLine(Canvas c, RecyclerView parent){
        if(!showBoder)return;
        {//外边框
            int w=parent.getWidth();
            int h=parent.getHeight();
            int startX=paddingBoder;
            w=w-paddingBoder;
            h=h-paddingBoder;

            startX-=boderLinePadding;
            w+=boderLinePadding;
            h+=boderLinePadding;

            c.drawLine(startX, startX, w, startX, paintLine);//顶
            c.drawLine(startX, h,w, h, paintLine);//底
            c.drawLine(startX, startX,startX, h, paintLine);//左
            c.drawLine(w, startX,w, h, paintLine);//右
        }
    }

    /**
     * 画水平线
     * @param c
     * @param parent
     */
    public void drawHLine(Canvas c, RecyclerView parent){
        int startX=paddingBoder-boderLinePadding;
        int stopX=parent.getWidth()-paddingBoder+boderLinePadding;

        if(spanCount==1){//只有一列， 用得是线性布局,直接把线拉满
            startX=0;
            stopX=parent.getWidth();
        }

        {//水平
            int count=(int)(parent.getChildCount()*1.0/spanCount+0.9999999999);
            if(count>1){
                for(int i=0;i<count-1;i++){
                    View view = parent.getChildAt(i*spanCount);
                    int top=view.getBottom()+ paddingItem /2;

                    c.drawLine(startX,top,stopX,top, paintLine);
                }
            }
        }
    }

    /***
     * 画所有竖向线， 空白item也画
     * @param c
     * @param parent
     */
    public void drawVLineAll(Canvas c, RecyclerView parent){
        int startX=paddingBoder-paddingItem/2;
        int startY=paddingBoder-boderLinePadding;
        int w=parent.getWidth()-paddingBoder*2+paddingItem;


        {//竖直
            int itemW=w/spanCount;
            for(int i=1;i<spanCount;i++){
                startX+=itemW;

                c.drawLine(startX, startY, startX, parent.getHeight()-paddingBoder+boderLinePadding, paintLine);

            }
        }

    }
    /***
     * 画所有竖向线 空白item不画
     * @param c
     * @param parent
     */
    public void drawVLine(Canvas c, RecyclerView parent){

        int childCount = parent.getChildCount();
        for(int i=headCount;i<childCount;i++){
            View view = parent.getChildAt(i);
            int positon=i-headCount;
            if(positon%spanCount==spanCount-1){//最右边得

            }else {


                int startX=view.getRight();
                int startY=view.getTop();
                startX+=paddingItem/2;
                startY-=paddingItem/2;

                int stopY=startY+view.getHeight()+paddingItem;


                if(positon<spanCount){//第一行
                    startY=startY+(paddingItem/2-boderLinePadding);
                }
                if(RecycleViewTool.isLastLine(parent.getAdapter().getItemCount()-headCount, positon, spanCount)){//最后一行
                    stopY=stopY-(paddingItem/2-boderLinePadding);
                }

                c.drawLine(startX,startY,startX, stopY, paintLine);
            }
        }


    }
    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if(paintLine!=null){
            drawBoderLine(c,parent);
            drawHLine(c, parent);


            if(showItemVLineAll){//画所有竖线
                drawVLineAll(c, parent);
            }else {//画所有竖线,空白item 不画
                drawVLine(c, parent);
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
                position=position-headCount;


                // 计算这个child 处于第几列
                int column = (position) % spanCount;
//                outRect.left = (column * padding / spanCount);
//                outRect.right = padding - (column + 1) * padding / spanCount;

                //设item 宽为：w（固定得，不包含间距）  间距为p  spanCount 为s
                //那么 每一项得宽 (s*w+(s-1)*p/s)/s= w+p*(s-1)/s
                //每一项得必须间距为 p*(s-1)/s  如果s=4， 那么就是 (3*p)/4
                //第一项得left =0   2:  p/s 3  2p/s  4:  3p/s

//
//                int oneItemDivider=(spanCount-1)*padding/spanCount;
//                int step = padding / spanCount;
//                outRect.left=column*step;
//                outRect.right=oneItemDivider-outRect.left;
//

                Point point = itemPadding.get(column);
                outRect.left=point.x;
                outRect.right=point.y;

                if(position<spanCount){//第一排
                    outRect.top=paddingBoder;
                }else {//其他
                    outRect.top = paddingItem;
                }
                boolean lastLine = RecycleViewTool.isLastLine(parent.getAdapter().getItemCount()-headCount, position, spanCount);
                if(lastLine){//最后一排
                    outRect.bottom=paddingBoder;
                }
            }

        }catch (Exception e){
            LogTool.ex(e);
        }
    }
    public static List<Point> getItemPadding(int span, int pItem, int pBoder){
        int pView=((span-1)*pItem+pBoder*2)/span;
        List<Point> list=new ArrayList<>();
        list.add(new Point(pBoder,pView-pBoder));
        for(int i=1;i<span;i++){
            Point point = list.get(i - 1);
            Point p=new Point();
            p.x=pItem-point.y;
            p.y=pView-p.x;
            list.add(p);
        }
        return list;
    }

}

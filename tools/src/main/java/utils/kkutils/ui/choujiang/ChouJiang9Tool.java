package utils.kkutils.ui.choujiang;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import utils.kkutils.AppTool;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.UiTool;
import utils.kkutils.parent.KKViewOnclickListener;

/***
 * 9宫格抽奖，  使用的时候  替换 ChouJiangViewDefault 里面的几个drawable 就行了
 */
public class ChouJiang9Tool {

    public static interface   ChouJiangViewInterface {


        /**
         * 重置为初始状态
         */
        public void reset();

        /**
         * 重置为抽奖按钮
         */
        public void resetBtnChouJiang();

        /**
         * 设置高亮
         * @param isLight
         */
        public void setLight(boolean isLight);

        public void setZhongJiang(Object o);

        public void setNotZhongJiang();

        public void setOnClickListener(View.OnClickListener clickListener);

        public View getView();
    }


    public static int [] items=new int[]{0,1,2,5,8,7,6,3};
    public static int i=0;
    public static int timeAll=5000;
    public static int timeCurr=0;
    public static int timeAdd=0;
    public static void reset(final List<ChouJiangViewInterface> viewList){
        for (ChouJiangViewInterface jiangPinView : viewList) {
            jiangPinView.reset();
            if(viewList.indexOf(jiangPinView)==4){
                jiangPinView.resetBtnChouJiang();
                jiangPinView.setOnClickListener(new KKViewOnclickListener() {
                    @Override
                    public void onClickKK(View view) {
                        ChouJiang9Tool.choujiang(viewList,5,0);
                    }
                });
            }

        }
    }
    public static void choujiang(final List<ChouJiangViewInterface> viewList, final int num, final int money){
        reset(viewList);
        i=0;
        timeCurr=0;
        timeAdd=50;

        AppTool.uiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(i>=items.length)i=0;
                int p = items[i];
                ChouJiangViewInterface jiangPinView = viewList.get(p);
                for (int i1 = 0; i1 < viewList.size(); i1++) {
                    ChouJiangViewInterface btn = viewList.get(i1);
                    btn.setLight(p==i1);
                }
                timeCurr+=timeAdd;
                if(timeCurr>=timeAll&&p==num){
                    LogTool.s("抽中了"+num+"  "+money);
                    if(money>0){
                        jiangPinView.setZhongJiang(money);
                    }else {
                        jiangPinView.setNotZhongJiang();
                    }
                }else {
                    i++;
                    timeAdd+=20;
                    AppTool.uiHandler.postDelayed(this,timeAdd);
                }
            }
        },timeAdd);

    }

    @SuppressLint("AppCompatCustomView")
    public static class ChouJiangViewDefault extends  TextView implements ChouJiangViewInterface{


        public ChouJiangViewDefault(Context context) {
            super(context);
        }

        public ChouJiangViewDefault(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        public ChouJiangViewDefault(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }
        public static Drawable bg_drawable_normal=new ColorDrawable(Color.BLUE);
        public static Drawable bg_drawable_btn=new ColorDrawable(Color.RED);
        public static Drawable bg_drawable_light=new ColorDrawable(Color.parseColor("#33ffffff"));
        public static Drawable bg_drawable_zhongJiang=new ColorDrawable(Color.YELLOW);
        public static Drawable bg_drawable_zhongJiang_no=new ColorDrawable(Color.BLACK);


        public int w= CommonTool.dip2px(82);
        public int h= CommonTool.dip2px(82);
        @Override
        public void reset() {
            //setText(""+i);
            getPaint().setFakeBoldText(true);
            setTextSize(TypedValue.COMPLEX_UNIT_DIP,13);
            setPadding(0,0,0, CommonTool.dip2px(10));
            setTextColor(Color.parseColor("#F8D060"));
            setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);
            UiTool.setWH(this,w,h);

            initDrawableBounds();

            setBackground(bg_drawable_normal);
        }
        public void initDrawableBounds(){
            bg_drawable_normal.setBounds(0,0,w,h);
            bg_drawable_btn.setBounds(0,0,w,h);
            bg_drawable_light.setBounds(0,0,w,h);
            bg_drawable_zhongJiang.setBounds(0,0,w,h);
            bg_drawable_zhongJiang_no.setBounds(0,0,w,h);
        }

        @Override
        public void resetBtnChouJiang() {
            setBackground(bg_drawable_btn);
        }
        public boolean isLight=false;
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if(isLight) {

                bg_drawable_light.draw(canvas);
            }
        }
        @Override
        public void setLight(boolean isLight) {
            this.isLight=isLight;
            postInvalidate();
        }

        @Override
        public void setZhongJiang(Object o) {
            setBackground(bg_drawable_zhongJiang);
            setText(""+o);
        }

        @Override
        public void setNotZhongJiang() {
            setBackground(bg_drawable_zhongJiang_no);
            setText("");
        }

        @Override
        public View getView() {
            return this;
        }
    }

    public static interface ChouJiangViewFactory{
        public ChouJiangViewInterface newView();
    }
    public static void initChouJiang(final GridView gridView, final ChouJiangViewFactory chouJiangViewFactory){
        final List<ChouJiangViewInterface> viewList=new ArrayList<>();

        {//初始化viewlist
            for(int i=0;i<9;i++){
                ChouJiangViewInterface view=chouJiangViewFactory.newView();
                viewList.add(view);
            }
            reset(viewList);
        }

        gridView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return viewList.size();
            }
            @Override
            public Object getItem(int position) {
                return position;
            }
            @Override
            public long getItemId(int position) {
                return position;
            }
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                return viewList.get(position).getView();
            }
        });
    }
}

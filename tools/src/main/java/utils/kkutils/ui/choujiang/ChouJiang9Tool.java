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
import java.util.Random;

import androidx.annotation.Nullable;
import utils.kkutils.AppTool;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.UiTool;
import utils.kkutils.parent.KKViewOnclickListener;

/***
 * 9宫格抽奖，  使用的时候  替换 ChouJiangViewDefault 里面的几个drawable 就行了
 *
 *
 *
 *
 *          ChouJiang9Tool.ChouJiangViewDefault.bg_drawable_btn=getResources().getDrawable(R.drawable.candy_btn_draw);
 *         ChouJiang9Tool.ChouJiangViewDefault.bg_drawable_normal=getResources().getDrawable(R.drawable.candy_img_box);
 *         ChouJiang9Tool.ChouJiangViewDefault.bg_drawable_light=getResources().getDrawable(R.drawable.candy_img_light);
 *         ChouJiang9Tool.ChouJiangViewDefault.bg_drawable_zhongJiang=getResources().getDrawable(R.drawable.candy_img_money);
 *         ChouJiang9Tool.ChouJiangViewDefault.bg_drawable_zhongJiang_no=getResources().getDrawable(R.drawable.candy_img_thanks);
 *
 *
 *          new ChouJiang9Tool(grid_view) {
 *             @Override
 *             public boolean onChouJiangClick() {
 *                 startChoujiang(3,1);
 *                 return true;
 *             }
 *
 *             @Override
 *             public ChouJiangViewInterface newView() {
 *                 return new ChouJiang9Tool.ChouJiangViewDefault(getContext());
 *             }
 *         };
 *
 *
 *
 */
public abstract class ChouJiang9Tool {
    /***
     * 抽奖按钮被点击  记得调用 startChoujiang 并且返回true
     * @param view
     */
    public abstract boolean onChouJiangClick(View view);

    /**
     * 创建一个按钮， 总共会创建9个
     * @return
     */
    public abstract ChouJiangViewInterface newView();

    public  void onChouJiangAnimationEnd(){};

    public ChouJiang9Tool(GridView gridView){
       initChouJiang(gridView);
    }



    public  int [] items=new int[]{0,1,2,5,8,7,6,3};
    public  int i=0;
    public  int timeAll=5000;
    public  int timeCurr=0;
    public  int timeAdd=0;
    ChouJiangViewInterface btnChouJiang;
    public  void reset(){
        for (ChouJiangViewInterface jiangPinView : viewList) {
            jiangPinView.reset();
            if(viewList.indexOf(jiangPinView)==4){
                btnChouJiang=jiangPinView;
                jiangPinView.resetBtnChouJiang();
                jiangPinView.setOnClickListener(new KKViewOnclickListener() {
                    @Override
                    public void onClickKK(View view) {
                        view.setEnabled(false);
                        LogTool.s("设置抽奖按钮不可点击");
                        if(!onChouJiangClick(view)){
                            startChoujiang(5,0);
                        }
                    }
                });
            }

        }
    }
    /***
     *
     * @param data 中奖数据， 没中奖  null
     */
    public  void startChoujiangRandom(  final Object data){
        int num=items[new Random().nextInt(items.length)];
        startChoujiang(num,data);
    }
    Object data;
    int num;
    Runnable choujiang=new Runnable() {
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
                LogTool.s("抽中了"+num+"  "+data);

                if(data!=null){
                    jiangPinView.setZhongJiang(data);
                }else {
                    jiangPinView.setNotZhongJiang();
                }
                try {
                    btnChouJiang.getView().setEnabled(true);
                }catch (Exception e){
                }
                onChouJiangAnimationEnd();
            }else {
                i++;
                timeAdd+=20;
                AppTool.uiHandler.postDelayed(this,timeAdd);
            }
        }
    };
    /***
     *
     * @param num  选中哪个数字  0-8  除了4
     * @param data 中奖数据， 没中奖  null
     */
    public  void startChoujiang( final int num, final Object data){
        reset();

        i=0;
        timeCurr=0;
        timeAdd=50;

        this.num=num;
        this.data=data;

        AppTool.uiHandler.removeCallbacks(choujiang);
        AppTool.uiHandler.postDelayed(choujiang,timeAdd);

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
        public static Drawable bg_drawable_light=new ColorDrawable(Color.parseColor("#33000000"));
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
            setText("");

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



    List<ChouJiangViewInterface> viewList=new ArrayList<>();

    public  void initChouJiang(final GridView gridView){
        viewList.clear();
        {//初始化viewlist
            for(int i=0;i<9;i++){
                ChouJiangViewInterface view=newView();
                viewList.add(view);
            }
            reset();
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

}

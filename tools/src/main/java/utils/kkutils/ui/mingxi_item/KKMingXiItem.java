package utils.kkutils.ui.mingxi_item;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import utils.kkutils.AppTool;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.LayoutInflaterTool;
import utils.kkutils.common.StringTool;
import utils.kkutils.common.UiTool;
import utils.kkutils.parent.KKViewOnclickListener;

public abstract class KKMingXiItem {
    public String leftStr;
    public String rightStr;
    public int left_leftDrawable;
    public int right_rightDrawable;
    public int right_tv_color;

    public int line_color;

    public boolean rightBold=false;
    public View viewParent;
    public TextView tv_mingxi_item_left;
    public TextView tv_mingxi_item_right;
    public OnMingXiItemClick onClickRight;
    /**
     * 间隔线
     */
    public KKMingXiItem() {

    }
    public KKMingXiItem(String leftStr, String rightStr) {
        this.leftStr = leftStr;
        this.rightStr = rightStr;
    }
    public KKMingXiItem(String leftStr, String rightStr, int right_tv_color) {
        this.leftStr = leftStr;
        this.rightStr = rightStr;
        this.right_tv_color=right_tv_color;
    }
    public KKMingXiItem(String leftStr, String rightStr, int left_leftDrawable, int right_rightDrawable, int right_tv_color) {
        this.leftStr = leftStr;
        this.rightStr = rightStr;
        this.left_leftDrawable = left_leftDrawable;
        this.right_rightDrawable = right_rightDrawable;
        this.right_tv_color = right_tv_color;
    }


    /**
     * item layout文件
     * @return
     */
    public abstract int getItemLayoutRes();

    /**
     * 左边textview id
     * @return
     */
    public abstract int getLeftTextViewResId();
    /**
     * 右边textview id
     * @return
     */
    public abstract int getRightTextViewResId();

//
//    public static void addItems(View itemView, List<KKMingXiItem> mingXiItemList){
//        ViewGroup viewGroup=itemView.findViewById(R.id.vg_mingxi_item);
//        addItemsInGroup(viewGroup,mingXiItemList);
//    }
public static void addItemsInGroup(ViewGroup viewGroup, List<KKMingXiItem> mingXiItemList){
        if(viewGroup.getTag()==null){
            viewGroup.removeAllViews();
            for(KKMingXiItem item:mingXiItemList){

                if(!item.isLine()){
                    View view= LayoutInflaterTool.getInflater(50,item.getItemLayoutRes()).inflate();
                    viewGroup.addView(view);
                    item.initItem(view);
                }else {//分割线
                    View view=new View(viewGroup.getContext());
                    ViewGroup.MarginLayoutParams params=new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1);
                    params.bottomMargin= CommonTool.dip2px(15);
                    viewGroup.addView(view,params);
                }

            }
            viewGroup.setTag(1);
        }else {
            for(int i=0;i<mingXiItemList.size();i++){
                KKMingXiItem item=mingXiItemList.get(i);
                View view=viewGroup.getChildAt(i);
                item.initItem(view);

            }
        }
    }

    public void initItem(View view){
        if(isLine()){
            view.setBackgroundResource(line_color);
            return;
        }
        this.viewParent=view;

        tv_mingxi_item_left=view.findViewById(getLeftTextViewResId());
        tv_mingxi_item_right=view.findViewById(getRightTextViewResId());

        UiTool.setTextView(tv_mingxi_item_left,leftStr);
        UiTool.setTextView(tv_mingxi_item_right,rightStr);
        if(right_tv_color!=0){
            UiTool.setTextColor(tv_mingxi_item_right,right_tv_color);
        }
        if(left_leftDrawable!=0){
            UiTool.setCompoundDrawables(AppTool.currActivity,tv_mingxi_item_left,left_leftDrawable,0,0,0);
        }
        if(right_rightDrawable!=0){
            UiTool.setCompoundDrawables(AppTool.currActivity,tv_mingxi_item_right,0,0,right_rightDrawable,0);
        }
        if(tv_mingxi_item_right!=null){
            if(rightBold){
                tv_mingxi_item_right.getPaint().setFakeBoldText(true);
            }else {
                tv_mingxi_item_right.getPaint().setFakeBoldText(false);
            }

            if(onClickRight!=null){
                tv_mingxi_item_right.setOnClickListener(new KKViewOnclickListener() {
                    @Override
                    public void onClickKK(View v) {
                        onClickRight.OnClickItem(KKMingXiItem.this);
                    }
                });
            }
        }


    }
    public static void test(View viewParent){
//        List<KKMingXiItem> mingXiItemList=new ArrayList<>();
//        new KKMingXiItem("支付宝","",android.R.drawable.star_on,0,0).addToList(mingXiItemList);
//        new KKMingXiItem("收款人","张三",0,android.R.drawable.star_off,0).addToList(mingXiItemList).setRightOnClick(null);
//        new KKMingXiItem("","").addToList(mingXiItemList);
//
//        KKMingXiItem mingXiItem = new KKMingXiItem("请在 14分10秒内 向以上收款方式支付 1,000.00 CNY", "").addToList(mingXiItemList);
//        KKMingXiItem.addItems(viewParent,mingXiItemList);
//        mingXiItem.tv_mingxi_item_left.setText("请在 14分20秒内 向以上收款方式支付 1,000.00 CNY");
    }
    public KKMingXiItem addToList(List<KKMingXiItem> mingXiItemList){
        if(mingXiItemList!=null)mingXiItemList.add(this);
        return this;
    }

    public KKMingXiItem setRightOnClick(OnMingXiItemClick onClick){
        onClickRight=onClick;
        return this;
    }

    public boolean isLine(){
        return StringTool.isEmpty(leftStr);
    }
    public KKMingXiItem setRightBold(){
        rightBold=true;
        return this;
    }
    public static abstract class  OnMingXiItemClick{
        public abstract void OnClickItem(KKMingXiItem mingXiItem);
    }
}

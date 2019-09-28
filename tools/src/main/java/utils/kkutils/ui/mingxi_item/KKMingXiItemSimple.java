package utils.kkutils.ui.mingxi_item;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import utils.kkutils.AppTool;
import utils.kkutils.R;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.LayoutInflaterTool;
import utils.kkutils.common.StringTool;
import utils.kkutils.common.UiTool;
import utils.kkutils.parent.KKViewOnclickListener;

public  class KKMingXiItemSimple extends KKMingXiItem{
    /**
     * 间隔线
     */
    public KKMingXiItemSimple() {

    }
    public KKMingXiItemSimple(String leftStr, String rightStr) {
        this.leftStr = leftStr;
        this.rightStr = rightStr;
    }
    public KKMingXiItemSimple(String leftStr, String rightStr, int right_tv_color) {
        this.leftStr = leftStr;
        this.rightStr = rightStr;
        this.right_tv_color=right_tv_color;
    }
    public KKMingXiItemSimple(String leftStr, String rightStr, int left_leftDrawable, int right_rightDrawable, int right_tv_color) {
        this.leftStr = leftStr;
        this.rightStr = rightStr;
        this.left_leftDrawable = left_leftDrawable;
        this.right_rightDrawable = right_rightDrawable;
        this.right_tv_color = right_tv_color;
    }

    @Override
    public int getItemLayoutRes() {
        return R.layout.kk_mingxi_item;
    }

    @Override
    public int getLeftTextViewResId() {
        return R.id.kk_tv_mingxi_item_left;
    }

    @Override
    public int getRightTextViewResId() {
        return R.id.kk_tv_mingxi_item_right;
    }

    public static void addItems(View itemView, List<KKMingXiItem> mingXiItemList){
        ViewGroup viewGroup=itemView.findViewById(R.id.kk_vg_mingxi_item);
        addItemsInGroup(viewGroup,mingXiItemList);
    }

    public static void test(View viewParent){
        List<KKMingXiItem> mingXiItemList=new ArrayList<>();
        new KKMingXiItemSimple("支付宝","",android.R.drawable.ic_lock_lock,0,0).addToList(mingXiItemList);
        new KKMingXiItemSimple("收款人","张三",0,android.R.drawable.ic_lock_lock,0).addToList(mingXiItemList).setRightOnClick(null);
        new KKMingXiItemSimple("","").addToList(mingXiItemList);
        KKMingXiItem mingXiItem = new KKMingXiItemSimple("请在 14分10秒内 向以上收款方式支付 1,000.00 CNY", "").addToList(mingXiItemList);



        KKMingXiItem.addItemsInGroup((ViewGroup) viewParent.findViewById(R.id.kk_vg_mingxi_item),mingXiItemList);



        mingXiItem.tv_mingxi_item_left.setText("请在 14分20秒内 向以上收款方式支付 1,000.00 CNY");
    }

}

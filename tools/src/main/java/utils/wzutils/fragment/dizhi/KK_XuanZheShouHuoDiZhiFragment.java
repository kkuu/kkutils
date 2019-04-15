package utils.wzutils.fragment.dizhi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

import utils.wzutils.R;
import utils.wzutils.common.CollectionsTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.common.UiTool;
import utils.wzutils.parent.WzNormalFragmentActivity;
import utils.wzutils.parent.WzParentFragment;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.WzSimpleRecycleView;

/**
 * Created by kk on 2017/3/23.
 */

public class KK_XuanZheShouHuoDiZhiFragment extends WzParentFragment {
    public WzSimpleRecycleView recycleView;
    @Override
    public int initContentViewId() {
        return R.layout.kk_normal_list;
    }
    @Override
    public void initData() {
        maxDep= (int) getArgument("maxDep",maxDep);
        currKKDiZhi = KK_DiZhi.loadFromLocal().list.get(0);
        if (getActivity() instanceof WzNormalFragmentActivity) {
            ((WzNormalFragmentActivity) getActivity()).setOnBackPressedListener(new WzNormalFragmentActivity.OnBackPressedListener() {
                @Override
                public boolean onBackPressed() {
                    if(currKKDiZhi !=null){
                        if (currKKDiZhi.getDep() == 2) {//2是不要国家， 1是要国家
                            getActivity().finish();
                        } else {
                            loadData(currKKDiZhi.parent);
                        }
                    }else {
                        getActivity().finish();
                    }

                    return true;
                }
            });
        }

        loadData(currKKDiZhi);
    }
    public int maxDep=1000;//最大选择层级 4是城市  5是地区
    public static final int maxDepCity=4;//选城市传这个
    public KK_DiZhi currKKDiZhi =null;
    public void loadData(final KK_DiZhi KKDiZhi){
        currKKDiZhi = KKDiZhi;
        recycleView.scrollToPosition(0);
        recycleView.setData(KKDiZhi.list, R.layout.kk_dizhi_item, new WzSimpleRecycleView.WzRecycleAdapter() {
            @Override
            public void initData(int positon, int type, View itemView) {
                super.initData(positon, type, itemView);
                final KK_DiZhi KKDiZhiItem = KKDiZhi.list.get(positon);

                TextView tv_xuanze= (TextView) itemView.findViewById(R.id.tv_xuanze);
                UiTool.setTextView(tv_xuanze, KKDiZhiItem.name);
                if(KKDiZhiItem.list!=null&& KKDiZhiItem.list.size()>0){
                    UiTool.setCompoundDrawables(getActivity(),tv_xuanze,0,0,R.drawable.kk_icon_right,0);
                }else {
                    UiTool.setCompoundDrawables(getActivity(),tv_xuanze,0,0,0,0);
                }


                itemView.setOnClickListener(new WzViewOnclickListener() {
                    @Override
                    public void onClickWz(View v) {
                        if(KKDiZhiItem.getDep()==2){
                            diZhiChoose.address_country= KKDiZhiItem.name;
                        }else if(KKDiZhiItem.getDep()==3){
                            diZhiChoose.address_province= KKDiZhiItem.name;
                        }else if(KKDiZhiItem.getDep()==4){
                            diZhiChoose.address_city= KKDiZhiItem.name;
                        }else if(KKDiZhiItem.getDep()==5){
                            diZhiChoose.address_area= KKDiZhiItem.name;
                        }

                        if(CollectionsTool.isEmptyList(KKDiZhiItem.list)|| KKDiZhiItem.getDep()>=maxDep){//子列表空 或者只需要指定层级就返回
                            Intent intent=new Intent();
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("dizhi",diZhiChoose);
                            intent.putExtras(bundle);
                            getActivity().setResult(Activity.RESULT_OK,intent);
                            getActivity().finish();
                        }else {
                            loadData(KKDiZhiItem);
                        }
                    }
                });
            }
        });
    }

    public static DiZhiChoose getDiZhiByIntent(Intent intent){
        try {
            return (DiZhiChoose) intent.getExtras().getSerializable("dizhi");
        }catch (Exception e){
            LogTool.ex(e);
        }
        return new DiZhiChoose();
    }
    public DiZhiChoose diZhiChoose=new DiZhiChoose();
    public static class DiZhiChoose implements Serializable {
        public String address_country="中国";
        public String address_province="";
        public String address_city="";
        public String address_area="";

        public String getCountryEN(){
            if("中国".equals(address_country))return "China";
            return address_country;
        }
        public String getCountryCN(){
            return getCountryCN(address_country);
        }

        public static String getCountryCN(String contry){
            if(StringTool.notEmpty(contry))if("china".equals(contry.toLowerCase()))return "中国";
            return contry;
        }
    }

    @Override
    public void initListener() {

    }

    @Override
    public void goForResult(WzParentFragment parentFragment, int requestCode) {
        super.goForResult(parentFragment, requestCode);
    }
    public void goForResult(WzParentFragment parentFragment, int requestCode,int maxDep) {
        Bundle bundle=new Bundle();
        bundle.putInt("maxDep",maxDep);
        setArguments(bundle);
        super.goForResult(parentFragment, requestCode);
    }

}

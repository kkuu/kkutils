package utils.kkutils.parent;


import android.view.View;

import java.util.List;

import utils.kkutils.http.HttpUiCallBack;
import utils.kkutils.json.JsonDataParent;
import utils.kkutils.ui.KKSimpleRecycleView;
import utils.kkutils.ui.pullrefresh.KKRefreshLayout;
import utils.kkutils.ui.pullrefresh.PageControl;

/***
 * 列表简化
 * 实际使用的时候  集成自己的  KKParentFragment
 * @param <Tdata>
 * @param <Titem>
 */
public abstract class KKParentListFragment<Tdata extends JsonDataParent,Titem > extends KKParentFragment {
    public abstract void loadData(int page);
    public abstract int getPageCurrPage(Tdata data);
    public abstract List<Titem> getPageListData(Tdata data);
    public abstract int getListItemLayout();
    public abstract  void initItem(View itemView,Titem titem);
    public abstract void onDataLoad(Tdata tdata);



    @Override
    public void initData() {
        refreshLayout.bindLoadDataAndRefresh(pageControl, new KKRefreshLayout.LoadListDataInterface() {
            @Override
            public void loadPageData(int i) {
                loadData(i);
            }
        });
    }

    public Tdata data;
    public PageControl<Titem> pageControl=new PageControl<>();
    public HttpUiCallBack<Tdata> getCallBack(){
        return new HttpUiCallBack<Tdata>() {
            @Override
            public void onSuccess(Tdata tdata) {
                refreshLayout.stopRefresh(pageControl);
                if(tdata.isDataOkAndToast()){
                    data=tdata;
                    onDataLoad(tdata);
                    pageControl.setCurrPageNum(getPageCurrPage(tdata),getPageListData(tdata));
                }
                initList();
            }
        };
    }

    public void initList(){
        final List<Titem> allDatas = pageControl.getAllDatas();
        recycleView.setData(pageControl.getAllDatas(), getListItemLayout(), new KKSimpleRecycleView.KKRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView, KKSimpleRecycleView.WzViewHolder wzViewHolder) {
                super.initData(position, type, itemView, wzViewHolder);
                initItem(itemView,allDatas.get(position));
            }
        });
    }


}

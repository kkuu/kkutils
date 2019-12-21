package kk.kktools.web;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import utils.kkutils.common.LogTool;
import utils.kkutils.common.UiTool;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.StatusBarTool;
import utils.kkutils.ui.webview.X5WebView;


public class WebFragment extends KKParentFragment {


    X5WebView webView;
    String detail="";
    String title;

    ViewGroup vg_bangzhu;


    @Override
    public View initContentView() {
        webView=new X5WebView(getActivity());
        UiTool.setWH(webView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return webView;
    }

    @Override
    public int initContentViewId() {
        setUseCacheView(false);
        return 0;
    }


    @Override
    public void initData() {
        detail=""+getArgument("detail",detail);
        title=""+getArgument("title",detail);

        webView.setBackgroundColor(0); // 设置背景色
        webView.loadUrl(detail);
        LogTool.s(detail);
    }

    @Override
    public boolean onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else {
            getActivity().finish();
        }
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
      //  App.languageTool.initLanguage(getActivity());//webview 会导致语言改变
    }
    @Override
    public void initListener() {

    }

    public static KKParentFragment byData(String detail, String title){

        KKParentFragment fragment=new WebFragment();
        Bundle bundle=new Bundle();
        bundle.putString("detail",detail);
        bundle.putString("title",title);
        fragment.setArguments(bundle);
        return fragment;
    }

}

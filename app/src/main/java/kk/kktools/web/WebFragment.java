package kk.kktools.web;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import utils.kkutils.common.LogTool;
import utils.kkutils.common.StringTool;
import utils.kkutils.common.UiTool;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.StatusBarTool;
import utils.kkutils.ui.webview.WebViewTool;
import utils.kkutils.ui.webview.WebViewTuWen;
import utils.kkutils.ui.webview.X5WebView;
import utils.kkutils.ui.webview.X5WebViewTuWen;


public class WebFragment extends KKParentFragment {


    X5WebViewTuWen webView;
    String detail="";
    String title;

    ViewGroup vg_bangzhu;


    @Override
    public View initContentView() {
        webView=new X5WebViewTuWen(getActivity());
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

        webView.setBackgroundColor(Color.WHITE); // 设置背景色
       // webView.loadUrl(detail);
        LogTool.s(detail);
        webView.loadTuWen(content);
    }
    public String content="<div class=\"content\"><p><img src=\"http://ximei-oss.oss-cn-shanghai.aliyuncs.com/20191228132258479426039e710ef9c5.gif\"></p>\n" +
            "<p>When amateur blogger Michael Schneider went skiing in the Swiss Alps with friends, he was looking to have a day of fun and adventure. But what he didnt expect was to take a photograph that would cause a sensation online. As Schneider waited for a friend on the mountain, he took advantage of the moment to test out the camera on his new smartphone. The resulting photographs, which capture striking halos around the sun, went viral after he posted one to Twitter.</p>\n" +
            "<p>当业余博主迈克尔·施奈德和朋友到瑞士阿尔卑斯山滑雪时，他期待这一天充满乐趣和冒险。但他没想到的是拍了一张能在网上引起轰动的照片。当施耐德在山上等一个朋友的时候，他利用这个机会测试了他的新智能手机上的摄像头。他在推特上发布了一张拍摄到的照片，这些照片捕捉到了太阳周围引人注目的光环。</p>\n" +
            "<p>What followed were a flurry of explanations about why, and how, these rings appear around the sun. A light halo is an optical phenomenon that occurs when light interacts with ice crystals suspended in the atmosphere. While we often think of a halo as being exclusively circular, they can actually take many different shapes. From arcs to light pillars, some are more common than others.</p>\n" +
            "<p>接下来是一连串关于为什么以及如何在太阳周围出现这些光环的解释。光晕是光与悬浮在大气中的冰晶相互作用时产生的一种光学现象。虽然我们通常认为光环只是圆形的，但它们实际上可以有许多不同的形状。从弧形到光柱，有些比其他更常见。</p>\n" +
            "<p>Luckily for us novices, several specialists set about annotating Schneiders photograph. Mark McCaughrean, Senior Advisor for Science &amp; Exploration at the European Space Agency, used information gathered from the website Atmospheric Optics to create a wonderful annotation of the different halos visible in Schneiders photo. Looking at his diagram, its incredible to see how many halo examples were captured in one image.</p>\n" +
            "<p>对于我们这些新手来说，幸运的是，几位专家开始给施奈德的照片做注解。马克·麦可瑞恩，欧洲航天局科学与探索高级顾问，利用从“大气光学”网站收集的信息，对施耐德的照片中不同的光晕做了精彩的注解。看看他的图表，难以置信的是在一张图片中有这么多光环的例子。</p>\n" +
            "<p>The complete circle formed closest to the sun is what most people simply call a halo; but to be more specific, its actually known as a 22 halo. There are also rarer halos in the photo, such as a Supralateral arc toward the top of the image. This faintly colored rainbow arc forms when rays pass between the side and basal (end) faces of singly oriented hexagonal columns.</p>\n" +
            "<p>离太阳最近形成的完整的圆被大多数人简单地称为光环。但更具体地说，它实际上被称为22晕。在照片中也有一些罕见的光晕，比如一个指向图像顶部的上外侧弧。当光线从六角形柱的侧面和底面(末端)之间穿过时，就形成了这种微弱的彩虹弧。</p>\n" +
            "<p>Overall, Schneiders experience is a great reminder that even if you dont have professional equipment on hand, it doesnt mean that you cant capture a meaningful photograph. Read more about Schneiders experience on his blog and see more photographs from his time on the mountain below.</p>\n" +
            "<p>总的来说，施耐德的经历是一个很好的提醒，即使你没有专业的拍摄设备，也不意味着你拍不出有意义的照片。在他的博客上阅读更多关于施耐德的经历，看看更多他在山下的照片。</p>\n" +
            "<p>While skiing in Switzerland, Michael Schneider captured an incredible photo of light halos on the mountain.</p>\n" +
            "<p>在瑞士滑雪时，迈克尔·施耐德拍下了一张令人难以置信的照片，照片中山上出现了光晕。</p>\n" +
            "<p><img src=\"http://ximei-oss.oss-cn-shanghai.aliyuncs.com/20191228132258539693163319cc6edc.jpeg\"></p>\n" +
            "<p>This annotation helps explain the different light phenomena seen in the image.</p>\n" +
            "<p>这个注释有助于解释图像中看到的不同光现象。</p>\n" +
            "<p><img src=\"http://ximei-oss.oss-cn-shanghai.aliyuncs.com/2019122813225856452615039c68b248.jpeg\"></p>\n" +
            "<p>Illustration: Mark McCaughrean</p>\n" +
            "<p><span class=\"op_dict_text2\">插图</span>：Mark McCaughrean</p>\n" +
            "<p>Here are more photos from the mountain, which show how the halos became more apparent over time.</p>\n" +
            "<p>这里有更多来自这座山的照片，显示了光环如何随着时间的推移变得更加明显。</p>\n" +
            "<p><img src=\"http://ximei-oss.oss-cn-shanghai.aliyuncs.com/20191228132258590373d9d289ce481e.jpeg\"><img src=\"http://ximei-oss.oss-cn-shanghai.aliyuncs.com/2019122813225861358235205aa4554b.jpeg\"><img src=\"http://ximei-oss.oss-cn-shanghai.aliyuncs.com/20191228132258637372a2c2a7d28f94.jpeg\"><img src=\"http://ximei-oss.oss-cn-shanghai.aliyuncs.com/20191228132258659267934876a21b24.jpeg\">Michael Schneider: Website | Twitter</p>\n" +
            "<p>My Modern Met granted permission to feature photos by Michael Schneider.</p>\n" +
            "<p>My Modern Met获准刊登迈克尔·施奈德拍摄照片。</p></div>";


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

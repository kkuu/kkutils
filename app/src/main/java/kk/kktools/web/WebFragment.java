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

        initTuWenXiangQing(webView,StringTool.unicode2String(content));
//        webView.loadDataWithBaseURL("http://null", StringTool.unicode2String(content), "text/html", "utf-8", null);
    }
    /***
     * 图文详情
     * <img alt=""   style="width: 100%;" 必须加width  100% ， 才能自适应
     */
    public static void initTuWenXiangQing(X5WebView webView, String content) {
        try {
            content="<div class=\"content\"><p><img src=\"http://ximei-oss.oss-cn-shanghai.aliyuncs.com/20191228132258479426039e710ef9c5.gif\"></p>\n" +
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
            content =html_start+ content+html_end;
            if (!StringTool.isEmpty(content)) {
                content=content.replace("&#x3D;","=").replace("\\&quot;","").replace("&quot;","");
                webView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
            }
        }catch (Exception e){
            LogTool.ex(e);
        }
    }
    public static final String html_start="<html><head> "
            +"<meta name=\"viewport\" content=\"width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no,minimal-ui\" />"
            +"<style>img{max-width:100% !important ;height:auto !important;}</style>"
            +"<style>p{font-size :17px !important;line-height:32px !important;} </sty1e>"
            +"<style>body(max- width:100% !important;}</style>"
            +"</head><body>";

    public static final String html_end="</body></html>";



    public String content="&quot;\\u003Cp\\u003E\\u003Cstrong\\u003E靴子落地，停播2个月的“斗鱼一姐”冯提莫，终于确定了下家。\\u003C\\u002Fstrong\\u003E\\u003C\\u002Fp\\u003E\\u003Cp\\u003E12月19日晚9时50分，冯提莫发布微博官宣入驻B站：亲爱的蘑菇们，对不起！让大家久等了！直播已有两个月没有营业，那就在生日来宣布新的开始吧！12月23日晚上8点，@哔哩哔哩弹幕网房间号1314，提莫牵手B站开启新的直播旅程。\\u003C\\u002Fp\\u003E\\u003Cimg src&#x3D;\\&quot;http:\\u002F\\u002Fp3.pstatp.com\\u002Flarge\\u002Fpgc-image\\u002FRmNmDr58mrmn5j\\&quot; img_width&#x3D;\\&quot;606\\&quot; img_height&#x3D;\\&quot;551\\&quot; alt&#x3D;\\&quot;斗鱼遭遇重大危机 各大主播纷纷跳槽\\&quot; inline&#x3D;\\&quot;0\\&quot;\\u003E\\u003Cp\\u003EB站官方微博迅速给出回应，表示欢迎。目前这条微博已经引发2万7千余条转发和1万3千条评论。话题#冯提莫签约B站#迅速登上微博热搜榜，\\u003Cstrong\\u003E彰显这位前“斗鱼一姐”即使停播2个月，依然有着不俗的流量实力。\\u003C\\u002Fstrong\\u003E\\u003C\\u002Fp\\u003E\\u003Cp\\u003E老东家斗鱼那边则略显落寞，连夜删除了具有2000多万订阅的冯提莫直播间。据网友截图，直播间消失前收礼小时榜上，冯提莫仍然排在275名。\\u003C\\u002Fp\\u003E\\u003Cp\\u003E\\u003Cstrong\\u003E5年平台与主播的共同成长，冯提莫是直播行业里唯一一个成功“出圈”的女主播，是斗鱼在过去在一起的日子里反复提及的主播“标杆”。在很长的一段时间里，斗鱼可能再也找不到第二个“冯提莫”。\\u003C\\u002Fstrong\\u003E\\u003C\\u002Fp\\u003E\\u003Cp\\u003E冯提莫一路抓住斗鱼的用户红利。斗鱼领先在行业内举办主播走到线下的直播嘉年华，帮助冯提莫走出直播间，锻炼了一身优秀大气的“台风”。\\u003C\\u002Fp\\u003E\\u003Cp\\u003E与其他主播的来来去去不同，冯提莫是真正5年扎根斗鱼。即使面临多次事业危机，两度传出要出走斗鱼，依然选择留下。如今真的一别两宽，冯提莫要在一片全新的土壤里继续自己的事业，而斗鱼巅峰时期的“四大歌姬”，却只剩阿冷一根独苗了。\\u003C\\u002Fp\\u003E\\u003Cp\\u003E\\u003Cstrong\\u003E“一姐”出走B站 未开播黑屏收礼200万\\u003C\\u002Fstrong\\u003E\\u003C\\u002Fp\\u003E\\u003Cp\\u003E9月30日，冯提莫开完最后一次直播，在斗鱼71017直播间的动态，永远停留在了“假期快乐，休息中直播时间待定”。彼时她的微博保持日更的状态，更新着假日见闻、自拍小视频、边走边唱的户外，在小视频里冯提莫带着粉丝们去了自己的家乡——以往这些内容，都是在直播间里完成的。\\u003C\\u002Fp\\u003E\\u003Cp\\u003E10月8日，或许最终还是没谈拢，冯提莫在微博承认了和斗鱼合约到期，并透露“和各个平台还在对未来的合作洽谈中，一旦定下来会马上通知大家”。据悉，当时抖音和快手两大短视频、直播平台都在积极和冯提莫沟通，抖音的呼声甚嚣直上。\\u003C\\u002Fp\\u003E\\u003Cp\\u003E整个11月到官宣前，除了偶尔有媒体在猜测外，对于最终会选择哪家直播平台，冯提莫始终没有露出一点风向，有条不紊地张罗自己的全国签售会，积极参加综艺节目，俨然一副专职艺人模样。前段时间甚至微博突然关注“快手”，做出迷惑之象。有行业人士认为，冯提莫是否或许不打算直播了，准备专心向真正的艺人方向发展。\\u003C\\u002Fp\\u003E\\u003Cimg src&#x3D;\\&quot;http:\\u002F\\u002Fp3.pstatp.com\\u002Flarge\\u002Fpgc-image\\u002FRmNmDrZ1YrbLpT\\&quot; img_width&#x3D;\\&quot;650\\&quot; img_height&#x3D;\\&quot;520\\&quot; alt&#x3D;\\&quot;斗鱼遭遇重大危机 各大主播纷纷跳槽\\&quot; inline&#x3D;\\&quot;0\\&quot;\\u003E\\u003Cp\\u003E相比斗鱼直播间未删除前的2049万订阅数，冯提莫在抖音的粉丝数已经达到了\\u003Cstrong\\u003E3207.4万，获赞1.4个亿\\u003C\\u002Fstrong\\u003E，包括《佛系少女》在内的多个音乐作品在抖音传唱度极高，使用率高达百万以上，用户基础良好。冯提莫官方快手号的粉丝数为105.1万。\\u003C\\u002Fp\\u003E\\u003Cp\\u003E从订阅数的角度，抖音无疑是冯提莫在离开斗鱼后最看好的平台，个人调性也最为符合。如今突然转投B站，还是比较出人意料的。即使是官宣后，冯提莫在B站直播间的订阅数截止至发稿时为10.5万。3个视频最高观看为51.1万，成绩相比普通UP主可以，但是对于冯提莫这个量级的主播只能算是一般。\\u003C\\u002Fp\\u003E\\u003Cimg src&#x3D;\\&quot;http:\\u002F\\u002Fp9.pstatp.com\\u002Flarge\\u002Fpgc-image\\u002FRmNmDs449wYgWi\\&quot; img_width&#x3D;\\&quot;650\\&quot; img_height&#x3D;\\&quot;514\\&quot; alt&#x3D;\\&quot;斗鱼遭遇重大危机 各大主播纷纷跳槽\\&quot; inline&#x3D;\\&quot;0\\&quot;\\u003E\\u003Cp\\u003E不过“一姐”的吸金能力还是值得认可的，\\u003Cstrong\\u003E在12月23日才正式首播的情况下，直播间礼物已超过了200万\\u003C\\u002Fstrong\\u003E，冯提莫“黑屏”升级为B站26级主播，实时排名第九。可以预想首播当日会非常热闹了，视听观察会持续关注和报道。\\u003C\\u002Fp\\u003E\\u003Cp\\u003E\\u003Cstrong\\u003E为什么会是B站？\\u003C\\u002Fstrong\\u003E\\u003Cstrong\\u003E怎么会是B站？\\u003C\\u002Fstrong\\u003E\\u003C\\u002Fp\\u003E\\u003Cimg src&#x3D;\\&quot;http:\\u002F\\u002Fp3.pstatp.com\\u002Flarge\\u002Fpgc-image\\u002FRmNmDsYCTNfhel\\&quot; img_width&#x3D;\\&quot;650\\&quot; img_height&#x3D;\\&quot;368\\&quot; alt&#x3D;\\&quot;斗鱼遭遇重大危机 各大主播纷纷跳槽\\&quot; inline&#x3D;\\&quot;0\\&quot;\\u003E\\u003Cp\\u003E为什么会是B站？除了可以想见的具有优势的签约费外，在直播圈内已经做到最顶级的冯提莫，看中的并不是以往主播所在意的各种“平台”资源。冯提莫想撕掉“主播”标签，而B站恰好是那个最不像直播平台的平台。\\u003C\\u002Fp\\u003E\\u003Cp\\u003E在11月21日的新闻推送中，视听观察分析了B站今年Q3的财报。多元化的营收结构上，\\u003Cstrong\\u003EB站的直播及增值服务营收为4.5亿元人民币，占比仅为24.2%。\\u003C\\u002Fstrong\\u003E这一数字在2017年上市前曾低至7.1%，与斗鱼、虎牙等直播平台动辄90%以上的直播收入占比极为不同。这意味着，\\u003Cstrong\\u003EB站可能不太需要依靠层出不穷的“粉丝节”、“打榜”来提高营收数据。\\u003C\\u002Fstrong\\u003E\\u003C\\u002Fp\\u003E\\u003Cp\\u003E事实上，在业内，B站被认为是最像youtobe的视频网站，因而即使始终在亏损，B站在资本上的认可程度和表现，依旧要优于同样在美股上市且始终在强调盈利的斗鱼和虎牙。因而B站得以有能力和自主权，强调在不伤害用户体验的前提下变现。\\u003C\\u002Fp\\u003E\\u003Cp\\u003E对于不打算专职做主播、目标是星辰大海的冯提莫而言，不背负礼物流水压力、参与流水比拼，实在是一件值得快乐的事。\\u003C\\u002Fp\\u003E\\u003Cp\\u003E其次，近两年直播行业面临人口红利衰退、增长速度变缓的问题，\\u003Cstrong\\u003EB站却在2019年Q3达到了1.28亿月活，同比增长38%，移动端月活用户达到1.14亿，同比增长40%；月均付费用户数同比增长124%，达到795万人，新增百万播放量视频数同比增长3倍以上。\\u003C\\u002Fstrong\\u003E\\u003C\\u002Fp\\u003E\\u003Cimg src&#x3D;\\&quot;http:\\u002F\\u002Fp3.pstatp.com\\u002Flarge\\u002Fpgc-image\\u002FRmNmDsxGhfnZ3O\\&quot; img_width&#x3D;\\&quot;623\\&quot; img_height&#x3D;\\&quot;644\\&quot; alt&#x3D;\\&quot;斗鱼遭遇重大危机 各大主播纷纷跳槽\\&quot; inline&#x3D;\\&quot;0\\&quot;\\u003E\\u003Cp\\u003EB站Q3财报图解中的用户数据部分\\u003C\\u002Fp\\u003E\\u003Cp\\u003E这些数据意味着，在经历多年“有个性”而稳定缓慢的发展后，B站终于迎来了有自己个性标签的青少年人口红利。\\u003C\\u002Fp\\u003E\\u003Cp\\u003E而这批青少年用户很大一个特点是对网络红人和传统艺人的看法分界线，没有那么明确。即使是陈道明、成龙这样的老戏骨，大家也一样可以做出鬼畜视频。更不必提《大碗宽面》这类频上热搜的“镇站之宝”，整体环境对主播出身的冯提莫更加友好。B站用户的活跃度、开放性和脑洞极大的创作性也是冯提莫的音乐作品二次创作推广所渴望的。\\u003C\\u002Fp\\u003E\\u003Cp\\u003E此外，B站正在多层面布局，除了二次元外，游戏、泛娱乐、音乐、动漫层面成绩不斐。可以预见，提莫在加入B站后，会以各种形式参与到这些布局中。\\u003C\\u002Fp\\u003E\\u003Cp\\u003E在社区氛围上，与斗鱼“带哥们”针对冯提莫自制各种低俗恶意梗不同，B站整体社区氛围要更加纯粹，看中UP主的实力。更适合急需抛却以往各种“黑点”、心无旁骛做音乐的冯提莫。\\u003C\\u002Fp\\u003E\\u003Cp\\u003E\\u003Cstrong\\u003E内容同质化、大主播出走\\u003C\\u002Fstrong\\u003E\\u003C\\u002Fp\\u003E\\u003Cp\\u003E\\u003Cstrong\\u003E斗鱼的护城河还在吗\\u003C\\u002Fstrong\\u003E\\u003C\\u002Fp\\u003E\\u003Cp\\u003E在斗鱼Q3财报发布后的电话会议上，斗鱼CFO曹昊回答分析师时称，“一些头部主播有时候会要求支付高额的签约费用，我们会综合考虑，当主播收益并不能覆盖主播签约价格时，就会出现不续约的情况”。\\u003C\\u002Fp\\u003E\\u003Cp\\u003E他认为，从斗鱼当前的数据上看，一些头部主播离开平台，对平台的营收和数据影响不大。\\u003C\\u002Fp\\u003E\\u003Cp\\u003E委婉回答了斗鱼在冯提莫、张大仙等S级主播到期后，没有选择续约的原因。而今，张大仙去了斗鱼的“老冤家”虎牙，而冯提莫去了口头上称“直播只是内容生态的自然延伸，不是对外竞争性业务”却“自然而然”对直播行业隐隐造成威胁的B站。\\u003C\\u002Fp\\u003E\\u003Cp\\u003E\\u003Cstrong\\u003E斗鱼昔日巅峰时期的“四大歌姬”，如今只剩下阿冷一棵独苗。\\u003C\\u002Fstrong\\u003E陈一发凉、冯提莫走，二珂停播，连小缘也因为抑郁症停播了一段时间。曾经被誉为“商业护城河”的大主播们一一离开，即使短期内没有过多负面影响，但无疑，失去这些颇有明星范儿的“歌姬”们后，斗鱼在直播平台里，似乎就不是那么具有谈资了。\\u003C\\u002Fp\\u003E\\u003Cimg src&#x3D;\\&quot;http:\\u002F\\u002Fp9.pstatp.com\\u002Flarge\\u002Fpgc-image\\u002FRmNmFkz53H4sxf\\&quot; img_width&#x3D;\\&quot;467\\&quot; img_height&#x3D;\\&quot;607\\&quot; alt&#x3D;\\&quot;斗鱼遭遇重大危机 各大主播纷纷跳槽\\&quot; inline&#x3D;\\&quot;0\\&quot;\\u003E\\u003Cp\\u003E斗鱼2019年度十大主播\\u003C\\u002Fp\\u003E\\u003Cp\\u003E斗鱼CFO曹昊认为，未来对于游戏赛事的版权费用比重会上升。实际上，如果不是独家版权，其实对于用户的意义不是非常大。而独家版权的费用，又是着急保持持续盈利的斗鱼所负担不起的。但是在版权变现上，“白嫖文化”盛行的斗鱼已经有过一次失败的教训。\\u003C\\u002Fp\\u003E\\u003Cp\\u003E在没有找到足够承担多个一线主播离开后位置的情况下，因为各种原因一连失去冯提莫、张大仙、二珂、纳豆、小缘等一线主播。同时，又面临B站、快手、抖音的追击，斗鱼当前的情况，比当年的“千播大战”更为尴尬。\\u003C\\u002Fp\\u003E\\u003Cp\\u003E“千播大战”时期正是直播人口红利期，“造星”简单，不需要过多投入，有特点的主播很容易成长。当前，2年时间过去也仅有一个小团团能在知名度上与前辈们一较高低。\\u003C\\u002Fp\\u003E\\u003Cimg src&#x3D;\\&quot;http:\\u002F\\u002Fp3.pstatp.com\\u002Flarge\\u002Fpgc-image\\u002FRmNmFlV6TrEnf\\&quot; img_width&#x3D;\\&quot;615\\&quot; img_height&#x3D;\\&quot;570\\&quot; alt&#x3D;\\&quot;斗鱼遭遇重大危机 各大主播纷纷跳槽\\&quot; inline&#x3D;\\&quot;0\\&quot;\\u003E\\u003Cp\\u003E\\u003Cstrong\\u003E12月20日凌晨，B站因为“大碗宽面”拿下微博热搜榜第一，同天中午再次因为冯提莫登上热搜。如果再没有新人接上，“最具话题性的直播平台”称号，斗鱼要退位让新人了。\\u003C\\u002Fstrong\\u003E\\u003C\\u002Fp\\u003E&quot;";


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

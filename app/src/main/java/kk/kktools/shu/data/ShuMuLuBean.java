package kk.kktools.shu.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utils.kkutils.common.CollectionsTool;

public class ShuMuLuBean implements Serializable {
    /**
     * status : 1
     * info : success
     * data : {"id":563905,"name":"大梦主","list":[{"name":"正文卷","list":[{"id":3017470,"name":"序章","hasContent":1},{"id":3017471,"name":"第一章 沈家","hasContent":1},{"id":3017472,"name":"第二章 罗道人","hasContent":1},{"id":3017473,"name":"第三章 记名弟子","hasContent":1},{"id":3018440,"name":"第四章 化阳入门","hasContent":1},{"id":3018661,"name":"第五章 刁难","hasContent":1},{"id":3018864,"name":"第六章 寻觅","hasContent":1},{"id":3019895,"name":"第七章 降妖纪事","hasContent":1},{"id":3020059,"name":"第八章 元石","hasContent":1},{"id":3020231,"name":"第九章 画符","hasContent":1},{"id":3021526,"name":"第十章 试符","hasContent":1},{"id":3021692,"name":"第十一章 白光","hasContent":1},{"id":3021869,"name":"第十二章 石球","hasContent":1},{"id":3023031,"name":"第十三章 玉枕","hasContent":1},{"id":3023344,"name":"第十四章 诡异山村","hasContent":1},{"id":3024453,"name":"第十五章 鬼打墙","hasContent":1},{"id":3024658,"name":"第十六章 不退反进","hasContent":1},{"id":3024829,"name":"第十七章 临危不惧","hasContent":1},{"id":3026072,"name":"第十八章 斗鬼 上","hasContent":1},{"id":3026185,"name":"第十九章 斗鬼 下","hasContent":1},{"id":3027344,"name":"第二十章 雷音上人","hasContent":1},{"id":3027618,"name":"第二十一章 一场梦","hasContent":1},{"id":3027723,"name":"第二十二章 坚不可摧","hasContent":1},{"id":3028675,"name":"第二十三章 物归原处","hasContent":1},{"id":3029005,"name":"第二十四章 又是梦?","hasContent":1},{"id":3030079,"name":"第二十五章 患难之交","hasContent":1},{"id":3030362,"name":"第二十六章 仙师","hasContent":1},{"id":3031607,"name":"第二十七章 劫后余生","hasContent":1},{"id":3031695,"name":"第二十八章 萧条市井","hasContent":1},{"id":3031858,"name":"第二十九章 竟然是他?","hasContent":1},{"id":3032768,"name":"第三十章 符箓之道","hasContent":1},{"id":3033681,"name":"第三十一章 一梦千年","hasContent":1},{"id":3034795,"name":"第三十二章 魔狼围城","hasContent":1},{"id":3035072,"name":"第三十三章 魔劫日","hasContent":1},{"id":3036204,"name":"第三十四章 月下偶遇","hasContent":1},{"id":3036274,"name":"第三十五章 初窥门径","hasContent":1},{"id":3036481,"name":"第三十六章 夜袭","hasContent":1},{"id":3037489,"name":"第三十七章 隐狼","hasContent":1},{"id":3037817,"name":"第三十八章 力挽狂澜","hasContent":1},{"id":3038712,"name":"第三十九章 另一场战斗","hasContent":1},{"id":3039067,"name":"第四十章 去而复回","hasContent":1},{"id":3040328,"name":"第四十一章 风阳真人","hasContent":1},{"id":3040371,"name":"第四十二章 林中斗剑","hasContent":1},{"id":3040431,"name":"第四十三章 符器","hasContent":1},{"id":3041409,"name":"第四十四章 修道难","hasContent":1},{"id":3041694,"name":"第四十五章 无法言说","hasContent":1},{"id":3042601,"name":"第四十六章 赌一把","hasContent":1},{"id":3042892,"name":"第四十七章 下山","hasContent":1},{"id":3044158,"name":"第四十八章 于大胆","hasContent":1},{"id":3044186,"name":"第四十九章 探河","hasContent":1},{"id":3045474,"name":"第五十章 袋子","hasContent":1},{"id":3045623,"name":"第五十一章 人事不省","hasContent":1},{"id":3045745,"name":"第五十二章 时间紧迫","hasContent":1},{"id":3046793,"name":"第五十三章 \u201c寻宝符\u201d","hasContent":1},{"id":3047130,"name":"第五十四章 雨中寻","hasContent":1},{"id":3048032,"name":"第五十五章 阵中物","hasContent":1},{"id":3048333,"name":"第五十六章 神秘石匣","hasContent":1},{"id":3049267,"name":"第五十七章 牙印","hasContent":1},{"id":3049559,"name":"第五十八章 一蹦一丈高","hasContent":1},{"id":3050511,"name":"第五十九章 缩物之能","hasContent":1},{"id":3050828,"name":"第六十章 \u201c小不点\u201d","hasContent":1},{"id":3051882,"name":"第六十一章 不得其解","hasContent":1},{"id":3051897,"name":"第六十二章 原来如此","hasContent":1},{"id":3052026,"name":"第六十三章 黑色骷髅","hasContent":1},{"id":3052942,"name":"第六十四章 阴阳交泰","hasContent":1},{"id":3053182,"name":"第六十五章 通法","hasContent":1},{"id":3054038,"name":"第六十六章 踏水而行","hasContent":1},{"id":3054259,"name":"第六十七章 有人来过","hasContent":1},{"id":3055102,"name":"第六十八章 侯掌柜的请求","hasContent":1},{"id":3055338,"name":"第六十九章 春风得意","hasContent":1},{"id":3056184,"name":"第七十章 妖魔之说","hasContent":1},{"id":3056549,"name":"第七十一章 不情之请","hasContent":1},{"id":3057343,"name":"第七十二章 初得符器","hasContent":1},{"id":3057641,"name":"第七十三章 物是人非","hasContent":1},{"id":3058614,"name":"第七十四章 后山偶遇","hasContent":1},{"id":3058615,"name":"第七十五章 虚惊一场","hasContent":1},{"id":3058666,"name":"第七十六章 控水自通","hasContent":1},{"id":3059465,"name":"第七十七章 试符","hasContent":1},{"id":3059729,"name":"第七十八章 惊魂","hasContent":1},{"id":3060506,"name":"第七十九章 玉枕异变","hasContent":1},{"id":3060717,"name":"第八十章 识海通灵","hasContent":1},{"id":3061644,"name":"第八十一章 随师下山","hasContent":1},{"id":3061776,"name":"第八十二章 狗眼看人低","hasContent":1},{"id":3062530,"name":"第八十三章 闹鬼","hasContent":1},{"id":3062737,"name":"第八十四章 七星阵","hasContent":1},{"id":3063534,"name":"第八十五章 斩妖","hasContent":1},{"id":3063707,"name":"第八十六章 来龙去脉","hasContent":1},{"id":3064620,"name":"第八十七章 珈蓝寺","hasContent":1},{"id":3064740,"name":"第八十八章 寺中惊变","hasContent":1},{"id":3065517,"name":"第八十九章 合力围杀","hasContent":1},{"id":3065646,"name":"第九十章 斗妖","hasContent":1},{"id":3065773,"name":"第九十一章 三眼狐妖","hasContent":1},{"id":3066465,"name":"第九十二章 携手抗妖","hasContent":1},{"id":3067607,"name":"第九十三章 寻找出路","hasContent":1},{"id":3068468,"name":"第九十四章 意外收获","hasContent":1},{"id":3068544,"name":"第九十五章 神秘骸骨","hasContent":1},{"id":3069346,"name":"第九十六章 资质大增","hasContent":1},{"id":3069594,"name":"第九十七章 突飞猛进","hasContent":1},{"id":3070347,"name":"第九十八章 通灵","hasContent":1},{"id":3070606,"name":"第九十九章 召唤兽","hasContent":1},{"id":3071388,"name":"第一百章 橙红虾怪","hasContent":1},{"id":3071621,"name":"第一百零一章 借尸还魂","hasContent":1},{"id":3072611,"name":"第一百零二章 梦中奇景","hasContent":1},{"id":3072843,"name":"第一百零三章 洞窟老者","hasContent":1},{"id":3073912,"name":"第一百零四章 梦中感悟","hasContent":1},{"id":3073913,"name":"第一百零五章 现实召唤","hasContent":1},{"id":3074468,"name":"第一百零六章 大海龟","hasContent":1},{"id":3074469,"name":"第一百零七章 不速之客","hasContent":1},{"id":3074653,"name":"第一百零八章 临危受命","hasContent":1},{"id":3075551,"name":"第一百零九章 师叔祖","hasContent":1},{"id":3076764,"name":"第一百一十章 临终授典","hasContent":1},{"id":3076765,"name":"第一百一十一章 三头尸狼","hasContent":1},{"id":3076778,"name":"第一百一十二章 追击","hasContent":1},{"id":3077654,"name":"第一百一十三章 危机四伏","hasContent":1},{"id":3077661,"name":"第一百一十四章 内奸","hasContent":1},{"id":3078576,"name":"第一百一十五章 绿毛僵尸","hasContent":1},{"id":3078581,"name":"第一百一十六章 尸影鬼","hasContent":1},{"id":3079455,"name":"第一百一十七章 怀疑","hasContent":1},{"id":3079460,"name":"第一百一十八章 追踪宝典","hasContent":1},{"id":3080289,"name":"第一百一十九章 夜奔","hasContent":1},{"id":3080290,"name":"第一百二十章 合体","hasContent":1},{"id":3081080,"name":"第一百二十一章 初战古化灵","hasContent":1},{"id":3081081,"name":"第一百二十二章 驱魔世家","hasContent":1},{"id":3081087,"name":"第一百二十三章 逃命","hasContent":1},{"id":3081959,"name":"第一百二十四章 甩不掉的小强","hasContent":1},{"id":3081960,"name":"第一百二十五章 再次召唤","hasContent":1},{"id":3082831,"name":"第一百二十六章 成功","hasContent":1},{"id":3082843,"name":"第一百二十七章 降神术","hasContent":1},{"id":3083630,"name":"第一百二十八章 再战古化灵","hasContent":1},{"id":3083631,"name":"第一百二十九章 又来一只虾兵","hasContent":1},{"id":3084655,"name":"第一百三十章 鬼将","hasContent":1},{"id":3084656,"name":"第一百三十一章 千钧一发","hasContent":1},{"id":3085260,"name":"第一百三十二章 回白家","hasContent":1},{"id":3085261,"name":"第一百三十三章 见家长","hasContent":1},{"id":3085278,"name":"第一百三十四章 闲聊","hasContent":1},{"id":3086190,"name":"第一百三十五章 妖风","hasContent":1},{"id":3086195,"name":"第一百三十六章 勾魂马面","hasContent":1},{"id":3086968,"name":"第一百三十七章 三恨鬼门关","hasContent":1},{"id":3086974,"name":"第一百三十八章 迷雾孔洞","hasContent":1},{"id":3087762,"name":"第一百三十九章 抵御妖袭","hasContent":1},{"id":3087763,"name":"第一百四十章 长寿村","hasContent":1},{"id":3088773,"name":"第一百四十一章 荒凉村落","hasContent":1},{"id":3088774,"name":"第一百四十二章 英洛的请求","hasContent":1},{"id":3089638,"name":"第一百四十三章 探查","hasContent":1},{"id":3089697,"name":"第一百四十四章 上山","hasContent":1},{"id":3090553,"name":"第一百四十五章 狂豹","hasContent":1},{"id":3091289,"name":"第一百四十六章 脱险","hasContent":1},{"id":3091290,"name":"第一百四十七章 静修","hasContent":1},{"id":3091291,"name":"第一百四十八章 初次尝试","hasContent":1},{"id":3092011,"name":"第一百四十九章 凝练法脉","hasContent":1},{"id":3092012,"name":"第一百五十章 惊人天资","hasContent":1},{"id":3092965,"name":"第一百五十一章 避水决-+","hasContent":1},{"id":3092966,"name":"第一百五十二章 祭炼","hasContent":1},{"id":3092967,"name":"第一百五十三章 兽袭","hasContent":1},{"id":3093549,"name":"第一百五十四章 猴王寻仇","hasContent":1},{"id":3093550,"name":"第一百五十五章 碾杀","hasContent":1},{"id":3094325,"name":"第一百五十六章 小鱼被抓","hasContent":1},{"id":3094330,"name":"第一百五十七章 它又回来了","hasContent":1},{"id":3095087,"name":"第一百五十八章 美人蛇","hasContent":1},{"id":3095088,"name":"第一百五十九章 马婆婆赐宝","hasContent":1},{"id":3096029,"name":"第一百六十章 追杀","hasContent":1},{"id":3096826,"name":"第一百六十一章 老巢","hasContent":1},{"id":3096827,"name":"第一百六十二章 另一条","hasContent":1},{"id":3096837,"name":"第一百六十三章 紧迫","hasContent":1},{"id":3096838,"name":"新书《大梦主》上架感言","hasContent":1}]},{"name":"初入梦途","list":[{"id":3097181,"name":"第一百六十四章 月轮斩妖","hasContent":1},{"id":3097182,"name":"第一百六十五章 再上方寸山","hasContent":1},{"id":3097183,"name":"第一百六十六章 一日千里","hasContent":1},{"id":3097406,"name":"第一百六十七章 噬天虎","hasContent":1},{"id":3097407,"name":"第一百六十八章 夺命狂奔","hasContent":1},{"id":3098109,"name":"第一百六十九章 蟹将浪普","hasContent":1},{"id":3098110,"name":"第一百七十章 以身投棋","hasContent":1},{"id":3098111,"name":"第一百七十一章 误入三星洞","hasContent":1},{"id":3098117,"name":"第一百七十二章 遗址","hasContent":1},{"id":3098118,"name":"第一百七十三章 玉简","hasContent":1},{"id":3098824,"name":"第一百七十四章 意外发现","hasContent":1},{"id":3098825,"name":"第一百七十五章 古怪黑白子","hasContent":1},{"id":3098826,"name":"第一百七十六章 百草谷","hasContent":1},{"id":3099697,"name":"第一百七十七章 人参","hasContent":1},{"id":3099698,"name":"第一百七十八章 七星笔","hasContent":1},{"id":3099699,"name":"第一百七十九章 仙灵百草","hasContent":1},{"id":3100357,"name":"第一百八十章 异响","hasContent":1},{"id":3100358,"name":"第一百八十一章 神魂离体","hasContent":1},{"id":3100359,"name":"第一百八十二章 出窍","hasContent":1},{"id":3101171,"name":"第一百八十三章 观道洞","hasContent":1},{"id":3101183,"name":"第一百八十四章 石门","hasContent":1},{"id":3101184,"name":"第一百八十五章 梦中梦","hasContent":1},{"id":3101849,"name":"第一百八十六章 黄庭经","hasContent":1},{"id":3101850,"name":"第一百八十七章 龙象之力","hasContent":1},{"id":3101851,"name":"第一百八十八章 情非得已","hasContent":1},{"id":3102540,"name":"第一百八十九章 恍如隔世","hasContent":1},{"id":3102541,"name":"第一百九十章 赠灵草","hasContent":1},{"id":3102542,"name":"第一百九十一章 另辟蹊径","hasContent":1},{"id":3103247,"name":"第一百九十二章 落雷符","hasContent":1},{"id":3103248,"name":"第一百九十三章 因势利导","hasContent":1},{"id":3103249,"name":"第一百九十四章 翱翔虚空","hasContent":1},{"id":3103913,"name":"第一百九十五章 围村","hasContent":1},{"id":3103914,"name":"第一百九十六章 极品法器","hasContent":1},{"id":3103915,"name":"第一百九十七章 意见不一","hasContent":1},{"id":3104548,"name":"第一百九十八章 来历","hasContent":1},{"id":3104549,"name":"第一百九十九章 强横肉身","hasContent":1},{"id":3104550,"name":"第两百章 试符","hasContent":1},{"id":3105238,"name":"第两百零一章 救治英洛","hasContent":1},{"id":3105239,"name":"第两百零二章 造化弄人","hasContent":1},{"id":3105241,"name":"第两百零三章 斜月步","hasContent":1},{"id":3105828,"name":"第两百零四章 乙木仙遁","hasContent":1},{"id":3105829,"name":"第两百零五章 天狼吞山","hasContent":1},{"id":3105830,"name":"第两百零六章 移村","hasContent":1},{"id":3106495,"name":"第两百零七章 珍惜","hasContent":1},{"id":3106496,"name":"第两百零八章 一夜噩梦","hasContent":1},{"id":3106497,"name":"第两百零九章 山下静室","hasContent":1},{"id":3107095,"name":"第两百一十章 完整纯阳决","hasContent":1},{"id":3107096,"name":"第两百一十一章 水暖阁出事","hasContent":1},{"id":3107097,"name":"第两百一十二章 白水道长","hasContent":1},{"id":3107707,"name":"第两百一十三章 白面书生","hasContent":1},{"id":3107708,"name":"第两百一十四章 二少爷","hasContent":1},{"id":3107709,"name":"第两百一十五章 画符材料","hasContent":1},{"id":3108285,"name":"第两百一十六章 符纸之分","hasContent":1},{"id":3108286,"name":"第两百一十七章 夜行","hasContent":1},{"id":3108287,"name":"第两百一十八章 水鬼","hasContent":1},{"id":3108981,"name":"第两百一十九章 勾魂马面","hasContent":1},{"id":3108982,"name":"第两百二十章 小雷符立功","hasContent":1},{"id":3108983,"name":"第两百二十一章 取香疗伤","hasContent":1},{"id":3109573,"name":"第两百二十二章 心照不宣","hasContent":1},{"id":3109574,"name":"第两百二十三章 第八位客卿","hasContent":1},{"id":3109575,"name":"第两百二十四章 仙玉","hasContent":1},{"id":3110734,"name":"第两百二十五章 再遇二少爷","hasContent":1},{"id":3110735,"name":"第两百二十六章 说话算话","hasContent":1},{"id":3110736,"name":"第两百二十七章 买符纸","hasContent":1},{"id":3111334,"name":"第两百二十八章 横财","hasContent":1},{"id":3111335,"name":"第两百二十九章 名声不佳","hasContent":1},{"id":3111336,"name":"第两百三十章 二少爷的赌约","hasContent":1}]}]}
     */

    public int status;
    public String info;
    public MuLuItem data;
    ShuSerachBean.BookInfo bookInfo;
    public List<MuLuItem > getAll(){
        List<MuLuItem > result=new ArrayList<>();
        getAllImp(data, result);
        Collections.reverse(result);
        return result;

    }
    public void getAllImp(MuLuItem muLuItem, List<MuLuItem > result){
        if(muLuItem==null)return;
        muLuItem.parentId=bookInfo.Id;
        if(muLuItem.hasContent==1){
            result.add(muLuItem);
        }else {
            if(CollectionsTool.NotEmptyList(muLuItem.list)){
                for (MuLuItem luItem : muLuItem.list) {
                    getAllImp(luItem, result);
                }
            }
        }

    }

    public void setParent(ShuSerachBean.BookInfo bookInfo) {
        this.bookInfo=bookInfo;
    }

    public static class MuLuItem implements Serializable{
        /**
         * id : 3017470
         * name : 序章
         * hasContent : 1
         */

        public int id;
        public int parentId;
        public String name;
        public int hasContent;
        public List<MuLuItem> list;
    }
}

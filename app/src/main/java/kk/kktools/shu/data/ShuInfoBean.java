package kk.kktools.shu.data;

import java.io.Serializable;
import java.util.List;

public class ShuInfoBean extends DataParent {

    /**
     * status : 1
     * info : success
     * data : {"Id":146769,"Name":"剑来","Img":"jianlai.jpg","Author":"烽火戏诸侯","Desc":"大千世界，无奇不有。我陈平安，唯有一剑，可搬山，倒海，降妖，镇魔，敕神，摘星，断江，摧城，开天！[剑来]","CId":95,"CName":"玄幻奇幻","LastTime":"8/24/2020 12:10:04 PM","FirstChapterId":7447709,"LastChapter":"第七百九十三章 很绣虎","LastChapterId":7789868,"BookStatus":"连载","SameUserBooks":[{"Id":6917,"Name":"陈二狗的妖孽人生","Author":"烽火戏诸侯","Img":"chenergoudeyaonierensheng.jpg","LastChapterId":3390160,"LastChapter":"大结局 下 全书完","Score":0},{"Id":266011,"Name":"极品公子","Author":"烽火戏诸侯","Img":"jipingongzi.jpg","LastChapterId":1515297,"LastChapter":"第249章 不教天下人负白家","Score":0},{"Id":26486,"Name":"老子是癞蛤蟆","Author":"烽火戏诸侯","Img":"laozishilaihama.jpg","LastChapterId":1471553,"LastChapter":"本书已经在当当网预售了...","Score":0},{"Id":258380,"Name":"桃花","Author":"烽火戏诸侯","Img":"taohua.jpg","LastChapterId":1339220,"LastChapter":"第114章 天地大苍生小","Score":0},{"Id":27996,"Name":"天神下凡","Author":"烽火戏诸侯","Img":"tianshenxiafan.jpg","LastChapterId":1654769,"LastChapter":"第15章 我征服 中","Score":0},{"Id":61,"Name":"雪中悍刀行","Author":"烽火戏诸侯","Img":"61.jpg","LastChapterId":4914359,"LastChapter":"番外第十章","Score":0}],"SameCategoryBooks":[{"Id":524120,"Name":"世界架构师之王","Img":"shijiejiagoushizhiwang.jpg","Score":0},{"Id":427360,"Name":"我真的是人生赢家","Img":"wozhendeshirenshengyingjia.jpg","Score":0},{"Id":146769,"Name":"剑来","Img":"jianlai.jpg","Score":0},{"Id":146,"Name":"魔王神官II","Img":"146.jpg","Score":0},{"Id":517589,"Name":"数攻","Img":"shugong.jpg","Score":0},{"Id":519454,"Name":"我能看见主角光环","Img":"wonengkanjianzhujiaoguanghuan.jpg","Score":0},{"Id":576916,"Name":"土狗超进化","Img":"tugouchaojinhua.jpg","Score":0},{"Id":8153,"Name":"人道至尊","Img":"rendaozhizun.jpg","Score":0},{"Id":518792,"Name":"纯白魔女","Img":"chunbaimonv.jpg","Score":0},{"Id":455761,"Name":"神树领主","Img":"shenshulingzhu.jpg","Score":0},{"Id":584677,"Name":"有四十八件帝具的我却只想靠自己","Img":"yousishibajiandijudewoquezhixiangkaoziji.jpg","Score":0},{"Id":132,"Name":"异世之光脑神官","Img":"132.jpg","Score":0}],"BookVote":{"BookId":146769,"TotalScore":6050,"VoterCount":669,"Score":9}}
     */

    public int status;
    public String info;
    public BookInfo data;

    public static class BookInfo implements Serializable {
        /**
         * Id : 146769
         * Name : 剑来
         * Img : jianlai.jpg
         * Author : 烽火戏诸侯
         * Desc : 大千世界，无奇不有。我陈平安，唯有一剑，可搬山，倒海，降妖，镇魔，敕神，摘星，断江，摧城，开天！[剑来]
         * CId : 95
         * CName : 玄幻奇幻
         * LastTime : 8/24/2020 12:10:04 PM
         * FirstChapterId : 7447709
         * LastChapter : 第七百九十三章 很绣虎
         * LastChapterId : 7789868
         * BookStatus : 连载
         * SameUserBooks : [{"Id":6917,"Name":"陈二狗的妖孽人生","Author":"烽火戏诸侯","Img":"chenergoudeyaonierensheng.jpg","LastChapterId":3390160,"LastChapter":"大结局 下 全书完","Score":0},{"Id":266011,"Name":"极品公子","Author":"烽火戏诸侯","Img":"jipingongzi.jpg","LastChapterId":1515297,"LastChapter":"第249章 不教天下人负白家","Score":0},{"Id":26486,"Name":"老子是癞蛤蟆","Author":"烽火戏诸侯","Img":"laozishilaihama.jpg","LastChapterId":1471553,"LastChapter":"本书已经在当当网预售了...","Score":0},{"Id":258380,"Name":"桃花","Author":"烽火戏诸侯","Img":"taohua.jpg","LastChapterId":1339220,"LastChapter":"第114章 天地大苍生小","Score":0},{"Id":27996,"Name":"天神下凡","Author":"烽火戏诸侯","Img":"tianshenxiafan.jpg","LastChapterId":1654769,"LastChapter":"第15章 我征服 中","Score":0},{"Id":61,"Name":"雪中悍刀行","Author":"烽火戏诸侯","Img":"61.jpg","LastChapterId":4914359,"LastChapter":"番外第十章","Score":0}]
         * SameCategoryBooks : [{"Id":524120,"Name":"世界架构师之王","Img":"shijiejiagoushizhiwang.jpg","Score":0},{"Id":427360,"Name":"我真的是人生赢家","Img":"wozhendeshirenshengyingjia.jpg","Score":0},{"Id":146769,"Name":"剑来","Img":"jianlai.jpg","Score":0},{"Id":146,"Name":"魔王神官II","Img":"146.jpg","Score":0},{"Id":517589,"Name":"数攻","Img":"shugong.jpg","Score":0},{"Id":519454,"Name":"我能看见主角光环","Img":"wonengkanjianzhujiaoguanghuan.jpg","Score":0},{"Id":576916,"Name":"土狗超进化","Img":"tugouchaojinhua.jpg","Score":0},{"Id":8153,"Name":"人道至尊","Img":"rendaozhizun.jpg","Score":0},{"Id":518792,"Name":"纯白魔女","Img":"chunbaimonv.jpg","Score":0},{"Id":455761,"Name":"神树领主","Img":"shenshulingzhu.jpg","Score":0},{"Id":584677,"Name":"有四十八件帝具的我却只想靠自己","Img":"yousishibajiandijudewoquezhixiangkaoziji.jpg","Score":0},{"Id":132,"Name":"异世之光脑神官","Img":"132.jpg","Score":0}]
         * BookVote : {"BookId":146769,"TotalScore":6050,"VoterCount":669,"Score":9}
         */

        public int currReadId;//当前阅读id，用于跳转

        public int Id;
        public String Name;
        public String Img;
        public String Author;
        public String Desc;
        public int CId;
        public String CName;
        public String LastTime;
        public int FirstChapterId;
        public String LastChapter;
        public int LastChapterId;
        public String BookStatus;
        public BookVoteBean BookVote;
        public List<SameUserBooksBean> SameUserBooks;
        public List<SameCategoryBooksBean> SameCategoryBooks;

        public static class BookVoteBean {
            /**
             * BookId : 146769
             * TotalScore : 6050
             * VoterCount : 669
             * Score : 9.0
             */

            public int BookId;
            public int TotalScore;
            public int VoterCount;
            public double Score;
        }

        public static class SameUserBooksBean {
            /**
             * Id : 6917
             * Name : 陈二狗的妖孽人生
             * Author : 烽火戏诸侯
             * Img : chenergoudeyaonierensheng.jpg
             * LastChapterId : 3390160
             * LastChapter : 大结局 下 全书完
             * Score : 0.0
             */

            public int Id;
            public String Name;
            public String Author;
            public String Img;
            public int LastChapterId;
            public String LastChapter;
            public double Score;
        }

        public static class SameCategoryBooksBean {
            /**
             * Id : 524120
             * Name : 世界架构师之王
             * Img : shijiejiagoushizhiwang.jpg
             * Score : 0.0
             */

            public int Id;
            public String Name;
            public String Img;
            public double Score;
        }
    }
}

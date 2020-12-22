package kk.kktools.tongxunlu;

import android.view.View;
import android.widget.TextView;



import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import kk.kktools.R;
import utils.kkutils.common.UiTool;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.ui.KKQuickIndexBar;
import utils.kkutils.ui.KKSimpleRecycleView;
import utils.kkutils.ui.recycleview.KKRecycleViewScrollTool;
import utils.kkutils.ui.recycleview.KKStickyHeaderDecoration;

/***
 * 观看记录
 */
public class UserRecordFragment extends KKParentFragment {

    @Override
    public int initContentViewId() {
        return R.layout.kk_test_user;
    }



    KKQuickIndexBar quickIndexBar;
    @Override
    public void initData() {


        List<String> datasResult=initTestData();



        int []types=new int[]{R.layout.kk_test_user_list,R.layout.kk_test_user_list_title};

        recycleView.setData(datasResult, types, new KKSimpleRecycleView.KKRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView, KKSimpleRecycleView.WzViewHolder wzViewHolder) {
                super.initData(position, type, itemView, wzViewHolder);

            }
        });
        recycleView.setData(datasResult,types, new KKSimpleRecycleView.KKRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView) {
                super.initData(position, type, itemView);

                type=getItemViewType(position);

                String name = datasResult.get(position);

                if(type==R.layout.kk_test_user_list){
                    UiTool.setTextView(itemView,R.id.tv_user_name_phone,name+"-"+position);
                }
                if(type==R.layout.kk_test_user_list_title){
                    UiTool.setTextView(itemView,R.id.tv_user_title,name);
                    quickIndexBar.setSelect(name);
                }
            }

            @Override
            public int getItemViewType(int position) {
                if(datasResult.get(position).length()==1)return types[1];
                return types[0];
            }
        });

        recycleView.addItemDecoration(new KKStickyHeaderDecoration(recycleView.getAdapter()) {
            @Override
            public boolean hasHeader(int position) {
                return  (datasResult.get(position).length()==1);
            }

            @Override
            public void headerChange(View currHead) {
                TextView tv=currHead.findViewById(R.id.tv_user_title);
                quickIndexBar.setSelect(tv.getText().toString());

            }
        });
        quickIndexBar.setOnIndexChangedListener(new KKQuickIndexBar.OnIndexChangedListener() {
            @Override
            public void onIndexChanged(int selectIndex, String selectStr, boolean showIndicator) {
                int position = datasResult.indexOf(selectStr);
                KKRecycleViewScrollTool.scrollToPositionToTop(recycleView,position);
            }

        });

    }


    public List<String> initTestData(){
        String test="张吉惟、林国瑞、林玟书、林雅南、江奕云、刘柏宏、阮建安、林子帆、夏志豪、吉茹定、李中冰、黄文隆、谢彦文、傅智翔、洪振霞、刘姿婷、荣姿康、吕致盈、方一强、黎芸贵、郑伊雯、雷进宝、吴美隆、吴心真、王美珠、郭芳天、李雅惠、陈文婷、曹敏侑、王依婷、陈婉璇、吴美玉、蔡依婷、郑昌梦、林家纶、黄丽昆、李育泉、黄芸欢、吴韵如、李肇芬、卢木仲、李成白、方兆玉、刘翊惠、丁汉臻、吴佳瑞、舒绿珮、周白芷、张姿妤、张虹伦、周琼玟、倪怡芳、郭贵妃、杨佩芳、黄文旺、黄盛玫、郑丽青、许智云、张孟涵、李小爱、王恩龙、朱政廷、邓诗涵、陈政倩、吴俊伯、阮馨学、翁惠珠、吴思翰、林佩玲、邓海来、陈翊依、李建智、武淑芬、金雅琪、赖怡宜、黄育霖、张仪湖、王俊民、张诗刚、林慧颖、沈俊君、陈淑妤、李姿伶、高咏钰、黄彦宜、周孟儒、潘欣臻、李祯韵、叶洁启、梁哲宇、黄晓萍、杨雅萍、卢志铭、张茂以、林婉婷、蔡宜芸、林珮瑜、黄柏仪、周逸珮、夏雅惠、王采珮、林孟霖、林竹水、王怡乐、王爱乐、金佳蓉、韩健毓、李士杰、陈萱珍、苏姿婷、张政霖、李志宏、陈素达、陈虹荣、何美玲、李仪琳、张俞幸、黄秋萍、潘吉维、陈智筠、蔡书玮、陈信峰、林培伦、查瑜舜、黎慧萱、郑士易、陈建豪、吴怡婷、徐紫富、张博海、黎宏儒、柯乔喜、胡睿纯、王淑月、陈百菁、王雅娥、黄佩珊、李必辰、吴耀华、彭郁婷、王秀玲、谢佳儒、罗静蓁、杨舒南、蔡政琳、杨绍瑜、金育木、杨韦成、韩宁政、蒋廷湖、毛展霞、廖婉宏、黄怡强、郭冰宇、黄伟依、叶元映、林智超、李姿婷、李莉火、邱雅雯、王淑芳、陈枝盈、高成彦、徐采伶、杨大雪、林彦韦、李升毓、邱宜瑶、陈政文、李宜豪、陈宜宁、陈志宏、阮柔治、林乐妹、简健昀、廖雅君、梁佩芬、苏玮伦、秦娇真、谢佳雯、李仁杰、李佳和、郭贤青、吴怡伶、陈怡婷、阮晴桦、辛翔坤、林孟富、刘美玲、涂昀琬、白凯修、黄蓉芳、赵吟琪、张裕忠、石春紫、方美君、潘右博、俞星如、张冠杰、钟庭玮、叶茜彦、陈伯薇、陈昭祥、陈伟伦、黄雅慧、郭子豪、黄彦霖、宋合、许雅婷、王圣如、何伶元、钟伦军、蔡佳蓉、溥康柔、冯成轩、陈嘉惠、吴惠劭、谢健铭、林怡婷、廖佳蓉、李佩伯、何珮甄、谢晓玲、许彦霖、林威强、周佳勋、林静怡、周筠亚、陈仲宜、胡东贵、陈绍翰、梁姵来、陈雅吉、张莉雯、陈韦荣、林素伦、李菁芷、蔡玉婷、郑智钧、吴孟钰、蔡国伟、连俊达、李雅婷、李礼娇、李忆孝、黄静雯、陈淳宝、李文育、林佳蓉、罗依茂、李淑佩、谢怡君、王美玲、黄慧学、邓幸韵、陈秀琬、许岳平、许爱礼、谢一忠、简志雪、赵若喜、许承翰、姚哲维、苏俊安、郭礼钰、姜佩珊、张鸿信、秦欣瑜、李旺劭、陈怡爱、陈秀德、张佳伶、郑凯婷、郑雅任、黄国妹、林芳江、江骏生、黄儒纯、王培伦、陈蕙侑、蔡宜慧、陈信意、陈惠雯、张琇纶、黄碧仪、陈志文、谢懿富、杨凡靖、蔡秀琴、温惠玲、林宗其、林绍泰、何佳慧、蔡辰纶、王雅雯、叶怡财、冯雅筑&oq=张吉惟、林国瑞、林玟书、林雅南、江奕云、刘柏宏、阮建安、林子帆、夏志豪、吉茹定、李中冰、黄文隆、谢彦文、傅智翔、洪振霞、刘姿婷、荣姿康、吕致盈、方一强、黎芸贵、郑伊雯、雷进宝、吴美隆、吴心真、王美珠、郭芳天、李雅惠、陈文婷、曹敏侑、王依婷、陈婉璇、吴美玉、蔡依婷、郑昌梦、林家纶、黄丽昆、李育泉、黄芸欢、吴韵如、李肇芬、卢木仲、李成白、方兆玉、刘翊惠、丁汉臻、吴佳瑞、舒绿珮、周白芷、张姿妤、张虹伦、周琼玟、倪怡芳、郭贵妃、杨佩芳、黄文旺、黄盛玫、郑丽青、许智云、张孟涵、李小爱、王恩龙、朱政廷、邓诗涵、陈政倩、吴俊伯、阮馨学、翁惠珠、吴思翰、林佩玲、邓海来、陈翊依、李建智、武淑芬、金雅琪、赖怡宜、黄育霖、张仪湖、王俊民、张诗刚、林慧颖、沈俊君、陈淑妤、李姿伶、高咏钰、黄彦宜、周孟儒、潘欣臻、李祯韵、叶洁启、梁哲宇、黄晓萍、杨雅萍、卢志铭、张茂以、林婉婷、蔡宜芸、林珮瑜、黄柏仪、周逸珮、夏雅惠、王采珮、林孟霖、林竹水、王怡乐、王爱乐、金佳蓉、韩健毓、李士杰、陈萱珍、苏姿婷、张政霖、李志宏、陈素达、陈虹荣、何美玲、李仪琳、张俞幸、黄秋萍、潘吉维、陈智筠、蔡书玮、陈信峰、林培伦、查瑜舜、黎慧萱、郑士易、陈建豪、吴怡婷、徐紫富、张博海、黎宏儒、柯乔喜、胡睿纯、王淑月、陈百菁、王雅娥、黄佩珊、李必辰、吴耀华、彭郁婷、王秀玲、谢佳儒、罗静蓁、杨舒南、蔡政琳、杨绍瑜、金育木、杨韦成、韩宁政、蒋廷湖、毛展霞、廖婉宏、黄怡强、郭冰宇、黄伟依、叶元映、林智超、李姿婷、李莉火、邱雅雯、王淑芳、陈枝盈、高成彦、徐采伶、杨大雪、林彦韦、李升毓、邱宜瑶、陈政文、李宜豪、陈宜宁、陈志宏、阮柔治、林乐妹、简健昀、廖雅君、梁佩芬、苏玮伦、秦娇真、谢佳雯、李仁杰、李佳和、郭贤青、吴怡伶、陈怡婷、阮晴桦、辛翔坤、林孟富、刘美玲、涂昀琬、白凯修、黄蓉芳、赵吟琪、张裕忠、石春紫、方美君、潘右博、俞星如、张冠杰、钟庭玮、叶茜彦、陈伯薇、陈昭祥、陈伟伦、黄雅慧、郭子豪、黄彦霖、宋合、许雅婷、王圣如、何伶元、钟伦军、蔡佳蓉、溥康柔、冯成轩、陈嘉惠、吴惠劭、谢健铭、林怡婷、廖佳蓉、李佩伯、何珮甄、谢晓玲、许彦霖、林威强、周佳勋、林静怡、周筠亚、陈仲宜、胡东贵、陈绍翰、梁姵来、陈雅吉、张莉雯、陈韦荣、林素伦、李菁芷、蔡玉婷、郑智钧、吴孟钰、蔡国伟、连俊达、李雅婷、李礼娇、李忆孝、黄静雯、陈淳宝、李文育、林佳蓉、罗依茂、李淑佩、谢怡君、王美玲、黄慧学、邓幸韵、陈秀琬、许岳平、许爱礼、谢一忠、简志雪、赵若喜、许承翰、姚哲维、苏俊安、郭礼钰、姜佩珊、张鸿信、秦欣瑜、李旺劭、陈怡爱、陈秀德、张佳伶、郑凯婷、郑雅任、黄国妹、林芳江、江骏生、黄儒纯、王培伦、陈蕙侑、蔡宜慧、陈信意、陈惠雯、张琇纶、黄碧仪、陈志文、谢懿富、杨凡靖、蔡秀琴、温惠玲、林宗其、林绍泰、何佳慧、蔡辰纶、王雅雯、叶怡财、冯雅筑";

        List<String> datasTem=new ArrayList<>();
        String[] split = test.split("、");
        List<String> datas= Arrays.asList(split);
        for (String data : datas) {
            String[] strings = PinyinHelper.toHanyuPinyinStringArray(data.charAt(0));
            String s = strings[0].toUpperCase();
            data=s.charAt(0)+data;
            if(!datasTem.contains(data))datasTem.add(data);
        }
        Collections.sort(datasTem);

        List<String> datasResult=new ArrayList<>();
        String group="";
        for (String s : datasTem) {
            char g = s.charAt(0);
            if(!group.equals(""+g)){
                group=""+g;
                datasResult.add(group);
            }
            datasResult.add(s);
        }

        return datasResult;
    }


}

package utils.kkutils.common;

import android.media.MediaPlayer;
import android.os.Build;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import utils.kkutils.AppTool;

/**
 * Created by ishare on 2016/6/23.
 */

public class MathTool {

    public static BigDecimal getBig(double d) {
        return new BigDecimal(Double.toString(d));
    }

    /**
     * double 相加
     */
    public static double jia(double d1, double d2) {
        return getBig(d1).add(getBig(d2)).doubleValue();
    }

    /**
     * double 相减
     */
    public static double jian(double d1, double d2) {
        return getBig(d1).subtract(getBig(d2)).doubleValue();
    }

    /**
     * double 相乘
     */
    public static double cheng(double d1, double d2) {
        return getBig(d1).multiply(getBig(d2)).doubleValue();
    }

    /**
     * double 相除
     */
    public static double chu(double d1, double d2) {
        return getBig(d1).divide(getBig(d2)).doubleValue();
    }


    /***
     * double 转string ， 不会出现科学计数法
     * @param d
     * @return
     */
    public static String toString(double d){
        NumberFormat numberFormat=NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(10000);
        return numberFormat.format(d);
    }


    /**
     * 保留指定小数位数
     * @param price
     * @param num
     * @return
     */
    public static String getNum(Object price,int num){
        try {
            return String.format("%."+num+"f",Double.valueOf(""+price));
        }catch (Exception e){
            LogTool.ex(e);
        }
        return ""+price;
    }
    /***
     * 获取两位有效数字
     *
     * @return
     */
    public static String get2num(Object price) {
       return getNum(price,2);
    }

    /**
     * 格式化数字， 如果小数位没有就返回整数
     * @return
     */
    public static String getNoDotNumStr(Object price) {
        double num=Double.valueOf(""+price);
        if(num==(int)num)return ""+(int)num;
        return get2num(num);
    }
    /**
     * 格式化数字， 如果小数位没有就返回整数 逗号分割
     * @return
     */
    public static String getMoneyStr(Object price){
        double num=Double.valueOf(""+price);
        DecimalFormat df = new DecimalFormat();
        df.applyPattern(",###.##");// ,###.##  这个是有小数就显示没有就不显示，  如果要都显示用  ,###.00
        return df.format(num);
    }

    /***
     * 计算年利率
     * @param money 本金
     * @param day  天数
     * @param moneyAdd  增加的本金
     * @return
     */
    public static double computNianLiLv(double money,int day,double moneyAdd){
        return moneyAdd/money/day*365*100;
    }

    /***
     * 计算年利率
     * @param moneyBegin  初始本金
     * @param day  天数
     * @param moneyEnd 结束本金
     * @return
     */
    public static double computNianLiLvEnd(double moneyBegin,int day,double moneyEnd){
       // return (moneyEnd-moneyBegin)/moneyBegin/day*365*100;
        return computNianLiLv(moneyBegin, day, moneyEnd-moneyBegin);
    }

    /***
     * 计算收益
     * @param money 本金
     * @param lilv 年化利率（365） 比如  4
     * @param day  计算天数
     * @return
     */
    public static double computeNianLiXi(double money,double lilv,int day){
        return money*lilv*day/365/100;
    }


    /***
     * 数字 转中文读音字符串
     * @param d
     * @return
     */
    public static String numberToCN(double d){
        return NumberUtil.bigDecimal2chineseNum(BigDecimal.valueOf(d));
    }

    /**
     * 数字转换中文
     *
     * @author huangshuai
     * @date 2019/11/22 0022
     */
    public static class NumberUtil {

        private NumberUtil() { }

        /**
         * 中文数字
         */
        private static final String[] CN_NUM = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};

        /**
         * 中文数字单位
         */
        private static final String[] CN_UNIT = {"", "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千"};

        /**
         * 特殊字符：负
         */
        private static final String CN_NEGATIVE = "负";

        /**
         * 特殊字符：点
         */
        private static final String CN_POINT = "点";


        /**
         * int 转 中文数字
         * 支持到int最大值
         *
         * @param intNum 要转换的整型数
         * @return 中文数字
         */
        public static String int2chineseNum(int intNum) {
            StringBuffer sb = new StringBuffer();
            boolean isNegative = false;
            if (intNum < 0) {
                isNegative = true;
                intNum *= -1;
            }
            int count = 0;
            while(intNum > 0) {
                sb.insert(0, CN_NUM[intNum % 10] + CN_UNIT[count]);
                intNum = intNum / 10;
                count++;
            }

            if (isNegative)
                sb.insert(0, CN_NEGATIVE);


            return sb.toString().replaceAll("零[千百十]", "零").replaceAll("零+万", "万")
                    .replaceAll("零+亿", "亿").replaceAll("亿万", "亿零")
                    .replaceAll("零+", "零").replaceAll("零$", "");
        }

        /**
         * bigDecimal 转 中文数字
         * 整数部分只支持到int的最大值
         *
         * @param bigDecimalNum 要转换的BigDecimal数
         * @return 中文数字
         */
        public static String bigDecimal2chineseNum(BigDecimal bigDecimalNum) {
            if (bigDecimalNum == null)
                return CN_NUM[0];

            StringBuffer sb = new StringBuffer();

            //将小数点后面的零给去除
            String numStr = bigDecimalNum.abs().stripTrailingZeros().toPlainString();

            String[] split = numStr.split("\\.");
            String integerStr = int2chineseNum(Integer.parseInt(split[0]));

            sb.append(integerStr);

            //如果传入的数有小数，则进行切割，将整数与小数部分分离
            if (split.length == 2) {
                //有小数部分
                sb.append(CN_POINT);
                String decimalStr = split[1];
                char[] chars = decimalStr.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    int index = Integer.parseInt(String.valueOf(chars[i]));
                    sb.append(CN_NUM[index]);
                }
            }

            //判断传入数字为正数还是负数
            int signum = bigDecimalNum.signum();
            if (signum == -1) {
                sb.insert(0, CN_NEGATIVE);
            }

            return sb.toString();
        }


        /***
         * 读数字
         * @param number 整数最大int
         * @param mapNumVoice  中文字对应 声音文件，raw 下面的id
         */
        public static void speak(double number,String danwei, float speed, Map<String ,Integer> mapNumVoice){
            speak(bigDecimal2chineseNum(BigDecimal.valueOf(number))+danwei,speed,mapNumVoice);
        }
        /***
         * 读数字
         * @param numberCn
         * @param mapNumVoice  中文字对应 声音文件，raw 下面的id
         */
        public static void speak(String numberCn, float speed,Map<String ,Integer> mapNumVoice){

            if(StringTool.isEmpty(numberCn))return;

//            HashMap<String ,Integer> mapNumVoice=new HashMap<>();
//            mapNumVoice.put("一",R.raw.n1);
//            mapNumVoice.put("二",R.raw.n2);
//            mapNumVoice.put("三",R.raw.n3);
//            mapNumVoice.put("四",R.raw.n4);
//
//            mapNumVoice.put("千",R.raw.q);
//            mapNumVoice.put("百",R.raw.b);
//            mapNumVoice.put("十",R.raw.s);
            char c = numberCn.charAt(0);
            LogTool.s("播放"+ c);
            MediaPlayer mediaPlayer = MediaPlayer.create(AppTool.getApplication(),mapNumVoice.get(""+c));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(speed));
            }
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer.release();
                    if(StringTool.notEmpty(numberCn)){
                        speak(numberCn.substring(1),speed,mapNumVoice);
                    }
                }
            });
            mediaPlayer.start();
        }

        /***
         * 读单个声音
         * @param id
         * @param end
         */
         static void speak(int id,Runnable end){
            MediaPlayer mediaPlayer = MediaPlayer.create(AppTool.getApplication(), id);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if(end!=null)end.run();
                }
            });
            mediaPlayer.start();
        }
    }


    /**
     * 保留指定小数位数 四舍五入
     * @param in
     * @param num
     * @return
     */
    public static double baoLiuXiaoShu(double in,int num){
        BigDecimal bigDecimal=new BigDecimal(in);
        return  bigDecimal.setScale(num,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}

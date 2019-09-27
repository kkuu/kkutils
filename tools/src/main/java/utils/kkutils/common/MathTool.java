package utils.kkutils.common;

import java.math.BigDecimal;
import java.text.NumberFormat;

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

}

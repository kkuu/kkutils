package utils.wzutils.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;

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

}

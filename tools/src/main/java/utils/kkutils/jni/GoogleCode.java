package utils.kkutils.jni;

public class GoogleCode {

    static {
        System.loadLibrary("native-lib");
    }

    /***
     *  goole 验证器 sm3实现算法
     * time (int) (System.currentTimeMillis()/1000)
     * num   6或者8
     * pwd   25971966bac6e7c0dedcf1082a6ed266
     */

    public static native String stringFromJNI(int time, int num, String pwd);
}

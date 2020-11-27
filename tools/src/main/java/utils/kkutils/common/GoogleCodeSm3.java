package utils.kkutils.common;

import utils.kkutils.encypt.Sm3;

/***
 * 不确定是否正确
 */
public class GoogleCodeSm3 {
    public static void main(String[] args) {
        System.out.println("fasdf");
        GoogleCodeSm3 main = new GoogleCodeSm3();


        int time = (int) (System.currentTimeMillis() / 1000);

//        for(int i=0;i<130;i++){
//
//            System.out.println("时间"+new Date((time+i)*1000L).toLocaleString());
//            main.test(time+i);
//        }
        main.test(1286874060);
    }


    public void test(int time) {

        int T0 = time;
        int X = 60;
        int T = T0 / X;

        byte[] bytes = intToBytes_Big(T);

        String k = "4225e56988184643c10ba5c2f52b84e8";
        byte[] K = hexToByteArray(k);//toByteArray(k);
        // K=merge2BytesTo1Byte(k);
        byte[] s = new byte[20];//T+K   T+ausTokenID

        s[0] = bytes[3];
        s[1] = bytes[2];
        s[2] = bytes[1];
        s[3] = bytes[0];


        for (int i = 4; i < s.length; i++) {
            s[i] = K[i - 4];
        }


        byte[] p0 = Sm3.hash(s);


        long all = 0;
        for (int i = 0; i < 32; i += 4) {

            byte[] tem = new byte[4];
            for (int j = i; j < 4; j++) {
                tem[0] = p0[j];
            }
            long temi = byteToIntBig(tem);

            all += temi;
        }

        long    I = floorMod(all, (long) Math.pow(2, 32));

        int p = (int) (I % (Math.pow(10, 6)));
        System.out.println(p);


    }
    public static long floorMod(long x, long y) {
        return x - floorDiv(x, y) * y;
    }
    public static long floorDiv(long x, long y) {
        long r = x / y;
        // if the signs are different and modulo not zero, round down
        if ((x ^ y) < 0 && (r * y != x)) {
            r--;
        }
        return r;
    }
    public static byte[] intToBytes_Big(int integer) {
        byte[] bytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            bytes[i] = (byte) (integer >>> (i * 8));
        }
        return bytes;
    }

    /***
     * 大端模式
     * @param rno
     * @return
     */
    public static int byteToIntBig(byte[] rno) {
        int i = (rno[0] << 24) & 0xff000000 |
                (rno[1] << 16) & 0x00ff0000 |
                (rno[2] << 8) & 0x0000ff00 |
                (rno[3] << 0) & 0x000000ff;
        return i;
    }

    /**
     * 16进制的字符串表示转成字节数组
     *
     * @param hexString 16进制格式的字符串
     * @return 转换后的字节数组
     **/
    public static byte[] hexToByteArray(String hexString) {
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }
}

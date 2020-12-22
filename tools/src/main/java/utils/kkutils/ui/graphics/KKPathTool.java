package utils.kkutils.ui.graphics;

import android.graphics.Path;

public class KKPathTool {
    /**
     * 画正n角星的路径:
     *https://juejin.cn/post/6844903705939050503
     * @param num 角数
     * @param R   外接圆半径
     * @return 画正n角星的路径
     */
    public static Path regularStarPath(int num, float R) {
        float degA, degB;
        if (num % 2 == 1) {//奇数和偶数角区别对待
            degA = 360 / num / 2 / 2;
            degB = 180 - degA - 360 / num / 2;
        } else {
            degA = 360 / num / 2;
            degB = 180 - degA - 360 / num / 2;
        }
        float r = (float) (R * Math.sin(rad(degA)) / Math.sin(rad(degB)));
        return nStarPath(num, R, r);
    }

    /**
     * n角星路径
     *
     * @param num 几角星
     * @param R   外接圆半径
     * @param r   内接圆半径
     * @return n角星路径
     */
    public static Path nStarPath(int num, float R, float r) {
        Path path = new Path();
        float perDeg = 360 / num;
        float degA = perDeg / 2 / 2;
        float degB = 360 / (num - 1) / 2 - degA / 2 + degA;
        path.moveTo(
                (float) (Math.cos(rad(degA + perDeg * 0)) * R + R * Math.cos(rad(degA))),
                (float) (-Math.sin(rad(degA + perDeg * 0)) * R + R));
        for (int i = 0; i < num; i++) {
            path.lineTo(
                    (float) (Math.cos(rad(degA + perDeg * i)) * R + R * Math.cos(rad(degA))),
                    (float) (-Math.sin(rad(degA + perDeg * i)) * R + R));
            path.lineTo(
                    (float) (Math.cos(rad(degB + perDeg * i)) * r + R * Math.cos(rad(degA))),
                    (float) (-Math.sin(rad(degB + perDeg * i)) * r + R));
        }
        path.close();
        return path;
    }
    /**
     * 角度制化为弧度制
     * @param deg 角度
     * @return 弧度
     */
    public static float rad(float deg) {
        return (float) (deg * Math.PI / 180);
    }

}

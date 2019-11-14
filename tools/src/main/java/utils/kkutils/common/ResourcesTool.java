package utils.kkutils.common;

import utils.kkutils.AppTool;

/***
 * 资源文件工具
 */
public class ResourcesTool {
    /**
     * 获取颜色值， 可以是资源文件color 或者本来就是color
     * @param color
     * @return
     */
    public static int getColor(int color){
        if(color>0){
            color= AppTool.getApplication().getResources().getColor(color);
        }
        return color;
    }
}

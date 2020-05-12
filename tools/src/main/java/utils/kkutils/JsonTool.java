package utils.kkutils;


import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import utils.kkutils.common.LogTool;
import utils.kkutils.common.StringTool;
import utils.kkutils.json.InterfaceJsonTool;
import utils.kkutils.json.implement.JsonToolGson;

/**
 * Created by coder on 15/12/25.
 * Json 操作工具类
 */
public class JsonTool {
    private static InterfaceJsonTool jsonToolImp = new JsonToolGson();

    /**
     * 把java 对象转为jsons
     *
     * @param javaObject java对象
     * @return 返回转换后的字符串
     */
    public static String toJsonStr(Object javaObject) {

        try {
            return jsonToolImp.toJsonStr(javaObject);
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return "";
    }

    public static <T> T toJava(String jsonStr, Class<T> tClass) {
        try {
            if (tClass.equals(String.class))//如果本身就是需要一个string , 就不转了
            {
                return (T) jsonStr;
            } else if (StringTool.isEmpty(jsonStr)) {
                try {
                    return tClass.newInstance();
                }catch (Exception e){
                    LogTool.s("没有空的构造方法"+tClass);
                    return null;
                }
            }
            T o = jsonToolImp.toJava(jsonStr, tClass);
            return o;
        } catch (Exception e) {
            LogTool.ex(new Throwable("解析json 失败了" + jsonStr));
            LogTool.ex(e);
            try {
                T o = tClass.newInstance();
                return o;
            } catch (Exception e2) {
                LogTool.s("没有空的构造方法"+tClass);
            }
        }
        return null;
    }

    public static <T> List<T> toJavaList(String jsonStr, Class<T> clazz) {
        List<T> o = new ArrayList<>();
        try {
            if (!TextUtils.isEmpty(jsonStr)) {
                o = jsonToolImp.toJavaList(jsonStr, clazz);
            }
            return o;
        } catch (Exception e) {
            LogTool.ex(new Throwable("解析json 失败了" + jsonStr));
            LogTool.ex(e);
            o = new ArrayList<>();
            return o;
        }
    }

}

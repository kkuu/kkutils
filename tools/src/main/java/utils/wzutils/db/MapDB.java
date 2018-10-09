package utils.wzutils.db;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import utils.wzutils.AppTool;
import utils.wzutils.JsonTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.encypt.AESTool;
import utils.wzutils.encypt.Md5Tool;

/**
 * Created by kk on 2016/5/11.
 * 键值对数据库， 主要用于非常简单的操作
 */
public class MapDB {
    private static String dbName="mapdb";

    public static void init(boolean isDebug){
        setDbName("mapdb"+isDebug);
    }
    /***
     * 保存一个对象到本地
     *
     * @param key
     * @param value
     */
    public static void saveObj(String key, Object value) {
        saveObjEncrypt(key,value,null);
    }
    public static <T> T loadObj(String key, Class<T> tClass) {
        return loadObjByDefault(key,tClass,null);
    }
    public static <T> T loadObjByDefault(String key, Class<T> tClass,T defaultObj) {
        return loadObjByDefaultEncrypt(key,tClass,defaultObj,null);
    }
    public static <T> List<T> loadObjList(String key, Class<T> tClass) {
       return loadObjListEncrypt(key,tClass,null);
    }



    public static void saveObjEncrypt(String key, Object value,String pwd) {
        try {
            String valueSave = JsonTool.toJsonStr(value);

            boolean encrypt= StringTool.notEmpty(pwd);
            if(encrypt){
                key= AESTool.encrypt(key,pwd);
                valueSave=AESTool.encrypt(valueSave,pwd);
            }
            try {
                getShare(key).edit().putString(key, valueSave).commit();//apply 异步提交， commit 同步提交
                LogTool.s("保存了一个对象到本地： key= " + key);
            } catch (Exception e) {
                LogTool.ex(e);
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }


    public static <T> T loadObjEncrypt(String key, Class<T> tClass,String pwd) {
        return loadObjByDefaultEncrypt(key,tClass,null,pwd);
    }
    public static <T> T loadObjByDefaultEncrypt(String key, Class<T> tClass,T defaultObj,String pwd) {
        T t = null;
        try {
            boolean encrypt= StringTool.notEmpty(pwd);
            if(encrypt)key= AESTool.encrypt(key,pwd);
            String value = getShare(key).getString(key, "");
            if(encrypt)value=AESTool.decrypt(value,pwd);

            if(StringTool.isEmpty(value)&&defaultObj!=null)return defaultObj;
            t = JsonTool.toJava(value, tClass);
            LogTool.s("从本地读取了一个对象: key=" + key);
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return t==null?defaultObj:t;
    }

    public static <T> List<T> loadObjListEncrypt(String key, Class<T> tClass,String pwd) {
        List<T> t = null;
        try {
            boolean encrypt= StringTool.notEmpty(pwd);
            if(encrypt)key= AESTool.encrypt(key,pwd);
            String value = getShare(key).getString(key, "");
            if(encrypt)value=AESTool.decrypt(value,pwd);
            t = JsonTool.toJavaList(value, tClass);
            LogTool.s("从本地读取了一个对象: key=" + key);
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return t;
    }












    private static SharedPreferences getShare(String key) {
        return AppTool.getApplication().getSharedPreferences(getDbName()+"--"+ Md5Tool.md5(key), Context.MODE_PRIVATE |Context.MODE_MULTI_PROCESS);
    }

    public static String getDbName() {
        return dbName;
    }

    public static void setDbName(String dbName) {
        MapDB.dbName = dbName;
    }

}

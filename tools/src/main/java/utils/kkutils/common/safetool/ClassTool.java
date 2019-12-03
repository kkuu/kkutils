package utils.kkutils.common.safetool;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import utils.kkutils.common.CollectionsTool;

public class ClassTool {
    public static HashMap<Object,List<Field>> fieldMap=new HashMap<>();
    /**
     * 获取所有属性包括父类
     * @param object
     * @return
     */
    public static List<Field> getAllFields(Object object) {
        return  getAllFields(object.getClass());
    }
    public static List<Field> getAllFields( Class clazz) {
        if(clazz==null)return new ArrayList<>();
        List<Field> fieldList = fieldMap.get(clazz);
        if(CollectionsTool.NotEmptyList(fieldList)){
            return fieldList;
        }else {
            fieldList=new ArrayList<>();


            Class temClass=clazz;
            while (temClass != null) {
                fieldList.addAll(getAllFieldsWithOutSuper(temClass));
                temClass = temClass.getSuperclass();
            }

            fieldMap.put(clazz,fieldList);
        }
        return fieldList;
    }
    public static List<Field> getAllFieldsWithOutSuper( Class clazz) {
        String key="withOutSuper"+clazz;
        if(clazz==null)return new ArrayList<>();

        List<Field> fieldList = fieldMap.get(key);
        if(CollectionsTool.NotEmptyList(fieldList)){
            return fieldList;
        }
        fieldList=new ArrayList<>();
        fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
        //对属性进行处理
        for (Field field : fieldList) {
            int mod = field.getModifiers();
            //跳过不可变动的常量
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            //设置属性为可修改
            field.setAccessible(true);
        }
        fieldMap.put(key,fieldList);
        return fieldList;
    }
}




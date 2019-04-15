package utils.wzutils.fragment.dizhi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import utils.wzutils.AppTool;
import utils.wzutils.JsonTool;
import utils.wzutils.common.FileTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.thread.ThreadTool;

/**
 * Created by ishare on 2016/6/23.
 */

public class KK_DiZhi implements Comparable, Serializable {
    public static void main(String []args){
        System.out.println("asdafdssdf");

        String path="D:\\国家.txt";





    }
    private static KK_DiZhi KKDiZhi = new KK_DiZhi();
    public List<KK_DiZhi> list = new ArrayList<>();
    public int id;
    public int parentId;
    public String name = "";
    public int sort;
    public KK_DiZhi parent=this;

    /***
     * 从本地读取
     *
     * @return
     */
    public static synchronized KK_DiZhi loadFromLocal() {
        try {
            long time = System.currentTimeMillis();
            if (KKDiZhi.list != null && KKDiZhi.list.size() > 0) {
            } else {
                try {
                    String dizhiStr = FileTool.readAllString(AppTool.getApplication().getAssets().open("kk_dizhi_china.txt"));
                    KKDiZhi = JsonTool.toJava(dizhiStr, KK_DiZhi.class);
                    setParent(KKDiZhi);
                } catch (Exception e) {
                    LogTool.ex(e);
                }

            }
            LogTool.s("加载完地址： 耗时" + (System.currentTimeMillis() - time) + "  加载数据：" + KKDiZhi.list.size());
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return KKDiZhi;
    }

    public static void setParent(KK_DiZhi KKDiZhi){
        if(KKDiZhi.list.size()>0){
            for(KK_DiZhi tem: KKDiZhi.list){
                tem.parent= KKDiZhi;
                tem.parentId= KKDiZhi.id;
                setParent(tem);
            }
        }
    }


    public static void preLoad() {
        ThreadTool.execute(new Runnable() {
            @Override
            public void run() {
                KK_DiZhi.loadFromLocal();//预加载  地址数据
            }
        });
    }

    public int getDep(){

        if(id==-1){
            return 1;
        }if(parent.id==-1){
            return 2;
        }if(parent.parent.id==-1){
            return 3;
        }if(parent.parent.parent.id==-1){
            return 4;
        }if(parent.parent.parent.parent.id==-1){
            return 5;
        }
        return 0;
    }
    @Override
    public String toString() {
        return name + "--" + id + "---" + parentId;
    }

    @Override
    public int compareTo(Object o) {
        try {
            if (o != null) {
                KK_DiZhi des = (KK_DiZhi) o;
                return (sort - des.sort);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


}

package utils.kkutils.json;


import java.io.Serializable;

import utils.kkutils.common.CommonTool;

/**
 * Created by coder on 15/12/25.
 * 所有的Json  Java 对象必须是这个的子类
 */
public abstract class JsonDataParent implements Serializable {
    public int code = 9999;
    public String msg = "";
    public abstract boolean isDataOk();
    public abstract boolean isDataOkAndToast() ;

    /***
     * 单纯判断 不要加任何其他的， 比如做UI 线程才能 做的事 ，跳转界面之类的
     * @return
     */
    public abstract boolean isDataOkNotInUiThread();
}

package utils.kkutils.common;

import java.util.ArrayList;
import java.util.List;

/**
 * abc on 2017/3/24.
 */

public class TestData {
    /***
     * 获取测试图片地址
     * @param index
     * @return
     */
    public static String getTestImgUrl(int index) {
        List<String> imgs=new ArrayList<>();

        imgs.add("http://img3.imgtn.bdimg.com/it/u=304048529,3594887020&fm=26&gp=0.jpg");
        imgs.add("http://img2.imgtn.bdimg.com/it/u=420517602,2844897433&fm=214&gp=0.jpg");
        imgs.add("http://photo2.tp88.net/upfile/20173/27/6906F28EBF77AA4B1BB45D82411D3D2C-0.jpg");
        imgs.add("http://pic1.win4000.com/wallpaper/1/57a2fa5f8da86.jpg");
        imgs.add("http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1309/13/c10/25636246_1379059568752.jpg");
        imgs.add("http://benyouhuifile.it168.com/forum/201210/31/040100zn8z874p4qmfgx7s.jpg");
        imgs.add("http://pic1.ytqmx.com:82/2015/0107/02/02.jpg!720.jpg");
        imgs.add("http://img.kutoo8.com/upload/thumb/154728/c2115e1f0baea1a62ff1de45d19d002c_960x540.jpg");
        imgs.add("http://img0.imgtn.bdimg.com/it/u=3906949260,2500752604&fm=26&gp=0.jpg");
        imgs.add("http://www.pptbz.com/pptpic/UploadFiles_6909/201302/2013021118441638.jpg");
        imgs.add("http://b-ssl.duitang.com/uploads/item/201610/15/20161015193414_msF3K.jpeg");
        imgs.add("http://b-ssl.duitang.com/uploads/item/201612/16/20161216181226_2sTan.jpeg");
        imgs.add("http://b-ssl.duitang.com/uploads/item/201603/02/20160302201622_zCKvN.thumb.700_0.jpeg");
        imgs.add("http://b-ssl.duitang.com/uploads/item/201604/24/20160424155828_ALRKE.thumb.700_0.jpeg");
        imgs.add("http://hbimg.b0.upaiyun.com/828e72e2855b51a005732f4e007c58c92417a61e1bcb1-61VzNc_fw658");
        return imgs.get(index);
    }
    public static List<String> getTestImgUrlList(int size) {
        List<String> imgs=new ArrayList<>();
        for(int i=0;i<size;i++){
            imgs.add(getTestImgUrl(i));
        }
        return imgs;
    }
    /***
     * 获取测试字符串列表
     * @param count
     * @return
     */
    public static List<String> getTestStrList(int count) {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            stringList.add("" + i);
        }
        return stringList;
    }
}

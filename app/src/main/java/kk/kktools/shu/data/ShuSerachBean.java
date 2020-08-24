package kk.kktools.shu.data;

import java.io.Serializable;
import java.util.List;

public class ShuSerachBean extends DataParent {
    /**
     * status : 1
     * info : success
     * data : [{"Id":"563905","Name":"大梦主","Author":"忘语","Img":"https://imgapi.jiaston.com/BookFiles/BookImages/damengzhu.jpg","Desc":"忘语新书《大梦主》于5月20日晚上7点发布。另外，不出意外，忘语的这本新书将会是曾经的国民手游\u201c梦幻西游\u201d的IP定制文。","BookStatus":"连载","LastChapterId":"3110736","LastChapter":"第两百二十七章 买符纸","CName":"玄幻奇幻","UpdateTime":"2020-08-20 00:00:00"}]
     */

    public int status;
    public String info;
    public List<ShuInfoBean.BookInfo> data;


}

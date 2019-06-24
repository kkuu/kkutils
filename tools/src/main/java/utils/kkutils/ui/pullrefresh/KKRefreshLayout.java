package utils.kkutils.ui.pullrefresh;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import utils.kkutils.R;
import utils.kkutils.common.UiTool;


/***
 *    compile 'com.scwang.smartrefresh:SmartRefreshLayout:1.0.5.1'
 compile 'com.scwang.smartrefresh:SmartRefreshHeader:1.0.5.1'//没有使用特殊Header，可以不加这行
 */
public class KKRefreshLayout extends SmartRefreshLayout {
    public KKRefreshLayout(Context context) {
        super(context);
        init();
    }

    public KKRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KKRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        setDragRate(1);
        setEnableOverScrollBounce(false);
        initHeaderAndFooter();
    }
    public void stopRefresh(PageControl pageControl) {
        finishRefresh();
        finishLoadMore();
    }

    public void initHeaderAndFooter(){
        ClassicsFooter.REFRESH_FOOTER_PULLING = getResources().getString(R.string.REFRESH_FOOTER_PULLUP);
        ClassicsFooter.REFRESH_FOOTER_RELEASE = getResources().getString(R.string.REFRESH_FOOTER_RELEASE);
        ClassicsFooter.REFRESH_FOOTER_LOADING = getResources().getString(R.string.REFRESH_FOOTER_LOADING);
        ClassicsFooter.REFRESH_FOOTER_REFRESHING = getResources().getString(R.string.REFRESH_FOOTER_REFRESHING);
        ClassicsFooter.REFRESH_FOOTER_FINISH = getResources().getString(R.string.REFRESH_FOOTER_FINISH);
        ClassicsFooter.REFRESH_FOOTER_FAILED = getResources().getString(R.string.REFRESH_FOOTER_FAILED);
        ClassicsFooter.REFRESH_FOOTER_NOTHING = getResources().getString(R.string.REFRESH_FOOTER_ALLLOADED);

        ClassicsHeader.REFRESH_HEADER_PULLING = getResources().getString(R.string.REFRESH_HEADER_PULLDOWN);
        ClassicsHeader.REFRESH_HEADER_REFRESHING = getResources().getString(R.string.REFRESH_HEADER_REFRESHING);
        ClassicsHeader.REFRESH_HEADER_LOADING = getResources().getString(R.string.REFRESH_HEADER_LOADING);
        ClassicsHeader.REFRESH_HEADER_RELEASE = getResources().getString(R.string.REFRESH_HEADER_RELEASE);
        ClassicsHeader.REFRESH_HEADER_FINISH = getResources().getString(R.string.REFRESH_HEADER_FINISH);
        ClassicsHeader.REFRESH_HEADER_FAILED = getResources().getString(R.string.REFRESH_HEADER_FAILED);

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {//设置手指按下的时候 就不要自动加载更多
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            setEnableAutoLoadMore(false);
        }else if(event.getAction()==MotionEvent.ACTION_UP||event.getAction()==MotionEvent.ACTION_CANCEL){
            setEnableAutoLoadMore(true);
        }
        return super.dispatchTouchEvent(event);
    }


    @Override
    public RefreshLayout finishRefresh(int delayed, boolean success, Boolean noMoreData) {
        return super.finishRefresh(0, success, noMoreData);
    }



    /***
     * 可以设置这个背景颜色
     * @param resColor
     */
    public void setRefreshPrimaryColor(final int resColor){
        post(new Runnable() {
            @Override
            public void run() {
                ClassicsHeader classicsHeader= (ClassicsHeader) getRefreshHeader();
                classicsHeader.setPrimaryColor(UiTool.getColorByResId(resColor));
                ClassicsFooter classicsFooter= (ClassicsFooter) getRefreshFooter();
                classicsFooter.setPrimaryColor(UiTool.getColorByResId(resColor));
            }
        });
    }




    public static void setDefaultColor(final int bgColor, final int tvColor){
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                layout.setHeaderHeight(50);
                layout.setFooterHeight(50);
                layout.setEnableClipHeaderWhenFixedBehind(true);

                ClassicsHeader classics=new ClassicsHeader(context);
                classics.setAccentColor(tvColor);
                classics.setPrimaryColor(bgColor);
                classics.setSpinnerStyle(SpinnerStyle.FixedBehind);
                classics.setTextSizeTitle(13);
                classics.setDrawableSize(18);
                classics.setFinishDuration(0);//设置刷新完成显示的停留时间
                classics.setDrawableArrowSize(15);
                classics.setEnableLastTime(false);
                return classics;//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                ClassicsFooter classics=new ClassicsFooter(context);
                classics.setAccentColor(tvColor);
                classics.setPrimaryColor(bgColor);
                classics.setTextSizeTitle(13);
                classics.setDrawableSize(18);
                classics.setFinishDuration(0);//设置刷新完成显示的停留时间
                return classics;
            }
        });
    }


    static {
         final int tvColor=Color.parseColor("#979797");
         final int bgColor=Color.parseColor("#35384B");
         setDefaultColor(bgColor,tvColor);
    }

    public void bindLoadDataAndRefresh( PageControl pageControl,final LoadListDataInterface loadListDataInterface) {
        if(pageControl==null)pageControl=new PageControl();
        bindLoadDataAndRefreshNotRefresh(pageControl,loadListDataInterface);
        loadListDataInterface.loadData(pageControl.init());
    }
    public void bindLoadDataAndRefreshNotRefresh(final PageControl pageControl,final LoadListDataInterface loadListDataInterface) {
        setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                loadListDataInterface.loadData(pageControl.getNextPageNum());
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                loadListDataInterface.loadData(pageControl.init());
            }
        });
    }
    public static interface LoadListDataInterface{
        public void loadData(final int page);
    }

}

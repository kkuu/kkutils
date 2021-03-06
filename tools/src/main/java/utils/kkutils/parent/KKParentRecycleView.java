package utils.kkutils.parent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;

import androidx.annotation.Nullable;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewConfigurationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import utils.kkutils.ImgTool;
import utils.kkutils.R;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.ui.pullrefresh.PageControl;
import utils.kkutils.ui.pullrefresh.KKRefreshImp;
import utils.kkutils.ui.recycleview.KKDecoration;
import utils.kkutils.ui.recycleview.RecycleViewTool;

/**
 * Created by ishare on 2016/6/7.
 * 放在 wzscroll 里面的时候  如果高度不对 ，可以试试setWrapContentHeight
 */
public class KKParentRecycleView extends RecyclerView {
    /******************
     *解决  recyclerview横竖嵌套 的问题，  横向滑动不流畅
     * http://www.open-open.com/lib/view/open1474352526193.html
     *
     *
     */

    private static final int INVALID_POINTER = -1;
    ItemDecoration itemDecoration;
    private boolean wrapContentHeight;
    private int mScrollPointerId = INVALID_POINTER;

    //    public ParentRecycleViewWz(Context context, @Nullable AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        init();
//    }
    private int mInitialTouchX, mInitialTouchY;
    private int mTouchSlop;

    public KKParentRecycleView(Context context) {
        this(context,null);
    }


    public KKParentRecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public KKParentRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        final ViewConfiguration vc = ViewConfiguration.get(getContext());
        mTouchSlop = vc.getScaledTouchSlop();

        if(attrs!=null){
            @SuppressLint("Recycle")
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.KKScrollView);
            maxHeight= (int) typedArray.getDimension(R.styleable.KKParentRecycleView_maxHeight, 0);
        }



        init();
    }

    public void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        setLayoutManager(linearLayoutManager);
       // setNestedScrollingEnabled(false);

        setItemAnimator(null);//不要动画

        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                try {
                    if(newState==SCROLL_STATE_IDLE){
                        ImgTool.resume();
                        autoLoadMoreCheck(newState);
                    }else {
                        ImgTool.pause();
                    }
                }catch (Exception e){
                    LogTool.ex(e);
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    /**
     *
     * @param state 当前滑动状态
     */
     void  autoLoadMoreCheck(int state){
         if(getAdapter()==null)return;
        if(autoLoadMorePageSize>0&&state==SCROLL_STATE_IDLE){
            try {
                if(getLayoutManager() instanceof LinearLayoutManager){//自动加载下一页
                    int totalItemCount = getAdapter().getItemCount();
                    if(totalItemCount<autoLoadMorePageSize||totalItemCount%autoLoadMorePageSize!=0)return;//不满一页   或者最后一页不满一页   不加载更多
                    int lastVisibleItemPosition =((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
                    int visibleItemCount = getChildCount();
                    if(visibleItemCount>1&&lastVisibleItemPosition==totalItemCount-1){
                        if(getParent() instanceof KKRefreshImp){
                            ((KKRefreshImp) getParent()).refreshByPullUp();
                        }
                    }
                }
            }catch (Exception e){
                LogTool.ex(e);
            }
        }
    }

    /***
     * 设置自动加载更多 每页条数， 大于这个才自动加载  <0 不自动加载更多
     * @param autoLoadMorePageSize
     */
    public void setAutoLoadMore(int autoLoadMorePageSize) {
        this.autoLoadMorePageSize = autoLoadMorePageSize;
    }
    int autoLoadMorePageSize= new PageControl<Object>().getPageSize();









//    @Override
//    public void setLayoutManager(LayoutManager layout) {
//
//        if(getLayoutManager()!=null&&layout!=null){
//            if(getLayoutManager() instanceof LinearLayoutManager &&layout instanceof LinearLayoutManager){
//                if(((LinearLayoutManager) getLayoutManager()).getOrientation()==((LinearLayoutManager) layout).getOrientation()
//                        &&((LinearLayoutManager) getLayoutManager()).getReverseLayout()==((LinearLayoutManager) layout).getReverseLayout()
//                        ){
//                    return;//方向和 是否 反序 都一样 就返回正常的
//                }
//            }else if(getLayoutManager() instanceof StaggeredGridLayoutManager &&layout instanceof StaggeredGridLayoutManager){
//
//                if(((StaggeredGridLayoutManager) getLayoutManager()).getOrientation()==((StaggeredGridLayoutManager) layout).getOrientation()
//                        &&((StaggeredGridLayoutManager) getLayoutManager()).getSpanCount()==((StaggeredGridLayoutManager) layout).getSpanCount()
//                        ){
//                    return;//方向和   列数一样
//                }
//
//
//            }
//
//
//
//        }
//
//
//
//        super.setLayoutManager(layout);
//
//    }

    /***
     * 设置点击事件， 默认的列表的点击事件好像有问题
     * @param onClickListener
     */
    @Override
    public void setOnClickListener(final OnClickListener onClickListener) {
        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try {
                    if(event.getAction()==MotionEvent.ACTION_UP){
                        if(onClickListener!=null){
                            onClickListener.onClick(KKParentRecycleView.this);
                        }
                    }
                }catch (Exception e){
                    LogTool.ex(e);
                }

                return true;
            }
        });
    }


    /****
     * 设置水平 和竖直间隔
     *
     * @param horizontal 水平间隔
     * @param verical    竖直间隔
     */
    public void setDividerDp(final int horizontal, final int verical) {
        setDividerDp(horizontal / 2, verical / 2, horizontal / 2, verical / 2);
    }
    /****
     * 设置一般用的， 比如传如10 可以设置4边都是10dp
     */
    public void setDividerNormal( int dividerNormalDp) {
        if (itemDecoration != null) {
            removeItemDecoration(itemDecoration);
        }
        final int dividerNormal= CommonTool.dip2px(dividerNormalDp);
        itemDecoration = new ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
                if(isEmptyView(view))return;//如果是空数据， 就不要间隔了
                super.getItemOffsets(outRect, view, parent, state);
                if(parent.getChildAdapterPosition(view)==0){
                    outRect.set(dividerNormal, dividerNormal, dividerNormal, dividerNormal);
                }else {
                    outRect.set(dividerNormal, 0, dividerNormal, dividerNormal);
                }
            }
        };
        addItemDecoration(itemDecoration);
    }
    /****
     * 设置水平 和竖直间隔
     */
    public void setDividerDp(final int left, final int top, final int ritht, final int bottom) {
        if (itemDecoration != null) {
            removeItemDecoration(itemDecoration);
        }
        itemDecoration = new ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
                if(isEmptyView(view))return;//如果是空数据， 就不要间隔了
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(CommonTool.dip2px(left), CommonTool.dip2px(top), CommonTool.dip2px(ritht), CommonTool.dip2px(bottom));

            }
        };
        addItemDecoration(itemDecoration);
    }



    public void setItemDecoration(ItemDecoration itemDecoration){
        RecycleViewTool.removeAllDecoration(this);
        addItemDecoration(itemDecoration);
    }



















    public boolean isEmptyView(View view){
        if(view!=null&&view==getEmptyView()){
            return true;
        }
        return false;
    }



    public View getEmptyView(){
        return null;
    }








//
//
//    @Override
//    public void setScrollingTouchSlop(int slopConstant) {
//        super.setScrollingTouchSlop(slopConstant);
//        final ViewConfiguration vc = ViewConfiguration.get(getContext());
//        switch (slopConstant) {
//            case TOUCH_SLOP_DEFAULT:
//                mTouchSlop = vc.getScaledTouchSlop();
//                break;
//            case TOUCH_SLOP_PAGING:
//                mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(vc);
//                break;
//            default:
//                break;
//        }
//    }
//
//    @Override
//    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent e) {
//        final int action = MotionEventCompat.getActionMasked(e);
//        final int actionIndex = MotionEventCompat.getActionIndex(e);
//
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                mScrollPointerId = MotionEventCompat.getPointerId(e, 0);
//                mInitialTouchX = (int) (e.getX() + 0.5f);
//                mInitialTouchY = (int) (e.getY() + 0.5f);
//                return super.onInterceptTouchEvent(e);
//
//            case MotionEventCompat.ACTION_POINTER_DOWN:
//                mScrollPointerId = MotionEventCompat.getPointerId(e, actionIndex);
//                mInitialTouchX = (int) (MotionEventCompat.getX(e, actionIndex) + 0.5f);
//                mInitialTouchY = (int) (MotionEventCompat.getY(e, actionIndex) + 0.5f);
//                return super.onInterceptTouchEvent(e);
//
//            case MotionEvent.ACTION_MOVE: {
//                final int index = MotionEventCompat.findPointerIndex(e, mScrollPointerId);
//                if (index < 0) {
//                    return false;
//                }
//
//                final int x = (int) (MotionEventCompat.getX(e, index) + 0.5f);
//                final int y = (int) (MotionEventCompat.getY(e, index) + 0.5f);
//                if (getScrollState() != SCROLL_STATE_DRAGGING) {
//                    final int dx = x - mInitialTouchX;
//                    final int dy = y - mInitialTouchY;
//                    final boolean canScrollHorizontally = getLayoutManager().canScrollHorizontally();
//                    final boolean canScrollVertically = getLayoutManager().canScrollVertically();
//                    boolean startScroll = false;
//                    if (canScrollHorizontally && Math.abs(dx) > mTouchSlop && (Math.abs(dx) >= Math.abs(dy) || canScrollVertically)) {//主要是这里，  需要判断 后面半截
//                        startScroll = true;
//                    }
//                    if (canScrollVertically && Math.abs(dy) > mTouchSlop && (Math.abs(dy) >= Math.abs(dx) || canScrollHorizontally)) {//主要是这里，  需要判断 后面半截
//                        startScroll = true;
//                    }
//                    return startScroll && super.onInterceptTouchEvent(e);
//                }
//                return super.onInterceptTouchEvent(e);
//            }
//
//            default:
//                return super.onInterceptTouchEvent(e);
//        }
//    }


    /***
     * 设置 自适应高
     *
     * @return
     */

    public void setWrapContentHeight(boolean wrapContentHeight) {
        this.wrapContentHeight = wrapContentHeight;
    }

    public int maxHeight=-1;
    /***
     * 设置最大多高
     * @param maxHeight
     */
    public void setMaxHeight(int maxHeight){
        this.maxHeight=maxHeight;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (wrapContentHeight) {
            heightMeasureSpec= View.MeasureSpec.makeMeasureSpec(maxHeight>0?maxHeight:999999, View.MeasureSpec.AT_MOST);
        } else {
            if(maxHeight>0){
                heightMeasureSpec=MeasureSpec.makeMeasureSpec(maxHeight,MeasureSpec.AT_MOST);
            }
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }


}

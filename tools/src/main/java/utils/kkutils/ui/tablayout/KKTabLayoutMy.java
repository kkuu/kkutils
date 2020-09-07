package utils.kkutils.ui.tablayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utils.kkutils.R;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.UiTool;
import utils.kkutils.ui.CommonButtonTool;
import utils.kkutils.ui.KKSimpleRecycleView;

/***
 * 自定义的Tab
 */
public class KKTabLayoutMy extends FrameLayout {
    public OnTabChecked onTabChecked;
    KKSimpleRecycleView kkSimpleRecycleView;

    public KKTabLayoutMy(@NonNull Context context) {
        this(context, null);
    }

    public KKTabLayoutMy(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KKTabLayoutMy(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            @SuppressLint("Recycle")
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.KKTabLayoutMy);
            tabAttrs = new TabAttrs();
            tabAttrs.kktab_bottomLineColor = typedArray.getColor(R.styleable.KKTabLayoutMy_kktab_bottomLineColor, tabAttrs.kktab_bottomLineColor);
            tabAttrs.kktab_bottomLineHeight = typedArray.getDimension(R.styleable.KKTabLayoutMy_kktab_bottomLineHeight, tabAttrs.kktab_bottomLineHeight);
            tabAttrs.kktab_color_checked = typedArray.getColor(R.styleable.KKTabLayoutMy_kktab_color_checked, tabAttrs.kktab_color_checked);
            tabAttrs.kktab_color_not_checked = typedArray.getColor(R.styleable.KKTabLayoutMy_kktab_color_not_checked, tabAttrs.kktab_color_not_checked);
            tabAttrs.kktab_isScroll = typedArray.getBoolean(R.styleable.KKTabLayoutMy_kktab_isScroll, tabAttrs.kktab_isScroll);
            tabAttrs.kktab_paddingRight = typedArray.getDimension(R.styleable.KKTabLayoutMy_kktab_paddingRight, tabAttrs.kktab_paddingRight);
            tabAttrs.kktab_isBoldOnChecked = typedArray.getBoolean(R.styleable.KKTabLayoutMy_kktab_isBoldOnChecked, tabAttrs.kktab_isBoldOnChecked);
            tabAttrs.kktab_textSize = typedArray.getDimension(R.styleable.KKTabLayoutMy_kktab_textSize, tabAttrs.kktab_textSize);

        }

        init();
    }

    public void init() {
        kkSimpleRecycleView = new KKSimpleRecycleView(getContext());
        addView(kkSimpleRecycleView);
        UiTool.setWH(kkSimpleRecycleView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        setTabs(new TabBean("测试1"), new TabBean("测试1"), new TabBean("测试1"), new TabBean("测试1"),
                new TabBean("测试1"), new TabBean("测试1"), new TabBean("测试1"), new TabBean("测试1"), new TabBean("测试1"));
    }

    public void setTabAttrs(TabAttrs tabAttrs) {
        this.tabAttrs = tabAttrs;
        for (TabBean tab : tabs) {
            tab.tabAttrs = tabAttrs;
        }
        setTabs(tabs);
    }

    public TabAttrs getTabAttrs() {
        return tabAttrs;
    }

    TabAttrs tabAttrs = new TabAttrs();

    List<TabBean> tabs = new ArrayList<>();

    public void setTabs(TabBean... tabsArrays) {
        tabs = new ArrayList<>(Arrays.asList(tabsArrays));
        setTabs(tabs);
    }

    public void setTabs(List<TabBean> tabsIn) {
        if (tabsIn == null) tabsIn = new ArrayList<>();
        tabs = tabsIn;
        for (TabBean tab : tabs) {
            if (tab.tabAttrs == null) tab.setTabAttrs(tabAttrs);
        }
        refreshData();
    }


    public void setOnTabChecked(OnTabChecked onTabChecked) {
        this.onTabChecked = onTabChecked;
    }


    public void refreshData() {
        initTab(tabs, kkSimpleRecycleView);
    }

    public static class TabAttrs {
        public int kktab_bottomLineColor = Color.parseColor("#E2231A");
        public float kktab_bottomLineHeight = CommonTool.dip2px(2);
        public int kktab_color_checked = Color.parseColor("#E2231A");
        public int kktab_color_not_checked = Color.parseColor("#333333");
        public boolean kktab_isScroll = true;
        public float kktab_paddingRight = CommonTool.dip2px(30);//isScroll=true 才生效
        public boolean kktab_isBoldOnChecked = true;
        public float kktab_textSize = CommonTool.dip2px(14);

        public TabAttrs(int kktab_bottomLineColor, float kktab_bottomLineHeight, int kktab_color_checked, int kktab_color_not_checked, boolean kktab_isScroll, float kktab_paddingRight, boolean kktab_isBoldOnChecked, int kktab_textSize) {
            this.kktab_bottomLineColor = kktab_bottomLineColor;
            this.kktab_bottomLineHeight = kktab_bottomLineHeight;
            this.kktab_color_checked = kktab_color_checked;
            this.kktab_color_not_checked = kktab_color_not_checked;
            this.kktab_isScroll = kktab_isScroll;
            this.kktab_paddingRight = kktab_paddingRight;
            this.kktab_isBoldOnChecked = kktab_isBoldOnChecked;
            this.kktab_textSize = kktab_textSize;
        }

        public TabAttrs() {
        }


        public void onCheckedChange(boolean isChecked, CompoundButton buttonView) {
            buttonView.getPaint().setFakeBoldText(false);
            if (kktab_isBoldOnChecked && isChecked) {
                buttonView.getPaint().setFakeBoldText(true);
            }
        }


        public void onInit(KKSimpleRecycleView recycleView, List<TabBean> tabBeanList, int position, View itemView, CompoundButton tab_btn) {
            TabBean tabBean = tabBeanList.get(position);

            tab_btn.setTextSize(TypedValue.COMPLEX_UNIT_PX, kktab_textSize);
            UiTool.setTextView(tab_btn, tabBean.name);
            setColor(tab_btn, kktab_color_checked, kktab_color_not_checked);

            if (itemView.getTag() == null) {
                itemView.setTag("1");
                tab_btn.addOnAttachStateChangeListener(new OnAttachStateChangeListener() {//改变宽度放这里面， 免得闪烁
                    @Override
                    public void onViewAttachedToWindow(View v) {
                        int w = recycleView.getWidth();
                        int h = recycleView.getHeight();

                        if (kktab_isScroll) {//滚动模式
                            tab_btn.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                            UiTool.setWH(tab_btn, ViewGroup.LayoutParams.WRAP_CONTENT, h);
                            itemView.setPadding(0, 0, (int) kktab_paddingRight, 0);

                        } else {//平分模式
                            int wTab = w / tabBeanList.size();
                            UiTool.setWH(tab_btn, wTab, h);
                            tab_btn.setGravity(Gravity.CENTER);
                        }
                    }

                    @Override
                    public void onViewDetachedFromWindow(View v) {

                    }
                });
            }


            tab_btn.post(new Runnable() {//获取宽度要放这里面
                @Override
                public void run() {
                    tab_btn.setCompoundDrawables(null, null, null,
                            getDrawableBottom(tab_btn.getWidth())
                    );
                }
            });
        }

        public Drawable getDrawableBottom(int w) {
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(kktab_bottomLineColor);
            gradientDrawable.setSize(w, (int) kktab_bottomLineHeight);

//            gradientDrawable.setCornerRadius(h/3);


            gradientDrawable.setBounds(0, 0, gradientDrawable.getIntrinsicWidth(), gradientDrawable.getIntrinsicHeight());


            StateListDrawable drawable = new StateListDrawable();
            drawable.addState(new int[]{android.R.attr.state_checked}, gradientDrawable);

            GradientDrawable noChecked = new GradientDrawable();
            noChecked.setBounds(gradientDrawable.getBounds());
            drawable.addState(new int[]{-android.R.attr.state_checked}, noChecked);
            drawable.setBounds(gradientDrawable.getBounds());
            return drawable;
        }

        public void setColor(TextView textView, int colorChecked, int colorNotChecked) {
            int[] colors = new int[]{colorChecked, colorNotChecked};
            int[][] states = new int[2][];
            states[0] = new int[]{android.R.attr.state_checked};
            states[1] = new int[]{-android.R.attr.state_checked};
            ColorStateList colorStateList = new ColorStateList(states, colors);


            textView.setTextColor(colorStateList);
        }

    }

    public static class TabBean {
        public boolean isChecked;
        public String name;
        public Object data;

        public TabBean(String name) {
            this(name, null);
        }

        public TabBean(String name, Object data) {
            setName(name);
            setData(data);
        }

        public TabBean setName(String name) {
            this.name = name;
            return this;
        }

        public TabBean setData(Object data) {
            this.data = data;
            return this;
        }

        public TabBean setTabAttrs(TabAttrs tabAttrs) {
            this.tabAttrs = tabAttrs;
            return this;
        }

        public TabAttrs tabAttrs;

    }

    public static interface OnTabChecked {
        public void onTabChecked(List<TabBean> tabBeanList, int index, TabBean tabBean, CompoundButton compoundButton);
    }


    public CommonButtonTool commonButtonTool = new CommonButtonTool();

    public CommonButtonTool getCommonButtonTool() {
        if (commonButtonTool == null) commonButtonTool = new CommonButtonTool();
        return commonButtonTool;
    }

    /***
     * 设置选中
     * @param index
     */
    public void setChecked(int index) {
        try {
            getCommonButtonTool().getAllButtons().get(index).setChecked(true);
        } catch (Exception e) {
            LogTool.ex(e);
        }

    }

    public void initTab(List<TabBean> tabBeanList, KKSimpleRecycleView recycleView) {
        recycleView.setLayoutManager(new LinearLayoutManager(recycleView.getContext(), RecyclerView.HORIZONTAL, false));
        commonButtonTool = new CommonButtonTool();
        tabBeanList.get(0).isChecked = true;
        recycleView.setItemViewCacheSize(100);
        recycleView.setLayoutCacheCount(0);
//        recycleView.getRecycledViewPool().clear();
        recycleView.setData(tabBeanList, R.layout.kk_tab_item, new KKSimpleRecycleView.KKRecycleAdapter() {
            @Override
            public void initData(int position, int typeTem, View itemView, KKSimpleRecycleView.WzViewHolder wzViewHolder) {
                super.initData(position, typeTem, itemView, wzViewHolder);
                TabBean tabBean = tabBeanList.get(position);
                CompoundButton cb_shouye_title_tab = itemView.findViewById(R.id.cb_tab_item);
                cb_shouye_title_tab.setTag(tabBean);
                commonButtonTool.add(cb_shouye_title_tab);
                cb_shouye_title_tab.setChecked(tabBean.isChecked);

                tabBean.tabAttrs.onCheckedChange(cb_shouye_title_tab.isChecked(), cb_shouye_title_tab);
                tabBean.tabAttrs.onInit(recycleView, tabBeanList, position, itemView, cb_shouye_title_tab);
            }
        });
        commonButtonTool.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TabBean tag = (TabBean) buttonView.getTag();
                tag.isChecked = isChecked;
                if (isChecked) {
                    if (onTabChecked != null) {
                        int index = commonButtonTool.getAllButtons().indexOf(buttonView);
                        onTabChecked.onTabChecked(tabBeanList, index, tabBeanList.get(index), buttonView);
                    }
                }
                tag.tabAttrs.onCheckedChange(isChecked, buttonView);
            }
        });

    }


}

package utils.kkutils.ui.navigation;



import android.content.Context;
import android.util.AttributeSet;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import utils.kkutils.common.LogTool;

public class KKNavigation extends BottomNavigationViewEx {
    public KKNavigation(Context context) {
        super(context);
        init();
    }

    public KKNavigation(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KKNavigation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public void init(){
        enableAnimation(false);
        setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        setItemIconTintList(null);
        setTextSize(10);
        setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                try{
                    int id=menuItem.getItemId();
                }catch (Exception e){
                    LogTool.ex(e);
                }
                return true;
            }
        });
        setCurrentItem(0);
        {//去掉背景
            com.google.android.material.bottomnavigation.BottomNavigationItemView[] bottomNavigationItemViews = getBottomNavigationItemViews();
            for(BottomNavigationItemView v:bottomNavigationItemViews){
                v.setBackground(null);
            }
        }

    }
}

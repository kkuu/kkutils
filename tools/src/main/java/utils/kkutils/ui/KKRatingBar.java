package utils.kkutils.ui;


import android.content.Context;
import androidx.annotation.Nullable;
import utils.kkutils.R;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class KKRatingBar extends LinearLayout {
    int max = 0;
    int rating = 0;
    int minRating=1;
    boolean canControl=true;
    int resStarDrawableIdEmpty = android.R.drawable.star_off;
    int resStarDrawableId = android.R.drawable.star_on;
    public KKRatingBar(Context context) {
        super(context);
        init(null,null);
    }

    public KKRatingBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }
    public KKRatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public void setMax(int max) {
        if(max<1)max=1;
        if(this.max!=max){
            this.max = max;
            setOrientation(HORIZONTAL);
            removeAllViews();
            for (int i = 0; i < max; i++) {
                ImageView imageView = new ImageView(getContext());
                addView(imageView);
            }
        }
    }
    public void setCanControl(boolean canControl) {
        this.canControl = canControl;
        if(canControl){
            setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    float width=getWidth();
                    float currX=event.getX();
                    float oneWidth=width/max;
                    if(oneWidth!=0){
                        int rating=(int) (currX/oneWidth+0.5f);
                        setRating(rating,true);
                    }
                    return true;
                }
            });
        }else {
            setOnTouchListener(null);
        }
    }

    public void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.KKRatingBar);


        int resStarDrawableIdEmpty =  typedArray.getResourceId(R.styleable.KKRatingBar_starDrawbleUnCheck, android.R.drawable.star_off);
        int resStarDrawableId = typedArray.getResourceId(R.styleable.KKRatingBar_starDrawbleChecked, android.R.drawable.star_on);


        int max = typedArray.getInt(R.styleable.KKRatingBar_maxRating, 5);
        int rating= typedArray.getInt(R.styleable.KKRatingBar_rating, 5);


        boolean canControl= typedArray.getBoolean(R.styleable.KKRatingBar_canControl, true);

        int itemPadding = typedArray.getInt(R.styleable.KKRatingBar_itemPadding, -1);
        if(itemPadding>-1){
            itemPaddingLeft =itemPadding;
            itemPaddingTop=itemPadding;
            itemPaddingRight =itemPadding;
            itemPaddingBottom =itemPadding;
        }
         itemPaddingLeft = typedArray.getInt(R.styleable.KKRatingBar_itemPaddingLeft, itemPaddingLeft);
         itemPaddingTop = typedArray.getInt(R.styleable.KKRatingBar_itemPaddingTop, itemPaddingTop);
         itemPaddingRight = typedArray.getInt(R.styleable.KKRatingBar_itemPaddingRight, itemPaddingRight);
         itemPaddingBottom = typedArray.getInt(R.styleable.KKRatingBar_itemPaddingBottom, itemPaddingBottom);



        setMax(max);
        setRating(rating);
        setCanControl(canControl);


        setResStarDrawableIdEmpty(resStarDrawableIdEmpty);
        setResStarDrawableId(resStarDrawableId);
    }
    public void setResStarDrawableIdEmpty(int resStarDrawableIdEmpty) {
        this.resStarDrawableIdEmpty = resStarDrawableIdEmpty;
        setRatingImp(rating,false);
    }

    public void setResStarDrawableId(int resStarDrawableId) {
        this.resStarDrawableId = resStarDrawableId;
        setRatingImp(rating,false);
    }

    public  interface OnWzRatingBarChangeListener{
        public void onRatingChanged(KKRatingBar ratingBar, float rating, boolean fromUser) ;
    }

    public void setOnWzRatingBarChangeListener(OnWzRatingBarChangeListener onWzRatingBarChangeListener) {
        this.onWzRatingBarChangeListener = onWzRatingBarChangeListener;
    }

    OnWzRatingBarChangeListener onWzRatingBarChangeListener;


    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        setRating(rating,false);
    }
    void setRating(int rating,boolean fromUser) {
        if(rating<minRating)rating=minRating;
        if(rating>max)rating=max;
        if(this.rating!=rating){
            setRatingImp(rating,fromUser);
        }
    }
    void setRatingImp(int rating,boolean fromUser){
        this.rating = rating;
        for (int i = 0; i < getChildCount(); i++) {
            ImageView imageView = (ImageView) getChildAt(i);
            if(i<rating){
                imageView.setImageResource(resStarDrawableId);
            }else {
                imageView.setImageResource(resStarDrawableIdEmpty);
            }
            imageView.setPadding(itemPaddingLeft,itemPaddingTop,itemPaddingRight,itemPaddingBottom);
        }
        if(onWzRatingBarChangeListener!=null){
            onWzRatingBarChangeListener.onRatingChanged(this,rating,fromUser);
        }
    }

    int itemPaddingLeft;
    int itemPaddingTop;
    int itemPaddingRight;
    int itemPaddingBottom;
    public void setItemPadding(int left, int top, int right, int bottom){
       this.itemPaddingLeft=left;
       this.itemPaddingTop=top;
       this.itemPaddingRight=right;
       this.itemPaddingBottom=bottom;
    }
}

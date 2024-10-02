package com.android.goally.customviews;

import android.view.View;

import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

public class ScalePageTransformer implements ViewPager.PageTransformer{
private float mScale = 1.5f;
    @Override
    public void transformPage(View view, float position) {
        if(position==0){
            ViewCompat.setScaleX(view, mScale);
            ViewCompat.setScaleY(view, mScale);
        }
        else{
            ViewCompat.setScaleX(view, 1);
            ViewCompat.setScaleY(view, 1);
        }
    }
}
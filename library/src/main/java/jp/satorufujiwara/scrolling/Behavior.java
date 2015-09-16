package jp.satorufujiwara.scrolling;

import com.github.ksoichiro.android.observablescrollview.ScrollState;

import android.view.View;

public abstract class Behavior {

    private int flexibleHeight;

    void setFlexibleHeight(int flexibleHeight) {
        this.flexibleHeight = flexibleHeight;
    }

    protected void onAttached(View target) {

    }

    protected int getFlexibleHeight() {
        return flexibleHeight;
    }

    protected void onScrolled(View target, int scrollY, int dy) {

    }

    protected void onDownMotionEvent() {

    }

    protected void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    protected void onGlobalLayout(View target) {

    }
}

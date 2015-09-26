package jp.satorufujiwara.scrolling;

import android.view.View;

import com.github.ksoichiro.android.observablescrollview.ScrollState;

public abstract class Behavior {

    private int flexibleHeight;

    void setFlexibleHeight(final int flexibleHeight) {
        this.flexibleHeight = flexibleHeight;
    }

    protected void onAttached(final View target) {

    }

    protected int getFlexibleHeight() {
        return flexibleHeight;
    }

    protected void onScrolled(final View target, final int scrollY, final int dy) {

    }

    protected void onDownMotionEvent() {

    }

    protected void onUpOrCancelMotionEvent(final ScrollState scrollState) {

    }

    protected void onGlobalLayout(final View target) {

    }
}

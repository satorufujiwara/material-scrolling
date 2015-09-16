package jp.satorufujiwara.scrolling.behavior;

import android.support.v4.view.ViewCompat;
import android.view.View;

import jp.satorufujiwara.scrolling.Behavior;

public class ScrollUntilBehavior extends Behavior {

    private final int until;
    private int targetHeight = 0;

    public ScrollUntilBehavior(int until) {
        this.until = until;
    }

    @Override
    protected void onGlobalLayout(View target) {
        targetHeight = target.getHeight();
    }

    @Override
    protected void onScrolled(View target, int scrollY, int dy) {
        computeTranslation(target, scrollY, dy);
    }

    protected void computeTranslation(View target, int scrollY, int dy) {
        ViewCompat.setTranslationY(target, -Math.min(scrollY, getFlexibleHeight() - targetHeight - until));
    }

}

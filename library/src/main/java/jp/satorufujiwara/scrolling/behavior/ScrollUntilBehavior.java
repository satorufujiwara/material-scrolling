package jp.satorufujiwara.scrolling.behavior;

import android.support.v4.view.ViewCompat;
import android.view.View;

import jp.satorufujiwara.scrolling.Behavior;

public class ScrollUntilBehavior extends Behavior {

    private final int until;
    private int targetHeight = 0;

    public ScrollUntilBehavior(final int until) {
        this.until = until;
    }

    @Override
    protected void onGlobalLayout(final View target) {
        targetHeight = target.getHeight();
    }

    @Override
    protected void onScrolled(final View target, final int scrollY, final int dy) {
        computeTranslation(target, scrollY, dy);
    }

    protected void computeTranslation(final View target, final int scrollY, final int dy) {
        ViewCompat.setTranslationY(target, -Math.min(scrollY, getFlexibleHeight() - targetHeight - until));
    }

}

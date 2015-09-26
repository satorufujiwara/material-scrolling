package jp.satorufujiwara.scrolling.behavior;

import android.view.View;

import jp.satorufujiwara.scrolling.Behavior;

public class ScrollFadingBehavior extends Behavior {

    private int targetTop = 0;

    @Override
    protected void onScrolled(final View target, final int scrollY, final int dy) {
        computeTranslation(target, scrollY, dy);
    }

    @Override
    protected void onGlobalLayout(final View target) {
        targetTop = target.getTop();
    }


    protected void computeTranslation(final View target, final int scrollY, final int y) {
        target.setTranslationY(-Math.min(scrollY, getFlexibleHeight()));
        if (targetTop != 0) {
            target.setAlpha(Math.max(0.0f, Math.min(1.0f, 1.0f - (float) y / (float) targetTop)));
        }
    }
}

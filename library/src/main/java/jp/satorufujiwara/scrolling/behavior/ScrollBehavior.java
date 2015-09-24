package jp.satorufujiwara.scrolling.behavior;

import android.support.v4.view.ViewCompat;
import android.view.View;

import jp.satorufujiwara.scrolling.Behavior;

public class ScrollBehavior extends Behavior {

    public ScrollBehavior() {
    }

    @Override
    protected void onScrolled(final View target, final int scrollY, final int dy) {
        computeTranslation(target, scrollY, dy);
    }

    protected void computeTranslation(final View target, final int scrollY, final int dy) {
        ViewCompat.setTranslationY(target, -Math.min(scrollY, getFlexibleHeight()));
    }

}

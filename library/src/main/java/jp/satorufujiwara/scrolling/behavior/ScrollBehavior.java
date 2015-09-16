package jp.satorufujiwara.scrolling.behavior;

import android.support.v4.view.ViewCompat;
import android.view.View;

import jp.satorufujiwara.scrolling.Behavior;

public class ScrollBehavior extends Behavior {

    public ScrollBehavior() {
    }

    @Override
    protected void onScrolled(View target, int scrollY, int dy) {
        computeTranslation(target, scrollY, dy);
    }

    protected void computeTranslation(View target, int scrollY, int dy) {
        ViewCompat.setTranslationY(target, -Math.min(scrollY, getFlexibleHeight()));
    }

}

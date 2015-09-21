package jp.satorufujiwara.scrolling.sample.behavior;

import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.view.View;

import jp.satorufujiwara.scrolling.Behavior;

public class OverlayViewBehavior extends Behavior {

    private final int until;

    public OverlayViewBehavior(final int until) {
        this.until = until;
    }

    @Override
    protected void onScrolled(View target, int scrollY, int dy) {
        final int movingHeight = getFlexibleHeight() - until;
        ViewCompat.setTranslationY(target, -Math.min(scrollY, movingHeight));
        if (scrollY >= movingHeight) {
            target.setBackgroundColor(Color.rgb(33, 33, 33));
        } else {
            final int alpha = 255 * scrollY / movingHeight;
            target.setBackgroundColor(Color.argb(alpha, 33, 33, 33));
        }
    }
}

package jp.satorufujiwara.scrolling.behavior;

import android.view.View;

import jp.satorufujiwara.scrolling.Behavior;

public class ExitUntilCollapsedBehavior extends Behavior {

    private final int pushUpOffset;
    private int targetHeight = 0;

    public ExitUntilCollapsedBehavior() {
        this(0);
    }

    public ExitUntilCollapsedBehavior(final int pushUpHeight) {
        pushUpOffset = pushUpHeight;
    }

    @Override
    protected void onGlobalLayout(final View target) {
        targetHeight = target.getHeight();
    }

    @Override
    protected void onScrolled(final View target, final int scrollY, final int dy) {
        computeTranslation(target, scrollY);
    }

    protected void computeTranslation(final View target, final int y) {
        final int h = getFlexibleHeight() - targetHeight - pushUpOffset;
        if (y < h) {
            target.setTranslationY(0);
            return;
        }
        target.setTranslationY(-y + h);
    }
}

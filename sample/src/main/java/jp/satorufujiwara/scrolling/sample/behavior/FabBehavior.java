package jp.satorufujiwara.scrolling.sample.behavior;

import android.content.res.Resources;
import android.support.v4.view.ViewCompat;
import android.view.View;

import jp.satorufujiwara.scrolling.Behavior;
import jp.satorufujiwara.scrolling.sample.R;

public class FabBehavior extends Behavior {

    private int targetHeight = 0;

    private final int startScalingHeight;

    public FabBehavior(Resources r) {
        startScalingHeight = r.getDimensionPixelOffset(R.dimen.fab_scaling_height);
    }

    protected void onGlobalLayout(View target) {
        targetHeight = target.getHeight();
    }

    @Override
    protected void onScrolled(View target, int scrollY, int dy) {
        final int movingHeight = getFlexibleHeight() - targetHeight;
        ViewCompat.setTranslationY(target, -Math.min(scrollY, movingHeight));
        if (scrollY >= movingHeight) {
            ViewCompat.setScaleX(target, 0.0f);
            ViewCompat.setScaleY(target, 0.0f);
        } else if (scrollY >= startScalingHeight) {
            final float scale = 1.0f - (scrollY - startScalingHeight) * 1.0f / (movingHeight
                    - startScalingHeight);
            ViewCompat.setScaleX(target, scale);
            ViewCompat.setScaleY(target, scale);
        } else {
            ViewCompat.setScaleX(target, 1.0f);
            ViewCompat.setScaleY(target, 1.0f);
        }
    }
}

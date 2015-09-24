package jp.satorufujiwara.scrolling.behavior;

import android.support.v4.view.ViewCompat;
import android.view.View;

public class ParallaxBehavior extends ScrollBehavior {

    private final float parallaxRate;

    public ParallaxBehavior() {
        this(1.5f);
    }

    public ParallaxBehavior(final float parallaxRate) {
        this.parallaxRate = parallaxRate;
    }

    @Override
    protected void computeTranslation(final View target, final int scrollY, final int y) {
        ViewCompat.setTranslationY(target, -scrollY / parallaxRate);
    }
}

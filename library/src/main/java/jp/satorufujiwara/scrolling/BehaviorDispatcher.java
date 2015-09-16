package jp.satorufujiwara.scrolling;


import com.github.ksoichiro.android.observablescrollview.ScrollState;

import android.os.Build;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

public class BehaviorDispatcher {

    private final int flexibleHeight;
    private final List<View> targets = new ArrayList<>();
    private final ArrayMap<View, Behavior> behaviors = new ArrayMap<>();

    public BehaviorDispatcher(final int flexibleHeight) {
        this.flexibleHeight = flexibleHeight;
    }

    private final ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener
            = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            for (View target : targets) {
                behaviors.get(target).onGlobalLayout(target);
            }
        }
    };

    public void onAttachedToWindow(View view) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);
    }

    public void onDetachedFromWindow(View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            view.getViewTreeObserver().removeGlobalOnLayoutListener(globalLayoutListener);
        } else {
            view.getViewTreeObserver().removeOnGlobalLayoutListener(globalLayoutListener);
        }
    }

    public void addBehavior(View target, Behavior behavior) {
        targets.add(target);
        behavior.setFlexibleHeight(flexibleHeight);
        behaviors.put(target, behavior);
        behavior.onAttached(target);
    }

    void onScrolled(int scrollY, int dy) {
        for (View target : targets) {
            behaviors.get(target).onScrolled(target, scrollY, dy);
        }
    }

    void onDownMotionEvent() {
        for (View target : targets) {
            behaviors.get(target).onDownMotionEvent();
        }
    }

    void onUpOrCancelMotionEvent(ScrollState scrollState) {
        for (View target : targets) {
            behaviors.get(target).onUpOrCancelMotionEvent(scrollState);
        }
    }

}

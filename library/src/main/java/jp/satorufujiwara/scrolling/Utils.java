package jp.satorufujiwara.scrolling;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;

import android.view.View;
import android.view.ViewGroup;

final class Utils {

    private Utils() {

    }

    static ObservableRecyclerView findRecyclerView(final View view) {
        if (view instanceof ObservableRecyclerView) {
            return (ObservableRecyclerView) view;
        }
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        ViewGroup group = (ViewGroup) view;
        int count = group.getChildCount();
        for (int i = 0; i < count; i++) {
            ObservableRecyclerView child = findRecyclerView(group.getChildAt(i));
            if (child != null) {
                return child;
            }
        }
        return null;
    }

}

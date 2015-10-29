package jp.satorufujiwara.scrolling;


import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import android.support.v7.widget.LinearLayoutManager;

public class RecyclerViewHolder {

    private final ObservableRecyclerView recyclerView;
    private final BehaviorDispatcher behaviorDispatcher;
    private int flexibleHeight;
    private boolean isDispatchScroll = true;
    private boolean isDirty = true;
    private int preScrollY = 0;
    private int scrollY = 0;

    public RecyclerViewHolder(final ObservableRecyclerView recyclerView,
            final BehaviorDispatcher behaviorDispatcher) {
        this.recyclerView = recyclerView;
        this.recyclerView.setClipToPadding(false);
        this.behaviorDispatcher = behaviorDispatcher;
        recyclerView.setScrollViewCallbacks(new OnScrollDelegate(this));
    }

    public void setFlexibleHeight(final int flexibleHeight) {
        this.flexibleHeight = flexibleHeight;
        recyclerView.setPadding(0, flexibleHeight, 0, 0);
    }

    public void setIsDispatchScroll(final boolean enable) {
        isDispatchScroll = enable;
    }

    public int getScrollY() {
        return isDirty ? scrollY : recyclerView.getCurrentScrollY() + flexibleHeight;
    }

    public void scrollTo(final int y) {
        isDirty = true;
        scrollY = y;
        final LinearLayoutManager lm = ((LinearLayoutManager) recyclerView.getLayoutManager());
        lm.scrollToPositionWithOffset(0, -y);
    }

    void onScrolled(final int y) {
        isDirty = false;
        if (!isDispatchScroll) {
            return;
        }
        scrollY = y + flexibleHeight;
        behaviorDispatcher.onScrolled(scrollY, scrollY - preScrollY);
        preScrollY = scrollY;
    }

    void onDownMotionEvent() {
        if (!isDispatchScroll) {
            return;
        }
        behaviorDispatcher.onDownMotionEvent();
    }

    void onUpOrCancelMotionEvent(final ScrollState scrollState) {
        if (!isDispatchScroll) {
            return;
        }
        behaviorDispatcher.onUpOrCancelMotionEvent(scrollState);
    }

    static class OnScrollDelegate implements ObservableScrollViewCallbacks {

        private final RecyclerViewHolder delegate;

        private OnScrollDelegate(final RecyclerViewHolder delegate) {
            this.delegate = delegate;
        }

        @Override
        public void onScrollChanged(final int scrollY, final boolean b, final boolean b1) {
            delegate.onScrolled(scrollY);
        }

        @Override
        public void onDownMotionEvent() {
            delegate.onDownMotionEvent();
        }

        @Override
        public void onUpOrCancelMotionEvent(final ScrollState scrollState) {
            delegate.onUpOrCancelMotionEvent(scrollState);
        }
    }

}

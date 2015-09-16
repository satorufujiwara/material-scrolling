package jp.satorufujiwara.scrolling;


import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerViewHolder {

    private final int flexibleHeight;
    private final ObservableRecyclerView recyclerView;
    private final BehaviorDispatcher behaviorDispatcher;
    private Spacer spacer = new DefaultSpacer();
    private boolean isDispatchScroll = true;
    private boolean isDirty = true;
    private int preScrollY = 0;
    private int scrollY = 0;

    public RecyclerViewHolder(final int flexibleHeight, final ObservableRecyclerView recyclerView,
            final BehaviorDispatcher behaviorDispatcher) {
        this.flexibleHeight = flexibleHeight;
        this.recyclerView = recyclerView;
        this.behaviorDispatcher = behaviorDispatcher;
        recyclerView.setScrollViewCallbacks(new OnScrollDelegate(this));
        recyclerView.addItemDecoration(new ItemDecorationDelegate(this), 0);
    }

    public void setIsDispatchScroll(boolean enable) {
        isDispatchScroll = enable;
    }

    public void setHeaderSpacer(Spacer spacer) {
        this.spacer = spacer;
    }

    public int getScrollY() {
        return isDirty ? scrollY : recyclerView.getCurrentScrollY() + flexibleHeight;
    }

    public void scrollTo(int y) {
        isDirty = true;
        scrollY = y;
        final LinearLayoutManager lm = ((LinearLayoutManager) recyclerView.getLayoutManager());
        lm.scrollToPositionWithOffset(0, -y);
    }

    void onScrolled(int y) {
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

    void onUpOrCancelMotionEvent(ScrollState scrollState) {
        if (!isDispatchScroll) {
            return;
        }
        behaviorDispatcher.onUpOrCancelMotionEvent(scrollState);
    }

    private boolean onInterceptItemOffset(Rect outRect, View view, RecyclerView parent,
            RecyclerView.State state) {
        return spacer.getItemOffset(outRect, view, parent, state, flexibleHeight);
    }

    public interface Spacer {

        boolean getItemOffset(Rect outRect, View view, RecyclerView parent,
                RecyclerView.State state, int flexibleHeight);
    }

    static class OnScrollDelegate implements ObservableScrollViewCallbacks {

        private final RecyclerViewHolder delegate;

        private OnScrollDelegate(RecyclerViewHolder delegate) {
            this.delegate = delegate;
        }

        @Override
        public void onScrollChanged(int scrollY, boolean b, boolean b1) {
            delegate.onScrolled(scrollY);
        }

        @Override
        public void onDownMotionEvent() {
            delegate.onDownMotionEvent();
        }

        @Override
        public void onUpOrCancelMotionEvent(ScrollState scrollState) {
            delegate.onUpOrCancelMotionEvent(scrollState);
        }
    }

    static class ItemDecorationDelegate extends RecyclerView.ItemDecoration {

        private final RecyclerViewHolder delegate;

        private ItemDecorationDelegate(RecyclerViewHolder delegate) {
            this.delegate = delegate;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                RecyclerView.State state) {
            if (delegate.onInterceptItemOffset(outRect, view, parent, state)) {
                return;
            }
            super.getItemOffsets(outRect, view, parent, state);
        }
    }

    static class DefaultSpacer implements Spacer {

        @Override
        public boolean getItemOffset(Rect outRect, View view, RecyclerView parent,
                RecyclerView.State state, int appBarHeight) {
            final RecyclerView.LayoutManager manager = parent.getLayoutManager();
            if (manager instanceof GridLayoutManager) {
                final int spanCount = ((GridLayoutManager) manager).getSpanCount();
                if (parent.getChildLayoutPosition(view) < spanCount) {
                    outRect.set(0, appBarHeight, 0, 0);
                    return true;
                }
                return false;
            }
            if (manager instanceof LinearLayoutManager) {
                if (parent.getChildLayoutPosition(view) == 0) {
                    outRect.set(0, appBarHeight, 0, 0);
                    return true;
                }
                return false;
            }
            return false;
        }
    }

}

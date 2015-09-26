package jp.satorufujiwara.scrolling;


import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

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

    public void setIsDispatchScroll(final boolean enable) {
        isDispatchScroll = enable;
    }

    public void setHeaderSpacer(final Spacer spacer) {
        this.spacer = spacer;
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

    private boolean onInterceptItemOffset(final Rect outRect, final View view, final RecyclerView parent,
            RecyclerView.State state) {
        return spacer.getItemOffset(outRect, view, parent, state, flexibleHeight);
    }

    public interface Spacer {

        boolean getItemOffset(final Rect outRect, final View view, final RecyclerView parent,
                              final RecyclerView.State state, final int flexibleHeight);
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

    static class ItemDecorationDelegate extends RecyclerView.ItemDecoration {

        private final RecyclerViewHolder delegate;

        private ItemDecorationDelegate(final RecyclerViewHolder delegate) {
            this.delegate = delegate;
        }

        @Override
        public void getItemOffsets(final Rect outRect, final View view, final RecyclerView parent,
                RecyclerView.State state) {
            if (delegate.onInterceptItemOffset(outRect, view, parent, state)) {
                return;
            }
            super.getItemOffsets(outRect, view, parent, state);
        }
    }

    static class DefaultSpacer implements Spacer {

        @Override
        public boolean getItemOffset(final Rect outRect, final View view, final RecyclerView parent,
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

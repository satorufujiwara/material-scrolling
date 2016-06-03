package jp.satorufujiwara.scrolling;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;

public class MaterialScrollingViewPager extends ViewPager {

    private final ArrayMap<View, ObservableRecyclerView> recyclerViews = new ArrayMap<>();
    private final Map<ObservableRecyclerView, RecyclerViewHolder> holders = new ArrayMap<>();
    private final BehaviorDispatcher behaviorDispatcher = new BehaviorDispatcher();
    private int flexibleHeight;
    private int baseHeight;
    private RecyclerViewHolder activeHolder;
    private boolean isFirst = true;

    private final OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(final int position, final float positionOffset,
                final int positionOffsetPixels) {
            // no op
        }

        @Override
        public void onPageSelected(final int position) {
            final ObservableRecyclerView recyclerView = findRecyclerViewFrom(position);
            if (recyclerView == null) {
                return;
            }
            for (RecyclerViewHolder holder : holders.values()) {
                holder.setIsDispatchScroll(false);
            }
            behaviorDispatcher.onScrolled(activeHolder.getScrollY(), 0);
            activeHolder = holders.get(recyclerView);
            activeHolder.setIsDispatchScroll(true);
        }

        @Override
        public void onPageScrollStateChanged(final int state) {
            findFirstActiveRecyclerView();
            if (state == SCROLL_STATE_IDLE) {
                return;
            }
            final int activeScrollY = activeHolder.getScrollY();
            for (Map.Entry<ObservableRecyclerView, RecyclerViewHolder> item : holders.entrySet()) {
                final RecyclerViewHolder holder = item.getValue();
                if (holder == activeHolder) {
                    continue;
                }
                final int scrollY = holder.getScrollY();
                if (activeScrollY >= flexibleHeight - baseHeight
                        && scrollY >= flexibleHeight - baseHeight) {
                    continue;
                }
                holder.scrollTo(Math.min(activeScrollY, flexibleHeight - baseHeight));
            }
        }
    };

    public MaterialScrollingViewPager(final Context context) {
        this(context, null);
    }

    public MaterialScrollingViewPager(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        int flexibleHeight = 0;
        int baseHeight = 0;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ms_MaterialScrolling);
        flexibleHeight = a.getDimensionPixelSize(
                R.styleable.ms_MaterialScrolling_ms_flexible_height, flexibleHeight);
        baseHeight = a.getDimensionPixelSize(
                R.styleable.ms_MaterialScrolling_ms_base_height, baseHeight);
        a.recycle();
        this.flexibleHeight = flexibleHeight;
        this.baseHeight = baseHeight;
        behaviorDispatcher.setFlexibleHeight(flexibleHeight);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        behaviorDispatcher.onAttachedToWindow(this);
        addOnPageChangeListener(onPageChangeListener);
    }

    @Override
    protected void onDetachedFromWindow() {
        removeOnPageChangeListener(onPageChangeListener);
        behaviorDispatcher.onDetachedFromWindow(this);
        super.onDetachedFromWindow();
    }

    @Override
    public void addView(final View child, final int index, final ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        ObservableRecyclerView recyclerView = Utils.findRecyclerView(child);
        if (recyclerView == null) {
            return;
        }
        RecyclerViewHolder holder = new RecyclerViewHolder(recyclerView, behaviorDispatcher);
        holder.setFlexibleHeight(flexibleHeight);
        recyclerViews.put(child, recyclerView);
        holders.put(recyclerView, holder);
    }

    @Override
    public void removeView(final View view) {
        if (recyclerViews.containsKey(view)) {
            holders.remove(recyclerViews.get(view));
            recyclerViews.remove(view);
        }
        super.removeView(view);
    }

    @Override
    public void setAdapter(final PagerAdapter adapter) {
        super.setAdapter(adapter);
        setOffscreenPageLimit(getAdapter().getCount());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        findFirstActiveRecyclerView();
        return super.onInterceptTouchEvent(ev);
    }

    public void addBehavior(final View target, final Behavior behavior) {
        behaviorDispatcher.addBehavior(target, behavior);
    }

    public void setFlexibleHeight(final int flexibleHeight) {
        this.flexibleHeight = flexibleHeight;
        behaviorDispatcher.setFlexibleHeight(flexibleHeight);
        for (RecyclerViewHolder holder : holders.values()) {
            holder.setFlexibleHeight(flexibleHeight);
        }
    }

    public void setBaseHeight(final int baseHeight) {
        this.baseHeight = baseHeight;
    }

    private void findFirstActiveRecyclerView() {
        if (!isFirst) {
            return;
        }
        final RecyclerViewHolder holder = holders.get(findRecyclerViewFrom(getCurrentItem()));
        if (holder != null) {
            holder.setIsDispatchScroll(true);
            activeHolder = holder;
        }
        isFirst = false;
    }

    private ObservableRecyclerView findRecyclerViewFrom(final int position) {
        final PagerAdapter adapter = getAdapter();
        if (adapter instanceof ContainRecyclerViewPagerAdapter) {
            return ((ContainRecyclerViewPagerAdapter) adapter).getRecyclerView(position);
        }
        return null;
    }

    public interface ContainRecyclerViewPagerAdapter {

        ObservableRecyclerView getRecyclerView(final int position);
    }

}

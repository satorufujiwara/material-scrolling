package jp.satorufujiwara.scrolling;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;

public class MaterialScrollingViewPager extends ViewPager {

    private final int flexibleHeight;
    private final int baseHeight;
    private final ArrayMap<View, ObservableRecyclerView> recyclerViews = new ArrayMap<>();
    private final Map<ObservableRecyclerView, RecyclerViewHolder> holders = new ArrayMap<>();
    private final BehaviorDispatcher behaviorDispatcher;
    private RecyclerViewHolder activeHolder;

    private final OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // no op
        }

        @Override
        public void onPageSelected(int position) {
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
        public void onPageScrollStateChanged(int state) {
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
                if (activeScrollY >= flexibleHeight - baseHeight && scrollY >= flexibleHeight - baseHeight) {
                    continue;
                }
                holder.scrollTo(Math.min(activeScrollY, flexibleHeight - baseHeight));
            }
        }
    };

    private boolean isFirstRecyclerView = true;

    public MaterialScrollingViewPager(Context context) {
        this(context, null);
    }

    public MaterialScrollingViewPager(Context context, AttributeSet attrs) {
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
        behaviorDispatcher = new BehaviorDispatcher(flexibleHeight);
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
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        ObservableRecyclerView recyclerView = findRecyclerView(child);
        if (recyclerView == null) {
            return;
        }
        RecyclerViewHolder holder = new RecyclerViewHolder(flexibleHeight, recyclerView, behaviorDispatcher);
        if (isFirstRecyclerView) {
            activeHolder = holder;
        }
        holder.setIsDispatchScroll(isFirstRecyclerView);
        isFirstRecyclerView = false;
        recyclerViews.put(child, recyclerView);
        holders.put(recyclerView, holder);
    }

    @Override
    public void removeView(View view) {
        if (recyclerViews.containsKey(view)) {
            holders.remove(recyclerViews.get(view));
            recyclerViews.remove(view);
        }
        super.removeView(view);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        setOffscreenPageLimit(getAdapter().getCount());
    }

    public void addBehavior(View target, Behavior behavior) {
        behaviorDispatcher.addBehavior(target, behavior);
    }

    private ObservableRecyclerView findRecyclerViewFrom(int position) {
        final PagerAdapter adapter = getAdapter();
        if (adapter instanceof ContainRecyclerViewPagerAdapter) {
            return ((ContainRecyclerViewPagerAdapter) adapter).getRecyclerView(position);
        }
        return null;
    }

    private ObservableRecyclerView findRecyclerView(final View view) {
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

    public interface ContainRecyclerViewPagerAdapter {

        ObservableRecyclerView getRecyclerView(final int position);
    }

}

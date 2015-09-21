package jp.satorufujiwara.scrolling;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class MaterialScrollingLayout extends FrameLayout {

    private final int flexibleHeight;
    private final int baseHeight;
    private final BehaviorDispatcher behaviorDispatcher;
    private RecyclerViewHolder recyclerViewHolder;

    public MaterialScrollingLayout(Context context) {
        this(context, null, 0);
    }

    public MaterialScrollingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialScrollingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
    }

    @Override
    protected void onDetachedFromWindow() {
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
        recyclerViewHolder = new RecyclerViewHolder(flexibleHeight, recyclerView,
                behaviorDispatcher);
        recyclerViewHolder.setIsDispatchScroll(true);
    }

    @Override
    public void removeView(View view) {
        recyclerViewHolder.setIsDispatchScroll(false);
        recyclerViewHolder = null;
        super.removeView(view);
    }

    public void addBehavior(View target, Behavior behavior) {
        behaviorDispatcher.addBehavior(target, behavior);
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
}

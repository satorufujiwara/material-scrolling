package jp.satorufujiwara.scrolling;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;

public class MaterialScrollingLayout extends FrameLayout {

    private final int flexibleHeight;
    private final int baseHeight;
    private final BehaviorDispatcher behaviorDispatcher;
    private RecyclerViewHolder recyclerViewHolder;

    public MaterialScrollingLayout(final Context context) {
        this(context, null, 0);
    }

    public MaterialScrollingLayout(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialScrollingLayout(final Context context, final AttributeSet attrs, final int defStyleAttr) {
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
    public void addView(final View child, final int index, final ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        ObservableRecyclerView recyclerView = Utils.findRecyclerView(child);
        if (recyclerView == null) {
            return;
        }
        recyclerViewHolder = new RecyclerViewHolder(flexibleHeight, recyclerView,
                behaviorDispatcher);
        recyclerViewHolder.setIsDispatchScroll(true);
    }

    @Override
    public void removeView(final View view) {
        recyclerViewHolder.setIsDispatchScroll(false);
        recyclerViewHolder = null;
        super.removeView(view);
    }

    public void addBehavior(final View target, final Behavior behavior) {
        behaviorDispatcher.addBehavior(target, behavior);
    }
}

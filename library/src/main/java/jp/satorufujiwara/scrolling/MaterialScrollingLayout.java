package jp.satorufujiwara.scrolling;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class MaterialScrollingLayout extends FrameLayout {

//    private final RecyclerViewHolder manager;

    public MaterialScrollingLayout(Context context) {
        this(context, null, 0);
    }

    public MaterialScrollingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialScrollingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        int flexibleHeight = 0;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ms_MaterialScrolling);
        flexibleHeight = a.getDimensionPixelSize(
                R.styleable.ms_MaterialScrolling_ms_flexible_height, flexibleHeight);
        a.recycle();
//        manager = new RecyclerViewHolder(flexibleHeight);
    }

//    public void addBehavior(View target, Behavior behavior) {
//        manager.addBehavior(target, behavior);
//    }

    public void setRecyclerView(final RecyclerView recyclerView) {
//        if (manager.getActiveRecyclerView() != null) {
//            throw new IllegalArgumentException(
//                    "MaterialScrollingLayout can't have multiple RecyclerView.");
//        }
//        manager.addRecyclerView(recyclerView);
//        manager.setRecyclerView(recyclerView);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        manager.onAttachedToWindow(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        manager.onDetachedFromWindow(this);
    }
//
//    @Override
//    protected Parcelable onSaveInstanceState() {
//        return new SavedState(super.onSaveInstanceState(), manager.onSaveInstanceState());
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Parcelable state) {
//        final SavedState savedState = (SavedState) state;
//        super.onRestoreInstanceState(savedState.getSuperState());
//        manager.onRestoreInstanceState(savedState.scrollingState);
//    }

    protected static class SavedState extends BaseSavedState {

        Parcelable scrollingState;
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        public SavedState(Parcel source) {
            super(source);
            scrollingState = source
                    .readParcelable(MaterialScrollingLayout.class.getClassLoader());
        }

        public SavedState(Parcelable superState, Parcelable scrollingState) {
            super(superState);
            this.scrollingState = scrollingState;
        }

        public void writeToParcel(@NonNull Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeParcelable(scrollingState, flags);
        }
    }
}

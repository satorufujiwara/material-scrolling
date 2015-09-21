package jp.satorufujiwara.scrolling.sample;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import jp.satorufujiwara.binder.recycler.RecyclerBinder;

public class TextBinder extends RecyclerBinder<SampleViewType> {

    private final String text;
    private final View.OnClickListener listener;

    public TextBinder(Activity activity, String text, View.OnClickListener listener) {
        super(activity, SampleViewType.TEXT);
        this.text = text;
        this.listener = listener;
    }

    @Override
    public int layoutResId() {
        return R.layout.text_binder;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final ViewHolder holder = (ViewHolder) viewHolder;
        holder.textView.setText(text);
        holder.textView.setOnClickListener(listener);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView = (TextView) itemView;

        ViewHolder(View view) {
            super(view);
        }
    }
}

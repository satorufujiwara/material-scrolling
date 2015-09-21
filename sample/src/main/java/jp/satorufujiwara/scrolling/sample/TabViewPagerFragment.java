package jp.satorufujiwara.scrolling.sample;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import jp.satorufujiwara.binder.Section;
import jp.satorufujiwara.binder.recycler.RecyclerBinderAdapter;

public class TabViewPagerFragment extends Fragment {

    public static TabViewPagerFragment newInstance() {
        return new TabViewPagerFragment();
    }

    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;

    private final RecyclerBinderAdapter<TabViewPagerSection, SampleViewType> adapter
            = new RecyclerBinderAdapter<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.tab_viewpager_fragment, container, false);
        ButterKnife.inject(this, v);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Activity activity = getActivity();
        for (String name : Constants.ANDROID_VERSIONS) {
            adapter.add(TabViewPagerSection.CONTENTS,
                    new TextBinder(activity, name, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }));
        }
    }

    enum TabViewPagerSection implements Section {
        CONTENTS
    }
}

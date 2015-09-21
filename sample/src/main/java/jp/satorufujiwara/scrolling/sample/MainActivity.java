package jp.satorufujiwara.scrolling.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.InjectView;
import jp.satorufujiwara.binder.Section;
import jp.satorufujiwara.binder.recycler.RecyclerBinderAdapter;


public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    @InjectView(R.id.toolBar)
    Toolbar toolBar;

    RecyclerBinderAdapter<MainSection, SampleViewType> adapter = new RecyclerBinderAdapter<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.inject(this);
        setSupportActionBar(toolBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.add(MainSection.CONTENTS, new TextBinder(this,
                "Tab + ViewPager", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(TabViewPagerActivity.newIntent(MainActivity.this));
            }
        }));

        adapter.add(MainSection.CONTENTS, new TextBinder(this,
                "Image + FAB", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ImageFabActivity.newIntent(MainActivity.this));
            }
        }));

    }

    enum MainSection implements Section {
        CONTENTS
    }
}

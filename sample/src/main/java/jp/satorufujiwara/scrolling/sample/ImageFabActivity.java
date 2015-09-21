package jp.satorufujiwara.scrolling.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import jp.satorufujiwara.binder.Section;
import jp.satorufujiwara.binder.recycler.RecyclerBinderAdapter;
import jp.satorufujiwara.scrolling.MaterialScrollingLayout;
import jp.satorufujiwara.scrolling.behavior.ParallaxBehavior;
import jp.satorufujiwara.scrolling.sample.behavior.FabBehavior;
import jp.satorufujiwara.scrolling.sample.behavior.OverlayViewBehavior;
import jp.satorufujiwara.scrolling.sample.behavior.TitleBehavior;

public class ImageFabActivity extends AppCompatActivity {

    private static final String TAG = "MaterialScrollingLayout";

    @InjectView(R.id.toolBar)
    Toolbar toolBar;

    @InjectView(R.id.bgImage)
    ImageView bgImage;

    @InjectView(R.id.overlayView)
    View overlayView;

    @InjectView(R.id.titleText)
    TextView titleText;

    @InjectView(R.id.fab)
    FloatingActionButton fab;

    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;

    @InjectView(R.id.materialScrollingLayout)
    MaterialScrollingLayout scrollingLayout;

    private final RecyclerBinderAdapter<ImageFabSection, SampleViewType> adapter
            = new RecyclerBinderAdapter<>();

    public static Intent newIntent(Activity activity) {
        return new Intent(activity, ImageFabActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_fab_activity);
        ButterKnife.inject(this);
        setTitle("");
        setSupportActionBar(toolBar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        titleText.setText("New York");

        scrollingLayout.addBehavior(bgImage, new ParallaxBehavior());
        scrollingLayout.addBehavior(overlayView, new OverlayViewBehavior(dp(56)));
        scrollingLayout.addBehavior(titleText, new TitleBehavior(getResources()));
        scrollingLayout.addBehavior(fab, new FabBehavior(getResources()));

        for (String name : Constants.ANDROID_VERSIONS) {
            adapter.add(ImageFabSection.CONTENTS,
                    new TextBinder(this, name, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }));
        }

    }


    enum ImageFabSection implements Section {
        CONTENTS
    }

    public int dp(final int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

}

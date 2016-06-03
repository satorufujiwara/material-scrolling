package jp.satorufujiwara.scrolling.sample;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import jp.satorufujiwara.scrolling.MaterialScrollingViewPager;
import jp.satorufujiwara.scrolling.behavior.ExitUntilCollapsedBehavior;
import jp.satorufujiwara.scrolling.behavior.ParallaxBehavior;
import jp.satorufujiwara.scrolling.behavior.ScrollUntilBehavior;
import jp.satorufujiwara.scrolling.sample.behavior.OverlayViewBehavior;
import jp.satorufujiwara.scrolling.sample.util.FragmentPagerItemAdapter;

public class TabViewPagerActivity extends AppCompatActivity {

    @InjectView(R.id.toolBar)
    Toolbar toolBar;

    @InjectView(R.id.bgImage)
    ImageView bgImage;

    @InjectView(R.id.tabLayout)
    SmartTabLayout tabLayout;

    @InjectView(R.id.overlayView)
    View overlayView;

    @InjectView(R.id.viewpager)
    MaterialScrollingViewPager viewPager;

    public static Intent newIntent(Activity activity) {
        return new Intent(activity, TabViewPagerActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_viewpager_activity);
        ButterKnife.inject(this);
        setTitle("");
        setSupportActionBar(toolBar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter.Builder(this)
                .add(R.string.tab1, TabViewPagerFragment.class)
                .add(R.string.tab2, TabViewPagerFragment.class)
                .add(R.string.tab3, TabViewPagerFragment.class)
                .add(R.string.tab4, TabViewPagerFragment.class)
                .add(R.string.tab5, TabViewPagerFragment.class)
                .build();
        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager);
        viewPager.addBehavior(bgImage, new ParallaxBehavior());
        viewPager.addBehavior(tabLayout, new ScrollUntilBehavior(0));
        viewPager.addBehavior(overlayView, new OverlayViewBehavior(dp(48)));
        viewPager.addBehavior(toolBar, new ExitUntilCollapsedBehavior(dp(48)));
    }

    public int dp(final int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

}

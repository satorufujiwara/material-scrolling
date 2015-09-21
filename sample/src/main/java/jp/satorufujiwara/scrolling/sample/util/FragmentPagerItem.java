package jp.satorufujiwara.scrolling.sample.util;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class FragmentPagerItem {

    private final String mTitle;
    private final Class<? extends Fragment> mFragmentClass;
    private final Bundle mArgs;

    protected FragmentPagerItem(final String title, final Class<? extends Fragment> f,
            final Bundle args) {
        mTitle = title;
        mFragmentClass = f;
        mArgs = args;
    }

    public static FragmentPagerItem create(final String title,
            final Class<? extends Fragment> fragmentClass) {
        return create(title, fragmentClass, new Bundle());
    }

    public static FragmentPagerItem create(final String title,
            final Class<? extends Fragment> fragmentClass,
            final Bundle args) {
        return new FragmentPagerItem(title, fragmentClass, args);
    }

    public CharSequence getPagerTitle() {
        return mTitle;
    }

    public Fragment newInstance(final Context context) {
        return Fragment.instantiate(context, mFragmentClass.getName(), mArgs);
    }

    public Bundle getArgs() {
        return mArgs;
    }


}

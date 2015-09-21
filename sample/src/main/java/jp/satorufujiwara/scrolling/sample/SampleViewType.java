package jp.satorufujiwara.scrolling.sample;

import jp.satorufujiwara.binder.ViewType;

public enum SampleViewType implements ViewType {
    TEXT;

    @Override
    public int viewType() {
        return ordinal();
    }
}
material-scrolling
===
[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-material--scrolling-green.svg?style=flat)](https://android-arsenal.com/details/1/2529)
[![Download](https://api.bintray.com/packages/satorufujiwara/maven/material-scrolling/images/download.svg)](https://bintray.com/satorufujiwara/maven/material-scrolling/_latestVersion)


Android library for [material scrolling techniques](http://www.google.com/design/spec/patterns/scrolling-techniques.html).

 ![ViewPager](/arts/viewpager.gif)  ![ImageFab](/arts/imagefab.gif)
 
 ![ViewPagerLand](/arts/viewpager_land.gif)

# Features
* Easily implement [material scrolling techniques](http://www.google.com/design/spec/patterns/scrolling-techniques.html) with RecyclerView.
* Customize the behavior while scrolling.
* Support RecyclerView in ViewPager.

# Usage

(For a working implementation of this project see the [sample](./sample).)

## MaterialScrollingLayout

Layout `ObservableRecyclerView` inside of `MaterialScrollingLayout`.

```xml
<jp.satorufujiwara.scrolling.MaterialScrollingLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/materialScrollingLayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:ms_flexible_height="240dp"
        >

    <com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView
            android:id="@+id/recyclerView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            />

</jp.satorufujiwara.scrolling.MaterialScrollingLayout>
```
`ms_flexible_height`is height from `RecyclerView`s top to `RecyclerView`'s first item.

And call `MaterialScrollingLayout.addBehavior(View, Behavior)` in Activity or Fragment.

First argument is target `View`.
Second argument is `View`'s behavior while scrolling.

```java
materialScrollingLayout.addBehavior(bgImageView, new ParallaxBehavior());
materialScrollingLayout.addBehavior(titleTextView, new ScrollingBehavior());
materialScrollingLayout.addBehavior(fabView, new FabBehavior(getResources()));
```

`FabBehavior` is customized `Behavior`.
If you want customize behavior, create class that extends 'Behavior'.

```java
public class TitleBehavior extends Behavior {
    
    private final int scrollLimitHeight;

    public TitleBehavior(Resources r) {
        scrollLimitHeight = r.getDimensionPixelOffset(R.dimen.title_scroll_height);
    }

    @Override
    protected void onScrolled(View target, int scrollY, int dy) {
        ViewCompat.setTranslationY(target, -Math.min(scrollY, scrollLimitHeight));
    }
}
```

## MaterialViewPager

If you want to use with `ViewPager`, use `MaterialViewPager`.

And PagerAdapter must imptelement `MaterialScrollingViewPager.ContainRecyclerViewPagerAdapter`.

```xml
<jp.satorufujiwara.scrolling.MaterialScrollingViewPager
        android:id="@+id/viewpager"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:ms_flexible_height="240dp"
        app:ms_base_height="48dp"
        />
```

And call `MaterialScrollingViewPager.addBehavior(View, Behavior)` in Activity or Fragment.

# Gradle

```groovy
repositories {
    jcenter()
}
dependencies {
    compile 'jp.satorufujiwara:material-scrolling:1.2.1'
    compile 'com.github.ksoichiro:android-observablescrollview:1.6.0'
}
```

# Developed By

Satoru Fujiwara (satorufujiwara)
* Twitter [satorufujiwara](https://twitter.com/satorufujiwara)
* holly.wist@gmail.com
 
Other Projects
* [recyclerview-binder](https://github.com/satorufujiwara/recyclerview-binder)
 * Android Library for RecyclerView to manage order of items and multiple view types. 
* [lighthttp](https://github.com/satorufujiwara/lighthttp)
 * Lightweitht HTTP client for Android 

# Dependencies

* Library
 * [Android-ObservableScrollView](https://github.com/ksoichiro/Android-ObservableScrollView)

* Sample
 * [recyclerview-binder](https://github.com/satorufujiwara/recyclerview-binder)
 * [SmartTabLayout](https://github.com/ogaclejapan/SmartTabLayout)


License
-------
    Copyright 2015 Satoru Fujiwara

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

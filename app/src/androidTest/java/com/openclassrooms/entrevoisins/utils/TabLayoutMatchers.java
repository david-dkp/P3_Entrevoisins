package com.openclassrooms.entrevoisins.utils;

import android.view.View;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.PerformException;

import com.google.android.material.tabs.TabLayout;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class TabLayoutMatchers {

    public static Matcher<View> matchCurrentTabTitle(String title) {
        return new MatchCurrentTabItemTitle(title);
    }

    public static Matcher<View> matchCurrentTabTitle(int id) {
        return new MatchCurrentTabItemTitle(id);
    }

    private static class MatchCurrentTabItemTitle extends BaseMatcher<View> {

        private String title;

        public MatchCurrentTabItemTitle(String title) {
            this.title = title;
        }

        public MatchCurrentTabItemTitle(int id) {
            this(ApplicationProvider.getApplicationContext().getString(id));
        }

        @Override
        public boolean matches(Object item) {
            TabLayout tabLayout = (TabLayout) item;
            TabLayout.Tab currentTab = tabLayout.getTabAt(tabLayout.getSelectedTabPosition());

            if (currentTab == null) {
                throw new PerformException.Builder()
                        .withCause(new Throwable("No tab item at position : " + tabLayout.getSelectedTabPosition()))
                        .build();
            }

            return currentTab.getText().toString().equals(title);
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("Current shown tab item title doesn't match "+this.title);
        }
    }
}

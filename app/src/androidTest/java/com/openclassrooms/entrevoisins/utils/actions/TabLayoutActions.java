package com.openclassrooms.entrevoisins.utils.actions;

import android.view.View;

import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import com.google.android.material.tabs.TabLayout;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

public class TabLayoutActions {

    public static ViewAction selectItemAction(int position) {
        return new ActionSelectTabItem(position);
    }

    private static class ActionSelectTabItem implements ViewAction{

        private int position;

        public ActionSelectTabItem(int position) {
            this.position = position;
        }

        @Override
        public Matcher<View> getConstraints() {
            return Matchers.instanceOf(TabLayout.class);
        }

        @Override
        public String getDescription() {
            return "Click on a tablayout specific item";
        }

        @Override
        public void perform(UiController uiController, View view) {
            TabLayout tabLayout = (TabLayout) view;
            TabLayout.Tab tab = tabLayout.getTabAt(position);

            if (tab == null) {
                throw new PerformException.Builder()
                        .withCause(new Throwable("No tab at position : " + position))
                        .build();
            }

            tabLayout.selectTab(tab);
        }
    }
}

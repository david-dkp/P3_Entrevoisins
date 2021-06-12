package com.openclassrooms.entrevoisins.neighbour_list;

import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.ui.neighbour_detail.NeighbourDetailActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.openclassrooms.entrevoisins.ui.neighbour_detail.NeighbourDetailActivity.EXTRA_NEIGHBOUR;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class NeighbourDetailTest {

    private static Intent intent;
    private static Neighbour neighbour;

    static {
        neighbour = new Neighbour(
                1,
                "Sarah",
                "https://i.pravatar.cc/150?img=31",
                "79  rue Bonneterie",
                "0123456789",
                "About moi",
                false
        );

        intent = new Intent(ApplicationProvider.getApplicationContext(), NeighbourDetailActivity.class);
        intent.putExtra(EXTRA_NEIGHBOUR, neighbour);
    }

    @Rule
    public ActivityScenarioRule<NeighbourDetailActivity> activityRule =
            new ActivityScenarioRule<NeighbourDetailActivity>(intent);

    @Test
    public void textView_shouldShow_neighbourName() {
        onView(withId(R.id.tvNeighbourName)).check(matches(ViewMatchers.withText(neighbour.getName())));
    }
}

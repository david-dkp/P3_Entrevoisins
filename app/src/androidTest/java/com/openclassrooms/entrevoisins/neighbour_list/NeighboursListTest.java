
package com.openclassrooms.entrevoisins.neighbour_list;


import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.DummyNeighbourGenerator;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.ui.neighbour_list.NeighbourDetailActivity;
import com.openclassrooms.entrevoisins.utils.actions.DeleteViewAction;
import com.openclassrooms.entrevoisins.utils.actions.TabLayoutActions;
import com.openclassrooms.entrevoisins.utils.matchers.TabLayoutMatchers;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.assertions.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    @Rule
    public ActivityScenarioRule<ListNeighbourActivity> mActivityRule =
            new ActivityScenarioRule<ListNeighbourActivity>(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivityRule.getScenario().onActivity(new ActivityScenario.ActivityAction<ListNeighbourActivity>() {
            @Override
            public void perform(ListNeighbourActivity activity) {
                assertThat(activity, notNullValue());
            }
        });
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(Matchers.allOf(ViewMatchers.withId(R.id.list_neighbours), isDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {

        // Given : We remove the element at position 2
        onView(Matchers.allOf(ViewMatchers.withId(R.id.list_neighbours), isDisplayed()))
                .check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(Matchers.allOf(ViewMatchers.withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(Matchers.allOf(ViewMatchers.withId(R.id.list_neighbours), isDisplayed()))
                .check(withItemCount(ITEMS_COUNT-1));
    }

    @Test
    public void myNeighboursList_showAction_shouldNavigateToNeighbourDetail() {
        Intents.init();

        onView(Matchers.allOf(ViewMatchers.withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));

        Intents.intended(IntentMatchers.hasComponent(NeighbourDetailActivity.class.getName()));
    }

    @Test
    public void navigate_to_favorite_tab_withSuccess() {
        onView(withId(R.id.tabs))
                .perform(TabLayoutActions.selectItemAction(1))
                .check(matches(TabLayoutMatchers.matchCurrentTabTitle(R.string.tab_favorites_title)));
    }

    @Test
    public void favoriteNeighboursList_showsOnly_favoriteNeighbours() {

        List<Neighbour> neighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;

         Neighbour[] favoriteNeighbours = {
            neighbours.get(0),
            neighbours.get(3),
            neighbours.get(7)
         };

        //Navigate to detail neighbour activity
        onView(Matchers.allOf(ViewMatchers.withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));

        //Add random neighbour to favorite
        for (Neighbour neighbour : favoriteNeighbours) { addNeighbourToFavorite(neighbour); }

        //Leave neighbour detail activity
        onView(isRoot()).perform(ViewActions.pressBack());

        //Navigate to favorite list tab
        onView(withId(R.id.tabs))
                .perform(TabLayoutActions.selectItemAction(1));

        //Check list contains neighbours
        //TODO
    }

    private void addNeighbourToFavorite(Neighbour neighbour) {
        //TODO
    }

}
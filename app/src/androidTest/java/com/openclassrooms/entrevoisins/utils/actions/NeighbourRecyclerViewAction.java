package com.openclassrooms.entrevoisins.utils.actions;

import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;

import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.ui.neighbour_list.MyNeighbourRecyclerViewAdapter;
import com.openclassrooms.entrevoisins.utils.matchers.RecyclerViewAdapterMatcher;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

public class NeighbourRecyclerViewAction implements ViewAction {

    private Neighbour neighbour;
    private ViewAction actionToPerform;

    public NeighbourRecyclerViewAction(Neighbour neighbour, ViewAction actionToPerform) {
        this.neighbour = neighbour;
        this.actionToPerform = actionToPerform;
    }

    @Override
    public Matcher<View> getConstraints() {
        return Matchers.allOf(Matchers.instanceOf(RecyclerView.class), new RecyclerViewAdapterMatcher(MyNeighbourRecyclerViewAdapter.class));
    }

    @Override
    public String getDescription() {
        return "Perform action on itemView neighbour with id : " + this.neighbour.getId();
    }

    @Override
    public void perform(UiController uiController, View view) {
        RecyclerView recyclerView = (RecyclerView) view;

        MyNeighbourRecyclerViewAdapter adapter = (MyNeighbourRecyclerViewAdapter) recyclerView.getAdapter();

        int index = adapter.getCurrentList().indexOf(this.neighbour);

        RecyclerViewActions.actionOnItemAtPosition(index, this.actionToPerform).perform(uiController, view);

    }
}

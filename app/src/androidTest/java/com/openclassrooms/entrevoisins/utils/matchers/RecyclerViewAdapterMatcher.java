package com.openclassrooms.entrevoisins.utils.matchers;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.PerformException;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class RecyclerViewAdapterMatcher extends BaseMatcher<View> {

    private Class<? extends RecyclerView.Adapter> adapterClass;

    public RecyclerViewAdapterMatcher(Class<? extends RecyclerView.Adapter> adapterClass) {
        this.adapterClass = adapterClass;
    }

    @Override
    public boolean matches(Object item) {

        if (!(item instanceof RecyclerView)) {
            throw new PerformException.Builder()
                    .withCause(new Throwable("View isnt a recycler view"))
                    .build();
        }

        RecyclerView recyclerView = (RecyclerView) item;

        return recyclerView.getAdapter().getClass().isAssignableFrom(this.adapterClass);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("This recycler view does not have an adapter of class : " + adapterClass.getName());
    }

}

package com.openclassrooms.entrevoisins.ui.neighbour_list.viewpagerfragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.events.NeighbourListChangedEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.MyNeighbourRecyclerViewAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class FavoriteNeighbourFragment extends Fragment {

    private NeighbourApiService neighbourApiService;
    private MyNeighbourRecyclerViewAdapter adapter;

    public FavoriteNeighbourFragment() {
        super(R.layout.fragment_neighbour_list);
    }

    public static FavoriteNeighbourFragment newInstance() {
        return new FavoriteNeighbourFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new MyNeighbourRecyclerViewAdapter();
        neighbourApiService = DI.getNeighbourApiService();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onNeighbourListChanged(NeighbourListChangedEvent event) {
        refreshList();
    }

    public void refreshList() {
        List<Neighbour> favoriteNeighbours = neighbourApiService.getFavoriteNeighbours();
        adapter.submitList(new ArrayList<Neighbour>(favoriteNeighbours));
    }
}

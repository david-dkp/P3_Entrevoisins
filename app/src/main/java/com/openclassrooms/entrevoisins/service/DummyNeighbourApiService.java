package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    @Override
    public List<Neighbour> getFavoriteNeighbours() {
        ArrayList<Neighbour> favoriteNeighbours = new ArrayList<>();

        for (Neighbour neighbour : neighbours) {
            if (neighbour.isFavorite()) favoriteNeighbours.add(neighbour);
        }

        return favoriteNeighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }

    @Override
    public void updateNeighbour(Neighbour neighbour) {
        for (int i = 0; i < neighbours.size(); i++) {
            Neighbour currentNeighbour = neighbours.get(i);

            if (currentNeighbour.getId() == neighbour.getId()) {
                neighbours.set(i, neighbour);
                break;
            }

        }
    }
}

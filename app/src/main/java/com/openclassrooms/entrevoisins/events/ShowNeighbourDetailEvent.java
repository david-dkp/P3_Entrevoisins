package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

public class ShowNeighbourDetailEvent {
    public Neighbour neighbour;

    public ShowNeighbourDetailEvent(Neighbour neighbour) { this.neighbour = neighbour; }
}

package model.backpack.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Floor {
	
	private final List<List<Room>> rooms;
	private final int nb_salle ;
	
	public Floor(int nb_salle) {
		rooms = new ArrayList<>();
		this.nb_salle = Objects.requireNonNull(nb_salle);
	}
}

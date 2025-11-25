package model.backpack.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Room {
	private final String name;
	private final List<Object> contain; 
	
	
	public Room(String name) {
		this.name =Objects.requireNonNull(name, "Le nom de la salle ne doit pas être null");
		contain = new ArrayList<>();		
	}
}

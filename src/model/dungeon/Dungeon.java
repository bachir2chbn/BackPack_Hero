package model.dungeon;

import java.util.ArrayList;
import java.util.List;

public class Dungeon {
	private final List<Floor> floors;
	private int currentFloorIndex = 0;

	public Dungeon() {
		floors = new ArrayList<>();
		initializeFloors();
	}

	private void initializeFloors() {
		Floor f1 = new Floor();

		//Salles spéciales
		f1.setRoom(0, 2, new Room(RoomType.ENTRANCE));
		f1.setRoom(4, 0, new Room(RoomType.ENEMY));
		f1.setRoom(4, 4, new Room(RoomType.TREASURE));
		f1.setRoom(8, 2, new Room(RoomType.HEALER));
		f1.setRoom(10, 2, new Room(RoomType.EXIT));

		//Chemins (couloirs)
		createCorridor(f1, 0, 2, 2, 2); // Départ -> Intersection
		createCorridor(f1, 2, 0, 2, 4); // Colonne vertébrale
		createCorridor(f1, 2, 0, 4, 0); // Vers Ennemi (Haut)
		createCorridor(f1, 2, 4, 4, 4); // Vers Trésor (Bas)

		// De l'ennemi vers le couloir principal
		createCorridor(f1, 4, 0, 4, 2);

		// Jusqu'à la fin
		createCorridor(f1, 4, 2, 10, 2);

		floors.add(f1);
		floors.add(new Floor());
		floors.add(new Floor());
	}

	private void createCorridor(Floor f, int x1, int y1, int x2, int y2) {
		int minX = Math.min(x1, x2);
		int maxX = Math.max(x1, x2);
		int minY = Math.min(y1, y2);
		int maxY = Math.max(y1, y2);

		for (int x = minX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				if (f.getRoom(x, y) == null) {
					f.setRoom(x, y, new Room(RoomType.EMPTY));
				}
			}
		}
	}

	public Floor getCurrentFloor() {
		if (currentFloorIndex < floors.size()) {
			return floors.get(currentFloorIndex);
		}
		return null;
	}

	public void ascend() {
		currentFloorIndex++;
	}

}

package model.dungeon;

import java.util.ArrayList;
import java.util.List;

import model.event.SmallRatWolf;

/**
 * Représente le donjon et la collection d'étages (Floor).
 * Gère l'initialisation des étages et la navigation entre eux.
 *
 * @author bachir2chbn
 * @author Mohammed442a
 * @version 1.0
 */
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
		f1.setRoom(0, 2, Room.create(RoomType.ENTRANCE));
		f1.setRoom(4, 0, Room.createEnemyRoom(new SmallRatWolf()));
		f1.setRoom(4, 4, Room.create(RoomType.TREASURE));
		f1.setRoom(8, 2, Room.create(RoomType.HEALER));
		f1.setRoom(10, 2, Room.create(RoomType.EXIT));

		//Chemins (couloirs)
		createCorridor(f1, 0, 2, 2, 2); // Départ vers intersection
		createCorridor(f1, 2, 0, 2, 4); // Ligne au millieu
		createCorridor(f1, 2, 0, 4, 0); // Vers ennemi (haut)
		createCorridor(f1, 2, 4, 4, 4); // Vers tresor (bas)
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
					f.setRoom(x, y, Room.create(RoomType.EMPTY));
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

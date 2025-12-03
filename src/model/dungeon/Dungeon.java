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
		f1.setRoom(0, 2, new Room(RoomType.ENTRANCE));
		f1.setRoom(1, 2, new Room(RoomType.ENEMY));
		f1.setRoom(2, 2, new Room(RoomType.TREASURE));
		f1.setRoom(10, 2, new Room(RoomType.EXIT));
		floors.add(f1);

		Floor f2 = new Floor();
		f2.setRoom(0, 1, new Room(RoomType.ENTRANCE));
		f2.setRoom(1, 1, new Room(RoomType.ENEMY));
		f2.setRoom(2, 2, new Room(RoomType.TREASURE));
		f2.setRoom(10, 2, new Room(RoomType.EXIT));
		floors.add(f2);

		Floor f3 = new Floor();
		f3.setRoom(1, 1, new Room(RoomType.ENTRANCE));
		f3.setRoom(1, 1, new Room(RoomType.ENEMY));
		f3.setRoom(2, 2, new Room(RoomType.TREASURE));
		f3.setRoom(10, 2, new Room(RoomType.EXIT));
		floors.add(f3);
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

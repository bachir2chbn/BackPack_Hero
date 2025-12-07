package model.dungeon;

/**
 * Représente un étage du donjon : grille de Room avec dimensions fixes.
 *
 * @author bachir2chbn
 * @author Mohammed442a
 * @version 1.0
 */
public class Floor {
	private static final int HEIGHT = 5;
	private static final int WIDTH = 11;
	private final Room[][] rooms;

	public Floor() {
		this.rooms = new Room[WIDTH][HEIGHT];
	}

	public void setRoom(int x, int y, Room room) {
		if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
			rooms[x][y] = room;
		}
	}

	public Room getRoom(int x, int y) {
		if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
			return rooms[x][y];
		}
		return null;
	}

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}

}

package model.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import model.event.Enemy;

/**
 * Représente une salle du donjon. Gère le type de salle, les ennemis et l'état
 * visité / nettoyé.
 *
 * @author bachir2chbn
 * @author Mohammed442a
 * @version 1.0
 */
public class Room {

	
	private final RoomType type;
	private final List<Enemy> enemies;
	private boolean visited;
	private boolean cleared;
	
	private Room(RoomType type) {
    this.type = Objects.requireNonNull(type);
    this.enemies = new ArrayList<Enemy>();
    this.visited = false;
    this.cleared = (type != RoomType.ENEMY);
	}
	
	public static Room create(RoomType type) {
		Objects.requireNonNull(type);
		return new Room(type);
	}
	
	public static Room createEnemyRoom(Enemy enemy) {
		Objects.requireNonNull(enemy);
		Room room = new Room(RoomType.ENEMY);
		room.addEnemy(enemy);
		return room;
	}
	
	private void addEnemy(Enemy enemy) {
		this.enemies.add(enemy);
		this.cleared = false;
	}
	
	public boolean isCleared() {
		if (type == RoomType.ENEMY && !cleared) {
			boolean allDead = true;
			for (var e : enemies) {
				if (!e.isDead()) {
					allDead = false;
					break;
				}
			}
			if (allDead) cleared = true;
		}
		return cleared;
	}
	
	public boolean isTraversable() {
		return switch (type) {
			case EMPTY, TREASURE, MERCHANT, HEALER, EXIT, ENTRANCE -> true;
			case ENEMY -> isCleared(); 
			case LOCKED_GATE -> false;
		};
	}
	
	public RoomType getType() {
    return type;
	}
	
	public List<Enemy> getEnemies() {
		return enemies;
	}
	
	
	public boolean isVisited() {
		return visited;
	}
	
	public void markVisited() {
		this.visited = true;
	}

	@Override
	public String toString() {
		String symbol = switch (type) {
		case EMPTY -> ".";
		case ENEMY -> cleared ? "E" : "e";
		case MERCHANT -> "M";
		case HEALER -> "H";
		case TREASURE -> "T";
		case EXIT -> "X" ;
		case LOCKED_GATE -> "#";
		default -> throw new IllegalArgumentException("Unexpected value: " + type);
		};
		return visited ? "[" + symbol + "]" : " " + symbol + " "; 
	}
}

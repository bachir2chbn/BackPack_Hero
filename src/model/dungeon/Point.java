package model.dungeon;

/**
 * Représente une position dans la grille du donjon.
 *
 * @author bachir2chbn
 * @author Mohammed442a
 * @version 1.0
 */
public record Point(int x, int y) {
	public boolean isAdjacent(Point other) {
    return Math.abs(x - other.x) + Math.abs(y - other.y) == 1;
}
}

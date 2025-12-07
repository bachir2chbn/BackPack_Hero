package model.dungeon;

public record Point(int x, int y) {
	public boolean isAdjacent(Point other) {
    return Math.abs(x - other.x) + Math.abs(y - other.y) == 1;
}
}

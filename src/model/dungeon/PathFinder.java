package model.dungeon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Utilitaire pour trouver le plus court chemin sur une grille Floor
 * entre deux coordonnées en utilisant BFS.
 *
 * @author bachir2chbn
 * @author Mohammed442a
 * @version 1.0
 */
public class PathFinder {
	
	public static List<Point> findShortestPath(Floor floor, int startX, int startY, int endX, int endY) {
		boolean[][] visited = new boolean[floor.getWidth()][floor.getHeight()];
		Point[][] predecessors = new Point[floor.getWidth()][floor.getHeight()];
		LinkedList<Point> queue = new LinkedList<>();

		Point start = new Point(startX, startY);
		queue.add(start);
		visited[startX][startY] = true;

		return runBFS(floor, queue, visited, predecessors, endX, endY);
	}

	private static List<Point> runBFS(Floor floor, LinkedList<Point> queue, boolean[][] visited, Point[][] predecessors,
	    int endX, int endY) {

		while (!queue.isEmpty()) {
			Point current = queue.poll();

			if (current.x() == endX && current.y() == endY) {
				return reconstructPath(predecessors, current);
			}

			exploreNeighbors(floor, current, queue, visited, predecessors, endX, endY);
		}
		return null;
	}

	private static void exploreNeighbors(Floor floor, Point current, LinkedList<Point> queue, boolean[][] visited, Point[][] predecessors, int endX, int endY) {
		
		int[][] directions = { {0, 1}, {0, -1}, {1, 0}, {-1, 0} };

		for (var dir : directions) {
			int nx = current.x() + dir[0];
			int ny = current.y() + dir[1];

			if (isValidMove(floor, nx, ny, endX, endY) && !visited[nx][ny]) {
				visited[nx][ny] = true;
				predecessors[nx][ny] = current;
				queue.add(new Point(nx, ny));
			}
		}
	}

	private static boolean isValidMove(Floor floor, int x, int y, int targetX, int targetY) {
		// Vérif des limites
		if (x < 0 || x >= floor.getWidth() || y < 0 || y >= floor.getHeight()) return false;
		
		Room room = floor.getRoom(x, y);
		if (room == null) return false;

		return room.isTraversable() || (x == targetX && y == targetY);
	}

	// Reconstruction du chemin
	private static List<Point> reconstructPath(Point[][] predecessors, Point current) {
		List<Point> path = new ArrayList<>();
		while (current != null) {
			path.add(0, current);
			current = predecessors[current.x()][current.y()];
		}
		return path;
	}
}

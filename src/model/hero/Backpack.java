package model.hero;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.items.Item;

public class Backpack {

	private static int rows = 3;
	private static int cols = 5;
	private Item[][] grid;
	private final List<Item> items;

	public Backpack() {
		this.grid = new Item[rows][cols];
		this.items = new ArrayList<>();
	}

	public static int getRows() {
		return rows;
	}

	public static int getCols() {
		return cols;
	}

	public Item getItemAt(int row, int col) {
		checkBounds(row, col);
		return grid[row][col];
	}

	public boolean canPlaceItem(Item item, int startRow, int startCol) {
		Objects.requireNonNull(item);
		var shape = item.getShape();
		int shapeRows = shape.length;
		int shapeCols = shape[0].length;
		if (startRow + shapeRows > rows || startCol + shapeCols > cols)
			return false;
		if (startRow < 0 || startCol < 0)
			return false;
		for (int i = 0; i < shapeRows; i++) {
			for (int j = 0; j < shapeCols; j++) {
				if (shape[i][j]) {
					int row = startRow + i;
					int col = startCol + j;
					if (grid[row][col] != null && !grid[row][col].equals(item))
						return false;
				}
			}
		}
		return true;
	}

	public boolean placeItem(Item item, int startRow, int startCol) {
		Objects.requireNonNull(item);
		if (!canPlaceItem(item, startRow, startCol)) {
			return false;
		}
		boolean[][] shape = item.getShape();
		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[i].length; j++) {
				if (shape[i][j]) {
					grid[startRow + i][startCol + j] = item;
				}
			}
		}

		if (!items.contains(item)) {
			items.add(item);
		}
		return true;
	}

	public boolean removeItem(Item item) {
		Objects.requireNonNull(item);
		if (!items.contains(item)) {
			return false;
		}

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (grid[i][j] != null && grid[i][j].equals(item)) {
					grid[i][j] = null;
				}
			}
		}
		items.remove(item);
		return true;
	}

	public int[] findItemPosition(Item item) {
		java.util.Objects.requireNonNull(item);
		// On récupère le decalage
		int[] offset = getFirstShapeBlock(item.getShape());

		// On cherche le premier morceau de l'item dans la grille
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				// On vérifie l'identité
				if (grid[i][j] != null && grid[i][j].equals(item)) {
					// On applique la correction
					return new int[] { i - offset[0], j - offset[1] };
				}
			}
		}
		return null;
	}

	// Trouve les coordonnées du premier true dans la forme
	private int[] getFirstShapeBlock(boolean[][] shape) {
		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[i].length; j++) {
				if (shape[i][j]) {
					return new int[] { i, j };
				}
			}
		}
		return new int[] { 0, 0 };
	}

	public boolean autoPlaceItem(Item item) {
		Objects.requireNonNull(item);

		var candidate = item;

		for (int rotation = 0; rotation < 4; rotation++) {
			for (int row = 0; row < rows; row++) {
				for (int col = 0; col < cols; col++) {
					if (placeItem(candidate, row, col)) {
						return true;
					}
				}
			}
			candidate = candidate.rotate();
		}
		return false;
	}

	public int getItemCount() {
		return items.size();
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}

	public void clear() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				grid[i][j] = null;
			}
		}
		items.clear();
	}

	private void checkBounds(int row, int col) {
		if (row < 0 || row >= rows || col < 0 || col >= cols) {
			throw new IllegalArgumentException(String.format("Position (%d, %d) out of bounds", row, col));
		}
	}

	public List<Item> getItems() {
		return List.copyOf(items);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Backpack [").append(items.size()).append(" items]\n");

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				sb.append(grid[i][j] != null ? "X" : ".");
				sb.append(" ");
			}
			sb.append("\n");
		}

		return sb.toString();
	}

}

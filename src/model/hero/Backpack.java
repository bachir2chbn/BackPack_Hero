package model.hero;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.items.Item;

/**
 * Représente le sac à dos du héros : une grille de cases et une liste d'objets
 * présents. Gère le placement, la suppression et la recherche d'objets.
 *
 * @author bachir2chbn
 * @author Mohammed442a
 * @version 1.0
 */
public class Backpack {

	private static int rows = 3;
	private static int cols = 5;
	private Item[][] grid;
	private final List<Item> items;

	/**
	 * Crée un nouveau sac à dos vide avec la taille par défaut.
	 */
	public Backpack() {
		this.grid = new Item[rows][cols];
		this.items = new ArrayList<>();
	}

	/**
	 * Retourne le nombre de lignes de la grille du sac.
	 *
	 * @return nombre de lignes
	 */
	public static int getRows() {
		return rows;
	}

	/**
	 * Retourne le nombre de colonnes de la grille du sac.
	 *
	 * @return nombre de colonnes
	 */
	public static int getCols() {
		return cols;
	}

	/**
	 * Retourne l'objet situé à la position donnée dans la grille.
	 *
	 * @param row index de ligne
	 * @param col index de colonne
	 * @return l'Item à la position, ou null si vide
	 */
	public Item getItemAt(int row, int col) {
		checkBounds(row, col);
		return grid[row][col];
	}

	/**
	 * Indique si un item peut être placé à partir d'une position de départ en
	 * tenant compte de sa forme.
	 *
	 * @param item     l'objet à placer
	 * @param startRow position de départ (ligne)
	 * @param startCol position de départ (colonne)
	 * @return true si l'objet peut être placé sans chevauchement ni sortie
	 *         de la grille
	 */
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

	/**
	 * Place un objet dans la grille à partir d'une position de départ si c'est
	 * possible.
	 *
	 * @param item     l'objet à placer
	 * @param startRow position de départ (ligne)
	 * @param startCol position de départ (colonne)
	 * @return true si l'opération réussit, false sinon
	 */
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

	/**
	 * Supprime un objet du sac s'il y est présent.
	 *
	 * @param item l'objet à supprimer
	 * @return true si l'objet a été retiré, false si il n'était pas présent
	 */
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

	/**
	 * Cherche la position (coin supérieur gauche) d'un item dans la grille.
	 *
	 * @param item l'objet recherché
	 * @return un tableau [row, col] indiquant la position, ou null si non trouvé
	 */
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

	/**
	 * Trouve les coordonnées du premier true dans la forme.
	 *
	 * @param shape forme de l'objet (matrice de booleans)
	 * @return coordonnées [row, col] du premier bloc
	 */
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

	/**
	 * Tente de placer automatiquement un objet dans le sac en essayant toutes
	 * les positions et rotations.
	 *
	 * @param item l'objet à placer
	 * @return true si l'objet a été placé
	 */
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

	/**
	 * Retourne le nombre d'objets dans le sac.
	 *
	 * @return nombre d'items
	 */
	public int getItemCount() {
		return items.size();
	}

	/**
	 * Indique si le sac est vide.
	 *
	 * @return true si le sac ne contient aucun objet
	 */
	public boolean isEmpty() {
		return items.isEmpty();
	}

	/**
	 * Vide complètement le sac (grille + liste).
	 */
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

	/**
	 * Retourne une copie immuable de la liste d'items contenus dans le sac.
	 *
	 * @return liste d'items
	 */
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

package view;

import java.awt.Graphics2D;
import model.dungeon.Point;
import model.hero.Backpack;
import model.items.Item;

/**
 * Vue représentant l'affichage du sac à dos et ses interactions graphiques.
 * Fournit des utilitaires pour convertir une position écran en case de grille.
 *
 * Note : implémentation simple servant de liaison avec GameController.
 *
 * @author bachir2chbn
 * @author Mohammed442a
 * @version 1.0
 */
public class BackpackView {
	private final int x;
	private final int y;
	private final int cellSize;
	private final Backpack backpack;
	private boolean open = false;

	public BackpackView(int x, int y, int cellSize, Backpack backpack) {
		this.x = x;
		this.y = y;
		this.cellSize = cellSize;
		this.backpack = backpack;
	}

	/**
	 * Dessine la vue du sac (grille + items).
	 *
	 * @param g contexte graphique
	 */
	public void draw(Graphics2D g) {
		// dessin minimal : grille et items (implémentation graphique simplifiée)
	}

	/**
	 * Convertit une position écran en position de grille dans le sac.
	 *
	 * @param mouseX coordonnée X souris
	 * @param mouseY coordonnée Y souris
	 * @return Point (col, row) correspondant à la case, ou null si hors grille
	 */
	public Point getGridPosition(int mouseX, int mouseY) {
		int relX = mouseX - x;
		int relY = mouseY - y;
		if (relX < 0 || relY < 0) return null;
		int col = relX / cellSize;
		int row = relY / cellSize;
		if (row >= Backpack.getRows() || col >= Backpack.getCols()) return null;
		return new Point(col, row);
	}

	/**
	 * Indique si le bouton toggle du sac a été cliqué (position fournie par la vue globale).
	 *
	 * @param mouseX X souris
	 * @param mouseY Y souris
	 * @return true si le toggle a été cliqué
	 */
	public boolean isToggleClicked(int mouseX, int mouseY) {
		// Simple placeholder : non utilisé dans l'impl actuelle
		return false;
	}

	/**
	 * Ouvre/ferme le sac.
	 */
	public void toggle() {
		open = !open;
	}

	/**
	 * Indique si le sac est ouvert.
	 *
	 * @return true si ouvert
	 */
	public boolean isOpen() {
		return open;
	}

	/**
	 * Place un item visuel en cours de drag (affiché par GameView).
	 *
	 * @param item item à afficher (peut être null)
	 * @param drawX position X d'affichage
	 * @param drawY position Y d'affichage
	 */
	public void setDraggedItem(Item item, int drawX, int drawY) {
		// placeholder pour l'affichage d'item en cours de drag
	}
}

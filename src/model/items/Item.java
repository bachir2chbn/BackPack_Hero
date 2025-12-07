package model.items;

/**
 * Interface scellée représentant un objet pouvant être stocké dans le sac.
 * Définit les opérations communes aux différents types d'objets.
 *
 * @author bachir2chbn
 * @author Mohammed442a
 * @version 1.0
 */
public sealed interface Item permits Armor, Gold, Magic, MeleeWeapon, RangeWeapon, Shield {

	/**
	 * Retourne une nouvelle instance de l'item avec la forme donnée.
	 *
	 * @param rotated forme (matrice) appliquée à l'item
	 * @return instance d'Item avec la nouvelle forme
	 */
	Item setShape(boolean[][] rotated);

	/**
	 * Identifiant unique de l'item.
	 *
	 * @return id
	 */
	long id();

	/**
	 * Rareté de l'objet.
	 *
	 * @return la rarity
	 */
	Rarity rarity();

	/**
	 * Coût en énergie (ou autre) pour utiliser l'objet.
	 *
	 * @return coût en énergie
	 */
	int cost();

	/**
	 * Forme de l'objet sous forme d'une matrice de booleans.
	 *
	 * @return matrice shape
	 */
	boolean[][] getShape();

	/**
	 * Nom affichable de l'objet (par défaut la toString()).
	 *
	 * @return nom affichable
	 */
	default String getDisplayName() {
		return this.toString();
	}

	/**
	 * Retourne une nouvelle instance de l'objet tournée (rotated).
	 *
	 * @return item tourné
	 */
	default Item rotate() {
		boolean[][] original = getShape();
		int rows = original.length;
		int cols = original[0].length;

		boolean[][] rotated = new boolean[cols][rows];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				rotated[j][rows - 1 - i] = original[i][j];
			}
		}

		return setShape(rotated);
	}

}

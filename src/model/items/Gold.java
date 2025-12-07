package model.items;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Représente un objet d'or stockable dans l'inventaire.
 *
 * @param id identifiant unique
 * @param name nom affichable
 * @param rarity rareté (généralement commun)
 * @param amount quantité d'or
 * @param shape forme dans l'inventaire
 * @param cost coût (usage)
 * @param stats description texte
 *
 * @author bachir2chbn
 * @author Mohammed442a
 * @version 1.0
 */
public record Gold(long id, String name, Rarity rarity, int amount, boolean[][] shape, int cost, String stats)
    implements Item {
	public Gold {
		Objects.requireNonNull(name, "name ne doit pas etre null");
		Objects.requireNonNull(rarity, "La rarity ne doit pas etre null");
		Objects.requireNonNull(stats, "le stats ne doit pas etre null");
		Objects.requireNonNull(shape);
		if (amount < 0) {
			throw new IllegalArgumentException("La quantité d'or doit être positive");
		}
	}

	/**
	 * Constructeur de commodité générant un id aléatoire.
	 *
	 * @param name nom affichable
	 * @param rarity rareté
	 * @param amount quantité
	 * @param shape forme
	 * @param cost coût
	 * @param stats description
	 */
	public Gold(String name, Rarity rarity, int amount, boolean[][] shape, int cost, String stats) {
		this(ThreadLocalRandom.current().nextLong(), name, rarity, amount, shape, cost, stats);
	}

	@Override
	public boolean[][] getShape() {
		return shape;
	}

	@Override
	public String getDisplayName() {
		return name.toString();
	}

	@Override
	public Item setShape(boolean[][] rotated) {
		return this;
	}

	@Override
	public int cost() {
		return cost;
	}

}

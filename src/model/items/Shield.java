package model.items;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Bouclier : apporte de la protection et possède une forme dans le sac.
 *
 * @param id identifiant unique
 * @param name nom du bouclier
 * @param protection valeur de protection
 * @param rarity rareté
 * @param shape forme dans l'inventaire
 * @param cost coût d'utilisation
 * @param stats description texte
 *
 * @author bachir2chbn
 * @author Mohammed442a
 * @version 1.0
 */
public record Shield(long id, ShieldName name, int protection, Rarity rarity, boolean[][] shape, int cost, String stats)
    implements Item {

	public Shield {
		Objects.requireNonNull(name);
		Objects.requireNonNull(rarity);
		Objects.requireNonNull(shape);
		Objects.requireNonNull(stats);

		if (protection <= 0) {
			throw new IllegalArgumentException("Protection must be positive");
		}
		if (cost < 0) {
			throw new IllegalArgumentException("Energy cost cannot be negative");
		}
	}

	/**
	 * Constructeur de commodité générant un id aléatoire.
	 *
	 * @param name nom du bouclier
	 * @param protection valeur de protection
	 * @param rarity rareté
	 * @param shape forme
	 * @param cost coût
	 * @param stats description
	 */
	public Shield(ShieldName name, int protection, Rarity rarity, boolean[][] shape, int cost, String stats) {
		this(ThreadLocalRandom.current().nextLong(), name, protection, rarity, shape, cost, stats);
	}

	@Override
	public boolean[][] getShape() {
		return shape;
	}

	@Override
	public Item setShape(boolean[][] rotated) {
		return new Shield(id, name, protection, rarity, rotated, cost, stats);
	}
}

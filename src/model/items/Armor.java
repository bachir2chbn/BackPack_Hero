package model.items;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Armure portée par le héros : protecte et possède une forme pour l'inventaire.
 *
 * @param id identifiant unique
 * @param name nom de l'armure
 * @param protectionBonus bonus de protection
 * @param rarity rareté
 * @param shape forme dans l'inventaire
 * @param cost coût d'utilisation
 * @param stats description texte des stats
 *
 * @author bachir2chbn
 * @author Mohammed442a
 * @version 1.0
 */
public record Armor(long id, ArmorName name, int protectionBonus, Rarity rarity, boolean[][] shape, int cost,
    String stats) implements Item {
	public Armor {
		Objects.requireNonNull(name, "name ne doit pas etre null");
		Objects.requireNonNull(rarity, "La rarity ne doit pas etre null");
		Objects.requireNonNull(stats, "le stats ne doit pas etre null");
		Objects.requireNonNull(shape);
		if (cost < 0 || cost > 2) {
			throw new IllegalArgumentException("le cost doit etre entre 0 et 2");
		}
		if (protectionBonus < 0) {
			throw new IllegalArgumentException("Protection bonus cannot be negative");
		}
	}

	/**
	 * Constructeur de commodité générant un id aléatoire.
	 *
	 * @param name nom de l'armure
	 * @param protectionBonus valeur de protection
	 * @param rarity rareté
	 * @param shape forme de l'objet
	 * @param cost coût d'utilisation
	 * @param stats description
	 */
	public Armor(ArmorName name, int protectionBonus, Rarity rarity, boolean[][] shape, int cost, String stats) {
		this(ThreadLocalRandom.current().nextLong(), name, protectionBonus, rarity, shape, cost, stats);
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
		return new Armor(id, name, protectionBonus, rarity, rotated, cost, stats);
	}

}

package model.items;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Arme à distance avec dégâts et forme pour l'inventaire.
 *
 * @param id identifiant
 * @param name nom de l'arme
 * @param damage dégâts
 * @param rarity rareté
 * @param shape forme
 * @param cost coût
 * @param stats description
 *
 * @author bachir2chbn
 * @author Mohammed442a
 * @version 1.0
 */
public record RangeWeapon(long id, WeaponName name, int damage, Rarity rarity, boolean[][] shape, int cost,
    String stats) implements Item {
	public RangeWeapon {
		Objects.requireNonNull(name, "name ne doit pas etre null");
		Objects.requireNonNull(rarity, "La rarity ne doit pas etre null");
		Objects.requireNonNull(stats, "le stats ne doit pas etre null");
		Objects.requireNonNull(shape);
		if (cost < 0 || cost > 2) {
			throw new IllegalArgumentException("le cost doit etre entre 0 et 2");
		}
	}

	/**
	 * Constructeur de commodité générant un id aléatoire.
	 *
	 * @param name nom de l'arme
	 * @param damage dégâts
	 * @param rarity rareté
	 * @param shape forme
	 * @param cost coût
	 * @param stats description
	 */
	public RangeWeapon(WeaponName name, int damage, Rarity rarity, boolean[][] shape, int cost, String stats) {
		this(ThreadLocalRandom.current().nextLong(), name, damage, rarity, shape, cost, stats);
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
		return new RangeWeapon(id, name, damage, rarity, rotated, cost, stats);
	}

}

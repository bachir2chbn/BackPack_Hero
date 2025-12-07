package model.items;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public record MeleeWeapon(long id, WeaponName name, int damage, Rarity rarity, boolean[][] shape, int cost,
    String stats) implements Item {
	public MeleeWeapon {
		Objects.requireNonNull(name, "name ne doit pas etre null");
		Objects.requireNonNull(rarity, "La rarity ne doit pas etre null");
		Objects.requireNonNull(stats, "le stats ne doit pas etre null");
		Objects.requireNonNull(shape);
		if (cost < 0 || cost > 2) {
			throw new IllegalArgumentException("le cost doit etre entre 0 et 2");
		}
	}

	public MeleeWeapon(WeaponName name, int damage, Rarity rarity, boolean[][] shape, int cost, String stats) {
		this(ThreadLocalRandom.current().nextLong(), name, damage, rarity, shape, cost, stats);
	}

	@Override
	public boolean[][] getShape() {
		return shape;
	}

	@Override
	public Item setShape(boolean[][] rotated) {
		return new MeleeWeapon(id, name, damage, rarity, rotated, cost, stats);
	}

	@Override
	public String getDisplayName() {
		return name.toString();
	}

	@Override
	public String toString() {
		boolean[][] s = getShape();
		StringBuilder sb = new StringBuilder();
		for (boolean[] row : s) {
			for (boolean b : row) {
				sb.append(b ? "X" : ".").append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}

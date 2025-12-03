package model.items;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

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

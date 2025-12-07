package model.items;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

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

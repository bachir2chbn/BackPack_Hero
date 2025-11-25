package model.items;

import java.util.Objects;

public record Shield(ShieldName name, int protection, Rarity rarity, boolean[][] shape, int cost) implements Item {
	public Shield{
		Objects.requireNonNull(name);
		Objects.requireNonNull(rarity);
		Objects.requireNonNull(shape);
		if (protection <= 0) {
      throw new IllegalArgumentException("Protection must be positive");
		}
		if (cost < 0) {
      throw new IllegalArgumentException("Energy cost cannot be negative");
		}
	}
	
	
	@Override
	public boolean[][] getShape() {
		return shape;
	}


	@Override
	public Item setShape(boolean[][] rotated) {
		return new Shield(name, protection, rarity, rotated, cost);
	}
}

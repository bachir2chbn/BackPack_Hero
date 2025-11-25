package model.items;

import java.util.Objects;

public record RangeWeapon(WeaponName name, int damage, Rarity rarity, boolean[][] shape, int cost, String stats) implements Item  {
	public RangeWeapon{
		Objects.requireNonNull(name,"name ne doit pas etre null");
		Objects.requireNonNull(rarity,"La rarity ne doit pas etre null");
		Objects.requireNonNull(stats,"le stats ne doit pas etre null");
		Objects.requireNonNull(shape);
		if (cost < 0 || cost >2 ) {
			throw new IllegalArgumentException("le cost doit etre entre 0 et 2");
		}
	}
	
	
	@Override
	public boolean[][] getShape() {
		return shape;
	}


	@Override
	public Item setShape(boolean[][] rotated) {
		return new RangeWeapon(name, damage, rarity, rotated, cost, stats);
	}
}

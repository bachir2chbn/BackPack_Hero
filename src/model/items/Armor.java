package model.items;
import java.util.Objects;

public record Armor(ArmorName name,int protectionBonus, Rarity rarity, boolean[][] shape, int cost, String stats) implements Item {
	public Armor{
		Objects.requireNonNull(name,"name ne doit pas etre null");
//		Objects.requireNonNull(type,"le type ne doit pas etre pas null");
		Objects.requireNonNull(rarity,"La rarity ne doit pas etre null");
		Objects.requireNonNull(stats,"le stats ne doit pas etre null");
		Objects.requireNonNull(shape);
		if (cost < 0 || cost >2 ) {
			throw new IllegalArgumentException("le cost doit etre entre 0 et 2");
		}
    if (protectionBonus < 0) {
      throw new IllegalArgumentException("Protection bonus cannot be negative");
  }
	}
	
	
	
	@Override
	public boolean[][] getShape() {
		return shape;
	}


	@Override
	public Item setShape(boolean[][] rotated) {
		return new Armor(name,protectionBonus, rarity, rotated, cost, stats);
	}
}

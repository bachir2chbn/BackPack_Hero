package model.backpack.item.armor;
import java.util.Objects;

import Backpack.Item.Item;
import Backpack.Item.Rarity;

public record Armor(ArmorName name, ArmorType type, Rarity rarity, int size, int cost, String stats) implements Item {
	public Armor{
		Objects.requireNonNull(name,"name ne doit pas etre null");
		Objects.requireNonNull(type,"le type ne doit pas etre pas null");
		Objects.requireNonNull(rarity,"La rarity ne doit pas etre null");
		Objects.requireNonNull(stats,"le stats ne doit pas etre null");
		if(size <= 0 || size > 5) {
			throw new IllegalArgumentException("le size doit etre entre 1 et 5 ");
		}
		if (cost < 0 || cost >2 ) {
			throw new IllegalArgumentException("le cost doit etre entre 0 et 2");
		}
	}
}

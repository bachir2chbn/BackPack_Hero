package Backpack.Item.magic;
import java.util.Objects;

import Backpack.Item.Object;
import Backpack.Item.Rarity;

public record Magic(MagicName name, MagicType type, Rarity rarity, int size, int cost, String stats) implements Object{
	public Magic{
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

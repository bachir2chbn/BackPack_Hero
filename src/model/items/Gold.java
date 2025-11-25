package model.items;

import java.util.Objects;

public record Gold(String name, Rarity rarity, int amount, boolean[][] shape, String stats) implements Item {
  public Gold {
    Objects.requireNonNull(name,"name ne doit pas etre null");
    Objects.requireNonNull(rarity,"La rarity ne doit pas etre null");
    Objects.requireNonNull(stats,"le stats ne doit pas etre null");
    Objects.requireNonNull(shape);
    
    if(amount < 0) {
      throw new IllegalArgumentException("La quantité d'or doit être positive");
    }
    
  }
  
  @Override
  public boolean[][] getShape() {
      return shape;
  }

  @Override
  public Item setShape(boolean[][] rotated) {
      return this;
  }
  
}

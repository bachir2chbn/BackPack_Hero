package model.dungeon;

import model.hero.Hero;
import model.items.Armor;
import model.items.ArmorName;
import model.items.Rarity;

public class GameData {
	private final Hero hero;
	private final Dungeon dungeon;
	private int heroX = 0; 
	private int heroY = 2;

	public GameData() {
    this.hero = new Hero(40);
    this.dungeon = new Dungeon();

    initTestInventory();
    }

	private void initTestInventory() {
		boolean[][] shapeArmor = {{true, true}};
		boolean[][] shapeArmor1 = {{true, false}, {false,true}};

		Armor testArmor = new Armor(ArmorName.tunic, 5, Rarity.commun, shapeArmor, 1, "Stats...");
		Armor testArmor2 = new Armor(ArmorName.tunic, 5, Rarity.commun, shapeArmor1, 1, "Stats...");

		hero.getBackpack().placeItem(testArmor, 0, 0);
		hero.getBackpack().placeItem(testArmor2, 1, 0);
	}
	
	public void moveHeroTo(int x, int y) {
		if (dungeon.getCurrentFloor().getRoom(x, y) != null) {
			this.heroX = x;
			this.heroY = y;
		}
	}

	public Hero getHero() {
		return hero;
	}

	public Dungeon getDungeon() {
		return dungeon;
	}

	public Floor getCurrentFloor() {
		return dungeon.getCurrentFloor();
	}
	
	public int getHeroX() {
		return heroX;
	}

	public int getHeroY() {
		return heroY;
	}
}
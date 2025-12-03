package model.dungeon;

import model.hero.Hero;
import model.items.Armor;
import model.items.ArmorName;
import model.items.Rarity;

public class GameData {
	private final Hero hero;
	private final Dungeon dungeon;

	public GameData() {
    this.hero = new Hero(40);
    this.dungeon = new Dungeon();

    initTestInventory();
    }

	private void initTestInventory() {
		boolean[][] shapeArmor = { { true, true } };

		Armor testArmor = new Armor(ArmorName.tunic, 5, Rarity.commun, shapeArmor, 1, "Stats...");

		hero.getBackpack().placeItem(testArmor, 0, 0);
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
}
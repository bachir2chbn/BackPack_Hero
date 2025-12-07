package model.dungeon;


import model.event.Combat;
import model.event.Enemy;
import model.hero.Hero;
import model.items.MeleeWeapon;
import model.items.Rarity;
import model.items.Shield;
import model.items.ShieldName;
import model.items.WeaponName;

import java.util.List;
import java.util.Objects;



public class GameData {
	private final Hero hero;
	private final Dungeon dungeon;
	private Point heroPosition;
	private Combat currentCombat;

	public GameData() {
    this.hero = new Hero(40);
    this.dungeon = new Dungeon();
    this.heroPosition = new Point(0, 2);
    this.currentCombat = null;
    initTestInventory();
  }

	private void initTestInventory() {
		boolean[][] shapeShield = {{true, true}, {true, true}};
		boolean[][] shapeSword = {{true, true}};

		var testShield = new Shield(ShieldName.KnightsShield, 5, Rarity.commun, shapeShield, 1, "Stats...");
		var testMeleeWeapon = new MeleeWeapon(WeaponName.woodenSword, 5, Rarity.commun, shapeSword, 1, "Stats...");

		hero.getBackpack().placeItem(testShield, 0, 0);
		hero.getBackpack().placeItem(testMeleeWeapon, 2, 0);
	}
	
	public void moveHeroTo(Point target) {
		Objects.requireNonNull(target);
		Floor currentFloor = dungeon.getCurrentFloor();
    
    List<Point> path = PathFinder.findShortestPath(currentFloor, heroPosition.x(), heroPosition.y(), target.x(), target.y());
		if (path != null && !path.isEmpty()) {
			this.heroPosition = target;
			checkTileContent(currentFloor, target);
		}
	}
	
	private void checkTileContent(Floor floor, Point pos) {
		Room room = floor.getRoom(pos.x(), pos.y());
		
		if (room.getType() == RoomType.ENEMY && !room.isCleared()) {
			if (!room.getEnemies().isEmpty()) {
				startCombat(room.getEnemies().get(0));
			}
		}
	}
	
	//--- GESTION DU COMBAT ---

	public void startCombat(Enemy enemy) {
		Objects.requireNonNull(enemy);
		System.out.println("Combat démarré contre : " + enemy.getName());
		this.currentCombat = new Combat(hero, List.of(enemy));
	}
	
	public void endCombat() {
		this.currentCombat = null;
	}

	public boolean isFighting() {
		return currentCombat != null;
	}

	public Combat getCurrentCombat() {
		return currentCombat;
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
	
	public Point getHeroPosition() {
		return heroPosition;
	}
}
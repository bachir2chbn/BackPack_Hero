package model.dungeon;


import model.event.Combat;
import model.event.Enemy;
import model.hero.Hero;
import model.items.Armor;
import model.items.ArmorName;
import model.items.MeleeWeapon;
import model.items.Rarity;
import model.items.Shield;
import model.items.ShieldName;
import model.items.WeaponName;

import java.util.List;
import java.util.Objects;

/**
 * Contient l'état global du jeu : héros, donjon, position et combat courant.
 * Fournit des méthodes de manipulation de l'état du jeu.
 *
 * @author bachir2chbn
 * @author Mohammed442a
 * @version 1.0
 */
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

		Shield testShield = new Shield(ShieldName.KnightsShield, 5, Rarity.commun, shapeShield, 1, "Stats...");
		MeleeWeapon testMeleeWeapon = new MeleeWeapon(WeaponName.woodenSword, 5, Rarity.commun, shapeSword, 1, "Stats...");

		hero.getBackpack().placeItem(testShield, 0, 0);
		hero.getBackpack().placeItem(testMeleeWeapon, 2, 0);
	}
	
	/**
	 * Demande au héros de se déplacer vers une case cible si un chemin existe.
	 *
	 * @param target position cible (Point) vers laquelle déplacer le héros
	 */
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
	
	/**
	 * Démarre un combat contre un ennemi donné.
	 *
	 * @param enemy l'ennemi à affronter
	 */
	public void startCombat(Enemy enemy) {
		Objects.requireNonNull(enemy);
		System.out.println("Combat démarré contre : " + enemy.getName());
		this.currentCombat = new Combat(hero, List.of(enemy));
	}
	
	/**
	 * Termine le combat courant.
	 */
	public void endCombat() {
		this.currentCombat = null;
	}
	
	/**
	 * Indique si un combat est en cours.
	 *
	 * @return true si un combat est actif, false sinon
	 */
	public boolean isFighting() {
		return currentCombat != null;
	}
	
	/**
	 * Retourne le combat courant (ou null si aucun).
	 *
	 * @return l'objet Combat courant
	 */
	public Combat getCurrentCombat() {
		return currentCombat;
	}
	
	/**
	 * Retourne le héros du jeu.
	 *
	 * @return l'objet Hero
	 */
	public Hero getHero() {
		return hero;
	}
	
	/**
	 * Retourne le donjon.
	 *
	 * @return l'objet Dungeon
	 */
	public Dungeon getDungeon() {
		return dungeon;
	}
	
	/**
	 * Retourne l'étage courant.
	 *
	 * @return l'objet Floor courant
	 */
	public Floor getCurrentFloor() {
		return dungeon.getCurrentFloor();
	}
	
	/**
	 * Retourne la position du héros.
	 *
	 * @return position du héros sous forme de Point
	 */
	public Point getHeroPosition() {
		return heroPosition;
	}
}

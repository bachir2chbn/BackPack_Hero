package model.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import model.hero.Hero;
import model.items.Armor;
import model.items.Gold;
import model.items.Item;
import model.items.Magic;
import model.items.MeleeWeapon;
import model.items.RangeWeapon;
import model.items.Shield;

/**
 * Gère la logique d'un combat entre le héros et une liste d'ennemis :
 * phases, tours, utilisation d'objets, application des dégâts et fin de combat.
 *
 * @author bachir2chbn
 * @author Mohammed442a
 * @version 1.0
 */
public class Combat {
	
	public enum Phase {
		HERO_TURN, ENEMY_TURN, VICTORY, DEFEAT
	}
	
	private final Hero hero;
	private final List<Enemy> enemies;
	private Phase currentPhase;
	private int turnNumber;
	private String logs = "";
	
	public Combat(Hero hero, List<Enemy> enemies) {
		this.hero = Objects.requireNonNull(hero, "hero ne doit pas etre null");
		this.enemies = new ArrayList<>(Objects.requireNonNull(enemies));
		
		if (enemies.isEmpty()) {
			throw new IllegalArgumentException("la liste d'enemies ne doit pas etre vide");
		}
		
		this.currentPhase = Phase.HERO_TURN;
		this.turnNumber = 1;
		this.logs = "Combat commencé !";
		
		hero.rechargeMana();
		hero.startNewTurn();
		
		//les ennemis choisissent leur premiere action
		enemies.forEach(Enemy::chooseNextActions);
		
	}
	
	/**
	 * Utilise un objet pendant le tour du héros.
	 *
	 * @param item l'objet utilisé
	 */
	public void useItem(Item item) {
		if (currentPhase != Phase.HERO_TURN || enemies.isEmpty()) return;
		
		if (!hero.consumeEnergy(item.cost())) {
			logs = "Pas assez d'énergie !";
			return;
		}

		applyItemEffect(item, enemies.get(0));
		checkDeaths();
	}
	private void applyItemEffect(Item item, Enemy target) {
		switch (item) {
			case Magic m -> {
				int damage = m.damage();
				target.takeDamage(damage);
				logs = "Attaque : " + damage + " dégâts !";
			}
		case MeleeWeapon mW -> {
				int damage = mW.damage();
				target.takeDamage(damage);
				logs = "Attaque : " + damage + " dégâts !";
			}
		case RangeWeapon rW -> {
				int damage = rW.damage();
				target.takeDamage(damage);
				logs = "Attaque : " + damage + " dégâts !";
				break;
			}
		case Shield s -> {
				int protection = s.protection();
				hero.addProtection(protection);
				logs = "Défense :  " + protection + "  armure.";
				break;
			}
		default -> logs = "Objet inutilisable";

		}
	}
	
	private void checkDeaths() {
		Iterator<Enemy> it = enemies.iterator();
		while (it.hasNext()) {
			Enemy e = it.next();
			if (e.isDead()) {
				int xp = e.getExperienceReward();
				hero.addExperience(xp);
				logs = e.getName() + " vaincu ! (+" + xp + " XP)";
				it.remove(); 
			}
		}
		if (enemies.isEmpty()) {
			currentPhase = Phase.VICTORY;
			logs = "Victoire ! Combat terminé.";
		}
	}
	
	/**
	 * Termine le tour du héros et déclenche l'exécution des actions ennemies.
	 */
	public void endHeroTurn() {
		if (currentPhase != Phase.HERO_TURN) {
			throw new IllegalStateException("ce n'est pas le tour de hero");
		}
		currentPhase = Phase.ENEMY_TURN;
		executeEnemiesTurn();
	}
	
	private void executeEnemiesTurn() {
		for(var enemy : enemies) {
			if (!enemy.isDead()) {
				enemy.resetProtection();
				enemy.executeActions(hero);
			}
		}
		if (hero.isDead()) {
			currentPhase = Phase.DEFEAT;
			return;
		} else {
			prepareNextTurn();
		}
	}
	
	private void prepareNextTurn() {
		currentPhase = Phase.HERO_TURN;
		turnNumber++;
		hero.startNewTurn();
		enemies.forEach(Enemy::chooseNextActions);
		logs = "À vous de jouer !";
	}
	
	//GETTERS
	
	/**
	 * Retourne le héros impliqué dans ce combat.
	 *
	 * @return l'objet Hero
	 */
	public Hero getHero() {
		return hero;
	}
	
	/**
	 * Retourne une copie de la liste d'ennemis.
	 *
	 * @return liste d'ennemis
	 */
	public List<Enemy> getEnemies(){
		return new ArrayList<>(enemies);
	}
	
	/**
	 * Retourne la phase actuelle du combat.
	 *
	 * @return la phase courante
	 */
	public Phase getCurrentPhase() {
		return currentPhase;
	}
	
	/**
	 * Retourne le numéro de tour courant.
	 *
	 * @return numéro du tour
	 */
	public int getTurnNumber() {
		return turnNumber;
	}
	
	/**
	 * Indique si le combat est terminé (victoire ou défaite).
	 *
	 * @return true si le combat est terminé
	 */
	public boolean isOver() {
		return currentPhase.equals(Phase.VICTORY) || currentPhase.equals(Phase.DEFEAT);
	}
	
	/**
	 * Retourne les logs texte du combat.
	 *
	 * @return chaîne des logs
	 */
	public String getLogs() {
		return logs;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("=== COMBAT - Turn ").append(turnNumber).append("==\n");
		sb.append("\nEnemies :\n");
		for (var enemy : enemies) {
			sb.append(" ").append(enemy).append("\n");
		}
		sb.append("Phase: ").append(currentPhase);
		return sb.toString();
	}
}

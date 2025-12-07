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
	
	public Hero getHero() {
		return hero;
	}
	
	public List<Enemy> getEnemies(){
		return new ArrayList<>(enemies);
	}
	
	public Phase getCurrentPhase() {
		return currentPhase;
	}
	
	public int getTurnNumber() {
		return turnNumber;
	}
	
	public boolean isOver() {
		return currentPhase.equals(Phase.VICTORY) || currentPhase.equals(Phase.DEFEAT);
	}
	
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

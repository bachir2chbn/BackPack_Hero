package model.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import model.hero.Hero;

public class EnemyData {
	private final String name;
	private final EnemyType type;
	private final int maxHp;
	private int hp;
	private int protection;
	private final int baseAttackDamage;
	private final int baseDefenseValue;
	private final int experienceReward;
	private final List<Action> nextActions;
	private final Random random;

	public EnemyData(String name, EnemyType type, int maxHp, int attackDamage, int defenseValue, int xpReward) {
		this.name = Objects.requireNonNull(name);
		this.type = Objects.requireNonNull(type);
		this.maxHp = maxHp;
		this.hp = maxHp;
		this.protection = 0;
		this.baseAttackDamage = attackDamage;
		this.baseDefenseValue = defenseValue;
		this.experienceReward = xpReward;
		this.nextActions = new ArrayList<>();
		this.random = new Random();
	}

	// Getters
	public String getName() {
		return name;
	}

	public EnemyType getType() {
		return type;
	}

	public int getHp() {
		return hp;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public int getProtection() {
		return protection;
	}

	public List<Action> getNextActions() {
		return new ArrayList<>(nextActions);
	}

	public int getExperienceReward() {
		return experienceReward;
	}

	public int getBaseAttackDamage() {
		return baseAttackDamage;
	}

	public int getBaseDefenseValue() {
		return baseDefenseValue;
	}

	public Random getRandom() {
		return random;
	}

	public boolean isDead() {
		return hp <= 0;
	}

	// Modifications
	public void takeDamage(int damage) {
		if (damage < 0)
			throw new IllegalArgumentException("Damage cannot be negative");
		int remainingDamage = damage - protection;
		protection = Math.max(0, protection - damage);
		if (remainingDamage > 0) {
			hp = Math.max(0, hp - remainingDamage);
		}
	}

	public void heal(int amount) {
		if (amount < 0)
			throw new IllegalArgumentException("Heal cannot be negative");
		hp = Math.min(maxHp, hp + amount);
	}

	public void addProtection(int amount) {
		if (amount < 0)
			throw new IllegalArgumentException("Protection cannot be negative");
		protection += amount;
	}

	public void resetProtection() {
		protection = 0;
	}

	public void clearActions() {
		nextActions.clear();
	}

	public void addAction(Action action) {
		nextActions.add(action);
	}

	// Actions de combat
	public void attack(Hero hero, int damage) {
		hero.takeDamage(damage);
	}

	public void defend(int defenseValue) {
		addProtection(defenseValue);
	}

	@Override
	public String toString() {
		return String.format("%s [%s] HP: %d/%d | Prot: %d", name, type, hp, maxHp, protection);
	}
}

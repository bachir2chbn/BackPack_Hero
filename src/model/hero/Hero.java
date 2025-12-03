package model.hero;

public class Hero {
	private final Backpack backpack;
	private int hp;
	private int maxHp;
	private int energy;
	private int mana;
	private int maxMana;
	private int gold;
	private int protection;
	private int experience;
	private int level;

	public Hero(int maxHp) {
		this.maxHp = maxHp;
		this.hp = maxHp;
		this.energy = 5;
		this.mana = 0;
		this.gold = 0;
		this.protection = 0;
		this.experience = 0;
		this.level = 1;
		this.backpack = new Backpack();
	}

	public int getHp() {
		return hp;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public int getEnergy() {
		return energy;
	}

	public int getMana() {
		return mana;
	}

	public int getMaxMana() {
		return maxMana;
	}

	public int getGold() {
		return gold;
	}

	public int getProtection() {
		return protection;
	}

	public int getExperience() {
		return experience;
	}

	public int getLevel() {
		return level;
	}

	public Backpack getBackpack() {
		return backpack;
	}

	public boolean isDead() {
		return hp <= 0;
	}

	public void takeDamage(int damage) {
		if (damage < 0) {
			throw new IllegalArgumentException("Damage cannot be negative");
		}
		int remainingDamage = damage - protection;
		protection = Math.max(0, protection - damage);

		if (remainingDamage > 0) {
			hp = Math.max(0, hp - remainingDamage);
		}

	}

	public void heal(int amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Heal amount cannot be negative");
		}
		hp = Math.min(maxHp, hp + amount);

	}

	public void increaseMaxHp(int amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Heal amount cannot be negative");
		}
		maxHp += amount;
	}

	public void consumeEnergy(int amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Heal amount cannot be negative");
		}
		if (amount > energy) {
			throw new IllegalArgumentException("Not enougp energy");
		}
		energy -= amount;
	}

	public void addEnergy(int amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Heal amount cannot be negative");
		}
		energy += amount;
	}

	public void consumeMana(int amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Heal amount cannot be negative");
		}
		if (amount > mana) {
			throw new IllegalArgumentException("Not enough mana");
		}

		mana -= amount;
	}

	public void increaseMaxMana(int amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Heal amount cannot be negative");
		}
		maxMana += amount;
		mana = maxMana;
	}

	public void addGold(int amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Heal amount cannot be negative");
		}
		gold += amount;
	}

	public boolean spendGold(int amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Heal amount cannot be negative");
		}
		if (amount > gold) {
			return false;
		}
		gold -= amount;
		return true;
	}

	public void addProtection(int amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Heal amount cannot be negative");
		}
		protection += amount;
	}

	public void addExperience(int amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Heal amount cannot be negative");
		}
		experience += amount;
	}

	public void startNewTurn() {
		energy = 0;
		protection = 0;
	}

	public void rechargeMana() {
		mana = maxMana;
	}

	@Override
	public String toString() {
		return String.format("Hero [HP: %d/%d | Energy: %d | Mana: %d/%d | Gold: %d | Protection: %d]", hp, maxHp, energy,
		    mana, maxMana, gold, protection);
	}
}

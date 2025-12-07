package model.hero;

public class Hero {
	private final Backpack backpack;
	
	private int hp;
	private int maxHp;
	
	private int energy;
	private int maxEnergy = 3;
	
	private int mana;
	private int maxMana;
	
	private int gold;
	private int protection;
	private int experience;
	private int level;

	public Hero(int maxHp) {
		this.backpack = new Backpack();
		
		this.maxHp = maxHp;
		this.hp = maxHp;
		
		this.energy = maxEnergy;
		
		this.mana = 0;
		this.gold = 0;
		this.protection = 0;
		this.experience = 0;
		this.level = 1;
	}
	
	public void startNewTurn() {
		this.energy = maxEnergy;
		this.protection = 0;
	}

	public void takeDamage(int damage) {
		if (damage < 0) {
			throw new IllegalArgumentException("Damage cannot be negative");
		}
		int remainingDamage = damage - protection;
		protection = Math.max(0, protection - damage);

		if (remainingDamage > 0) hp = Math.max(0, hp - remainingDamage);
	}

	public void increaseMaxHp(int amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Heal amount cannot be negative");
		}
		maxHp += amount;
	}

	public boolean consumeEnergy(int cost) {
		if (cost < 0) throw new IllegalArgumentException("Heal amount cannot be negative");
		if (cost <= energy) {
			energy -= cost;
			return true;
		}
		return false;
	}
	
	public void addEnergy(int amount) {
		if (amount < 0) throw new IllegalArgumentException("Heal amount cannot be negative");
		energy += amount;
	}
	
	public void heal(int amount) {
		if (amount < 0) throw new IllegalArgumentException("Heal amount cannot be negative");
		hp = Math.min(maxHp, hp + amount);
	}
	
	public void addProtection(int amount) {
		if (amount < 0) throw new IllegalArgumentException("Heal amount cannot be negative");
		protection += amount;
	}

	public void consumeMana(int cost) {
		if (cost < 0) {
			throw new IllegalArgumentException("Heal amount cannot be negative");
		}
		if (cost > mana) {
			throw new IllegalArgumentException("Not enough mana");
		}

		mana -= cost;
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



	public void addExperience(int amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Heal amount cannot be negative");
		}
		experience += amount;
	}



	public void rechargeMana() {
		mana = maxMana;
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

	@Override
	public String toString() {
		return String.format("Hero [HP: %d/%d | Energy: %d | Mana: %d/%d | Gold: %d | Protection: %d]", hp, maxHp, energy,
		    mana, maxMana, gold, protection);
	}
}

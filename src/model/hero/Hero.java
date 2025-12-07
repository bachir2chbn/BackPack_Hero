package model.hero;

/**
 * Représente le héros : statistiques, ressources (mana, énergie, or),
 * protection et inventaire (Backpack).
 *
 * @author bachir2chbn
 * @author Mohammed442a
 * @version 1.0
 */
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

	/**
	 * Crée un héros avec un maximum de points de vie donné.
	 *
	 * @param maxHp points de vie maximum initial
	 */
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

	/**
	 * Prépare l'état du héros au début d'un nouveau tour : réinitialise l'énergie
	 * et la protection temporaire.
	 */
	public void startNewTurn() {
		this.energy = maxEnergy;
		this.protection = 0;
	}

	/**
	 * Applique des dégâts au héros en prenant en compte la protection.
	 *
	 * @param damage quantité de dégâts entrants (non négative)
	 */
	public void takeDamage(int damage) {
		if (damage < 0) {
			throw new IllegalArgumentException("Damage cannot be negative");
		}
		int remainingDamage = damage - protection;
		protection = Math.max(0, protection - damage);

		if (remainingDamage > 0) hp = Math.max(0, hp - remainingDamage);
	}

	/**
	 * Augmente la vie maximale du héros.
	 *
	 * @param amount valeur à ajouter à maxHp (doit être positive)
	 */
	public void increaseMaxHp(int amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Heal amount cannot be negative");
		}
		maxHp += amount;
	}

	/**
	 * Consomme de l'énergie si disponible.
	 *
	 * @param cost coût en énergie
	 * @return true si l'énergie a été consommée, false si pas assez d'énergie
	 */
	public boolean consumeEnergy(int cost) {
		if (cost < 0) throw new IllegalArgumentException("Heal amount cannot be negative");
		if (cost <= energy) {
			energy -= cost;
			return true;
		}
		return false;
	}

	/**
	 * Ajoute de l'énergie au héros.
	 *
	 * @param amount quantité d'énergie à ajouter
	 */
	public void addEnergy(int amount) {
		if (amount < 0) throw new IllegalArgumentException("Heal amount cannot be negative");
		energy += amount;
	}

	/**
	 * Soigne le héros.
	 *
	 * @param amount quantité de points de vie à restaurer
	 */
	public void heal(int amount) {
		if (amount < 0) throw new IllegalArgumentException("Heal amount cannot be negative");
		hp = Math.min(maxHp, hp + amount);
	}

	/**
	 * Ajoute une protection temporaire (réduit les dégâts entrants).
	 *
	 * @param amount valeur de protection à ajouter
	 */
	public void addProtection(int amount) {
		if (amount < 0) throw new IllegalArgumentException("Heal amount cannot be negative");
		protection += amount;
	}

	/**
	 * Consomme du mana.
	 *
	 * @param cost coût en mana
	 */
	public void consumeMana(int cost) {
		if (cost < 0) {
			throw new IllegalArgumentException("Heal amount cannot be negative");
		}
		if (cost > mana) {
			throw new IllegalArgumentException("Not enough mana");
		}

		mana -= cost;
	}

	/**
	 * Augmente le mana maximum et recharge le mana courant à la nouvelle valeur.
	 *
	 * @param amount augmentation du mana maximum
	 */
	public void increaseMaxMana(int amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Heal amount cannot be negative");
		}
		maxMana += amount;
		mana = maxMana;
	}

	/**
	 * Ajoute de l'or au héros.
	 *
	 * @param amount quantité d'or à ajouter
	 */
	public void addGold(int amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Heal amount cannot be negative");
		}
		gold += amount;
	}

	/**
	 * Dépense de l'or si disponible.
	 *
	 * @param amount montant à dépenser
	 * @return true si le paiement a été effectué
	 */
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

	/**
	 * Ajoute de l'expérience au héros.
	 *
	 * @param amount quantité d'expérience à ajouter
	 */
	public void addExperience(int amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Heal amount cannot be negative");
		}
		experience += amount;
	}

	/**
	 * Recharge le mana du héros au maximum.
	 */
	public void rechargeMana() {
		mana = maxMana;
	}

	// --- Getters ---

	/**
	 * @return points de vie actuels
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * @return points de vie maximum
	 */
	public int getMaxHp() {
		return maxHp;
	}

	/**
	 * @return énergie courante
	 */
	public int getEnergy() {
		return energy;
	}

	/**
	 * @return mana courant
	 */
	public int getMana() {
		return mana;
	}

	/**
	 * @return mana maximum
	 */
	public int getMaxMana() {
		return maxMana;
	}

	/**
	 * @return quantité d'or
	 */
	public int getGold() {
		return gold;
	}

	/**
	 * @return protection temporaire actuelle
	 */
	public int getProtection() {
		return protection;
	}

	/**
	 * @return expérience accumulée
	 */
	public int getExperience() {
		return experience;
	}

	/**
	 * @return niveau du héros
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @return le sac à dos du héros
	 */
	public Backpack getBackpack() {
		return backpack;
	}

	/**
	 * Indique si le héros est mort.
	 *
	 * @return true si les PV sont à zéro ou moins
	 */
	public boolean isDead() {
		return hp <= 0;
	}

	@Override
	public String toString() {
		return String.format("Hero [HP: %d/%d | Energy: %d | Mana: %d/%d | Gold: %d | Protection: %d]", hp, maxHp, energy,
				mana, maxMana, gold, protection);
	}
}

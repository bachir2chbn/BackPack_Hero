package model.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import model.hero.Hero;

/**
 * Implémentation « data » réutilisable pour représenter un ennemi simple :
 * stocke nom, type, PV, protection, valeurs d'attaque/défense, actions prochaines,
 * et fournit des opérations pour infliger/soigner des dégâts, défendre et attaquer.
 *
 * Ce type est utile comme base pour des ennemis concrets ou pour des tests.
 *
 * @author bachir2chbn
 * @author Mohammed442a
 * @version 1.0
 */
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

  /**
   * Crée une instance d'EnemyData avec les valeurs de base.
   *
   * @param name nom de l'ennemi
   * @param type type de l'ennemi (EnemyType)
   * @param maxHp points de vie maximum
   * @param attackDamage valeur d'attaque de base
   * @param defenseValue valeur de défense de base (protection ajoutée lors d'une défense)
   * @param xpReward récompense en expérience quand l'ennemi est vaincu
   */
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

  /**
   * @return nom de l'ennemi
   */
  public String getName() {
    return name;
  }

  /**
   * @return type de l'ennemi
   */
  public EnemyType getType() {
    return type;
  }

  /**
   * @return PV courants
   */
  public int getHp() {
    return hp;
  }

  /**
   * @return PV maximum
   */
  public int getMaxHp() {
    return maxHp;
  }

  /**
   * @return protection actuelle
   */
  public int getProtection() {
    return protection;
  }

  /**
   * Retourne une copie de la liste des actions prévues.
   *
   * @return liste des actions prochaines
   */
  public List<Action> getNextActions() {
    return new ArrayList<>(nextActions);
  }

  /**
   * @return récompense en expérience
   */
  public int getExperienceReward() {
    return experienceReward;
  }

  /**
   * @return dommage d'attaque de base
   */
  public int getBaseAttackDamage() {
    return baseAttackDamage;
  }

  /**
   * @return valeur de défense de base
   */
  public int getBaseDefenseValue() {
    return baseDefenseValue;
  }

  /**
   * @return générateur de nombres aléatoires utilisé par cet ennemi
   */
  public Random getRandom() {
    return random;
  }

  /**
   * Indique si l'ennemi est mort.
   *
   * @return true si hp ≤ 0
   */
  public boolean isDead() {
    return hp <= 0;
  }

  // Modifications

  /**
   * Applique des dégâts à l'ennemi en tenant compte de sa protection.
   *
   * @param damage quantité de dégâts entrants
   */
  public void takeDamage(int damage) {
    if (damage < 0)
      throw new IllegalArgumentException("Damage cannot be negative");
    int remainingDamage = damage - protection;
    protection = Math.max(0, protection - damage);
    if (remainingDamage > 0) {
      hp = Math.max(0, hp - remainingDamage);
    }
  }

  /**
   * Soigne l'ennemi sans dépasser son maximum.
   *
   * @param amount quantité de vie à restaurer
   */
  public void heal(int amount) {
    if (amount < 0)
      throw new IllegalArgumentException("Heal cannot be negative");
    hp = Math.min(maxHp, hp + amount);
  }

  /**
   * Ajoute de la protection temporaire (blocage).
   *
   * @param amount valeur de protection à ajouter
   */
  public void addProtection(int amount) {
    if (amount < 0)
      throw new IllegalArgumentException("Protection cannot be negative");
    protection += amount;
  }

  /**
   * Réinitialise la protection à zéro.
   */
  public void resetProtection() {
    protection = 0;
  }

  /**
   * Vide la liste des actions prévues.
   */
  public void clearActions() {
    nextActions.clear();
  }

  /**
   * Ajoute une action prévue à la liste.
   *
   * @param action action à ajouter
   */
  public void addAction(Action action) {
    nextActions.add(action);
  }

  // Actions de combat

  /**
   * Attaque le héros en lui infligeant des dégâts.
   *
   * @param hero cible de l'attaque
   * @param damage quantité de dégâts infligés
   */
  public void attack(Hero hero, int damage) {
    hero.takeDamage(damage);
  }

  /**
   * Défend en ajoutant de la protection selon la valeur fournie.
   *
   * @param defenseValue valeur de défense à appliquer
   */
  public void defend(int defenseValue) {
    addProtection(defenseValue);
  }

  @Override
  public String toString() {
    return String.format("%s [%s] HP: %d/%d | Prot: %d", name, type, hp, maxHp, protection);
  }
}

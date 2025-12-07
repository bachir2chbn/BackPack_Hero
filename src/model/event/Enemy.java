package model.event;

import java.util.List;

import model.hero.Hero;

/**
 * Interface représentant un ennemi dans un combat.
 * Définit les opérations essentielles qu'un ennemi doit implémenter :
 * accès aux statistiques, gestion des dégâts/protection, IA (choix et exécution
 * d'actions) et interaction avec le héros.
 *
 * @author bachir2chbn
 * @author Mohammed442a
 * @version 1.0
 */
public interface Enemy {

  /**
   * Retourne le nom affichable de l'ennemi.
   *
   * @return nom de l'ennemi
   */
  String getName();

  /**
   * Retourne le type de l'ennemi (classification).
   *
   * @return type d'ennemi
   */
  EnemyType getType();

  /**
   * Retourne les points de vie actuels de l'ennemi.
   *
   * @return PV courants
   */
  int getHp();

  /**
   * Retourne le maximum de points de vie de l'ennemi.
   *
   * @return PV maximum
   */
  int getMaxHp();

  /**
   * Retourne la valeur de protection actuelle de l'ennemi (blocage temporaire).
   *
   * @return valeur de protection
   */
  int getProtection();

  /**
   * Retourne la liste des actions que l'ennemi prévoit d'exécuter au prochain tour.
   *
   * @return liste des actions prévues
   */
  List<Action> getNextActions();

  /**
   * Retourne la quantité d'expérience accordée au héros si cet ennemi est vaincu.
   *
   * @return récompense en XP
   */
  int getExperienceReward();

  /**
   * Indique si l'ennemi est mort (PV ≤ 0).
   *
   * @return true si l'ennemi est mort
   */
  boolean isDead();

  // Actions sur l'ennemi

  /**
   * Applique des dégâts à l'ennemi en tenant compte de sa protection.
   *
   * @param damage quantité de dégâts à appliquer
   */
  void takeDamage(int damage);

  /**
   * Réinitialise la protection temporaire de l'ennemi (par ex. après un tour).
   */
  void resetProtection();

  // IA de l'ennemi

  /**
   * Calcule et choisit les actions que l'ennemi exécutera lors de son prochain tour.
   * Cette méthode met à jour l'état interne des actions prévues.
   */
  void chooseNextActions();

  /**
   * Exécute les actions choisies sur le héros (application des effets).
   *
   * @param hero instance du héros cible des actions
   */
  void executeActions(Hero hero);
}

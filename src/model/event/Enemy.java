package model.event;

import java.util.List;

import model.hero.Hero;

public interface Enemy {

//Getters
  String getName();
  EnemyType getType();
  int getHp();
  int getMaxHp();
  int getProtection();
  List<Action> getNextActions();
  int getExperienceReward();
  boolean isDead();
  
  // Actions sur l'ennemi
  void takeDamage(int damage);
  void resetProtection();
  
  // IA de l'ennemi
  void chooseNextActions();
  void executeActions(Hero hero);
}

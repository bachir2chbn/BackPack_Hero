package model.event;

import java.util.List;
import java.util.Objects;

import model.hero.Hero;

public class SmallRatWolf implements Enemy {
	private final EnemyData enemyData;
	
	public SmallRatWolf() {
		this.enemyData = new EnemyData("Small Rat-Wolf", EnemyType.BEAST, 15, 8, 5, 4);
	}

	@Override
	public String getName() { return enemyData.getName(); }

	@Override
	public EnemyType getType() { return enemyData.getType(); }

	@Override
	public int getHp() { return enemyData.getHp(); }

	@Override
	public int getMaxHp() { return enemyData.getMaxHp(); }

	@Override
	public int getProtection() { return enemyData.getProtection(); }

	@Override
	public List<Action> getNextActions() { return enemyData.getNextActions(); }

	@Override
	public int getExperienceReward() { return enemyData.getExperienceReward(); }

	@Override
	public boolean isDead() { return enemyData.isDead(); }

	@Override
	public void takeDamage(int damage) { enemyData.takeDamage(damage); }

	@Override
	public void resetProtection() { enemyData.resetProtection(); }

	@Override
	public void chooseNextActions() { 
		enemyData.clearActions();
		
		// 70% attack, 30% defense
		if (enemyData.getRandom().nextDouble() < 0.7) {
			enemyData.addAction(Action.ATTACK);
		}
		else {
			enemyData.addAction(Action.DEFEND);
		}
	}

	@Override
	public void executeActions(Hero hero) {
		Objects.requireNonNull(hero);
		if (isDead()) return;
		for (var action : getNextActions()) {
			switch (action) {
			case ATTACK -> enemyData.attack(hero, enemyData.getBaseAttackDamage());
			case  DEFEND -> enemyData.defend(enemyData.getBaseDefenseValue());
			}
		}
	}

	@Override
  public String toString() { return enemyData.toString(); }

}

package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import model.event.Combat;
import model.event.Enemy;

/**
 * Vue affichant l'interface de combat : ennemi, logs et bouton de fin de tour.
 * Ce record encapsule la position et la taille de la zone de combat.
 *
 * @author bachir2chbn
 * @author Mohammed442a
 * @version 1.0
 */
public record CombatView(int x, int y, int width, int height) {

	public void draw(Graphics2D graphics, Combat combat) {
		drawBackground(graphics);
		
		if (!combat.getEnemies().isEmpty()) {
			drawEnemy(graphics, combat.getEnemies().get(0));
		}

		drawLogs(graphics, combat.getLogs());
		drawEndTurnButton(graphics);
	}

	private void drawBackground(Graphics2D g) {
		g.setColor(new Color(0, 0, 0, 230));
		g.fillRect(x, y, width, height);
		g.setColor(Color.WHITE);
		g.drawRect(x, y, width, height);
	}

	private void drawEnemy(Graphics2D g, Enemy enemy) {
		int centerX = x + width / 2;
		int centerY = y + 80;

		// Carré Rouge pour un ennemi
		g.setColor(Color.RED);
		g.fillRect(centerX - 50, centerY, 100, 100);

		//Infos Texte
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString(enemy.getName(), centerX - 40, centerY - 10);
		
		String stats = String.format("PV: %d/%d | Block: %d",enemy.getHp(), enemy.getMaxHp(), enemy.getProtection());
		g.drawString(stats, centerX - 60, centerY + 130);

		g.setColor(Color.YELLOW);
		g.drawString("Intention: " + enemy.getNextActions(), centerX - 80, centerY - 40);
	}

	private void drawLogs(Graphics2D g, String logs) {
		g.setColor(Color.CYAN);
		g.setFont(new Font("Arial", Font.ITALIC, 16));
		g.drawString(logs, x + 20, y + height - 80);
	}

	private void drawEndTurnButton(Graphics2D g) {
		int btnX = x + width - 140;
		int btnY = y + height - 50;
		g.setColor(Color.GREEN);
		g.fillRect(btnX, btnY, 120, 40);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 14));
		g.drawString("FIN TOUR", btnX + 25, btnY + 25);
	}

	/**
	 * Indique si la position de la souris correspond au bouton Fin Tour.
	 *
	 * @param mouseX coordonnée X de la souris
	 * @param mouseY coordonnée Y de la souris
	 * @return true si le bouton est cliqué
	 */
	public boolean isEndTurnClicked(int mouseX, int mouseY) {
		int btnX = x + width - 140;
		int btnY = y + height - 50;
		return mouseX >= btnX && mouseX <= btnX + 120 &&
			   mouseY >= btnY && mouseY <= btnY + 40;
	}
}

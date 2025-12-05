package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import model.hero.Backpack;
import model.items.Item;

public record BackpackView(int xOrigin, int yOrigin, int width, int height, int squareSize) {

	public void draw(Graphics2D graphics, Backpack backpack) {
		graphics.setStroke(new BasicStroke(2));
		graphics.setColor(Color.DARK_GRAY);

		for (int row = 0; row < Backpack.getRows(); row++) {
			for (int col = 0; col < Backpack.getCols(); col++) {
				int px = xOrigin + col * squareSize;
				int py = yOrigin + row * squareSize;

				graphics.draw(new Rectangle2D.Float(px, py, squareSize, squareSize));
			}
		}
		for (var item : backpack.getItems()) {
			int[] pos = backpack.findItemPosition(item);

			if (pos != null) {
				drawItem(graphics, item, pos[0], pos[1]);
			}
		}
	}

	private void drawItem(Graphics2D graphics, Item item, int row, int col) {
		// Coordonnées de base
		int px = xOrigin + col * squareSize;
		int py = yOrigin + row * squareSize;
		boolean[][] shape = item.getShape();

		// Dessiner uniquement les cases pleines
		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[i].length; j++) {
				if (shape[i][j]) {
					drawSingleCell(graphics, px + j * squareSize, py + i * squareSize);
				}
			}
		}

		drawItemName(graphics, item, px, py);
	}

	private void drawSingleCell(Graphics2D graphics, int x, int y) {
		graphics.setColor(Color.BLACK);
		graphics.fill(new Rectangle2D.Float(x, y, squareSize, squareSize));

		graphics.setColor(Color.WHITE);
		graphics.draw(new Rectangle2D.Float(x, y, squareSize, squareSize));
	}

	private void drawItemName(Graphics2D graphics, Item item, int px, int py) {
		boolean[][] shape = item.getShape();
		int pixelW = shape[0].length * squareSize;
		int pixelH = shape.length * squareSize;

		String text = item.getDisplayName();
		graphics.setFont(new Font("Arial", Font.BOLD, 12));
		FontMetrics metrics = graphics.getFontMetrics();

		// Si texte trop long
		if (metrics.stringWidth(text) > pixelW) {
			text = text.substring(0, Math.min(text.length(), 4)) + ".";
		}

		int textX = px + (pixelW - metrics.stringWidth(text)) / 2;
		int textY = py + ((pixelH - metrics.getHeight()) / 2) + metrics.getAscent();

		// Ombre du texte pour la lisibilité si la diagonale est vide au milieu
		graphics.setColor(Color.BLACK);
		graphics.drawString(text, textX + 1, textY + 1);
		graphics.setColor(Color.WHITE);
		graphics.drawString(text, textX, textY);
		
	}

	public int[] getGridPosition(float mouseX, float mouseY) {
		int totalWidth = Backpack.getCols() * squareSize;
		int totalHeight = Backpack.getRows() * squareSize;

		if (mouseX < xOrigin || mouseX >= xOrigin + totalWidth || mouseY < yOrigin || mouseY >= yOrigin + totalHeight) { // Si clic en dehors
			return null;
		}

		// Gestion des index
		int col = (int) (mouseX - xOrigin) / squareSize;
		int row = (int) (mouseY - yOrigin) / squareSize;

		return new int[] { row, col };
	}

	public void drawFloatingItem(Graphics2D graphics, Item item, float x, float y) {
		boolean[][] shape = item.getShape();

		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[i].length; j++) {
				if (shape[i][j]) {
					// ici x et y sont les coordonnées de la souris
					float cellX = x + j * squareSize;
					float cellY = y + i * squareSize;

					// Optionnel (pour le kiff) objet translucide
					graphics.setColor(new Color(0, 0, 0, 180));
					graphics.fill(new Rectangle2D.Float(cellX, cellY, squareSize, squareSize));

					// Des bordures jaunes pour voir l'item selectionné
					graphics.setColor(Color.YELLOW);
					graphics.draw(new Rectangle2D.Float(cellX, cellY, squareSize, squareSize));
				}
			}
		}
		// On dessine le texte par dessus
		drawItemName(graphics, item, (int) x, (int) y);
	}

}
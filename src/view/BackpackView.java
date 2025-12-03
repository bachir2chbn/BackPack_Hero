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
		int px = xOrigin + col * squareSize;
		int py = yOrigin + row * squareSize;
		var shape = item.getShape();
		int itemHeight = shape.length;
		int itemWidth = shape[0].length;
		int pixelW = itemWidth * squareSize;
		int pixelH = itemHeight * squareSize;
		graphics.setColor(Color.BLACK);
		graphics.fill(new Rectangle2D.Float(px, py, pixelW, pixelH));//trace le rectange qui représente l'item
		graphics.setColor(Color.WHITE);
		var text = item.getDisplayName();
		var metrics = graphics.getFontMetrics();//pour centrer le texte
		int textX = px + (pixelW - metrics.stringWidth(text)) / 2;
		int textY = py + ((pixelH - metrics.getHeight()) / 2) + metrics.getAscent();

		graphics.drawString(text, textX, textY);
	}
	
	public int[] getGridPosition(float mouseX, float mouseY) {
    int totalWidth = Backpack.getCols() * squareSize; 
    int totalHeight = Backpack.getRows() * squareSize;
    
    if (mouseX < xOrigin || mouseX >= xOrigin + totalWidth || mouseY < yOrigin || mouseY >= yOrigin + totalHeight) { //Si clic en dehors 
        return null;
    }

    //Gestion des index
    int col = (int) (mouseX - xOrigin) / squareSize;
    int row = (int) (mouseY - yOrigin) / squareSize;

    return new int[]{row, col};
	}
	
	
	public void drawFloatingItem(Graphics2D graphics, Item item, float x, float y) {
    //Dimensions du sac
    int pixelW = item.getShape()[0].length * squareSize;
    int pixelH = item.getShape().length * squareSize;

    //Optionnel (pour le kiff) objet translucide
    graphics.setColor(new Color(0, 0, 0, 180));
    graphics.fill(new Rectangle2D.Float(x, y, pixelW, pixelH));

    //Des bordures jaunes pour voir l'item selectionné
    graphics.setColor(Color.YELLOW);
    graphics.draw(new Rectangle2D.Float(x, y, pixelW, pixelH));

    //Le nom de l'item
    String text = item.getDisplayName();
    graphics.setColor(Color.WHITE);
    graphics.drawString(text, x + 5, y + 20);
	}
	
}
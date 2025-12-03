package view;

import java.awt.Color;
import java.awt.Graphics2D;
import model.dungeon.GameData;
import model.items.Item;

public class GameView {

	private final GameData data;
	private final BackpackView backpackView;
	private Item draggedItem; 
	private float dragX, dragY;

	public void setDraggedItem(Item item, float x, float y) {
	    this.draggedItem = item;
	    this.dragX = x;
	    this.dragY = y;
	}

	public GameView(GameData data) {
		this.data = data;

		this.backpackView = new BackpackView(50, 100, 5, 3, 80);
	}

	public void draw(Graphics2D graphics) {
		// Fond d'écran
		graphics.setColor(new Color(30, 30, 30));
		graphics.fillRect(0, 0, 1920, 1080);

		if (data.getHero() != null) {
			backpackView.draw(graphics, data.getHero().getBackpack());
		}
		
		 if (draggedItem != null) {
       backpackView.drawFloatingItem(graphics, draggedItem, dragX, dragY);
   }
	}

	public BackpackView getBackpackView() {
    return backpackView;
	}
}
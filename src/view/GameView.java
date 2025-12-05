package view;

import java.awt.Color;
import java.awt.Graphics2D;
import model.dungeon.GameData;
import model.items.Item;

public class GameView {

	private final GameData data;
	private final BackpackView backpackView;
	private final FloorView floorView;
	
	private Item draggedItem; 
	private float dragX, dragY;

	public void setDraggedItem(Item item, float x, float y) {
	    this.draggedItem = item;
	    this.dragX = x;
	    this.dragY = y;
	}

	public GameView(GameData data) {
		this.data = data;

		this.backpackView = new BackpackView(50, 500, 5, 3, 80);
		
		this.floorView = new FloorView(50, 50, 80);
	}

	public void draw(Graphics2D graphics) {
		// Fond d'ecran
		graphics.setColor(new Color(30, 30, 30));
		graphics.fillRect(0, 0, 1920, 1080);

		//Floor
    if (data.getCurrentFloor() != null) {
    	graphics.setColor(Color.WHITE);
      graphics.drawString("Carte du Donjon", 50, 40);
      if (data.getCurrentFloor() != null) {
  			floorView.draw(graphics, data.getCurrentFloor(), data.getHeroX(), data.getHeroY());
  		}
    }
    
		//Backpack
		if (data.getHero() != null) {
			graphics.setColor(Color.WHITE);
      graphics.drawString("Sac à dos", 50, 490);
			backpackView.draw(graphics, data.getHero().getBackpack());
		}
		
		//Item choisi pour deplacer
		if (draggedItem != null) {
			backpackView.drawFloatingItem(graphics, draggedItem, dragX, dragY);
   }
	}

	public BackpackView getBackpackView() {
    return backpackView;
	}
	
	public FloorView getFloorView() {
    return floorView;
	}
}
package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Objects;

import model.dungeon.GameData;
import model.hero.Hero;
import model.items.Item;

public class GameView {

	private final GameData data;
	private BackpackView backpackView;
	private FloorView floorView;
	private CombatView combatView;
	
	private Item draggedItem; 
	private float dragX, dragY;
	
	private boolean showBackpack = false;
	
	private int currentWidth = 0;
  private int currentHeight = 0;
	
  private int uiX;      
  private int buttonY;  
  private int buttonW = 150;
  private int buttonH = 50;
  private int statsY;


	public GameView(GameData data) {
		Objects.requireNonNull(data);
		this.data = data;
		this.floorView = new FloorView(50, 50, 80);
	}
	
	public void setDraggedItem(Item item, float x, float y) {
		//On ne met pas de requireNonNull car null indique qu'il n'y a pas d'item selectioné
    this.draggedItem = item;
    this.dragX = x;
    this.dragY = y;
	}

	public void draw(Graphics2D g, int width, int height) {
		Objects.requireNonNull(g);
    if (width != currentWidth || height != currentHeight)
        updateLayout(width, height);

    drawBackground(g);
    drawMap(g);

    drawCombat(g);

    drawInterface(g);
    drawDraggedItem(g);
	}
	
	private void updateLayout(int w, int h) {
    this.currentWidth = w;
    this.currentHeight = h;
    recalculatePositions(w, h);
	}
	
	private void recalculatePositions(int w, int h) {
    //Calcul pour l'interface de Combat
    int cw = 800, ch = 600;
    int cx = Math.max(0, (w - cw) / 2);
    int cy = Math.max(0, (h - ch) / 2);
    this.combatView = new CombatView(cx, cy, cw, ch);

    //Calcul pos pour barre de vie, bouton et sac 
    this.uiX = Math.max(cx + cw + 20, w - 400);
    this.statsY = 50;
    this.buttonY = 120;
    
    //Creation de la vue sac
    this.backpackView = new BackpackView(uiX, buttonY + buttonH + 50, 5, 3, 80);
	}
	
	private void drawBackground(Graphics2D g) {
    g.setColor(new Color(30, 30, 30));
    g.fillRect(0, 0, currentWidth, currentHeight);
	}
	
	private void drawMap(Graphics2D g) {
    if (data.getCurrentFloor() != null) {
      g.setColor(Color.WHITE);
      g.drawString("Carte du Donjon", 50, 40);
      floorView.draw(g, data.getCurrentFloor(), data.getHeroPosition());
    }
	}
	
	private void drawCombat(Graphics2D g) {
    if (data.isFighting() && data.getCurrentCombat() != null) {
        combatView.draw(g, data.getCurrentCombat());
    }
	}
	
	private void drawInterface(Graphics2D g) {
    drawHeroStats(g);
    drawBackpackButton(g);
    if (data.getHero() != null && showBackpack) {
        g.setColor(Color.WHITE);
        g.drawString("Sac à dos", uiX, buttonY + buttonH + 40);
        backpackView.draw(g, data.getHero().getBackpack());
    }
	}
	
	private void drawDraggedItem(Graphics2D g) {
    if (draggedItem != null) {
        backpackView.drawFloatingItem(g, draggedItem, dragX, dragY);
    }
	}
	
	private void drawHeroStats(Graphics2D g) {
    Hero hero = data.getHero();
    if (hero == null) return;
    
    drawHealthBar(g, hero);
    drawShieldIcon(g, hero);
	}
	
	private void drawHealthBar(Graphics2D g, Hero hero) {
	    int w = 200, h = 30;
	    // Fond
	    g.setColor(new Color(50, 0, 0));
	    g.fillRect(uiX, statsY, w, h);
	    
	    // Jauge Rouge
	    float ratio = Math.max(0, (float) hero.getHp() / hero.getMaxHp());
	    g.setColor(Color.RED);
	    g.fillRect(uiX, statsY, (int)(w * ratio), h);
	    
	    // Contour et Texte
	    g.setColor(Color.WHITE);
	    g.drawRect(uiX, statsY, w, h);
	    g.setFont(new Font("Arial", Font.BOLD, 16));
	    g.drawString(hero.getHp() + " / " + hero.getMaxHp(), uiX + 70, statsY + 22);
	}
	
	private void drawShieldIcon(Graphics2D g, Hero hero) {
	    if (hero.getProtection() > 0) {
	        g.setColor(Color.CYAN);
	        g.setFont(new Font("Arial", Font.BOLD, 20));
	        g.drawString("🛡 " + hero.getProtection(), uiX + 215, statsY + 25);
	    }
	}
	
	private void drawBackpackButton(Graphics2D g) {
    g.setColor(showBackpack ? Color.GRAY : Color.ORANGE);
    g.fillRect(uiX, buttonY, buttonW, buttonH);
    
    g.setColor(Color.BLACK);
    g.drawRect(uiX, buttonY, buttonW, buttonH);
    
    g.setColor(Color.WHITE);
    g.setFont(new Font("Arial", Font.BOLD, 18));
    String text = showBackpack ? "FERMER" : "INVENTAIRE";
    g.drawString(text, uiX + 25, buttonY + 30);
	}
	
	public boolean isBackpackToggleButtonClicked(int x, int y) {
    return x >= uiX && x <= uiX + buttonW && y >= buttonY && y <= buttonY + buttonH;
	}
	
	public void toggleBackpack() {
    this.showBackpack = !this.showBackpack;
	}
	
	public boolean isBackpackOpen() {
    return showBackpack;
	}

	public BackpackView getBackpackView() {
    return backpackView;
	}
	
	public FloorView getFloorView() {
    return floorView;
	}
	
	public CombatView getCombatView() {
		return combatView;
	}
}

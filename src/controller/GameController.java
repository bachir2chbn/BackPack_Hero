package controller;

import java.awt.Color;
import java.awt.geom.Point2D;

import com.github.forax.zen.Application;
import com.github.forax.zen.ApplicationContext;
import com.github.forax.zen.Event;
import com.github.forax.zen.PointerEvent;
import com.github.forax.zen.KeyboardEvent;

import model.dungeon.GameData;
import model.items.Item;
import view.GameView;

public class GameController {

	private static Item draggedItem = null;
	private static int originalRow = -1;
	private static int originalCol = -1;
	private static Point2D.Float mousePosition = new Point2D.Float(0, 0);

	public static void main(String[] args) {
		GameData data = new GameData();
		GameView view = new GameView(data);

		Application.run(Color.BLACK, context -> gameLoop(context, data, view));
	}

	//La Boucle principale du Jeu 
	private static void gameLoop(ApplicationContext context, GameData data, GameView view) {
		while (true) {
			Event event = context.pollOrWaitEvent(10);
			if (event != null) {
				eventsHandler(event, data, view);
			}
			updateViewDragPosition(view);
			context.renderFrame(graphics -> view.draw(graphics));
		}
	}

	//La gestion des events
	private static void eventsHandler(Event event, GameData data, GameView view) {
		switch (event) {
			case PointerEvent e -> handlePointerEvent(e, data, view);
			case KeyboardEvent k -> handleKeyboardEvent(k);
			default -> {
			}
		}
	}

	//La gestion du clavier
	private static void handleKeyboardEvent(KeyboardEvent k) {
		if (k.action() == KeyboardEvent.Action.KEY_PRESSED && "R".equals(k.key().toString())) {
			if (draggedItem != null) {
				draggedItem = draggedItem.rotate();
				System.out.println("Item tourné !");
			}
		}
	}

	//Mise à jour de la vue (item qui suit la souris)
	private static void updateViewDragPosition(GameView view) {
		if (draggedItem != null) {
			view.setDraggedItem(draggedItem, mousePosition.x - 20, mousePosition.y - 20);
		} else {
			view.setDraggedItem(null, 0, 0);
		}
	}

	//Gestion des clics
	private static void handlePointerEvent(PointerEvent event, GameData data, GameView view) {
		int x = event.location().x();
		int y = event.location().y();
		mousePosition = new Point2D.Float(x, y);

		switch (event.action()) {
			case POINTER_DOWN -> handlePointerDown(x, y, data, view);
			case POINTER_UP -> handlePointerUp(x, y, data, view);
		}
	}

	//Choix de l'item à prendre
	private static void handlePointerDown(int x, int y, GameData data, GameView view) {
		var backpackView = view.getBackpackView();
		int[] gridPos = backpackView.getGridPosition(x, y);

		if (gridPos == null)
			return; // Clic hors grille

		var backpack = data.getHero().getBackpack();
		Item item = backpack.getItemAt(gridPos[0], gridPos[1]);

		if (item != null) {
			prepareDrag(item, backpack);
		}
	}

	//Initialisation de l'item choisi
	private static void prepareDrag(Item item, model.hero.Backpack backpack) {
		draggedItem = item;
		int[] truePos = backpack.findItemPosition(item);
		originalRow = truePos[0];
		originalCol = truePos[1];
		backpack.removeItem(item);
		System.out.println("Pris : " + item.getDisplayName());// Pour le debug, à retirer apres!!!!!
	}

	//Deposer l'item choisi
	private static void handlePointerUp(int x, int y, GameData data, GameView view) {
		if (draggedItem == null)
			return;

		var backpack = data.getHero().getBackpack();
		int[] gridPos = view.getBackpackView().getGridPosition(x, y);

		boolean success = (gridPos != null) && backpack.placeItem(draggedItem, gridPos[0], gridPos[1]);

		if (!success) {
			System.out.println("NOPE!!");// Pour le debug, à retirer apres!!!!!
			backpack.placeItem(draggedItem, originalRow, originalCol);
		} else {
			System.out.println("Placé en " + gridPos[0] + "," + gridPos[1]);// Pour le debug, à retirer apres!!!!!
		}
		draggedItem = null;
	}
}
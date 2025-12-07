package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

import model.dungeon.Floor;
import model.dungeon.Point;
import model.dungeon.Room;
import model.dungeon.RoomType;

public record FloorView(int xOrigin, int yOrigin, int roomSize) {

	public void draw(Graphics2D graphics, Floor floor, Point heroPos) {
		for (int x = 0; x < floor.getWidth(); x++) {
			for (int y = 0; y < floor.getHeight(); y++) {
				drawRoom(graphics, floor, x, y);
			}
		}
		
		//Dessiner le héros par dessus
		drawHeroMarker(graphics, heroPos.x(), heroPos.y());
	}
	
	private void drawHeroMarker(Graphics2D graphics, int x, int y) {
		int px = xOrigin + x * roomSize;
		int py = yOrigin + y * roomSize;
		
		//Un pion au centre de la case
		int padding = 10;
		graphics.setColor(Color.BLACK);
		graphics.fillOval(px + padding, py + padding, roomSize - 2*padding, roomSize - 2*padding);
		
		graphics.setColor(Color.WHITE);
		graphics.drawOval(px + padding, py + padding, roomSize - 2*padding, roomSize - 2*padding);
	}

	private void drawRoom(Graphics2D graphics, Floor floor, int x, int y) {
		Room room = floor.getRoom(x, y);
		if (room == null)
			return;

		int px = xOrigin + x * roomSize;
		int py = yOrigin + y * roomSize;

		Color color = getRoomColor(room.getType());

		// Carré qui représente la salle
		graphics.setColor(color);
		graphics.fill(new Rectangle2D.Float(px, py, roomSize - 2, roomSize - 2));

		// Des bordures pour que ça soit clean
		graphics.setColor(Color.BLACK);
		graphics.draw(new Rectangle2D.Float(px, py, roomSize - 2, roomSize - 2));

	}

	private Color getRoomColor(RoomType type) {
		return switch (type) {
		case ENEMY -> Color.RED;
		case TREASURE -> Color.YELLOW;
		case MERCHANT -> Color.ORANGE;
		case HEALER -> Color.GREEN;
		case EXIT -> Color.MAGENTA;
		case ENTRANCE -> Color.CYAN;
		case EMPTY -> Color.DARK_GRAY;
		default -> Color.BLACK;
		};
	}

	//Convertit un pixel en coordonnées (x, y)
	public Point getGridPosition(int mouseX, int mouseY) {
		int widthPx = 11 * roomSize; // 11 et 5 pour la taille, pour l'instant codé en dur
		int heightPx = 5 * roomSize;

		if (mouseX < xOrigin || mouseX >= xOrigin + widthPx || mouseY < yOrigin || mouseY >= yOrigin + heightPx) {
			return null;
		}

		//Calcul des indices
		int col = (mouseX - xOrigin) / roomSize;
		int row = (mouseY - yOrigin) / roomSize;

		return new Point(col, row);
	}

}

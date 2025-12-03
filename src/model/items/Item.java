package model.items;

public interface Item {

	Item setShape(boolean[][] rotated);

	long id();

	Rarity rarity();

	int cost();

	boolean[][] getShape();

	default String getDisplayName() {
		return this.toString();
	}

	default Item rotate() {
		boolean[][] original = getShape();
		int rows = original.length;
		int cols = original[0].length;

		boolean[][] rotated = new boolean[cols][rows];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				rotated[j][rows - 1 - i] = original[i][j];
			}
		}

		return setShape(rotated);
	}

}

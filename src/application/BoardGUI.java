package application;

import application.gameoflife.LifeGrid;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoardGUI {
	public static int TILE_DIME = 10;
	public static int TILE_SPACING = 2;
	private TilePane tilePane = new TilePane(TILE_SPACING, TILE_SPACING);

	private int rowLength;

	public BoardGUI(LifeGrid grid) {
		rowLength = grid.getWidth();
		tilePane.setPrefRows(grid.getHeight());
		tilePane.setPrefColumns(grid.getWidth());

		for (int j = 0; j < grid.getHeight(); j++) {
			int[] row = grid.row(j);
			for (int i = 0; i < grid.getWidth(); i++) {
				Rectangle r = new Rectangle(TILE_DIME, TILE_DIME, getRectangleColor(row[i]));
				r.setId(j + ":" + i);

				r.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						String id = r.getId();
						String[] locStringArray = id.split(":");
						grid.toggleCell(Integer.parseInt(locStringArray[1]), Integer.parseInt(locStringArray[0]));
						r.setFill(getRectangleColor(1));
					}
				});
				tilePane.getChildren().add(r);
			}
		}
	}

	public void updateBoard(int[][] gridUpdate) {
		for (int j = 0; j < gridUpdate.length; j++) {
			int[] row = gridUpdate[j];
			for (int i = 0; i < gridUpdate.length; i++) {
				Rectangle r = (Rectangle) tilePane.getChildren().get(((rowLength * j) + i));
				r.setFill(getRectangleColor(row[i]));
			}
		}
	}

	Color getRectangleColor(int i) {
		switch (i) {
		case 0:
			return Color.WHITESMOKE;
		case 1:
			return Color.RED;
		case 2:
			return Color.DARKRED;
		default:
			return Color.WHITE;
		}
	}

	public TilePane getTilePane() {
		return tilePane;
	}

}

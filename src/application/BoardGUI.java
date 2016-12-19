package application;

import application.gameoflife.LifeGrid;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoardGUI {

	public static int DEFAULT_TILE_DIME = 5;
	public static int DEFAULT_TILE_SPACING = 2;

	
	private GridPane gridPane = new GridPane();
	
	private LifeGrid grid;
	private int width;
	private int height;

	private double tileSpacingHeight = DEFAULT_TILE_SPACING;
	private double tileSpacingWidth = DEFAULT_TILE_SPACING;


	private double tileWidth = DEFAULT_TILE_DIME;
	private double tileHeight = DEFAULT_TILE_DIME;

	public BoardGUI(LifeGrid grid, int spacing) {
		this(grid);
		this.tileSpacingHeight = spacing;
		this.tileSpacingWidth = spacing;

	}
	
	public BoardGUI(LifeGrid grid) {
		this.grid = grid;
		
		width = grid.getWidth();
		height = grid.getHeight();
		for (int j = 0; j < grid.getHeight(); j++) {
			int[] row = grid.row(j);
			for (int i = 0; i < grid.getWidth(); i++) {
				Rectangle r = new Rectangle(tileWidth, tileHeight, getRectangleColor(row[i])); 
				r.setOnMouseEntered(new CellClickHandler(i, j, false));
				r.setOnMouseClicked(new CellClickHandler(i, j, true));
				gridPane.add(r, i, j);
				GridPane.setMargin(r, new Insets(tileSpacingWidth, tileSpacingWidth, tileSpacingHeight, tileSpacingWidth));
			}
		}
	}

	public void updateBoard(int[][] gridUpdate) {
		for (int j = 0; j < gridUpdate.length; j++) {
			for (int i = 0; i < gridUpdate[j].length; i++) {
				if(gridUpdate[j][i] != 3) {
					Rectangle r = (Rectangle) gridPane.getChildren().get(((width * j) + i));
					Color cl = getRectangleColor(gridUpdate[j][i]);
					r.setFill(cl);
				}
			}
		}
	}
	public void updateCellSize() {
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				Rectangle r = (Rectangle) gridPane.getChildren().get(((width * j) + i));
				r.setWidth(tileWidth);
				r.setHeight(tileHeight);
				GridPane.setMargin(r, new Insets(tileSpacingWidth, tileSpacingWidth, tileSpacingHeight, tileSpacingWidth));
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

	public GridPane getGridPane() {
		gridPane.getStyleClass().add("tilePane");
		return gridPane;
	}

	public double getTileWidth() {
		return tileWidth;
	}

	public double getTileHeight() {
		return tileHeight;
	}

	public void setTileWidth(double tileWidth) {
		this.tileWidth = tileWidth;
		updateCellSize();
	}

	public void setTileHeight(double tileHeight) {
		this.tileHeight = tileHeight;
		updateCellSize();
	}

	public double getTileSpacingHeight() {
		return tileSpacingHeight;
	}

	public double getTileSpacingWidth() {
		return tileSpacingWidth;
	}

	public void setTileSpacingHeight(double d) {
		this.tileSpacingHeight = d;
		updateCellSize();
	}

	public void setTileSpacingWidth(double d) {
		this.tileSpacingWidth = d;
		updateCellSize();
	}
	
	private class CellClickHandler implements EventHandler<MouseEvent> {
		int x;
		int y;
		boolean alwaysEnable;
		
		public CellClickHandler(int x, int y, boolean alwaysEnable) {
			this.x = x;
			this.y = y;
			this.alwaysEnable = alwaysEnable;
		}
		
		@Override
		public void handle(MouseEvent event) {
			if(event.isShiftDown()||alwaysEnable){
				int f = grid.toggleCell(x, y);
				Rectangle r = (Rectangle) gridPane.getChildren().get(((width * y) + x));
				Color cl = getRectangleColor(f);
				r.setFill(cl);
			}
		}
	}
}

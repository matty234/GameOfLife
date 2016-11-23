package application;

import application.gameoflife.LifeGrid;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoardGUI {

	static int DEFAULT_TILE_DIME = 5;
	static int DEFAULT_TILE_SPACING = 2;

	
	private GridPane gridPane = new GridPane();
	private int rowLength;
	private int columnLength;

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
		rowLength = grid.getWidth();
		columnLength = grid.getHeight();
		for (int j = 0; j < grid.getHeight(); j++) {
			int[] row = grid.row(j);
			for (int i = 0; i < grid.getWidth(); i++) {
				Rectangle r = new Rectangle(tileWidth, tileHeight, getRectangleColor(row[i]));
			
				r.setId(j + ":" + i);
 
				r.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						String id = r.getId();
						String[] locStringArray = id.split(":");
						int v = grid.toggleCell(Integer.parseInt(locStringArray[1]), Integer.parseInt(locStringArray[0]));
						r.setFill(getRectangleColor(v));
					}
				});
				
				gridPane.add(r, i, j);
				GridPane.setMargin(r, new Insets(tileSpacingWidth, tileSpacingWidth, tileSpacingHeight, tileSpacingWidth));
			}
		}
	}

	public void updateBoard(int[][] gridUpdate) {
		for (int j = 0; j < gridUpdate.length; j++) {
			for (int i = 0; i < gridUpdate[j].length; i++) {
				if(gridUpdate[j][i] != 3) {
					Rectangle r = (Rectangle) gridPane.getChildren().get(((rowLength * j) + i));
					Color cl = getRectangleColor(gridUpdate[j][i]);
					r.setFill(cl);
				}
			}
		}
	}
	public void updateCellSize() {
		for (int j = 0; j < columnLength; j++) {
			for (int i = 0; i < rowLength; i++) {
				Rectangle r = (Rectangle) gridPane.getChildren().get(((rowLength * j) + i));
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

	public GridPane getTilePane() {
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
	
}

package application;

import application.gameoflife.LifeGrid;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoardGUI {

	public static int DEFAULT_TILE_DIME = 5;
	public static int DEFAULT_TILE_SPACING = 2;

	
	private Canvas canvas;
	private GraphicsContext gc;
	private LifeGrid grid;
	private int width;
	private int height;

	private double tileSpacingHeight = DEFAULT_TILE_SPACING;
	private double tileSpacingWidth = DEFAULT_TILE_SPACING;
	private int squareDime = 0;

	private double tileWidth = DEFAULT_TILE_DIME;
	private double tileHeight = DEFAULT_TILE_DIME;

	public BoardGUI(LifeGrid grid, int squareDime, int spacing) {
		this(grid, squareDime);
		this.tileSpacingHeight = spacing;
		this.tileSpacingWidth = spacing;

	}
	
	public BoardGUI(LifeGrid grid, int squareDime) {
		this.squareDime = squareDime;
		this.grid = grid;
		width = grid.getWidth();
		height = grid.getHeight();
		this.canvas = new Canvas(width*squareDime, height*squareDime);
		this.gc = canvas.getGraphicsContext2D();
		canvas.setOnMouseClicked(new CellClickHandler());
		canvas.setOnMouseDragged(new CellClickHandler());
		for (int j = 0; j < grid.getHeight(); j++) {
			int[] row = grid.row(j);
			for (int i = 0; i < grid.getWidth(); i++) {
				gc.setFill(getRectangleColor(row[i]));
				gc.fillRect(squareDime * j, squareDime * i, squareDime, squareDime);
				
				/*Rectangle r = new Rectangle(tileWidth, tileHeight, getRectangleColor(row[i])); 
				r.setOnMouseEntered(new CellClickHandler(i, j, false));
				r.setOnMouseClicked(new CellClickHandler(i, j, true));
				gridPane.add(r, i, j);*/
				//GridPane.setMargin(r, new Insets(tileSpacingWidth, tileSpacingWidth, tileSpacingHeight, tileSpacingWidth));
			}
		}
	}

	public void updateBoard(int[][] gridUpdate) {
		for (int j = 0; j < gridUpdate.length; j++) {
			for (int i = 0; i < gridUpdate[j].length; i++) {
				System.out.println(gridUpdate[j][i] != 3);
				if(gridUpdate[j][i] != 3) {
					//Rectangle r = (Rectangle) gridPane.getChildren().get(((width * j) + i));
					gc.setFill(getRectangleColor(gridUpdate[j][i]));
					gc.fillRect(squareDime * j, squareDime * i, squareDime, squareDime);
					
					
					//Color cl = getRectangleColor(gridUpdate[j][i]);
					//r.setFill(cl);
				}
			}
		}
	}
	/*public void updateCellSize() {
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				Rectangle r = (Rectangle) gridPane.getChildren().get(((width * j) + i));
				r.setWidth(tileWidth);
				r.setHeight(tileHeight);
				GridPane.setMargin(r, new Insets(tileSpacingWidth, tileSpacingWidth, tileSpacingHeight, tileSpacingWidth));
			}
		}
	}*/
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

	public Canvas getCanvas() {
		canvas.getStyleClass().add("canvasPane");
		return canvas;
	}

	public double getTileWidth() {
		return tileWidth;
	}

	public double getTileHeight() {
		return tileHeight;
	}

	public void setTileWidth(double tileWidth) {
		this.tileWidth = tileWidth;
		//updateCellSize();
	}

	public void setTileHeight(double tileHeight) {
		this.tileHeight = tileHeight;
		//updateCellSize();
	}

	public double getTileSpacingHeight() {
		return tileSpacingHeight;
	}

	public double getTileSpacingWidth() {
		return tileSpacingWidth;
	}

	public void setTileSpacingHeight(double d) {
		this.tileSpacingHeight = d;
		//updateCellSize();
	}

	public void setTileSpacingWidth(double d) {
		this.tileSpacingWidth = d;
		//updateCellSize();
	}
	
	private class CellClickHandler implements EventHandler<MouseEvent> {
		
		
		@Override
		public void handle(MouseEvent event) {
			try {	
			int f = grid.toggleCell( (int) event.getY()/squareDime, (int) event.getX()/squareDime);
				Color cl = getRectangleColor(f);
				
				gc.setFill(cl);
				gc.fillRect(event.getX() - event.getX()%squareDime, event.getY() - event.getY()%squareDime, squareDime, squareDime);
			} catch (ArrayIndexOutOfBoundsException e) {
				//Ignore, faster to error and ignore than check.
			}
			
		}
	}
}

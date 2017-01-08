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
	private int initialWidth;
	private int initialHeight;

	private double tileSpacingHeight = DEFAULT_TILE_SPACING;
	private double tileSpacingWidth = DEFAULT_TILE_SPACING;
	private int squareDime = 0;

	private double tileWidth = DEFAULT_TILE_DIME;
	private double tileHeight = DEFAULT_TILE_DIME;

	private int xOffset = 0;
	private int yOffset = 0;
	
	private int xVisible;
	private int yVisible;
	
	private int zoom = 1;
	public BoardGUI(LifeGrid grid, int zoom, int squareDime, int spacing) {
		this(grid, zoom, squareDime);
		this.tileSpacingHeight = spacing;
		this.tileSpacingWidth = spacing;

	}
	
	public BoardGUI(LifeGrid grid, int zoom,  int squareDime) {
		this.grid = grid;
		initialWidth = grid.getWidth();
		initialHeight = grid.getHeight();
		
		this.zoom = zoom;
		this.squareDime = squareDime * zoom;
		xVisible = initialWidth / this.zoom;
		yVisible = initialHeight / this.zoom;
		
		this.canvas = new Canvas(xVisible*squareDime, yVisible*squareDime);
		this.gc = canvas.getGraphicsContext2D();
		canvas.setOnMouseClicked(new CellClickHandler());
		canvas.setOnMouseDragged(new CellClickHandler());

		updateBoard(grid.run());
	}

	public void updateBoard(int[][] gridUpdate) {
		for (int j = yOffset; j < yVisible + yOffset; j++) {
			for (int i = xOffset; i < xVisible + xOffset; i++) {
				if(gridUpdate[j][i] != 0) {
					//Rectangle r = (Rectangle) gridPane.getChildren().get(((width * j) + i));
					gc.setFill(getDeltaRectangleColor(gridUpdate[j][i]));
					gc.fillRect(squareDime * (i-xOffset), squareDime * (j-yOffset), squareDime, squareDime);
				}
			}
		}
	}

	Color getRectangleColor(int i) {
		switch (i) {
		case 0:
			return Color.WHITE;
		case 1:
			return Color.RED;
		case 2:
			return Color.DARKRED;
		default:
			return Color.WHITE;
		}
	}

	Color getDeltaRectangleColor(int i) {
		switch (i) {
		case 1:
			return Color.WHITE;
		case 2:
			return Color.RED;
		case 3:
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

			if (event.getClickCount() == 2) {
				zoomIn();
			} else {
				try {
					 grid.toggleCell(((int) event.getX() / squareDime + xOffset),
							((int) event.getY() / squareDime + yOffset));
					 Color cl = Color.BLACK;
					 gc.setFill(cl);
					 gc.fillRect( event.getX() - event.getX()%squareDime, event.getY() - event.getY() % squareDime, squareDime, squareDime);
				} catch (ArrayIndexOutOfBoundsException e) {
					// Ignore, faster to error and ignore than check.
				}

			}
			 
			
		}
	}
	

	private void setXOffset(int xOffset) {
		if(xOffset < grid.getWidth() - xVisible  && xOffset >= 0) {
			gc.clearRect(0, 0, initialWidth*squareDime, initialHeight*squareDime);
			this.xOffset = xOffset;
		} 
	}

	private void setYOffset(int yOffset) {
		if(yOffset < grid.getHeight() - yVisible && yOffset >= 0) {
			gc.clearRect(0, 0, initialWidth*squareDime, initialHeight*squareDime);
			this.yOffset = yOffset;
		} 
	}
	
	public void incYOffset(){
		setYOffset(yOffset + 1); 
	}
	public void incXOffset(){
		setXOffset(xOffset + 1); 
	}
	
	public void decYOffset(){
		setYOffset(yOffset - 1); 
	}
	public void decXOffset(){
		setXOffset(xOffset - 1); 
	}
	
	public void zoomIn() {
		if(zoom < 10) {
	
			gc.clearRect(0, 0, initialWidth*squareDime, initialHeight*squareDime); 
			this.zoom ++;
			this.squareDime = squareDime * zoom;
			xVisible = initialWidth / zoom;
			yVisible = initialHeight / zoom;
		}
	}
	
	public void zoomOut() { //Doesn't work atms
		if(zoom > 1) {
			gc.clearRect(0, 0, initialWidth*squareDime, initialHeight*squareDime);

			this.squareDime = squareDime * 1/ zoom;
			
			this.zoom --;
			
		
			xVisible = initialWidth * zoom;
			yVisible = initialHeight * zoom;
			
			if(zoom == 1) {
				yOffset = 0;
				xOffset = 0;
			}
			


		}
	}
}

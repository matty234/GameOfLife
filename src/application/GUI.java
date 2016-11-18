package application;


import application.gameoflife.LifeGrid;
import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GUI extends Application {
	static LifeGrid grid;
	BoardGUI board;
	Stage primaryStage;
	static int x;
	static int y;
	
	static int delay = 200;
	static String file = null;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		try {
			grid = new LifeGrid(x, y, file);
			board = new BoardGUI(grid);
			Scene scene = new Scene(board.getTilePane());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			double defaultWidth = primaryStage.getWidth();
			double defaultHeight = primaryStage.getHeight();
			
			
			primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
			    @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldVal, Number newVal) {
					board.setTileWidth((newVal.longValue() / defaultWidth) * BoardGUI.DEFAULT_TILE_DIME);
					board.setTileSpacingWidth((newVal.longValue() / defaultWidth) * BoardGUI.DEFAULT_TILE_SPACING);
			    }
			});
			primaryStage.heightProperty().addListener(new ChangeListener<Number>() {
			    @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldVal, Number newVal) {
					board.setTileHeight((newVal.longValue() / defaultHeight) * BoardGUI.DEFAULT_TILE_DIME);
					board.setTileSpacingHeight((newVal.longValue() / defaultWidth) * BoardGUI.DEFAULT_TILE_SPACING);
			    }
			});
	
			primaryStage.setTitle("Game Of Life");
			
			Timeline loop = new Timeline(new KeyFrame(Duration.millis(delay), e -> {
				boardStep();
			}));
			
			
	        loop.setCycleCount(Animation.INDEFINITE);
	        loop.play();
	        
	        scene.setOnKeyPressed((keyEvent) -> {
	        	if(keyEvent.getCode() == KeyCode.P && loop.getStatus() == Status.RUNNING ) {
	        		loop.pause();
	        	} else if(keyEvent.getCode() == KeyCode.S) {
	        		loop.pause();
	        		boardStep();
	        	} else if(keyEvent.getCode() == KeyCode.C) {
	        		grid.show();
	        	} else if(keyEvent.getCode() == KeyCode.I) {
	        		grid.randomise();
	        		loop.play();
	        	}else {
	        		loop.play();
	        	}
	        });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void boardStep() {
		int[][] newGrid = grid.run();
		board.updateBoard(newGrid);
		primaryStage.setTitle("Game Of Life (generation: "+grid.getGeneration()+")");
	}
}
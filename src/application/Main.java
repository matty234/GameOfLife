package application;

import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;

import application.gameoflife.LifeGrid;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation.Status;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
	LifeGrid grid;
	BoardGUI board;
	Stage primaryStage;
	static int x;
	static int y;
	static String file;
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		try {
			grid = new LifeGrid(x, y, file);
			board = new BoardGUI(grid);
			
			Scene scene = new Scene(board.getTilePane(), grid.getWidth()*(BoardGUI.TILE_DIME+BoardGUI.TILE_SPACING), grid.getHeight()*(BoardGUI.TILE_DIME+BoardGUI.TILE_SPACING));
			
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setResizable(false);
			
			primaryStage.setTitle("Game Of Life");
			
			Timeline loop = new Timeline(new KeyFrame(Duration.millis(100), e -> {
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
	        	} else if(keyEvent.getCode() == KeyCode.UP) {
	        		loop.setDelay(loop.getDelay().add(Duration.millis(10)));
	        	} else if(keyEvent.getCode() == KeyCode.I) {
	        		grid.randomise();
	        	}else {
	        		loop.play();
	        	}
	        });

	        
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void boardStep() {
		// TODO Auto-generated method stub
		int[][] newGrid = grid.run();
		board.updateBoard(newGrid);
		primaryStage.setTitle("Game Of Life (generation: "+grid.getGeneration()+")");

	}
	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		x = Integer.parseInt(args[0]);
		y = Integer.parseInt(args[1]);
		if(args.length == 3){
			file = args[2];
		} else if(args.length > 3){
			file = args[2];
			while(true) {
				LifeGrid grid = new LifeGrid(x, y, file);
				while(true){
					grid.show();
					grid.run();
					Thread.sleep(200);
				}
			}
		} else {
			file = null;
		}
		launch(args);
	}
}

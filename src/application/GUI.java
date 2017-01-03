package application;

import java.io.File;

import application.CustomFooter.RefreshChangeListener;
import application.MoveButton.MoveDirection;
import application.gameoflife.LifeGrid;
import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GUI extends Application {
	static LifeGrid grid;
	BoardGUI board;
	Stage primaryStage;
	static int x;
	static int y;
	final String FOOTER = "";
	static int delay = 200;
	static String file = null;
	//Text generationText;
	private CustomFooter footer;

	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		try {
			if(null == file) {
				grid = new LifeGrid(x, y);
			} else {
				grid = new LifeGrid(new File(file), x, y);
			}
			
			board = new BoardGUI(grid, 1, 2);


			VBox settingsPane = new VBox(10);
			settingsPane.setId("settingsPane");
			Button pauseButton = new Button("Pause");
			Button resumeButton = new Button("Resume");
			resumeButton.setDisable(true);
			Button stepButton = new Button("Step");
			Button randomiseButton = new Button("Randomise");
			
			Button zoomInButton = new Button("+");
			Button zoomOutButton = new Button("-");
			
			Label infoText = new Label("Click or drag to make a cell alive");
			
			pauseButton.setMaxWidth(Double.MAX_VALUE);
			resumeButton.setMaxWidth(Double.MAX_VALUE);
			stepButton.setMaxWidth(Double.MAX_VALUE);
			randomiseButton.setMaxWidth(Double.MAX_VALUE);
			infoText.setMaxWidth(100);
			infoText.setWrapText(true);
			
			footer = new CustomFooter(10, delay);
			footer.setId("footer");
			settingsPane.getChildren().add(resumeButton);
			settingsPane.getChildren().add(pauseButton);
			settingsPane.getChildren().add(stepButton);
			settingsPane.getChildren().add(randomiseButton);
			
			MoveButton moveButton = new MoveButton(new SceneMoveChanged());
			
			HBox zoomBox = new HBox(zoomOutButton, zoomInButton);
			
			
			settingsPane.getChildren().add(moveButton);
			settingsPane.getChildren().add(zoomBox);

			settingsPane.getChildren().add(infoText);

			BorderPane borderPane = new BorderPane(settingsPane, null, null, footer,
					board.getCanvas());
			borderPane.getStylesheets().add("http://pastebin.com/raw/c3ndLLFP");
			Scene mainScene = new Scene(borderPane);
			primaryStage.setScene(mainScene);
			primaryStage.show();

			/*
			 * Resizing does work - just a bit buggy on some platforms (for
			 * example, the title bar is taken into account on MacOS but not
			 * Windows.
			 */
			primaryStage.setResizable(true);
			/*
			 * double defaultWidth = primaryStage.getWidth(); double
			 * defaultHeight = primaryStage.getHeight();
			 * 
			 * 
			 * primaryStage.widthProperty().addListener(new
			 * ChangeListener<Number>() {
			 * 
			 * @Override public void changed(ObservableValue<? extends Number>
			 * observableValue, Number oldVal, Number newVal) {
			 * board.setTileWidth((newVal.longValue() / defaultWidth) *
			 * BoardGUI.DEFAULT_TILE_DIME);
			 * board.setTileSpacingWidth((newVal.longValue() / defaultWidth) *
			 * BoardGUI.DEFAULT_TILE_SPACING); } });
			 * primaryStage.heightProperty().addListener(new
			 * ChangeListener<Number>() {
			 * 
			 * @Override public void changed(ObservableValue<? extends Number>
			 * observableValue, Number oldVal, Number newVal) {
			 * board.setTileHeight((newVal.longValue() / defaultHeight) *
			 * BoardGUI.DEFAULT_TILE_DIME);
			 * board.setTileSpacingHeight((newVal.longValue() / defaultWidth) *
			 * BoardGUI.DEFAULT_TILE_SPACING); } });
			 */

			primaryStage.setTitle("Game Of Life");
			Timeline loop = new Timeline(new KeyFrame(Duration.millis(delay), e -> {
				boardStep(true);
			}));
			footer.setRefreshChangeListener(new RefreshChangeListener() {
				@Override
				public void onChange(int value) {
					loop.stop();
					loop.getKeyFrames().setAll(new KeyFrame(Duration.millis(value), e -> {
						boardStep(true);
					}));
					loop.play();
				}
			});
			loop.setCycleCount(Animation.INDEFINITE);
			loop.play();

			mainScene.setOnKeyPressed((keyEvent) -> {
				if (keyEvent.getCode() == KeyCode.P && loop.getStatus() == Status.RUNNING) {
					loop.pause();
					pauseButton.setDisable(true);
					resumeButton.setDisable(false);
				} else if (keyEvent.getCode() == KeyCode.P) {
					loop.play();
					pauseButton.setDisable(false);
					resumeButton.setDisable(true);
				} else if (keyEvent.getCode() == KeyCode.S) {
					boardStep(true);
				} else if (keyEvent.getCode() == KeyCode.C) {
					grid.show();
				} else if (keyEvent.getCode() == KeyCode.R) {
					grid.randomise();
					pauseButton.setDisable(false);
					resumeButton.setDisable(true);
					loop.play();
				}
			});
			randomiseButton.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					grid.randomise();
					loop.play();
					pauseButton.setDisable(false);
					resumeButton.setDisable(true);
				}
			});
			stepButton.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					loop.pause();
					boardStep(true);
				}
			});
			pauseButton.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					loop.pause();
					pauseButton.setDisable(true);
					resumeButton.setDisable(false);
				}
			});
			resumeButton.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					loop.play();
					pauseButton.setDisable(false);
					resumeButton.setDisable(true);
				}
			});
			
			
			zoomInButton.setOnMousePressed(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					board.zoomIn();
					boardStep(false);
				}
			});
			
			zoomOutButton.setOnMousePressed(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					board.zoomOut();
					boardStep(false);
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class SceneMoveChanged implements MoveButton.OnMoveChanged {

		@Override
		public void direction(MoveDirection direction) {
			switch(direction) {
			case Down:
				board.incYOffset();
				break;
			case Left:
				board.decXOffset();
				break;
			case LeftDown:
				board.decXOffset();
				board.incXOffset();
				break;
			case LeftUp:
				board.decXOffset();
				board.decYOffset();
				break;
			case Right:
				board.incXOffset();
				break;
			case RightDown:
				board.incXOffset();
				board.incYOffset();
				break;
			case RightUp:
				board.incXOffset();
				board.decYOffset();
				break;
			case Up:
				board.decYOffset();
				break;
			default:
				break;
			}
			boardStep(false);
		}
		
	}
	private void boardStep(boolean useDelta) {
		if(useDelta) {
			int[][] newGrid = grid.runDelta();
			board.updateBoard(newGrid);
		} else {
			int[][] newGrid = grid.run();
			board.updateBoard(newGrid);
		}
		footer.setGeneration(grid.getGeneration());
		primaryStage.setTitle("Game Of Life (generation: " + grid.getGeneration() + ")");
	}
}
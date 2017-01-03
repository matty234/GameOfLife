package application;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MoveButton extends VBox {
	OnMoveChanged moveChanged;
	
	public MoveButton(OnMoveChanged moveChanged) {
		super(0);
		this.moveChanged = moveChanged;
		
		Button leftUpButton = new Button("↖");
		Button upButton = new Button("↑");
		Button rightUpButton = new Button("↗");
		
		Button leftButton = new Button("←");
		Button middleButton = new Button("   ");
		Button rightButton = new Button("→");

		Button leftDownButton = new Button("↙");
		Button downButton = new Button("↓");
		Button rightDownButton = new Button("↘");
		
		 HBox top = new HBox(leftUpButton, upButton, rightUpButton) ;
		 HBox middle = new HBox(leftButton, middleButton, rightButton) ;
		 HBox bottom = new HBox(leftDownButton, downButton, rightDownButton) ;
		 
		leftUpButton.setOnMouseClicked(new MousePressed(MoveDirection.LeftUp));
		upButton.setOnMouseClicked(new MousePressed(MoveDirection.Up));
		rightUpButton.setOnMouseClicked(new MousePressed(MoveDirection.RightUp));
		
		leftButton.setOnMouseClicked(new MousePressed(MoveDirection.Left));
		rightButton.setOnMouseClicked(new MousePressed(MoveDirection.Right));
		
		leftDownButton.setOnMouseClicked(new MousePressed(MoveDirection.LeftDown));
		downButton.setOnMouseClicked(new MousePressed(MoveDirection.Down));
		rightDownButton.setOnMouseClicked(new MousePressed(MoveDirection.RightDown));


		this.getChildren().add(top);
		this.getChildren().add(middle);
		this.getChildren().add(bottom);		
	}
	
	private class MousePressed implements EventHandler<MouseEvent>{
		MoveDirection direction;
		public MousePressed(MoveDirection direction) {
			this.direction = direction;
		}
		
		@Override
		public void handle(MouseEvent event) {
			moveChanged.direction(direction);
		}
		
	}
	
	public interface OnMoveChanged {
		void direction(MoveDirection direction);
	}
	
	public enum MoveDirection {
		LeftUp, Up, RightUp,
		Left, Right, LeftDown, Down, RightDown;
	}
}

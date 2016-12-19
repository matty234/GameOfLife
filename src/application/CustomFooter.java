package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class CustomFooter extends HBox {
	Text generationTextValue = new Text();

	private RefreshChangeListener changeListener;
	public CustomFooter(double padding, double currentRefresh) {
		super(padding);
		
		ObservableList<Node> t = this.getChildren();
		Text generationText = new Text("Generation:");
		generationText.setId("generationTextLabel");
		
		generationTextValue.setText("0");
		
		VBox vBox = new VBox(2, generationText, generationTextValue );
		t.add(vBox);

		Slider slider = new Slider(60, 1000, currentRefresh);
		t.add(slider);
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(2000f);
		slider.valueProperty().addListener(new RefreshChanged());

	}
		
	public void setGeneration(int generation) {
		generationTextValue.setText(generation + "");
	}
	


	public void setRefreshChangeListener(RefreshChangeListener changeListener) {
		this.changeListener = changeListener;
	}

	private class RefreshChanged implements ChangeListener<Number>{

		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			
			changeListener.onChange(newValue.intValue());
		}
		
		
	}
	
	public interface RefreshChangeListener {
		void onChange(int value);
	}
}

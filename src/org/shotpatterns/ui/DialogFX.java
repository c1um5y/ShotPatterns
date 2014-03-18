package org.shotpatterns.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

public class DialogFX extends Stage {
	private BorderPane elementContainer = new BorderPane();

	public DialogFX(String title, String text, Window window, int width, int height) {
		setTitle(title);
		Text aboutField = new Text(text);
		elementContainer.setCenter(aboutField);
		setWidth(width);
		setHeight(height);
		setMinHeight(30);
		setMinWidth(50);
		setScene(new Scene(elementContainer));
		initOwner(window);
		setResizable(false);
	}

	public void addOkButton() {
		StackPane buttonPane = new StackPane();
		Button okButton = new Button("OK");
		buttonPane.setAlignment(Pos.BASELINE_CENTER);
		buttonPane.getChildren().add(okButton);
		elementContainer.setBottom(buttonPane);
		okButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				close();
			}
		});
	}
}

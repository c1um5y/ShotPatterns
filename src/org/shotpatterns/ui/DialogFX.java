package org.shotpatterns.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

public class DialogFX extends Stage {
	private BorderPane elementContainer = new BorderPane();
	private static final int ICON_SIZE = 70;
	private static final Image ERROR_ICON = new Image(DialogFX.class.getResourceAsStream("/resources/ErrorLogo.png"),
	        ICON_SIZE, ICON_SIZE, true, true);
	private static final Image INFORMATION_ICON = new Image(
	        DialogFX.class.getResourceAsStream("/resources/InfoLogo.png"), ICON_SIZE, ICON_SIZE, true, true);
	private static final Image OKAY_ICON = new Image(DialogFX.class.getResourceAsStream("/resources/OkayLogo.png"),
	        ICON_SIZE, ICON_SIZE, true, true);
	private static final Image WARNING_ICON = new Image(
	        DialogFX.class.getResourceAsStream("/resources/WarningLogo.png"), ICON_SIZE, ICON_SIZE, true, true);

	public enum Type {
		ERROR, INFORMATION, WARNING, OKAY
	}

	public DialogFX(String title, String text, Window window, int width, int height, Type type) {
		setTitle(title);
		Text aboutField = new Text(text);
		elementContainer.setCenter(aboutField);
		ImageView imageView = null;
		if (type == Type.ERROR) {
			imageView = new ImageView(ERROR_ICON);
		} else if (type == Type.INFORMATION) {
			imageView = new ImageView(INFORMATION_ICON);
		} else if (type == Type.WARNING) {
			imageView = new ImageView(WARNING_ICON);
		} else if (type == Type.OKAY) {
			imageView = new ImageView(OKAY_ICON);
		}
		elementContainer.setLeft(imageView);
		setScene(new Scene(elementContainer));
		initOwner(window);
		sizeToScene();
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

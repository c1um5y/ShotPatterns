package org.shotpatterns.ui;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

public class SPButton extends Button {
	public static final int BUTTON_HEIGHT = 30;

	public SPButton(String name) {
		super(name);
		setMinHeight(BUTTON_HEIGHT);
	}

	public SPButton(String name, String tooltip) {
		this(name);
		setTooltip(new Tooltip(tooltip));
	}
}

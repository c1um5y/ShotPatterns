package org.shotpatterns.exception;

public class TitleAlreadyExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6959918161536275595L;

	public TitleAlreadyExistsException(String title) {
		super("Movie is already in the db: " + title);
	}
}

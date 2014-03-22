package org.shotpatterns.exception;

import org.apache.poi.ss.usermodel.Cell;

public class InvalidCellException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -125596524475449313L;

	public InvalidCellException(Cell cell) {
		super("Invalid cell! Type: " + extractCellType(cell.getCellType()));
	}

	public InvalidCellException(Cell cell, double value) {
		super("Invalid cell! Type: " + extractCellType(cell.getCellType()) + ", value: " + value);
	}

	public InvalidCellException(Cell cell, String value) {
		super("Invalid cell! Type: " + extractCellType(cell.getCellType()) + ", value: " + value);
	}

	private static String extractCellType(int type) {
		switch (type) {
		case Cell.CELL_TYPE_BLANK:
			return "BLANK";
		case Cell.CELL_TYPE_BOOLEAN:
			return "BOOLEAN";
		case Cell.CELL_TYPE_ERROR:
			return "ERROR";
		case Cell.CELL_TYPE_FORMULA:
			return "FORMULA";
		case Cell.CELL_TYPE_NUMERIC:
			return "NUMERIC";
		case Cell.CELL_TYPE_STRING:
			return "STRING";
		default:
			return "";
		}
	}
}

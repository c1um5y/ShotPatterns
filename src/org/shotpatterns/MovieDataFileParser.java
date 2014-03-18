package org.shotpatterns;

import static org.shotpatterns.data.ShotData.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.shotpatterns.data.MovieData;
import org.shotpatterns.data.ShotData;
import org.shotpatterns.exception.InsufficientDataException;
import org.shotpatterns.exception.InvalidCellException;
import org.shotpatterns.exception.TitleAlreadyExistsException;

public class MovieDataFileParser {

	public Map<Integer, MovieData> parse(File excelFile, int sheetIndex, int movieTitleRowIndex,
	        int movieTitleColumnIndex, int startRowIndex, int shotDataColumnIndex, Set<Integer> keySet)
	        throws InvalidCellException, InvalidFormatException, IOException, InsufficientDataException,
	        TitleAlreadyExistsException {
		Workbook workbook = WorkbookFactory.create(new FileInputStream(excelFile));
		Sheet sheet = workbook.getSheetAt(sheetIndex);
		Row titleRow = sheet.getRow(movieTitleRowIndex);
		String title = getTitle(titleRow, movieTitleColumnIndex);

		return createShotMap(sheet, startRowIndex, title, shotDataColumnIndex, keySet);
	}

	private String getTitle(Row titleRow, int movieTitleCellIndex) throws InvalidCellException {
		Cell cell = titleRow.getCell(movieTitleCellIndex);
		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			return titleRow.getCell(movieTitleCellIndex).getStringCellValue();
		} else {
			throw new InvalidCellException(cell);
		}
	}

	private Map<Integer, MovieData> createShotMap(Sheet sheet, int startRowIndex, String title, int columnIndex,
	        Set<Integer> keySet) throws InvalidCellException, InsufficientDataException, TitleAlreadyExistsException {
		Map<Integer, MovieData> shotValues = new HashMap<Integer, MovieData>();
		int key = title.hashCode();
		if (keySet.contains(key)) {
			throw new TitleAlreadyExistsException(title);
		}
		shotValues.put(key,
		        new MovieData(title, calculatePercentage(getValuesFromColumn(columnIndex, startRowIndex, sheet))));

		return shotValues;
	}

	private List<Double> getValuesFromColumn(int columnIndex, int startRowIndex, Sheet sheet)
	        throws InvalidCellException {
		List<Double> values = new ArrayList<Double>();
		Cell cell;
		while (sheet.getRow(startRowIndex) != null) {
			cell = sheet.getRow(startRowIndex).getCell(columnIndex);
			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				break;
			}
			values.add(getNumericValue(cell));
			startRowIndex++;
		}

		return values;
	}

	protected double getNumericValue(Cell cell) throws InvalidCellException {
		int cellType = cell.getCellType();
		if (cellType != Cell.CELL_TYPE_STRING && cellType != Cell.CELL_TYPE_NUMERIC) {
			throw new InvalidCellException(cell);
		} else if (cellType == Cell.CELL_TYPE_STRING) {
			try {
				double value = Double.parseDouble(cell.getStringCellValue());
				if (isCellValueValid(value)) {
					return value;
				} else {
					throw new InvalidCellException(cell, value);
				}
			} catch (NumberFormatException ex) {
				throw new InvalidCellException(cell, cell.getStringCellValue());
			}
		} else if (cellType == Cell.CELL_TYPE_NUMERIC) {
			double value = cell.getNumericCellValue();
			if (isCellValueValid(value)) {
				return value;
			} else {
				throw new InvalidCellException(cell, value);
			}
		}

		throw new InvalidCellException(cell);
	}

	private boolean isCellValueValid(double value) {
		return value <= 8.0 && value >= 0.0;
	}

	protected List<ShotData> calculatePercentage(List<Double> values) {
		Collections.sort(values);
		List<ShotData> shotData = new ArrayList<ShotData>();
		ShotData[] data = new ShotData[9];
		for (int i = 0; i < 9; i++) {
			data[i] = new ShotData(i, 0.0);
		}
		double nrOfShots = 0;
		double shotType = OVER_THE_SHOULDER;
		for (int i = 0; i < values.size();) {
			if (shotType != values.get(i)) {
				shotType++;
			} else {
				nrOfShots++;
				if (i + 1 == values.size() || shotType < values.get(i + 1)) {
					double percentage = (nrOfShots / values.size()) * 100;
					data[(int) shotType] = new ShotData(shotType, percentage);
					shotType++;
					nrOfShots = 0;
				}
				i++;
			}
		}

		if (!values.contains(INSERT)) {
			shotData.add(new ShotData(INSERT, 0));
		}

		return Arrays.asList(data);
	}
}

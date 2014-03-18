package org.shotpatterns;

import static org.mockito.Mockito.*;
import static org.shotpatterns.data.ShotData.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.junit.Assert;
import org.junit.Test;
import org.shotpatterns.MovieDataFileParser;
import org.shotpatterns.data.ShotData;
import org.shotpatterns.exception.InvalidCellException;

public class MovieDataFileParserTest {

	@Test
	public void test_calculate_percentage() {
		MovieDataFileParser mo = new MovieDataFileParser();
		List<Double> values = new ArrayList<Double>();

		for (int i = 0; i < 15; i++) {
			values.add(OVER_THE_SHOULDER);
		}
		for (int i = 0; i < 15; i++) {
			values.add(CLOSE_UP);
		}
		for (int i = 0; i < 15; i++) {
			values.add(MEDIUM_CLOSE_UP);
		}
		for (int i = 0; i < 15; i++) {
			values.add(OVER_THE_SHOULDER);
		}
		for (int i = 0; i < 40; i++) {
			values.add(EXTREME_LONG_SHOT);
		}

		List<ShotData> percentages = mo.calculatePercentage(values);

		List<ShotData> expectedData = new ArrayList<ShotData>();
		ShotData otsData = new ShotData(OVER_THE_SHOULDER, 30);
		ShotData cuData = new ShotData(CLOSE_UP, 15);
		ShotData mcuData = new ShotData(MEDIUM_CLOSE_UP, 15);
		ShotData elsData = new ShotData(EXTREME_LONG_SHOT, 40);
		expectedData.add(otsData);
		expectedData.add(cuData);
		expectedData.add(mcuData);
		expectedData.add(elsData);

		Assert.assertEquals(otsData, percentages.get(0));
		Assert.assertEquals(new ShotData(EXTREME_CLOSE_UP, 0), percentages.get(1));
		Assert.assertEquals(cuData, percentages.get(2));
		Assert.assertEquals(mcuData, percentages.get(3));
		Assert.assertEquals(new ShotData(MEDIUM_SHOT, 0), percentages.get(4));
		Assert.assertEquals(new ShotData(MEDIUM_LONG_SHOT, 0), percentages.get(5));
		Assert.assertEquals(new ShotData(LONG_SHOT, 0), percentages.get(6));
		Assert.assertEquals(elsData, percentages.get(7));
		Assert.assertEquals(new ShotData(INSERT, 0), percentages.get(8));
	}

	@Test
	public void test_calculate_percentage_values_are_different_after_each_other() {
		MovieDataFileParser mo = new MovieDataFileParser();
		List<Double> values = new ArrayList<Double>();
		for (int i = 0; i < 9; i++) {
			values.add(OVER_THE_SHOULDER);
			values.add(EXTREME_CLOSE_UP);
			values.add(CLOSE_UP);
			values.add(MEDIUM_CLOSE_UP);
			values.add(MEDIUM_SHOT);
			values.add(MEDIUM_LONG_SHOT);
			values.add(LONG_SHOT);
			values.add(EXTREME_LONG_SHOT);
			values.add(INSERT);
		}
		List<ShotData> percentages = mo.calculatePercentage(values);
		double d = 9.0 / 81 * 100;
		ShotData otsData = new ShotData(OVER_THE_SHOULDER, d);
		ShotData ecuData = new ShotData(EXTREME_CLOSE_UP, d);
		ShotData cuData = new ShotData(CLOSE_UP, d);
		ShotData mcuData = new ShotData(MEDIUM_CLOSE_UP, d);
		ShotData ameData = new ShotData(MEDIUM_SHOT, d);
		ShotData mlsData = new ShotData(MEDIUM_LONG_SHOT, d);
		ShotData lsData = new ShotData(LONG_SHOT, d);
		ShotData elsData = new ShotData(EXTREME_LONG_SHOT, d);
		ShotData insData = new ShotData(INSERT, d);
		Assert.assertEquals(otsData, percentages.get(0));
		Assert.assertEquals(ecuData, percentages.get(1));
		Assert.assertEquals(cuData, percentages.get(2));
		Assert.assertEquals(mcuData, percentages.get(3));
		Assert.assertEquals(ameData, percentages.get(4));
		Assert.assertEquals(mlsData, percentages.get(5));
		Assert.assertEquals(lsData, percentages.get(6));
		Assert.assertEquals(elsData, percentages.get(7));
		Assert.assertEquals(insData, percentages.get(8));
	}

	@Test
	public void test_calculate_percentage_no_over_the_shoulder() {
		MovieDataFileParser mo = new MovieDataFileParser();
		List<Double> values = new ArrayList<Double>();
		values.add(EXTREME_CLOSE_UP);
		values.add(EXTREME_CLOSE_UP);
		values.add(CLOSE_UP);
		values.add(MEDIUM_LONG_SHOT);
		values.add(MEDIUM_CLOSE_UP);
		values.add(MEDIUM_LONG_SHOT);
		values.add(MEDIUM_SHOT);
		values.add(MEDIUM_SHOT);
		values.add(MEDIUM_SHOT);
		values.add(MEDIUM_SHOT);
		values.add(MEDIUM_LONG_SHOT);
		values.add(INSERT);
		values.add(INSERT);
		values.add(EXTREME_CLOSE_UP);
		values.add(MEDIUM_LONG_SHOT);
		values.add(LONG_SHOT);
		values.add(EXTREME_LONG_SHOT);
		values.add(INSERT);
		values.add(INSERT);
		values.add(INSERT);

		List<ShotData> percentages = mo.calculatePercentage(values);
		Double d = (3.0 / 20) * 100;
		ShotData ecuData = new ShotData(EXTREME_CLOSE_UP, d);
		d = (1.0 / 20) * 100;
		ShotData cuData = new ShotData(CLOSE_UP, d);
		d = (1.0 / 20) * 100;
		ShotData mcuData = new ShotData(MEDIUM_CLOSE_UP, d);
		d = (4.0 / 20) * 100;
		ShotData ameData = new ShotData(MEDIUM_SHOT, d);
		d = (4.0 / 20) * 100;
		ShotData mlsData = new ShotData(MEDIUM_LONG_SHOT, d);
		d = (1.0 / 20) * 100;
		ShotData lsData = new ShotData(LONG_SHOT, d);
		d = (1.0 / 20) * 100;
		ShotData elsData = new ShotData(EXTREME_LONG_SHOT, d);
		d = (5.0 / 20) * 100;
		ShotData insData = new ShotData(INSERT, d);

		Assert.assertEquals(new ShotData(OVER_THE_SHOULDER, 0), percentages.get(0));
		Assert.assertEquals(ecuData, percentages.get(1));
		Assert.assertEquals(cuData, percentages.get(2));
		Assert.assertEquals(mcuData, percentages.get(3));
		Assert.assertEquals(ameData, percentages.get(4));
		Assert.assertEquals(mlsData, percentages.get(5));
		Assert.assertEquals(lsData, percentages.get(6));
		Assert.assertEquals(elsData, percentages.get(7));
		Assert.assertEquals(insData, percentages.get(8));
	}

	@Test
	public void test_calculate_percentage_no_insert() {
		MovieDataFileParser mo = new MovieDataFileParser();
		List<Double> values = new ArrayList<Double>();
		values.add(EXTREME_CLOSE_UP);
		values.add(EXTREME_CLOSE_UP);
		values.add(CLOSE_UP);
		values.add(MEDIUM_LONG_SHOT);
		values.add(MEDIUM_CLOSE_UP);
		values.add(MEDIUM_LONG_SHOT);
		values.add(MEDIUM_SHOT);
		values.add(MEDIUM_SHOT);
		values.add(MEDIUM_SHOT);
		values.add(MEDIUM_SHOT);
		values.add(MEDIUM_LONG_SHOT);
		values.add(OVER_THE_SHOULDER);
		values.add(OVER_THE_SHOULDER);
		values.add(EXTREME_CLOSE_UP);
		values.add(MEDIUM_LONG_SHOT);
		values.add(LONG_SHOT);
		values.add(EXTREME_LONG_SHOT);
		values.add(OVER_THE_SHOULDER);
		values.add(OVER_THE_SHOULDER);
		values.add(OVER_THE_SHOULDER);

		List<ShotData> percentages = mo.calculatePercentage(values);
		Double d = (5.0 / 20) * 100;
		ShotData otsData = new ShotData(OVER_THE_SHOULDER, d);
		d = (3.0 / 20) * 100;
		ShotData ecuData = new ShotData(EXTREME_CLOSE_UP, d);
		d = (1.0 / 20) * 100;
		ShotData cuData = new ShotData(CLOSE_UP, d);
		d = (1.0 / 20) * 100;
		ShotData mcuData = new ShotData(MEDIUM_CLOSE_UP, d);
		d = (4.0 / 20) * 100;
		ShotData ameData = new ShotData(MEDIUM_SHOT, d);
		d = (4.0 / 20) * 100;
		ShotData mlsData = new ShotData(MEDIUM_LONG_SHOT, d);
		d = (1.0 / 20) * 100;
		ShotData lsData = new ShotData(LONG_SHOT, d);
		d = (1.0 / 20) * 100;
		ShotData elsData = new ShotData(EXTREME_LONG_SHOT, d);

		Assert.assertEquals(otsData, percentages.get(0));
		Assert.assertEquals(ecuData, percentages.get(1));
		Assert.assertEquals(cuData, percentages.get(2));
		Assert.assertEquals(mcuData, percentages.get(3));
		Assert.assertEquals(ameData, percentages.get(4));
		Assert.assertEquals(mlsData, percentages.get(5));
		Assert.assertEquals(lsData, percentages.get(6));
		Assert.assertEquals(elsData, percentages.get(7));
		Assert.assertEquals(new ShotData(INSERT, 0.0), percentages.get(8));
	}
	
	@Test
	public void test_calculate_percentage_no_insert_extreme_close_up_extreme_long_shot() {
		MovieDataFileParser mo = new MovieDataFileParser();
		List<Double> values = new ArrayList<Double>();
		values.add(CLOSE_UP);
		values.add(MEDIUM_LONG_SHOT);
		values.add(MEDIUM_CLOSE_UP);
		values.add(MEDIUM_LONG_SHOT);
		values.add(MEDIUM_SHOT);
		values.add(MEDIUM_SHOT);
		values.add(MEDIUM_SHOT);
		values.add(MEDIUM_SHOT);
		values.add(MEDIUM_LONG_SHOT);
		values.add(OVER_THE_SHOULDER);
		values.add(OVER_THE_SHOULDER);
		values.add(MEDIUM_LONG_SHOT);
		values.add(LONG_SHOT);
		values.add(OVER_THE_SHOULDER);
		values.add(OVER_THE_SHOULDER);
		values.add(OVER_THE_SHOULDER);

		List<ShotData> percentages = mo.calculatePercentage(values);
		Double d = (5.0 / 16) * 100;
		ShotData otsData = new ShotData(OVER_THE_SHOULDER, d);
		d = 0.0;
		ShotData ecuData = new ShotData(EXTREME_CLOSE_UP, d);
		d = (1.0 / 16) * 100;
		ShotData cuData = new ShotData(CLOSE_UP, d);
		d = (1.0 / 16) * 100;
		ShotData mcuData = new ShotData(MEDIUM_CLOSE_UP, d);
		d = (4.0 / 16) * 100;
		ShotData ameData = new ShotData(MEDIUM_SHOT, d);
		d = (4.0 / 16) * 100;
		ShotData mlsData = new ShotData(MEDIUM_LONG_SHOT, d);
		d = (1.0 / 16) * 100;
		ShotData lsData = new ShotData(LONG_SHOT, d);
		d = 0.0;
		ShotData elsData = new ShotData(EXTREME_LONG_SHOT, d);

		Assert.assertEquals(otsData, percentages.get(0));
		Assert.assertEquals(ecuData, percentages.get(1));
		Assert.assertEquals(cuData, percentages.get(2));
		Assert.assertEquals(mcuData, percentages.get(3));
		Assert.assertEquals(ameData, percentages.get(4));
		Assert.assertEquals(mlsData, percentages.get(5));
		Assert.assertEquals(lsData, percentages.get(6));
		Assert.assertEquals(elsData, percentages.get(7));
		Assert.assertEquals(new ShotData(INSERT, 0.0), percentages.get(8));
	}
	
	@Test
	public void test_get_numeric_value_celltype_is_numeric() throws InvalidCellException {
		MovieDataFileParser dc = new MovieDataFileParser();
		Cell fakeCell = mock(Cell.class);
		when(fakeCell.getCellType()).thenReturn(Cell.CELL_TYPE_NUMERIC);
		when(fakeCell.getNumericCellValue()).thenReturn(8.0);

		Assert.assertEquals(8.0, dc.getNumericValue(fakeCell), 0);
	}

	@Test(expected = InvalidCellException.class)
	public void test_get_numeric_value_celltype_is_too_big_numeric() throws InvalidCellException {
		MovieDataFileParser dc = new MovieDataFileParser();
		Cell fakeCell = mock(Cell.class);
		when(fakeCell.getCellType()).thenReturn(Cell.CELL_TYPE_NUMERIC);
		when(fakeCell.getNumericCellValue()).thenReturn(10.0);

		dc.getNumericValue(fakeCell);
	}

	@Test(expected = InvalidCellException.class)
	public void test_get_numeric_value_celltype_is_negative_numeric() throws InvalidCellException {
		MovieDataFileParser dc = new MovieDataFileParser();
		Cell fakeCell = mock(Cell.class);
		when(fakeCell.getCellType()).thenReturn(Cell.CELL_TYPE_NUMERIC);
		when(fakeCell.getNumericCellValue()).thenReturn(-1.0);

		dc.getNumericValue(fakeCell);
	}

	@Test
	public void test_get_numeric_value_celltype_is_string_with_valid_numeric_value() throws InvalidCellException {
		MovieDataFileParser dc = new MovieDataFileParser();
		Cell fakeCell = mock(Cell.class);
		when(fakeCell.getCellType()).thenReturn(Cell.CELL_TYPE_STRING);
		when(fakeCell.getStringCellValue()).thenReturn("8.0");

		Assert.assertEquals(8.0, dc.getNumericValue(fakeCell), 0);
	}
	
	@Test(expected = InvalidCellException.class)
	public void test_get_numeric_value_celltype_is_string_with_too_big_numeric_value() throws InvalidCellException {
		MovieDataFileParser dc = new MovieDataFileParser();
		Cell fakeCell = mock(Cell.class);
		when(fakeCell.getCellType()).thenReturn(Cell.CELL_TYPE_STRING);
		when(fakeCell.getStringCellValue()).thenReturn("12.0");

		dc.getNumericValue(fakeCell);
	}
	
	@Test(expected = InvalidCellException.class)
	public void test_get_numeric_value_celltype_is_string_with_negative_numeric_value() throws InvalidCellException {
		MovieDataFileParser dc = new MovieDataFileParser();
		Cell fakeCell = mock(Cell.class);
		when(fakeCell.getCellType()).thenReturn(Cell.CELL_TYPE_STRING);
		when(fakeCell.getStringCellValue()).thenReturn("-12.0");

		dc.getNumericValue(fakeCell);
	}
	
	@Test(expected = InvalidCellException.class)
	public void test_get_numeric_value_celltype_is_string_with_non_numeric_value() throws InvalidCellException {
		MovieDataFileParser dc = new MovieDataFileParser();
		Cell fakeCell = mock(Cell.class);
		when(fakeCell.getCellType()).thenReturn(Cell.CELL_TYPE_STRING);
		when(fakeCell.getStringCellValue()).thenReturn("apple");

		dc.getNumericValue(fakeCell);
	}
}

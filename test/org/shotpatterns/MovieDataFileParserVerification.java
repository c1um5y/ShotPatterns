package org.shotpatterns;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.shotpatterns.MovieDataFileParser;
import org.shotpatterns.data.MovieData;
import org.shotpatterns.data.ShotData;
import org.shotpatterns.exception.InsufficientDataException;
import org.shotpatterns.exception.InvalidCellException;
import org.shotpatterns.exception.TitleAlreadyExistsException;

public class MovieDataFileParserVerification {

	private static final int TITLE_COLUMN_INDEX = 0;
	private static final int DATA_COLUMN_INDEX = 4;

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	private File dbFile;
	private File excelFile;
	String title0 = "title0";
	String title1 = "title1";
	String title2 = "title2";
	String title3 = "title3";
	String title4 = "title4";
	String title5 = "title5";

	@Before
	public void createTempFile() throws IOException, InvalidFormatException {
		dbFile = tempFolder.newFile("temp_db");
		BufferedWriter writer = new BufferedWriter(new FileWriter(dbFile));

		writer.write(title0.hashCode() + "," + title0 + "," + "10,20,0,1,2,30,17,8,12");
		writer.write(title1.hashCode() + "," + title1 + "," + "10,20,0,15,0,30,5,20,0\n");
		writer.write(title2.hashCode() + "," + title2 + "," + "8,25,0,13,0,15,10,20,5\n");
		writer.write(title3.hashCode() + "," + title3 + "," + "0,20,2,0,0,53,15,0,10\n");
		writer.write(title4.hashCode() + "," + title4 + "," + "0,20,10,15,0,30,5,20,0\n");
		writer.write(title5.hashCode() + "," + title5 + "," + "10,20,0,15,0,30,5,20,0\n");

		writer.close();

		excelFile = tempFolder.newFile("tempExcel.xls");
		Workbook wb = WorkbookFactory.create(excelFile);
		Sheet sheet = wb.createSheet();

		Row row = sheet.createRow(3);
		Cell cell = row.createCell(TITLE_COLUMN_INDEX);
		cell.setCellValue(title0);
		cell = row.createCell(4);
		cell.setCellValue(ShotData.OVER_THE_SHOULDER);
		row = sheet.createRow(5);
		cell = row.createCell(DATA_COLUMN_INDEX);
		cell.setCellValue(ShotData.OVER_THE_SHOULDER);
		row = sheet.createRow(6);
		cell = row.createCell(DATA_COLUMN_INDEX);
		cell.setCellValue(ShotData.OVER_THE_SHOULDER);
		row = sheet.createRow(7);
		cell = row.createCell(DATA_COLUMN_INDEX);
		cell.setCellValue(ShotData.OVER_THE_SHOULDER);
		row = sheet.createRow(8);
		cell = row.createCell(DATA_COLUMN_INDEX);
		cell.setCellValue(ShotData.CLOSE_UP);
		row = sheet.createRow(9);
		cell = row.createCell(DATA_COLUMN_INDEX);
		cell.setCellValue(ShotData.CLOSE_UP);
		row = sheet.createRow(10);
		cell = row.createCell(DATA_COLUMN_INDEX);
		cell.setCellValue(ShotData.MEDIUM_SHOT);
		row = sheet.createRow(11);
		cell = row.createCell(DATA_COLUMN_INDEX);
		cell.setCellValue(ShotData.MEDIUM_SHOT);
		row = sheet.createRow(12);
		cell = row.createCell(DATA_COLUMN_INDEX);
		cell.setCellValue(ShotData.MEDIUM_SHOT);
		row = sheet.createRow(13);
		cell = row.createCell(DATA_COLUMN_INDEX);
		cell.setCellValue(ShotData.MEDIUM_SHOT);
	}

	@Ignore
	@Test
	public void test_parse_valid_file() throws InvalidFormatException, InvalidCellException, IOException,
	        InsufficientDataException, TitleAlreadyExistsException {
		Assert.assertEquals(true, dbFile.exists());
		Assert.assertEquals(true, excelFile.exists());

		MovieDataFileParser parser = new MovieDataFileParser();
		Set<Integer> dummyKeySet = new HashSet<Integer>();
		dummyKeySet.add(3);
		Map<Integer, MovieData> parsed = parser.parse(excelFile, 0, 3, 0, 3, 4, dummyKeySet);

		Map<Integer, MovieData> expected = new HashMap<Integer, MovieData>();
		List<ShotData> shotData = new ArrayList<ShotData>();
		shotData.add(new ShotData(ShotData.OVER_THE_SHOULDER, 40));
		shotData.add(new ShotData(ShotData.CLOSE_UP, 20));
		shotData.add(new ShotData(ShotData.MEDIUM_SHOT, 40));
		expected.put(title0.hashCode(), new MovieData(title0, shotData));

		for (int key : expected.keySet()) {
			Assert.assertTrue(parsed.containsKey(key));
			Assert.assertEquals(expected.get(key), parsed.get(key));
		}
	}
}

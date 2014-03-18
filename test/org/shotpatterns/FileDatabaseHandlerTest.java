package org.shotpatterns;

import static org.mockito.Mockito.*;
import static org.shotpatterns.data.ShotData.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.shotpatterns.FileDatabaseHandler;
import org.shotpatterns.data.MovieData;
import org.shotpatterns.data.ShotData;
import org.shotpatterns.exception.InsufficientDataException;

public class FileDatabaseHandlerTest {

	@Test
	public void test_parse_database() throws IOException, NumberFormatException, InsufficientDataException {
		FileDatabaseHandler creator = new FileDatabaseHandler();
		BufferedReader mockReader = mock(BufferedReader.class);
		when(mockReader.readLine()).thenReturn("111222333;title;22;34;22;4;5;1;54;5;7",
		        "1234;title2;1;2;3;4;5;6;7;8;9", null);

		Map<Integer, MovieData> data = creator.parseDatabaseFile(mockReader);
		MovieData expected1 = new MovieData("title", new ShotData(OVER_THE_SHOULDER, 22), new ShotData(
		        EXTREME_CLOSE_UP, 34), new ShotData(CLOSE_UP, 22), new ShotData(MEDIUM_CLOSE_UP, 4), new ShotData(
		        MEDIUM_SHOT, 5), new ShotData(MEDIUM_LONG_SHOT, 1), new ShotData(LONG_SHOT, 54), new ShotData(
		        EXTREME_LONG_SHOT, 5), new ShotData(INSERT, 7));
		MovieData expected2 = new MovieData("title", new ShotData(OVER_THE_SHOULDER, 1), new ShotData(EXTREME_CLOSE_UP,
		        2), new ShotData(CLOSE_UP, 3), new ShotData(MEDIUM_CLOSE_UP, 4), new ShotData(MEDIUM_SHOT, 5),
		        new ShotData(MEDIUM_LONG_SHOT, 6), new ShotData(LONG_SHOT, 7), new ShotData(EXTREME_LONG_SHOT, 8),
		        new ShotData(INSERT, 9));
		Assert.assertEquals(data.get(111222333), expected1);
		Assert.assertEquals(data.get(1234), expected2);
	}

	@Test
	public void test_parse_db_contains_empty_lines() throws IOException, NumberFormatException,
	        InsufficientDataException {
		FileDatabaseHandler creator = new FileDatabaseHandler();
		BufferedReader mockReader = mock(BufferedReader.class);
		when(mockReader.readLine()).thenReturn("111222333;title;22;34;22;4;5;1;54;5;7", "", "",
		        "1234;title2;1;2;3;4;5;6;7;8;9", "", null);

		Map<Integer, MovieData> data = creator.parseDatabaseFile(mockReader);
		MovieData expected1 = new MovieData("title", new ShotData(OVER_THE_SHOULDER, 22), new ShotData(
		        EXTREME_CLOSE_UP, 34), new ShotData(CLOSE_UP, 22), new ShotData(MEDIUM_CLOSE_UP, 4), new ShotData(
		        MEDIUM_SHOT, 5), new ShotData(MEDIUM_LONG_SHOT, 1), new ShotData(LONG_SHOT, 54), new ShotData(
		        EXTREME_LONG_SHOT, 5), new ShotData(INSERT, 7));
		MovieData expected2 = new MovieData("title", new ShotData(OVER_THE_SHOULDER, 1), new ShotData(EXTREME_CLOSE_UP,
		        2), new ShotData(CLOSE_UP, 3), new ShotData(MEDIUM_CLOSE_UP, 4), new ShotData(MEDIUM_SHOT, 5),
		        new ShotData(MEDIUM_LONG_SHOT, 6), new ShotData(LONG_SHOT, 7), new ShotData(EXTREME_LONG_SHOT, 8),
		        new ShotData(INSERT, 9));
		Assert.assertEquals(data.get(111222333), expected1);
		Assert.assertEquals(data.get(1234), expected2);
	}

	@Test(expected = InsufficientDataException.class)
	public void test_parse_db_invalid_nr_of_shotdata() throws IOException, NumberFormatException,
	        InsufficientDataException {
		FileDatabaseHandler creator = new FileDatabaseHandler();
		BufferedReader mockReader = mock(BufferedReader.class);
		when(mockReader.readLine()).thenReturn("111222333;title;22;34;22;4;5;54;5;7", "", "",
		        "1234;title2;1;2;3;4;5;6;7;8;9", "", null);

		creator.parseDatabaseFile(mockReader);
	}

	@Test(expected = NumberFormatException.class)
	public void test_parse_db_invalid_data_in_db_file() throws IOException, NumberFormatException,
	        InsufficientDataException {
		FileDatabaseHandler creator = new FileDatabaseHandler();
		BufferedReader mockReader = mock(BufferedReader.class);
		when(mockReader.readLine()).thenReturn("111222333;title;22;eee;22;4;5;54;5;7", "", "",
		        "1234;title2;1;2;3;4;5;6;7;8;9", "", null);

		creator.parseDatabaseFile(mockReader);
	}
}

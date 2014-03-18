package org.shotpatterns;

import static org.shotpatterns.data.ShotData.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.shotpatterns.MovieDataFinder;
import org.shotpatterns.data.MovieData;
import org.shotpatterns.data.ShotData;
import org.shotpatterns.exception.InsufficientDataException;

public class MovieDataFinderTest {

	@Test
	public void test_same_data_for_two_movies() throws InsufficientDataException {
		MovieData expected = new MovieData("title", new ShotData(OVER_THE_SHOULDER, 14), new ShotData(EXTREME_CLOSE_UP,
		        17), new ShotData(CLOSE_UP, 20), new ShotData(MEDIUM_CLOSE_UP, 3), new ShotData(MEDIUM_SHOT, 19),
		        new ShotData(MEDIUM_CLOSE_UP, 3), new ShotData(LONG_SHOT, 9), new ShotData(EXTREME_LONG_SHOT, 3),
		        new ShotData(INSERT, 9));
		MovieData movie = new MovieData("title2", new ShotData(OVER_THE_SHOULDER, 14), new ShotData(EXTREME_CLOSE_UP,
		        17), new ShotData(CLOSE_UP, 20), new ShotData(MEDIUM_CLOSE_UP, 3), new ShotData(MEDIUM_SHOT, 19),
		        new ShotData(MEDIUM_CLOSE_UP, 3), new ShotData(LONG_SHOT, 9), new ShotData(EXTREME_LONG_SHOT, 3),
		        new ShotData(INSERT, 9));
		List<MovieData> dataList = new ArrayList<MovieData>();
		dataList.add(movie);

		MovieDataFinder finder = new MovieDataFinder();

		Assert.assertEquals(movie, finder.search(expected, dataList, 10).get(0));
	}

	@Test
	public void test_similar_data_for_two_movies() throws InsufficientDataException {
		MovieData expected = new MovieData("title", new ShotData(OVER_THE_SHOULDER, 12), new ShotData(EXTREME_CLOSE_UP,
		        20), new ShotData(CLOSE_UP, 23), new ShotData(MEDIUM_CLOSE_UP, 3), new ShotData(MEDIUM_SHOT, 15),
		        new ShotData(MEDIUM_CLOSE_UP, 8), new ShotData(LONG_SHOT, 6), new ShotData(EXTREME_LONG_SHOT, 3),
		        new ShotData(INSERT, 4));
		MovieData movie = new MovieData("title2", new ShotData(OVER_THE_SHOULDER, 14), new ShotData(EXTREME_CLOSE_UP,
		        17), new ShotData(CLOSE_UP, 20), new ShotData(MEDIUM_CLOSE_UP, 3), new ShotData(MEDIUM_SHOT, 19),
		        new ShotData(MEDIUM_CLOSE_UP, 3), new ShotData(LONG_SHOT, 9), new ShotData(EXTREME_LONG_SHOT, 3),
		        new ShotData(INSERT, 9));
		List<MovieData> dataList = new ArrayList<MovieData>();
		dataList.add(movie);

		MovieDataFinder finder = new MovieDataFinder();

		Assert.assertEquals(movie, finder.search(expected, dataList, 10).get(0));
	}

	@Test
	public void test_similar_one_value_is_different_for_two_movies() throws InsufficientDataException {
		MovieData expected = new MovieData("title", new ShotData(OVER_THE_SHOULDER, 12), new ShotData(EXTREME_CLOSE_UP,
		        20), new ShotData(CLOSE_UP, 23), new ShotData(MEDIUM_CLOSE_UP, 3), new ShotData(MEDIUM_SHOT, 15),
		        new ShotData(MEDIUM_CLOSE_UP, 28), new ShotData(LONG_SHOT, 6), new ShotData(EXTREME_LONG_SHOT, 3),
		        new ShotData(INSERT, 4));
		MovieData movie = new MovieData("title2", new ShotData(OVER_THE_SHOULDER, 14), new ShotData(EXTREME_CLOSE_UP,
		        17), new ShotData(CLOSE_UP, 20), new ShotData(MEDIUM_CLOSE_UP, 3), new ShotData(MEDIUM_SHOT, 19),
		        new ShotData(MEDIUM_CLOSE_UP, 3), new ShotData(LONG_SHOT, 9), new ShotData(EXTREME_LONG_SHOT, 3),
		        new ShotData(INSERT, 9));
		List<MovieData> dataList = new ArrayList<MovieData>();
		dataList.add(movie);

		MovieDataFinder finder = new MovieDataFinder();

		Assert.assertEquals(0, finder.search(expected, dataList, 10).size());
	}

	@Test
	public void test_similar_more_values_are_on_edge_for_two_movies() throws InsufficientDataException {
		MovieData expected = new MovieData("title1", new ShotData(OVER_THE_SHOULDER, 24), new ShotData(
		        EXTREME_CLOSE_UP, 17), new ShotData(CLOSE_UP, 13), new ShotData(MEDIUM_CLOSE_UP, 13), new ShotData(
		        MEDIUM_SHOT, 19), new ShotData(MEDIUM_CLOSE_UP, 3), new ShotData(LONG_SHOT, 9), new ShotData(
		        EXTREME_LONG_SHOT, 3), new ShotData(INSERT, 9));
		MovieData movie = new MovieData("title2", new ShotData(OVER_THE_SHOULDER, 14), new ShotData(EXTREME_CLOSE_UP,
		        17), new ShotData(CLOSE_UP, 13), new ShotData(MEDIUM_CLOSE_UP, 3), new ShotData(MEDIUM_SHOT, 19),
		        new ShotData(MEDIUM_CLOSE_UP, 3), new ShotData(LONG_SHOT, 9), new ShotData(EXTREME_LONG_SHOT, 3),
		        new ShotData(INSERT, 9));
		List<MovieData> dataList = new ArrayList<MovieData>();
		dataList.add(movie);

		MovieDataFinder finder = new MovieDataFinder();

		Assert.assertEquals(movie, finder.search(expected, dataList, 10).get(0));
	}

	@Test
	public void test_similar_more_values_differ_by_one_for_two_movies() throws InsufficientDataException {
		MovieData expected = new MovieData("title1", new ShotData(OVER_THE_SHOULDER, 14), new ShotData(
		        EXTREME_CLOSE_UP, 17), new ShotData(CLOSE_UP, 13), new ShotData(MEDIUM_CLOSE_UP, 14), new ShotData(
		        MEDIUM_SHOT, 19), new ShotData(MEDIUM_CLOSE_UP, 14), new ShotData(LONG_SHOT, 9), new ShotData(
		        EXTREME_LONG_SHOT, 3), new ShotData(INSERT, 9));
		MovieData movie = new MovieData("title2", new ShotData(OVER_THE_SHOULDER, 14), new ShotData(EXTREME_CLOSE_UP,
		        17), new ShotData(CLOSE_UP, 13), new ShotData(MEDIUM_CLOSE_UP, 3), new ShotData(MEDIUM_SHOT, 19),
		        new ShotData(MEDIUM_CLOSE_UP, 3), new ShotData(LONG_SHOT, 9), new ShotData(EXTREME_LONG_SHOT, 3),
		        new ShotData(INSERT, 9));
		List<MovieData> dataList = new ArrayList<MovieData>();
		dataList.add(movie);

		MovieDataFinder finder = new MovieDataFinder();

		Assert.assertEquals(0, finder.search(expected, dataList, 10).size());
	}
}

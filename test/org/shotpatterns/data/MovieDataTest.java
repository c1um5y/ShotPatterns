package org.shotpatterns.data;

import static org.shotpatterns.data.ShotData.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.shotpatterns.data.MovieData;
import org.shotpatterns.data.ShotData;
import org.shotpatterns.exception.InsufficientDataException;

public class MovieDataTest {

	@Test
	public void test_toString() throws InsufficientDataException {
		ShotData data1 = new ShotData(OVER_THE_SHOULDER, 11);
		ShotData data2 = new ShotData(CLOSE_UP, 3);
		ShotData data3 = new ShotData(MEDIUM_CLOSE_UP, 88);
		ShotData data4 = new ShotData(MEDIUM_SHOT, 88);
		ShotData data5 = new ShotData(MEDIUM_LONG_SHOT, 88);
		ShotData data6 = new ShotData(LONG_SHOT, 88);
		ShotData data7 = new ShotData(LONG_SHOT, 88);
		ShotData data8 = new ShotData(EXTREME_LONG_SHOT, 88);
		ShotData data9 = new ShotData(INSERT, 88);

		List<ShotData> shotDataList = new ArrayList<ShotData>();
		shotDataList.add(data1);
		shotDataList.add(data2);
		shotDataList.add(data3);
		shotDataList.add(data4);
		shotDataList.add(data5);
		shotDataList.add(data6);
		shotDataList.add(data7);
		shotDataList.add(data8);
		shotDataList.add(data9);

		MovieData movieData = new MovieData("title", shotDataList);

		Assert.assertEquals("title;11.0;3.0;88.0;88.0;88.0;88.0;88.0;88.0;88.0", movieData.toString());
	}
}

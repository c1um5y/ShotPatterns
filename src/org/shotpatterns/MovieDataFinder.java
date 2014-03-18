package org.shotpatterns;

import java.util.ArrayList;
import java.util.List;

import org.shotpatterns.data.MovieData;

public class MovieDataFinder {

	public List<MovieData> search(MovieData selectedItem, List<MovieData> items, int percent) {
		List<MovieData> similarMovies = new ArrayList<MovieData>();
		for (MovieData data : items) {
			if (!selectedItem.getOts().isNear(data.getOts(), percent)) {
				continue;
			}
			if (!selectedItem.getEcu().isNear(data.getEcu(), percent)) {
				continue;
			}
			if (!selectedItem.getCu().isNear(data.getCu(), percent)) {
				continue;
			}
			if (!selectedItem.getMcu().isNear(data.getMcu(), percent)) {
				continue;
			}
			if (!selectedItem.getMs().isNear(data.getMs(), percent)) {
				continue;
			}
			if (!selectedItem.getMls().isNear(data.getMls(), percent)) {
				continue;
			}
			if (!selectedItem.getLs().isNear(data.getLs(), percent)) {
				continue;
			}
			if (!selectedItem.getEls().isNear(data.getEls(), percent)) {
				continue;
			}
			if (!selectedItem.getIns().isNear(data.getIns(), percent)) {
				continue;
			}
			similarMovies.add(data);
		}

		return similarMovies;
	}
}

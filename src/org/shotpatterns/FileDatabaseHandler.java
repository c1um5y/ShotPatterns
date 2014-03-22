package org.shotpatterns;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.shotpatterns.data.MovieData;
import org.shotpatterns.data.ShotData;
import org.shotpatterns.exception.InsufficientDataException;

public class FileDatabaseHandler {

	private static final int KEY_POSITION = 0;
	private static final int MOVIE_TITLE_POS = 1;
	private static final int PERCENTAGE_START_POS = MOVIE_TITLE_POS + 1;
	public static final String SEPARATOR = ";";

	public BufferedWriter createFileWriter(String fileName, StandardOpenOption option) throws IOException {
		Path databaseFile = getDatabaseFile(fileName);
		BufferedWriter databaseWriter = Files.newBufferedWriter(databaseFile, StandardCharsets.UTF_8, option);

		return databaseWriter;
	}

	public BufferedReader createFileReader(String fileName) throws IOException {
		Path databaseFile = getDatabaseFile(fileName);
		BufferedReader databaseReader = Files.newBufferedReader(databaseFile, StandardCharsets.UTF_8);

		return databaseReader;
	}

	private Path getDatabaseFile(String fileName) throws IOException {
		Path databaseFile = Paths.get(fileName);
		if (!Files.exists(databaseFile)) {
			Files.createFile(databaseFile);
		}
		return databaseFile;
	}

	public void createDatabaseFile(Map<Integer, MovieData> data, Writer writer, List<Integer> existingKeys)
	        throws IOException {
		for (int key : data.keySet()) {
			if (!existingKeys.contains(key)) {
				writer.write(key + SEPARATOR + data.get(key));
				writer.write(System.getProperty("line.separator"));
			}
		}
	}

	public Map<Integer, MovieData> parseDatabaseFile(BufferedReader reader) throws IOException, NumberFormatException,
	        InsufficientDataException {
		Map<Integer, MovieData> movieData = new HashMap<Integer, MovieData>();
		for (String line = reader.readLine(); line != null; line = reader.readLine()) {
			if (line != "") {
				String[] data = line.split(SEPARATOR);
				List<ShotData> shotData = new ArrayList<ShotData>();
				for (int i = PERCENTAGE_START_POS; i < data.length; i++) {
					shotData.add(new ShotData(i - PERCENTAGE_START_POS, Double.parseDouble(data[i])));
				}
				movieData.put(Integer.parseInt(data[KEY_POSITION]), new MovieData(data[MOVIE_TITLE_POS], shotData));
			}
		}
		return movieData;
	}
}

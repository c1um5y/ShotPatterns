package org.shotpatterns.ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.shotpatterns.FileDatabaseHandler;
import org.shotpatterns.MovieDataFileParser;
import org.shotpatterns.MovieDataFinder;
import org.shotpatterns.data.MovieData;
import org.shotpatterns.exception.InsufficientDataException;
import org.shotpatterns.exception.InvalidCellException;
import org.shotpatterns.exception.TitleAlreadyExistsException;

public class ShotPatternsFX extends Application {

	private static final int BUTTON_HEIGHT = 30;
	private static final String VERSION = "actor (0.1)";
	private static final String MOVIE_DB_FILE = "movieDB.csv";
	private static final int DEFAULT_PERCENTAGE = 5;
	private TableView<MovieData> moviesTable = new TableView<MovieData>();
	private FileDatabaseHandler dbHandler = new FileDatabaseHandler();
	private FileChooser excelChooser = new FileChooser();
	private Map<Integer, MovieData> existingMovies = new HashMap<Integer, MovieData>();
	private ComboBox<Integer> selectablePercentages;

	@Override
	public void start(Stage stage) throws Exception {
		initializeTable();

		excelChooser.getExtensionFilters().add(new ExtensionFilter("Excel files (*.xls, *.xlsx)", "*.xls", "*.xlsx"));
		excelChooser.getExtensionFilters().add(new ExtensionFilter("All files (*.*)", "*.*"));

		Button loadFiles = new Button("Load excel files");
		loadFiles.setMinHeight(BUTTON_HEIGHT);
		loadFiles.setOnAction(new LoadExcelFilesButtonHandler(stage));

		Button search = new Button("Search");
		search.setMinHeight(BUTTON_HEIGHT);
		search.setOnAction(new SearchSimilarMoviesButtonHandler());
		search.setTooltip(new Tooltip("Search for movies similar to the selected one"));

		Button about = new Button("About");
		about.setMinHeight(BUTTON_HEIGHT);
		about.setOnAction(new AboutButtonHandler());

		ObservableList<Integer> percentageValues = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
		        12, 13, 14, 15);
		selectablePercentages = new ComboBox<Integer>(percentageValues);
		selectablePercentages.setMinHeight(BUTTON_HEIGHT);
		selectablePercentages.setValue(DEFAULT_PERCENTAGE);
		selectablePercentages.setTooltip(new Tooltip("Set the maximum difference in percentage"));
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(moviesTable);
		HBox buttonPane = new HBox();
		buttonPane.getChildren().add(loadFiles);
		buttonPane.getChildren().add(search);
		buttonPane.getChildren().add(about);
		buttonPane.getChildren().add(selectablePercentages);
		borderPane.setTop(buttonPane);
		borderPane.setMinHeight(100);
		Scene scene = new Scene(borderPane);
		stage.setTitle("ShotPatterns");
		stage.setWidth(1000);
		stage.setHeight(500);
		stage.setScene(scene);
		stage.getIcons().add(new Image(ShotPatternsFX.class.getResourceAsStream("/resources/icon.png")));
		stage.show();
	}

	private void initializeTable() throws IOException, InsufficientDataException {
		createDataTable(moviesTable);
		BufferedReader reader = dbHandler.createFileReader(System.getProperty("user.home")
		        + System.getProperty("file.separator") + MOVIE_DB_FILE);
		existingMovies = dbHandler.parseDatabaseFile(reader);
		ObservableList<MovieData> databaseElements = FXCollections.observableList(new ArrayList<MovieData>(
		        existingMovies.values()));
		moviesTable.setItems(databaseElements);
		reader.close();
	}

	public static void createDataTable(TableView<MovieData> moviesTable) {
		TableColumn<MovieData, String> title = new TableColumn<MovieData, String>("Title");
		moviesTable.getColumns().add(title);
		title.prefWidthProperty().bind(moviesTable.widthProperty().multiply(0.19));
		title.setCellValueFactory(new PropertyValueFactory<MovieData, String>("title"));
		TableColumn<MovieData, String> ots = new TableColumn<MovieData, String>("Over-the-shoulder (%)");
		moviesTable.getColumns().add(ots);
		ots.prefWidthProperty().bind(moviesTable.widthProperty().multiply(0.09));
		ots.setCellValueFactory(new PropertyValueFactory<MovieData, String>("ots"));
		TableColumn<MovieData, String> ecu = new TableColumn<MovieData, String>("Extreme close up (%)");
		moviesTable.getColumns().add(ecu);
		ecu.prefWidthProperty().bind(moviesTable.widthProperty().multiply(0.09));
		ecu.setCellValueFactory(new PropertyValueFactory<MovieData, String>("ecu"));
		TableColumn<MovieData, String> cu = new TableColumn<MovieData, String>("Close up (%)");
		moviesTable.getColumns().add(cu);
		cu.prefWidthProperty().bind(moviesTable.widthProperty().multiply(0.09));
		cu.setCellValueFactory(new PropertyValueFactory<MovieData, String>("cu"));
		TableColumn<MovieData, String> mcu = new TableColumn<MovieData, String>("Medium close up (%)");
		moviesTable.getColumns().add(mcu);
		mcu.prefWidthProperty().bind(moviesTable.widthProperty().multiply(0.09));
		mcu.setCellValueFactory(new PropertyValueFactory<MovieData, String>("mcu"));
		TableColumn<MovieData, String> ms = new TableColumn<MovieData, String>("Medium shot (%)");
		moviesTable.getColumns().add(ms);
		ms.prefWidthProperty().bind(moviesTable.widthProperty().multiply(0.09));
		ms.setCellValueFactory(new PropertyValueFactory<MovieData, String>("ms"));
		TableColumn<MovieData, String> mls = new TableColumn<MovieData, String>("Medium long shot (%)");
		moviesTable.getColumns().add(mls);
		mls.prefWidthProperty().bind(moviesTable.widthProperty().multiply(0.09));
		mls.setCellValueFactory(new PropertyValueFactory<MovieData, String>("mls"));
		TableColumn<MovieData, String> ls = new TableColumn<MovieData, String>("Long shot (%)");
		moviesTable.getColumns().add(ls);
		ls.prefWidthProperty().bind(moviesTable.widthProperty().multiply(0.09));
		ls.setCellValueFactory(new PropertyValueFactory<MovieData, String>("ls"));
		TableColumn<MovieData, String> els = new TableColumn<MovieData, String>("Extreme long shot (%)");
		moviesTable.getColumns().add(els);
		els.prefWidthProperty().bind(moviesTable.widthProperty().multiply(0.09));
		els.setCellValueFactory(new PropertyValueFactory<MovieData, String>("els"));
		TableColumn<MovieData, String> ins = new TableColumn<MovieData, String>("Insert (%)");
		moviesTable.getColumns().add(ins);
		ins.prefWidthProperty().bind(moviesTable.widthProperty().multiply(0.09));
		ins.setCellValueFactory(new PropertyValueFactory<MovieData, String>("ins"));
	}

	private ObservableList<MovieData> loadElements(FileDatabaseHandler dbHandler, TableView<MovieData> moviesTable)
	        throws IOException, InsufficientDataException {
		ObservableList<MovieData> databaseElements = FXCollections.observableList(new ArrayList<MovieData>(
		        existingMovies.values()));
		moviesTable.setItems(databaseElements);

		return databaseElements;
	}

	public List<Integer> getExistingKeys(Map<Integer, MovieData> dataMap) {
		List<Integer> keys = new ArrayList<Integer>();
		for (int key : dataMap.keySet()) {
			keys.add(key);
		}

		return keys;
	}

	public static void main(String[] args) {
		launch(args);
	}

	private class LoadExcelFilesButtonHandler implements EventHandler<ActionEvent> {

		private Stage stage;

		public LoadExcelFilesButtonHandler(Stage stage) {
			this.stage = stage;
		}

		@Override
		public void handle(ActionEvent event) {
			moviesTable.setDisable(true);
			List<File> files = excelChooser.showOpenMultipleDialog(stage);
			if (files != null) {
				StringBuilder invalidFiles = new StringBuilder();
				for (File file : files) {
					MovieDataFileParser parser = new MovieDataFileParser();
					Map<Integer, MovieData> movieData;
					BufferedWriter writer = null;
					try {
						movieData = parser.parse(file, 0, 2, 0, 2, 4, existingMovies.keySet());
						List<Integer> existingKeys = getExistingKeys(existingMovies);
						writer = dbHandler.createFileWriter(System.getProperty("user.home")
						        + System.getProperty("file.separator") + MOVIE_DB_FILE);
						dbHandler.createDatabaseFile(movieData, writer, existingKeys);
					} catch (InvalidFormatException e) {
						invalidFiles.append("Invalid excel format: " + file.getName() + "\n");
					} catch (InvalidCellException e) {
						invalidFiles.append("Invalid cell found in file: " + file.getName() + "\n");
					} catch (InsufficientDataException e) {
						// TODO can't happen
					} catch (TitleAlreadyExistsException e) {
						invalidFiles.append("File is already in the DB: " + file.getName() + "\n");
					} catch (FileNotFoundException e) {
						invalidFiles.append("File not found: " + file.getName() + "\n");
					} catch (IOException ex) {
						invalidFiles.append("IO error while parsing the excel file: " + file.getName() + "\n");
					} finally {
						try {
							if (writer != null) {
								writer.close();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

				BufferedReader reader = null;
				try {
					reader = dbHandler.createFileReader(System.getProperty("user.home")
					        + System.getProperty("file.separator") + MOVIE_DB_FILE);
					existingMovies = dbHandler.parseDatabaseFile(reader);
					loadElements(dbHandler, moviesTable);
				} catch (InsufficientDataException e) {
					e.printStackTrace();
				} catch (IOException ex) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (!invalidFiles.toString().equals("")) {
					DialogFX dialog = new DialogFX("", invalidFiles.toString(), ((Node) event.getSource()).getScene()
					        .getWindow(), 500, 200);
					dialog.addOkButton();
					dialog.show();
				}
			}
			moviesTable.setDisable(false);
		}
	}

	private class SearchSimilarMoviesButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			MovieData selectedItem = moviesTable.getSelectionModel().getSelectedItem();
			if (selectedItem == null) {
				DialogFX dialog = new DialogFX("Information", "No movie selected!", ((Node) event.getSource())
				        .getScene().getWindow(), 200, 100);
				dialog.addOkButton();
				dialog.show();

				return;
			}
			ObservableList<MovieData> items = moviesTable.getItems();
			MovieDataFinder finder = new MovieDataFinder();
			List<MovieData> foundItems = finder.search(selectedItem, items, selectablePercentages.getSelectionModel()
			        .getSelectedItem());

			if (foundItems.size() == 1) {
				DialogFX dialog = new DialogFX("Information", "No similar movie found!", ((Node) event.getSource())
				        .getScene().getWindow(), 200, 100);
				dialog.addOkButton();
				dialog.show();
			} else {
				TableView<MovieData> moviesTable = new TableView<MovieData>();
				ShotPatternsFX.createDataTable(moviesTable);
				moviesTable.setItems(FXCollections.observableList(foundItems));

				BorderPane borderPane = new BorderPane();
				borderPane.setCenter(moviesTable);
				Scene scene = new Scene(borderPane);
				Stage stage = new Stage();
				stage.setTitle("Similar movies found");
				stage.setWidth(900);
				stage.setHeight(200);
				stage.setScene(scene);
				stage.initOwner(((Node) event.getSource()).getScene().getWindow());
				stage.show();
			}
		}
	}

	private class AboutButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			new DialogFX("About", "ShotPatterns\nVersion: " + VERSION + "\n\nAuthor: Kornel Jenei",
			        ((Node) event.getSource()).getScene().getWindow(), 200, 100).show();
		}

	}
}
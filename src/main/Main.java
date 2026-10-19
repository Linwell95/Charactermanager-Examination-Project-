package main;

import java.util.ArrayList;
import java.util.List;

import controller.MainController;
import db.DatabaseManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;


public class Main extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		// DatabaseManager.dropAllTables();
		DatabaseManager.initDatabase();
		
		MainController mainController = new MainController();
		
		Scene scene = new Scene(mainController.getMainView());
		scene.getStylesheets().add(getClass().getResource("/CSS/default.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("Charaktermanager");
		stage.setOnCloseRequest(event -> {
			List<Window> windows = new ArrayList<>(Window.getWindows());
		    for (Window w : windows) {
		        if (w instanceof Stage && w != stage) {
		            ((Stage) w).close();
		        }
		    }
		});
		stage.show();
	}

}

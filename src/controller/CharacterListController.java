package controller;

import java.util.Optional;

import db.DatabaseService;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Character;
import view.CharacterListCell;
import view.CharacterListView;

public class CharacterListController {

	private final MainController mainController;
	
	private final CharacterListView characterListView;
	
	private final ObservableList<Character> characterList;
	
	private final ObjectProperty<Character> selectedCharacterProperty = new SimpleObjectProperty<>();
	
	// =============================================
	// Constructor
	// =============================================
	public CharacterListController(MainController mainController) {
		this.mainController = mainController;
		this.characterListView = mainController.getCharacterListview();
		this.characterList = mainController.getMasterList();
		
		this.characterListView.getListView().setCellFactory(listView -> new CharacterListCell());
		this.characterListView.getListView().setItems(characterList);
		
		this.selectedCharacterProperty.set(null);
		
		setupEventHandlers();
	}
	
    
	// =============================================
	// Event handlers
	// =============================================
	private void setupEventHandlers() {
		// LISTENER
		this.characterListView.getListView().getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {			
			this.selectedCharacterProperty.set(newValue);
			mainController.setSelectedCharacter(newValue);	
		});
		
		// DELETE
		this.characterListView.getBtnDelete().setOnAction(e -> {
			if (this.selectedCharacterProperty.get() == null) return;
			
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.getDialogPane().getStylesheets().add(getClass().getResource("/CSS/default.css").toExternalForm());
			alert.setTitle("Character löschen");
			alert.setHeaderText("Willst du '" + this.selectedCharacterProperty.get().getName() + "' wirklich löschen?");
			alert.setContentText("Dieser Vorgang kann nich rückgängig gemacht werden.");
			
			Optional<ButtonType> result = alert.showAndWait();
			
			if (result.isPresent() && result.get() == ButtonType.OK) {
				// IMPORTANT!!! First delete from DB because after deleting from list, character does not
				// exists and selection model would take the next one, witch would be deleted from DB!
				DatabaseService.deleteCharacter(this.selectedCharacterProperty.get());
				this.characterList.remove(this.selectedCharacterProperty.get());
			}
		});
		
		this.characterListView.getBtnDelete().disableProperty().bind(Bindings.isNull(selectedCharacterProperty));
		
		// CREATE
		this.characterListView.getBtnNew().setOnAction(e -> {
			CharacterCreationController characterCreationController = new CharacterCreationController(this.mainController);
			characterCreationController.openCharacterCreation();
		});
	}
}

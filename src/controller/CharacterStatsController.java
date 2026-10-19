package controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.Character;
import view.CharacterStatsView;

public class CharacterStatsController {

	private final CharacterStatsView characterStatsView;
	private final ObjectProperty<Character> selectedCharacterProperty = new SimpleObjectProperty<>();
	
	public CharacterStatsController(MainController mainController) {
		this.characterStatsView = mainController.getCharacterStatsView();
		
		setupEventHandlers();
	}
	
	public void setSelectedCharacter(Character selectedCharacter) {
		this.selectedCharacterProperty.set(selectedCharacter);
		if(this.selectedCharacterProperty.get() != null) this.characterStatsView.bindCharacter(selectedCharacter);
	}
	
	private void setupEventHandlers() {
		this.characterStatsView.getBtnEdit().setOnAction(e -> {
			CharacterEditController characterEditController = new CharacterEditController();
			characterEditController.openCharacterEdit(selectedCharacterProperty.get());
		});
		
		this.characterStatsView.getBtnEdit().disableProperty().bind(Bindings.isNull(selectedCharacterProperty));
	}
}

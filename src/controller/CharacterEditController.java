package controller;

import db.DatabaseService;
import model.Character;
import view.CharacterEditWindow;

public class CharacterEditController {

	private final CharacterEditWindow characterEditWindow;
	
	private Character selectedCharacter;
	
	public CharacterEditController() {
		this.characterEditWindow = new CharacterEditWindow();
		
		setupEventHandler();
	}
	
	public void openCharacterEdit(Character selectedCharacter) {
		this.selectedCharacter = selectedCharacter;
		
		if(this.selectedCharacter == null) return;
		
		this.characterEditWindow.getChaSpinner().getValueFactory().setValue(selectedCharacter.getCharisma());
		this.characterEditWindow.getConSpinner().getValueFactory().setValue(selectedCharacter.getConstitution());
		this.characterEditWindow.getIntSpinner().getValueFactory().setValue(selectedCharacter.getIntelligence());
		this.characterEditWindow.getDexSpinner().getValueFactory().setValue(selectedCharacter.getDexterity());
		this.characterEditWindow.getWisSpinner().getValueFactory().setValue(selectedCharacter.getWisdom());
		this.characterEditWindow.getStrSpinner().getValueFactory().setValue(selectedCharacter.getStrength());

		this.characterEditWindow.getTextFieldName().setText(selectedCharacter.getName());
		
		this.characterEditWindow.getRaceComboBox().getSelectionModel().select(selectedCharacter.getSpecies());
		this.characterEditWindow.getClassComboBox().getSelectionModel().select(selectedCharacter.getCharacterClass());
		
		this.characterEditWindow.show();
	}
	

    // =============================================
    // Event Handlers
    // =============================================
    private void setupEventHandler() {
        this.characterEditWindow.getBtnCancel().setOnAction(e -> this.characterEditWindow.close());
        this.characterEditWindow.getBtnApply().setOnAction(e -> {
            this.selectedCharacter.setName(characterEditWindow.getInputName().trim());
            
            this.selectedCharacter.setSpecies(this.characterEditWindow.getRaceComboBox().getValue());
            this.selectedCharacter.setCharacterClass(this.characterEditWindow.getClassComboBox().getValue());
            
            this.selectedCharacter.setStrength(this.characterEditWindow.getInputStr());
            this.selectedCharacter.setDexterity(this.characterEditWindow.getInputDex());
            this.selectedCharacter.setConstitution(this.characterEditWindow.getInputCon());
            this.selectedCharacter.setCharisma(this.characterEditWindow.getInputCha());
            this.selectedCharacter.setWisdom(this.characterEditWindow.getInputWis());
            this.selectedCharacter.setIntelligence(this.characterEditWindow.getInputInt());

            DatabaseService.safeCharactersStats(selectedCharacter);
            
            this.characterEditWindow.close();
        });       
    }
}

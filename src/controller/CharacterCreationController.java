package controller;

import db.DatabaseService;
import model.Character;
import model.CharacterClass;
import model.Species;
import view.CharacterCreationWindow;

public class CharacterCreationController {
    private final CharacterCreationWindow characterCreationWindow;
    private final MainController mainController;


    // =============================================
    // Constructor
    // =============================================
    public CharacterCreationController (MainController mainController) {
        this.characterCreationWindow = new CharacterCreationWindow();

        this.mainController = mainController;
        
        setupEventHandler();
    }


    // =============================================
    // Open character creation window
    // =============================================
    public void openCharacterCreation() {
        this.characterCreationWindow.showAndWait();
    }


    // =============================================
    // Event Handlers
    // =============================================
    private void setupEventHandler() {
        this.characterCreationWindow.getBtnCancel().setOnAction(e -> this.characterCreationWindow.close());
        this.characterCreationWindow.getBtnCreate().setOnAction(e -> {
            String name = this.characterCreationWindow.getInputName();
            Species race = this.characterCreationWindow.getRaceComboBox().getValue();
            CharacterClass characterClass = this.characterCreationWindow.getClassComboBox().getValue();
            int str = this.characterCreationWindow.getInputStr();
            int dex = this.characterCreationWindow.getInputDex();
            int con = this.characterCreationWindow.getInputCon();
            int cha = this.characterCreationWindow.getInputCha();
            int wis = this.characterCreationWindow.getInputWis();
            int intelligence = this.characterCreationWindow.getInputInt();
            
            Character character = new Character(0, name, race, characterClass, str, dex, con, intelligence, wis, cha);
            DatabaseService.addCharacter(character);
            this.mainController.getMasterList().add(character);
            
            this.characterCreationWindow.close();
        });       
    }
}

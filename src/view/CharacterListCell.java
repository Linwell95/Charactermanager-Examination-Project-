package view;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import model.Character;

public class CharacterListCell extends ListCell<Character> {
    private Label lblName;
    private Label lblSpeciesAndClass;
    

    // =============================================
    // Constructor
    // =============================================
    public CharacterListCell () {
        // the overridden updateItem method controls the 
        // view. so if cell is needed in list, a new 'banner'
        // is created, also if something changed the banner
        // is updated. If its empty or character is deleted the cell shows
        // null instead of the banner
    }


    // =============================================
    // Visuals for the character banner
    // =============================================
    private VBox createCharacterBanner(Character character) {
        VBox banner = new VBox();

        this.lblName = new Label(character.getName());
        this.lblName.getStyleClass().add("label-bold");
        
        this.lblSpeciesAndClass = new Label(character.getSpecies().getDisplayName() + " " + character.getCharacterClass().getDisplayName());

        banner.getChildren().addAll(
            lblName,
            lblSpeciesAndClass
        );
        
        banner.getStyleClass().add("character-list-cell");

        return banner;
    }


    // =============================================
    // Overrides
    // =============================================
    @Override
    protected void updateItem(Character character, boolean empty) {
        super.updateItem(character, empty);
        if (empty || character == null) {
            setGraphic(null);
        } else {
            setGraphic(createCharacterBanner(character));
        }
    }
    
}

package view;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import model.InventoryItem;

public class CharacterInventoryCell extends ListCell<InventoryItem> {
	private Label lblQuantity;
	private Label lblName;
	
    // =============================================
    // Constructor
    // =============================================
    public CharacterInventoryCell () {
    	
    }

    // =============================================
    // Overrides
    // =============================================
    @Override
    protected void updateItem(InventoryItem inventoryItem, boolean empty) {
        super.updateItem(inventoryItem, empty);
        if (empty || inventoryItem == null) {
            setGraphic(null);
        } else {
        	if (lblQuantity == null) {
                lblQuantity = new Label();
                lblName = new Label();
            }

            // Name
            lblName.setText(inventoryItem.getItem().getName());

            // Quantity LIVE binding
            lblQuantity.textProperty().bind(inventoryItem.quantityProperty().asString());

            HBox cell = new HBox(lblQuantity, new Label(" x "), lblName);
            cell.getStyleClass().add("character-inventory-cell");
            
            UIToolbox toolbox = new UIToolbox();
            Tooltip tooltip = toolbox.createTooltip(toolbox.createTooltipString(inventoryItem.getItem()));
            
            Tooltip.install(cell, tooltip);
            
            setGraphic(cell);        
        }
    }
}

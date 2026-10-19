package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.InventoryItem;

public class CharacterInventoryView extends VBox {
	private Button btnDelete;
	private Button btnEquip;
	
	private ListView<InventoryItem> inventoryItemView;
	
	public CharacterInventoryView() {
		this.getChildren().addAll(
				createInventoryBox(),
				createButtonBox());
		
		this.getStyleClass().add("character-inventory-view");
		this.setSpacing(10);
	}
	
	private HBox createButtonBox() {
		HBox buttonBox = new HBox(5);
		this.btnDelete = new Button("Löschen");
		this.btnEquip = new Button("Ausrüsten");
		
		buttonBox.getChildren().addAll(
				this.btnDelete,
				this.btnEquip);
		
		buttonBox.setAlignment(Pos.BASELINE_RIGHT);
		return buttonBox;
	}
	
	private VBox createInventoryBox() {
		VBox inventoryBox = new VBox();
		VBox.setVgrow(inventoryBox, Priority.ALWAYS);
		
		this.inventoryItemView = new ListView<>();
		VBox.setVgrow(inventoryItemView, Priority.ALWAYS);
		
		inventoryBox.getChildren().add(inventoryItemView);
		inventoryBox.getStyleClass().add("character-inventory-box");
		
		return inventoryBox;
	}
	
	public Button getBtnDelete() { return this.btnDelete; }
	public Button getBtnEquip() { return this.btnEquip; }
	
	public ListView<InventoryItem> getInventoryItemView() { return this.inventoryItemView; }
}

package controller;

import db.DatabaseService;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import model.Character;
import model.InventoryItem;
import view.CharacterInventoryCell;
import view.CharacterInventoryView;

public class CharacterInventoryController {
	
	private final CharacterInventoryView characterInventoryView;
	
	private ObservableList<InventoryItem> inventory;
	
	private final ObjectProperty<InventoryItem> selectedInventoryItemProperty = new SimpleObjectProperty<>();
	private final ObjectProperty<Character> selectedCharacterProperty = new SimpleObjectProperty<>();
	
	
	public CharacterInventoryController(MainController mainController) {
		this.characterInventoryView = mainController.getCharacterInventoryView();
		
		setupEventHandlers();
	}
	
	public void setSelectedCharacter(Character character) {
		this.selectedCharacterProperty.set(character);
		this.selectedInventoryItemProperty.set(null);
		
		if (this.selectedCharacterProperty.get() != null) this.inventory = this.selectedCharacterProperty.get().getInventory();
		this.characterInventoryView.getInventoryItemView().setCellFactory(listView -> new CharacterInventoryCell());
		this.characterInventoryView.getInventoryItemView().setItems(inventory);
		
	}
	
	private void setupEventHandlers() {
		// LISTENER
		this.characterInventoryView.getInventoryItemView().getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			this.selectedInventoryItemProperty.set(newValue);
		});
		
		// DELETE
		this.characterInventoryView.getBtnDelete().setOnAction(e -> {
			if (this.selectedInventoryItemProperty.get() == null || this.selectedInventoryItemProperty.get() == null) return;
			this.selectedCharacterProperty.get().removeItemFromInventory(this.selectedInventoryItemProperty.get().getItem(), 1);
			DatabaseService.safeCharactersInventory(selectedCharacterProperty.get());
		});
		
		this.characterInventoryView.getBtnDelete().disableProperty().bind(
			Bindings.or(
				Bindings.isNull(selectedCharacterProperty),
				Bindings.isNull(selectedInventoryItemProperty)
			)
		);
		
		this.characterInventoryView.getBtnEquip().disableProperty().bind(
			Bindings.or(
				Bindings.isNull(selectedCharacterProperty),
				Bindings.isNull(selectedInventoryItemProperty)
			)
		);
		// EQUIP
		this.characterInventoryView.getBtnEquip().setOnAction(e -> {
			if (this.selectedCharacterProperty.get() == null || this.selectedInventoryItemProperty.get() == null) return;
			this.selectedCharacterProperty.get().equipItem(this.selectedInventoryItemProperty.get().getItem());
			DatabaseService.safeCharactersEquipment(selectedCharacterProperty.get());
			DatabaseService.safeCharactersInventory(selectedCharacterProperty.get());
		});
	}
}

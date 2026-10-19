package controller;

import view.CharacterEquipmentView;
import db.DatabaseService;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import model.Character;
import model.Slot;

public class CharacterEquipmentController {
	
	private final CharacterEquipmentView characterEquipmentView;
	
	private Character selectedCharacter;
	
	public CharacterEquipmentController(MainController mainController) {
		this.characterEquipmentView = mainController.getCharacterEquipmentView();
		
		setupEventHandlers();
	}
	
	public void setSelectedCharacter(Character character) {
		this.selectedCharacter = character;
		if (this.selectedCharacter != null) this.characterEquipmentView.bindEquipment(this.selectedCharacter.getEquipment());
	}
	
	
	private void handleSlotClick(MouseEvent event, StackPane slotPane) {
		// If not right click -> return
		// The event to unequip is on right click
		if (event.getButton() != MouseButton.SECONDARY) return;
		
		if (this.selectedCharacter == null) return;
		if (this.selectedCharacter.getEquipment() == null) return;
		
		Slot slot = (Slot) slotPane.getUserData();
			
		this.selectedCharacter.unequipItem(slot);
		
		DatabaseService.safeCharactersInventory(selectedCharacter);
		DatabaseService.safeCharactersEquipment(selectedCharacter);
	}
	
	private void setupEventHandlers() {
		
		// add an on mouse clicked event listener to all slots
		this.characterEquipmentView.getHeadPane().setOnMouseClicked(event -> handleSlotClick(event, this.characterEquipmentView.getHeadPane()));
		this.characterEquipmentView.getChestPane().setOnMouseClicked(event -> handleSlotClick(event, this.characterEquipmentView.getChestPane()));
		this.characterEquipmentView.getHandsPane().setOnMouseClicked(event -> handleSlotClick(event, this.characterEquipmentView.getHandsPane()));
		this.characterEquipmentView.getLegsPane().setOnMouseClicked(event -> handleSlotClick(event, this.characterEquipmentView.getLegsPane()));
		this.characterEquipmentView.getMainHandPane().setOnMouseClicked(event -> handleSlotClick(event, this.characterEquipmentView.getMainHandPane()));
		this.characterEquipmentView.getOffHandPane().setOnMouseClicked(event -> handleSlotClick(event, this.characterEquipmentView.getOffHandPane()));
		
	}
	
}

package controller;

import java.util.List;

import db.DatabaseService;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import model.Armor;
import model.Character;
import model.Filter;
import model.Item;
import model.Slot;
import model.Weapon;
import model.WeaponType;
import view.ItemShopWindow;

public class ItemShopController {

	private final ItemShopWindow itemShopWindow;
	
	private ObservableList<Item> observableItemList;
	private FilteredList<Item> filteredItemList;
	
	// changed the Character and Item objects to properties for button property (disabled if null)
	private final ObjectProperty<Character> selectedCharacterProperty = new SimpleObjectProperty<>();
	private final ObjectProperty<Item> selectedItemProperty = new SimpleObjectProperty<>();
	
	public ItemShopController(MainController mainController) {
		this.itemShopWindow = mainController.getItemShopWindow();
		
		List<Item> itemList = DatabaseService.loadItemsData();
		this.observableItemList = FXCollections.observableArrayList(itemList);
		this.filteredItemList = new FilteredList<>(observableItemList, p -> true);
		SortedList<Item> sortedList = new SortedList<>(this.filteredItemList, (item1, item2) -> {
			return item1.getName().compareToIgnoreCase(item2.getName());
		});
		sortedList.comparatorProperty().bind(this.itemShopWindow.getItemTable().comparatorProperty());
		this.itemShopWindow.getItemTable().setItems(sortedList);
		
		setupEventHandlers();
	}
	
	public void openItemShop() {
		this.itemShopWindow.show();
	}
	
	public void setSelectedCharacter(Character character) {
		this.selectedCharacterProperty.set(character);
	}
	
	private void setupEventHandlers() {
		// LISTENER
		this.itemShopWindow.getItemTable().getSelectionModel().selectedItemProperty().addListener((observable, oldItem, newItem) -> {
            this.selectedItemProperty.set(newItem);
        });
		
		// BUY
		this.itemShopWindow.getBtnAddToInventory().setOnAction(e -> {
			if (this.selectedCharacterProperty.get() != null && this.selectedItemProperty.get() != null) {
                this.selectedCharacterProperty.get().addItemToInventory(selectedItemProperty.get(), 1);
                DatabaseService.safeCharactersInventory(selectedCharacterProperty.get());
            }
		});
		
		this.itemShopWindow.getBtnAddToInventory().disableProperty().bind(
			Bindings.or(
				Bindings.isNull(selectedCharacterProperty),
				Bindings.isNull(selectedItemProperty)
			)
		);
		
		// TYPE FILTER
		this.itemShopWindow.getComboTypeFilter().valueProperty().addListener((obs, oldValue, newValue) -> {
			updateFilter();
		});
		
		// TYPE FILTER TO CONTROLL SUB FILTER
		this.itemShopWindow.getComboTypeFilter().setOnAction(e -> {
			// clear the filter first
			Filter selected = this.itemShopWindow.getComboTypeFilter().getValue();
			this.itemShopWindow.getComboSubFilter().getItems().clear();
			
			if (selected == Filter.WEAPON) {
				this.itemShopWindow.getComboSubFilter().setDisable(false);
				this.itemShopWindow.getComboSubFilter().getItems().addAll("Alle", "Einhändig", "Zweihändig");	
			} else if (selected == Filter.ARMOR) {
				this.itemShopWindow.getComboSubFilter().setDisable(false);
				this.itemShopWindow.getComboSubFilter().getItems().addAll("Alle", "Kopf", "Brust", "Hände", "Beine");
			} else {
				this.itemShopWindow.getComboSubFilter().setDisable(true);
				this.itemShopWindow.getComboSubFilter().getItems().add("Alle");
			}
			
			this.itemShopWindow.getComboSubFilter().getSelectionModel().selectFirst();
		});
		
		// TYPE FILTER
		this.itemShopWindow.getComboSubFilter().valueProperty().addListener((obs, oldValue, newValue) -> {
			updateFilter();
		});
		
		// TEXT FILTER
		this.itemShopWindow.getTxtSearch().textProperty().addListener((observable, oldValue, newValue) -> {	
			updateFilter();
		});
		
        // RESET FILTERS
        this.itemShopWindow.getBtnReset().setOnAction(e -> {
            this.itemShopWindow.getTxtSearch().setText("");
            this.itemShopWindow.getComboTypeFilter().getSelectionModel().selectFirst();
            this.itemShopWindow.getComboSubFilter().setDisable(true);

            updateFilter();
        });

	}
	
	private void updateFilter() {
        Filter selectedType = this.itemShopWindow.getComboTypeFilter().getValue();
        String selectedSubType = this.itemShopWindow.getComboSubFilter().getValue();
        String searchText = this.itemShopWindow.getTxtSearch().getText();

        filteredItemList.setPredicate(item -> {
        	
        	// Mainfilter
            boolean typeMatches = (selectedType == null || selectedType == Filter.ALL || selectedType.name().equals(item.getType().name()));
           
            // Subfilter
            boolean subTypeMatches = true;
            
            boolean weaponSubCheck = true;
            boolean armorSubCheck = true;
            
            // weapon
            if (selectedType == Filter.WEAPON && selectedSubType != null) {

            	if (item instanceof Weapon w) {
	
	                if (selectedSubType.equals("Einhändig")) {
	                    weaponSubCheck = w.getWeaponType() == WeaponType.ONE_HANDED;
	                }
	
	                if (selectedSubType.equals("Zweihändig")) {
	                    weaponSubCheck = w.getWeaponType() == WeaponType.TWO_HANDED;
	                }
	                
	                if (selectedSubType.equals("Alle")) {
	                	weaponSubCheck = true;
	                }
	            }
            }
            
			// armor
            if (selectedType == Filter.ARMOR && selectedSubType != null) {
            	
	            if (item instanceof Armor a) {
	
	            	if (selectedSubType.equals("Alle")) {
	            		armorSubCheck = true;
	            	}
	                if (selectedSubType.equals("Kopf")) {
	                    armorSubCheck = a.getSlot() == Slot.HEAD;
	                }
	                if (selectedSubType.equals("Brust")) {
	                    armorSubCheck = a.getSlot() == Slot.CHEST;
	                }
	                if (selectedSubType.equals("Beine")) {
	                    armorSubCheck = a.getSlot() == Slot.LEGS;
	                }
	                if (selectedSubType.equals("Hände")) {
	                    armorSubCheck = a.getSlot() == Slot.HANDS;
	                }
	            }
            }
            
            subTypeMatches = weaponSubCheck && armorSubCheck;
            
        
            
            // Textfilter
            boolean textMatches = true;
            
            if (searchText != null && !searchText.trim().isEmpty()) {
                String searchValue = searchText.toLowerCase().trim();

                boolean nameCheck   	 = item.getName().toLowerCase().contains(searchValue);
                boolean typeCheck   	 = item.getType().name().toLowerCase().contains(searchValue);
                boolean idCheck     	 = Integer.toString(item.getId()).contains(searchValue);
                boolean descriptionCheck = item.getDescription().toLowerCase().contains(searchValue);
                
                // --- Weapon-spezific Checks ---
                boolean weaponCheck = false;
                if (item instanceof Weapon w) {
                    weaponCheck =
                            Integer.toString(w.getDamage()).contains(searchValue)
                            || w.getRange().name().toLowerCase().contains(searchValue)
                            || w.getRange().getDisplayName().toLowerCase().contains(searchValue)
                            || w.getWeaponType().name().toLowerCase().contains(searchValue)
                            || w.getWeaponType().getDisplayName().toLowerCase().contains(searchValue)
                            || w.getSlot().name().toLowerCase().contains(searchValue)
                    		|| w.getSlot().getDisplayName().toLowerCase().contains(searchValue);
                }

                // --- Armor-spezific Checks ---
                boolean armorCheck = false;
                if (item instanceof Armor a) {
                    armorCheck =
                            Integer.toString(a.getDefense()).contains(searchValue)
                            || a.getSlot().name().toLowerCase().contains(searchValue)
                            || a.getSlot().getDisplayName().toLowerCase().contains(searchValue);
                }
                
                textMatches = nameCheck || typeCheck || idCheck || descriptionCheck || weaponCheck || armorCheck;
            }

            return typeMatches && textMatches && subTypeMatches;
        });
	}
}

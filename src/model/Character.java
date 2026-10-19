package model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Character {
	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty name = new SimpleStringProperty();
	private ObjectProperty<Species> species = new SimpleObjectProperty<>();
	private ObjectProperty<CharacterClass> characterClass = new SimpleObjectProperty<>();
	private IntegerProperty strength = new SimpleIntegerProperty();
	private IntegerProperty dexterity = new SimpleIntegerProperty();
	private IntegerProperty constitution = new SimpleIntegerProperty();
	private IntegerProperty intelligence = new SimpleIntegerProperty();
	private IntegerProperty wisdom = new SimpleIntegerProperty();
	private IntegerProperty charisma = new SimpleIntegerProperty();
	private Equipment equipment = new Equipment();
	private ObservableList<InventoryItem> inventory = FXCollections.observableArrayList();
	
	// Constructor
	public Character(
			int id, 
			String name, 
			Species species, 
			CharacterClass characterClass, 
			int strength, 
			int dexterity, 
			int constitution, 
			int intelligence, 
			int wisdom, 
			int charisma) {
		this.id.set(id);
		this.name.set(name);
		this.species.set(species);
		this.characterClass.set(characterClass);
		this.strength.set(strength);
		this.dexterity.set(dexterity);
		this.constitution.set(constitution);
		this.intelligence.set(intelligence);
		this.wisdom.set(wisdom);
		this.charisma.set(charisma);
	}
	
	// Inventory Methods
	public void addItemToInventory(Item item, int quantity) {
		if (item == null) return;
		for (InventoryItem i : inventory) {
			if (i.getItem().equals(item)) {
				i.setQuantity(i.getQuantity() + quantity);
				return; // End method, because item found and quantity risen 
			}
		}
		
		// If not exists -> create new and add
		InventoryItem newItem = new InventoryItem(item, quantity);
		inventory.add(newItem);
	}
	
	public void removeItemFromInventory(Item item, int quantity) {
		if (item == null) return;
		InventoryItem itemToRemove = null;
		
		for (InventoryItem i : inventory) {
			if (i.getItem().equals(item)) {
				if (i.getQuantity() > quantity) {
					i.setQuantity(i.getQuantity() - quantity);
					return;
				} else {
					itemToRemove = i;
					break;
				}	
			}
		}
		
		if (itemToRemove != null) {
			inventory.remove(itemToRemove);
		}
	}
	
	public void equipItem(Item item) {

	    List<Item> itemsToReturn = new ArrayList<>();

	    Weapon oldMain = equipment.getMainHandSlot();
	    Weapon oldOff  = equipment.getOffHandSlot();

	    // WEAPON
	    if (item instanceof Weapon weapon) {
	    	// NEW WEAPON IS TWO HANDED
	    	if (weapon != null && weapon.getWeaponType() == WeaponType.TWO_HANDED) {
	    		// OLD WEAPON IS TWO HANDED
	    		if (oldMain != null && oldMain.getWeaponType() == WeaponType.TWO_HANDED) {
	    			itemsToReturn.add(oldMain);
	    		}
	    		// OLD WEAPON IS ONE HANDED (+OFF)
	    		if (oldMain != null && oldMain.getWeaponType() == WeaponType.ONE_HANDED) {
	    			itemsToReturn.add(oldMain);
	    		}
	    		// OLD OFF IS ONE HANDED (+MAIN)
	    		if (oldOff != null && oldOff.getWeaponType() == WeaponType.ONE_HANDED) {
	    			itemsToReturn.add(oldOff);
	    		}
	    		
	    		// --> set two handed of both slots
	    		equipment.setMainHandSlot(weapon);
	    		equipment.setOffHandSlot(weapon);
	    		
	    	// NEW WEAPON IS ONE HANDED ON MAIN	
	    	} else if (weapon != null && weapon.getWeaponType() == WeaponType.ONE_HANDED && weapon.getSlot() == Slot.MAINHAND) {
	    		// OLD WEAPON IS TWO HANDED
	    		if (oldMain != null && oldMain.getWeaponType() == WeaponType.TWO_HANDED) {
	    			itemsToReturn.add(oldMain);
	    			equipment.setOffHandSlot(null);
	    		}
	    		// NEW WEAPON IS ONE HANDED (OFF IS NOT IMPORTANT HERE)
	    		if (oldMain != null && oldMain.getWeaponType() == WeaponType.ONE_HANDED) {
	    			itemsToReturn.add(oldMain);
	    		}
	    		
	    		equipment.setMainHandSlot(weapon);
	    	
	    	// NEW WEAPON IS ONE HANDED ON OFF	
	    	} else if (weapon != null && weapon.getWeaponType() == WeaponType.ONE_HANDED && weapon.getSlot() == Slot.OFFHAND) {
	    		// OLD OFF IS TWO HANDED
	    		if (oldOff != null && oldOff.getWeaponType() == WeaponType.TWO_HANDED) {
	    			itemsToReturn.add(oldOff);
	    			equipment.setMainHandSlot(null);
	    		}
	    		// NEW WEAPON IS ONE HANDED (MAIN IS NOT IMPORTANT HERE)
	    		if (oldOff != null && oldOff.getWeaponType() == WeaponType.ONE_HANDED) {
	    			itemsToReturn.add(oldOff);
	    		}
	    		
	    		equipment.setOffHandSlot(weapon);
	    	}
	    }
	    		

	    // ARMOR
	    if (item instanceof Armor armor) {

	        switch (armor.getSlot()) {
	            case HEAD:
	                if (equipment.getHeadSlot() != null) itemsToReturn.add(equipment.getHeadSlot());
	                equipment.setHeadSlot(armor);
	                break;

	            case HANDS:
	                if (equipment.getHandsSlot() != null) itemsToReturn.add(equipment.getHandsSlot());
	                equipment.setHandsSlot(armor);
	                break;

	            case CHEST:
	                if (equipment.getChestSlot() != null) itemsToReturn.add(equipment.getChestSlot());
	                equipment.setChestSlot(armor);
	                break;

	            case LEGS:
	                if (equipment.getLegsSlot() != null) itemsToReturn.add(equipment.getLegsSlot());
	                equipment.setLegsSlot(armor);
	                break;
	        }
	    }

        removeItemFromInventory(item, 1);
        itemsToReturn.forEach(i -> addItemToInventory(i, 1));
	    
	}
		
	
	public void unequipItem(Slot slot) {

	    switch (slot) {

	        case MAINHAND: {
	            Weapon weapon = equipment.getMainHandSlot();
	            	
	            // TWO_HANDED
	            if (weapon != null && weapon.getWeaponType() == WeaponType.TWO_HANDED) {
	            	equipment.setMainHandSlot(null);
	            	equipment.setOffHandSlot(null);
	            }
	            // ONE_HANDED
	            if (weapon != null && weapon.getWeaponType() == WeaponType.ONE_HANDED) {
	            	equipment.setMainHandSlot(null);
	            }
	            
	            addItemToInventory(weapon, 1);
	            
	            break;
	        }

	        case OFFHAND: {
	            Weapon weapon = equipment.getOffHandSlot();

	            if (weapon != null && weapon.getWeaponType() == WeaponType.TWO_HANDED) {
	            	equipment.setMainHandSlot(null);
	            	equipment.setOffHandSlot(null);
	            }
	            if (weapon != null && weapon.getWeaponType() == WeaponType.ONE_HANDED) {
	            	equipment.setOffHandSlot(null);
	            }
	            
	            addItemToInventory(weapon, 1);

	            break;
	        }

	        case HEAD:
	            if (equipment.getHeadSlot() != null) {
	                addItemToInventory(equipment.getHeadSlot(), 1);
	                equipment.setHeadSlot(null);
	            }
	            break;

	        case HANDS:
	            if (equipment.getHandsSlot() != null) {
	                addItemToInventory(equipment.getHandsSlot(), 1);
	                equipment.setHandsSlot(null);
	            }
	            break;

	        case CHEST:
	            if (equipment.getChestSlot() != null) {
	                addItemToInventory(equipment.getChestSlot(), 1);
	                equipment.setChestSlot(null);
	            }
	            break;

	        case LEGS:
	            if (equipment.getLegsSlot() != null) {
	                addItemToInventory(equipment.getLegsSlot(), 1);
	                equipment.setLegsSlot(null);
	            }
	            break;
	    }
	}

	
	
	// For UI-Binding
	public IntegerProperty idProperty() { return id; }
	public StringProperty nameProperty() { return name; }
	public ObjectProperty<Species> speciesProperty() { return species; }
	public ObjectProperty<CharacterClass> characterClassProperty() {	return characterClass; }
	public IntegerProperty strengthProperty() { return strength; }
	public IntegerProperty dexterityProperty() { return dexterity; }
	public IntegerProperty constitutionProperty() { return constitution; }
	public IntegerProperty intelligenceProperty() { return intelligence; }
	public IntegerProperty wisdomProperty() { return wisdom; }
	public IntegerProperty charismaProperty() { return charisma; }
	// Equipment and Inventory do have their own Property/Observable binding
	
	// Getter
	public int getId() { return this.id.get(); }
	public String getName() { return this.name.get(); }
	public Species getSpecies() { return this.species.get(); }
	public CharacterClass getCharacterClass() { return this.characterClass.get(); }
	public int getStrength() { return this.strength.get(); }
	public int getDexterity() { return this.dexterity.get(); }
	public int getConstitution() { return this.constitution.get(); }
	public int getIntelligence() { return this.intelligence.get(); }
	public int getWisdom() { return this.wisdom.get(); }
	public int getCharisma() { return this.charisma.get(); }
	public ObservableList<InventoryItem> getInventory() { return this.inventory; }
	public Equipment getEquipment() { return this.equipment; }
	
	// Setter
	public void setId(int id) {	this.id.set(id); }
	public void setName(String name) { this.name.set(name); }
	public void setSpecies(Species species) { this.species.set(species); }
	public void setCharacterClass(CharacterClass characterClass) { this.characterClass.set(characterClass); }
	public void setStrength(int strength) { this.strength.set(strength); }
	public void setDexterity(int dexterity) { this.dexterity.set(dexterity); }
	public void setConstitution(int constitution) { this.constitution.set(constitution); }
	public void setIntelligence(int intelligence) { this.intelligence.set(intelligence); }
	public void setWisdom(int wisdom) { this.wisdom.set(wisdom); }
	public void setCharisma(int charisma) { this.charisma.set(charisma); }
	public void setItems(List<InventoryItem> items) { this.inventory.setAll(items); }
	public void setEquipment(Equipment equipment) { this.equipment = equipment; } 
}

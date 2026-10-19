package db;

import java.util.List;

import model.Character;
import model.Item;


public class DatabaseService {
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_RESET  = "\u001B[0m";

	private static final CharactersDAO charactersDAO = new CharactersDAO();
	private static final EquipmentDAO equipmentDAO = new EquipmentDAO();
	private static final ItemsDAO itemsDAO = new ItemsDAO();
	private static final InventoryDAO inventoryDAO = new InventoryDAO();
	
	// =============================================
	// Constructor (Singleton)
	// =============================================
	private DatabaseService() {

	}
	
	
	// =============================================
	// Load data
	// =============================================
	// This Methods are used on application start, 
	// to get all data out of the database
	public static List<Character> loadCharactersData() {
		// Load characters (no inventory or equipment jet)
		List<Character> characterList = charactersDAO.loadAllCharacters();
		// Fill equipments and inventories
		for (Character c : characterList) {
			c.getInventory().addAll(inventoryDAO.loadInventoryOf(c));
			equipmentDAO.loadEquipmentOf(c);
		}

		return characterList;
	}
	
	public static List<Item> loadItemsData() {
		return itemsDAO.loadAllItems();
	}
	
	
	// =============================================
	// Update data
	// =============================================
	// Those method is used during character switch, save button
	// And if closed on confirmation dialog
 	public static void safeCharactersStats(Character character) {
		charactersDAO.updateCharacter(character);
		System.out.println(ANSI_YELLOW + "DB: Statuswerte von " + character.getName() + " aktualisiert." + ANSI_RESET);
	}
	
	public static void safeCharactersInventory(Character character) {
		inventoryDAO.saveInventoryOf(character);
		System.out.println(ANSI_YELLOW + "DB: Inventar von " + character.getName() + " aktualisiert." + ANSI_RESET);
	}
	
	public static void safeCharactersEquipment(Character character) {
		equipmentDAO.saveEquipmentOf(character);
		System.out.println(ANSI_YELLOW + "DB: Ausrüstung von " + character.getName() + " aktualisiert." + ANSI_RESET);

	}
	
	
	// =============================================
	// Delete data
	// =============================================
	// This method is called immediately after deleting a character
	// Equipment and inventory are on delete cascade
	public static void deleteCharacter(Character character) {
		charactersDAO.deleteCharacter(character);
		System.out.println(ANSI_YELLOW + "DB: Charakter " + character.getName() + " gelöscht." + ANSI_RESET);
	}
	

	// =============================================
	// Create data
	// =============================================
	// This method is called immediately after creating a new character
	// A new character has no equipment or inventory
	public static void addCharacter(Character character) {
		charactersDAO.addCharacter(character);
		System.out.println(ANSI_YELLOW + "DB: Charakter " + character.getName() + " hinzugefügt." + ANSI_RESET);
	}
	
}

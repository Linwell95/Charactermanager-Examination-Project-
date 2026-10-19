package config;

public class SQLConstants {
	// URL for database connection.
	public static final String DB_URL = "jdbc:sqlite:database/app.db?foreign_keys=on";

	// Select first item's id (for checking if CSV already loaded)
	public static final String CHECK_FOR_ITEM_ENTRIES = "SELECT id FROM items LIMIT 1;"; 
	
		
	// =============================================
	// CSV Paths
	public static final String ITEMS_CSV_PATH = "resources/CSV/items.CSV";
	
	public static final String ARMOR_CSV_PATH = "resources/CSV/armor_stats.CSV";
	
	public static final String WEAPON_CSV_PATH = "resources/CSV/weapon_stats.CSV";
	// =============================================
	
	
	// =============================================
	// CREATE Statements
	public static final String CREATE_ITEMS = "CREATE TABLE IF NOT EXISTS items ("
			+ "id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ "name TEXT NOT NULL, "
			+ "type TEXT NOT NULL, "
			+ "description TEXT NOT NULL);";
	
	public static final String CREATE_ITEMS_WEAPON = "CREATE TABLE IF NOT EXISTS weapons ("
			+ "id INTEGER PRIMARY KEY, "
			+ "damage INTEGER NOT NULL, "
			+ "range TEXT NOT NULL, "
			+ "weapon_slot TEXT NOT NULL, "
			+ "weapon_type TEXT NOT NULL, "
			+ "FOREIGN KEY (id) REFERENCES items(id) ON DELETE CASCADE);";
	
	public static final String CREATE_ITEMS_ARMOR = "CREATE TABLE IF NOT EXISTS armor ("
			+ "id INTEGER PRIMARY KEY, "
			+ "defense INTEGER NOT NULL, "
			+ "armor_slot TEXT NOT NULL, "
			+ "FOREIGN KEY (id) REFERENCES items(id) ON DELETE CASCADE);";
	
	public static final String CREATE_CHARACTERS = "CREATE TABLE IF NOT EXISTS characters ("
			+ "id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ "name TEXT NOT NULL, "
			+ "species TEXT NOT NULL, "
			+ "class TEXT NOT NULL, "
			+ "strength INTEGER NOT NULL, "
			+ "dexterity INTEGER NOT NULL, "
			+ "constitution INTEGER NOT NULL, "
			+ "intelligence INTEGER NOT NULL, "
			+ "wisdom INTEGER NOT NULL, "
			+ "charisma INTEGER NOT NULL);";
	
	public static final String CREATE_CHARACTERS_INVENTORIES = "CREATE TABLE IF NOT EXISTS inventories ("
			+ "character_id INTEGER, "
			+ "item_id INTEGER, "
			+ "quantity INTEGER NOT NULL, "
			+ "PRIMARY KEY (character_id, item_id), "
			+ "FOREIGN KEY (character_id) REFERENCES characters (id) ON DELETE CASCADE, "
			+ "FOREIGN KEY (item_id) REFERENCES items (id));";
	
	public static final String CREATE_CHARACTERS_EQUIPMENTS = "CREATE TABLE IF NOT EXISTS equipments ("
			+ "character_id INTEGER, "
			+ "item_id INTEGER, "
			+ "PRIMARY KEY (character_id, item_id), "
			+ "FOREIGN KEY (character_id) REFERENCES characters (id) ON DELETE CASCADE, "
			+ "FOREIGN KEY (item_id) REFERENCES items (id));";
	// =============================================
	
	
	// =============================================
	// INSERT Statements
	// Items
	public static final String INSERT_INTO_ITEMS = "INSERT INTO items (id, name, type, description) VALUES (?, ?, ?, ?);";
	
	public static final String INSERT_INTO_WEAPONS = "INSERT INTO weapons (id, damage, range, weapon_slot, weapon_type) VALUES (?, ?, ?, ?, ?);";
	
	public static final String INSERT_INTO_ARMOR = "INSERT INTO armor (id, defense, armor_slot) VALUES (?, ?, ?);";
	
	// Characters
	public static final String INSERT_INTO_CHARACTERS = "INSERT INTO characters "
			+ "(name, species, class, strength, dexterity, constitution, intelligence, wisdom, charisma) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
	
	// Equipment
	public static final String INSERT_INTO_EQUIPMENTS = "INSERT INTO equipments (character_id, item_id) "
			+ "VALUES (?, ?);"; 
	
	// Inventory
	public static final String INSERT_INTO_INVENTORIES = "INSERT INTO inventories (character_id, item_id, quantity) "
			+ "VALUES (?, ?, ?);";
	// =============================================
	
	
	// =============================================
	// UPDATE Statements
	public static final String UPDATE_CHARACTER = "UPDATE characters SET "
			+ "name = ?, "
			+ "species = ?, "
			+ "class = ?, "
			+ "strength = ?, "
			+ "dexterity = ?, "
			+ "constitution = ?, "
			+ "intelligence = ?, "
			+ "wisdom = ?, "
			+ "charisma = ? "
			+ "WHERE id = ?;";
	// =============================================
	
	
	// =============================================
	// DELETE Statements
	// Characters
	// ON DELETE CASCADE deletes all inventory and equipment entries, of the character, too
	public static final String DELETE_CHARACTER = "DELETE FROM characters WHERE id = ?;";
	
	// Equipment
	public static final String DELETE_EQUIPMENT = "DELETE FROM equipments WHERE character_id = ?;";
	
	// Inventory
	public static final String DELETE_INVENTORY = "DELETE FROM inventories WHERE character_id = ?;";
	// =============================================
	
	
	// =============================================
	// DROP Tables Statements
	public static final String DROP_ITEMS = "DROP TABLE IF EXISTS items;";
	
	public static final String DROP_ITEMS_WEAPONS = "DROP TABLE IF EXISTS weapons;";
	
	public static final String DROP_ITEMS_ARMOR = "DROP TABLE IF EXISTS armor;";
	
	public static final String DROP_CHARACTERS = "DROP TABLE IF EXISTS characters;";
	
	public static final String DROP_CHARACTERS_EQUIPMENT = "DROP TABLE IF EXISTS equipments;";
	
	public static final String DROP_CHARACTERS_INVENTORY = "DROP TABLE IF EXISTS inventories;";
	// =============================================
	
	
	// =============================================
	// SELECT Statements
	// Items
	public static final String SELECT_ALL_ITEMS = "SELECT i.*, w.damage, w.range, w.weapon_slot, w.weapon_type, a.defense, a.armor_slot "
			+ "FROM items i "
			+ "LEFT JOIN weapons w ON i.id = w.id "
			+ "LEFT JOIN armor a ON i.id = a.id;";
	
	// Characters
	public static final String SELECT_ALL_CHARACTERS = "SELECT * FROM characters;";
	
	// Equipment
	public static final String SELECT_EQUIPMENT = "SELECT i.*, w.damage, w.range, w.weapon_slot, w.weapon_type, a.defense, a.armor_slot "
			+ "FROM equipments eq "
			+ "JOIN items i ON eq.item_id = i.id "
			+ "LEFT JOIN weapons w ON i.id = w.id "
			+ "LEFT JOIN armor a ON i.id = a.id "
			+ "WHERE eq.character_id = ?;";
	
	// Inventory
	public static final String SELECT_INVENTORY = "SELECT inv.quantity, i.*, w.damage, w.range, w.weapon_slot, w.weapon_type, a.defense, a.armor_slot "
			+ "FROM inventories inv "
			+ "JOIN items i ON inv.item_id = i.id "
			+ "LEFT JOIN weapons w ON w.id = i.id "
			+ "LEFT JOIN armor a ON a.id = i.id "
			+ "WHERE inv.character_id = ?;";
	// =============================================
}

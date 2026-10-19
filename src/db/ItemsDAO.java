package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.SQLConstants;
import model.Armor;
import model.Item;
import model.ItemType;
import model.Range;
import model.Slot;
import model.Weapon;
import model.WeaponType;


// Package private -> Service has the DAOs and uses their methods.
// Table items is read only, no 'save' or 'update' or 'new' methods are needed.
class ItemsDAO {

	// =============================================
	// LOAD all items of the SQLite database
	// =============================================
	List<Item> loadAllItems() {
		List<Item> itemList = new ArrayList<Item>();
		
		try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(SQLConstants.SELECT_ALL_ITEMS);
				ResultSet rs = stmt.executeQuery()) {
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				ItemType type = ItemType.valueOf(rs.getString("type"));
				String description = rs.getString("description");
				
				switch (type) {
				case WEAPON:
					int damage = rs.getInt("damage");
					Range range = Range.valueOf(rs.getString("range"));
					Slot weaponSlot = Slot.valueOf(rs.getString("weapon_slot"));
					WeaponType weaponType = WeaponType.valueOf(rs.getString("weapon_type"));
										
					Weapon weapon = new Weapon(id, name, type, description, damage, range, weaponSlot, weaponType);
					itemList.add(weapon);
					break;
					
				case ARMOR:
					int defense = rs.getInt("defense");
					Slot armorSlot = Slot.valueOf(rs.getString("armor_slot"));
							
					Armor armor = new Armor(id, name, type, description, defense, armorSlot);
					itemList.add(armor);
					break;
				}
			}
			
		} catch (SQLException e) {
			System.err.println("Fehler: Laden der Itemliste fehlgeschlagen.");
			e.printStackTrace();
		}
		
		return itemList;
	}
}

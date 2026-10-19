package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.SQLConstants;
import model.Armor;
import model.Character;
import model.InventoryItem;
import model.ItemType;
import model.Slot;
import model.Weapon;
import model.WeaponType;
import model.Range;


// Package private -> Service has the DAOs and uses their methods.
class InventoryDAO {

	// =============================================
	// LOAD inventory-list of a character
	// =============================================
	List<InventoryItem> loadInventoryOf(Character character) {
		List<InventoryItem> inventoryList = new ArrayList<>();
		
		try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(SQLConstants.SELECT_INVENTORY)) {
			
			pstmt.setInt(1, character.getId());
			
			try (ResultSet rs = pstmt.executeQuery()) {
				
				while (rs.next()) {
					int id = rs.getInt("id");
					String name = rs.getString("name");
					String description = rs.getString("description");
					ItemType type = ItemType.valueOf(rs.getString("type"));
					int quantity = rs.getInt("quantity");
					
					switch (type) {
					case ARMOR:
						int defense = rs.getInt("defense");
						Slot armorSlot = Slot.valueOf(rs.getString("armor_slot"));
						
						Armor armor = new Armor(id, name, type, description, defense, armorSlot);
						inventoryList.add(new InventoryItem(armor, quantity));
						break;
					
					case WEAPON:
						int damage = rs.getInt("damage");
						Range range = Range.valueOf(rs.getString("range"));
						Slot weaponSlot = Slot.valueOf(rs.getString("weapon_slot"));
						WeaponType weaponType = WeaponType.valueOf(rs.getString("weapon_type"));
						
						Weapon weapon = new Weapon(id, name, type, description, damage, range, weaponSlot, weaponType);
						inventoryList.add(new InventoryItem(weapon, quantity));
						break;
						
					default:
						break;
						
					}
				}
			}
			
		} catch (SQLException e) {
			System.err.println("Fehler: Laden des Inventars von '" + character.getName() + "' fehlgeschlagen.");
			e.printStackTrace();
		}
		
		return inventoryList;
	}

	
	// =============================================
	// SAFE inventory-list of a character
	// =============================================
	void saveInventoryOf(Character character) {

		Connection conn = null;
		
		try {
			// starting transaction
			conn = DatabaseManager.getConnection();
			conn.setAutoCommit(false);
			// delete the old data
			try (PreparedStatement deleteStmt = conn.prepareStatement(SQLConstants.DELETE_INVENTORY)) {
				deleteStmt.setInt(1, character.getId());
                deleteStmt.executeUpdate();
			}
			// save all inventory items
			try (PreparedStatement insertStmt = conn.prepareStatement(SQLConstants.INSERT_INTO_INVENTORIES)) {
				for (InventoryItem i : character.getInventory()) {
					insertStmt.setInt(1, character.getId());
					insertStmt.setInt(2, i.getItem().getId());
					insertStmt.setInt(3, i.getQuantity());
					insertStmt.addBatch();
				}
				 
				insertStmt.executeBatch();
			}
			
			// commit the action
			conn.commit();
			
		} catch (Exception e) {
			// on exception during transaction -> rollback
			if (conn != null) {
                try { 
                	conn.rollback(); 
                } catch (SQLException ex) { 
                	ex.printStackTrace(); 
                }
            }
		} finally {
			// end the transaction and close the connection (because it was not a try resource)
			if (conn != null) {
                try { 
                    conn.setAutoCommit(true); 
                    conn.close();             
                } catch (SQLException e) { 
                    e.printStackTrace(); 
                }
            }
		}
	}
}

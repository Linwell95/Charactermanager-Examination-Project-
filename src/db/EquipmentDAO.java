package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.SQLConstants;
import model.Armor;
import model.Character;
import model.ItemType;
import model.Range;
import model.Slot;
import model.Weapon;
import model.WeaponType;


// Package private -> Service has the DAOs and uses their methods.
class EquipmentDAO {

	// =============================================
	// LOAD equipment-set of a character
	// =============================================
	void loadEquipmentOf(Character character) {
		try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(SQLConstants.SELECT_EQUIPMENT)) {
			
			pstmt.setInt(1, character.getId());
			
			try (ResultSet rs = pstmt.executeQuery()) {
				
				while (rs.next()) {
					int id = rs.getInt("id");
					String name = rs.getString("name");
					String description = rs.getString("description");
					ItemType type = ItemType.valueOf(rs.getString("type"));
					
					switch (type) {
					case WEAPON: 
						int damage = rs.getInt("damage");
						Range range = Range.valueOf(rs.getString("range"));
						Slot weaponSlot = Slot.valueOf(rs.getString("weapon_slot"));
						WeaponType weaponType = WeaponType.valueOf(rs.getString("weapon_type"));
						
						Weapon weapon = new Weapon(id, name, type, description, damage, range, weaponSlot, weaponType);
						character.equipItem(weapon);
						break;
						
					case ARMOR:
						int defense = rs.getInt("defense");
						Slot armorSlot = Slot.valueOf(rs.getString("armor_slot"));
						
						Armor armor = new Armor(id, name, type, description, defense, armorSlot);
						character.equipItem(armor);
						break;
					}
				}
			}
			
		} catch (SQLException e) {
			System.err.println("Fehler: Ausrüstung von '" + character.getName() + "' konnte nicht geladen werden.");
			e.printStackTrace();
		}		
	}
	
	
	// =============================================
	// SAFE equipment-set of a character
	// =============================================
	void saveEquipmentOf(Character character) {
	
		Connection conn = null;
		
		try {
			// starting transaction
			conn = DatabaseManager.getConnection();
			conn.setAutoCommit(false);
			// delete the old data
			try (PreparedStatement deleteStmt = conn.prepareStatement(SQLConstants.DELETE_EQUIPMENT)) {
				deleteStmt.setInt(1, character.getId());
                deleteStmt.executeUpdate();
			}
			// save the new data (if not null)
			try (PreparedStatement insertStmt = conn.prepareStatement(SQLConstants.INSERT_INTO_EQUIPMENTS)) {
				// two handed
				if (character.getEquipment().getMainHandSlot() != null && character.getEquipment().getMainHandSlot().getWeaponType() == WeaponType.TWO_HANDED) {
					insertStmt.setInt(1, character.getId());
					insertStmt.setInt(2, character.getEquipment().getMainHandSlot().getId());
					insertStmt.addBatch();
				} else {
					// main hand
					if (character.getEquipment().getMainHandSlot() != null) {
	                    insertStmt.setInt(1, character.getId());
	                    insertStmt.setInt(2, character.getEquipment().getMainHandSlot().getId());
	                    insertStmt.addBatch();
	                }
					// off hand
					if (character.getEquipment().getOffHandSlot() != null) {
	                    insertStmt.setInt(1, character.getId());
	                    insertStmt.setInt(2, character.getEquipment().getOffHandSlot().getId());
	                    insertStmt.addBatch();
					}
				
	            }
                // chest
                if (character.getEquipment().getChestSlot() != null) {
                    insertStmt.setInt(1, character.getId());
                    insertStmt.setInt(2, character.getEquipment().getChestSlot().getId());
                    insertStmt.addBatch();
                }
                // legs
                if (character.getEquipment().getLegsSlot() != null) {
                    insertStmt.setInt(1, character.getId());
                    insertStmt.setInt(2, character.getEquipment().getLegsSlot().getId());
                    insertStmt.addBatch();
                }
                // head
                if (character.getEquipment().getHeadSlot() != null) {
                    insertStmt.setInt(1, character.getId());
                    insertStmt.setInt(2, character.getEquipment().getHeadSlot().getId());
                    insertStmt.addBatch();
                }
                // hands
                if (character.getEquipment().getHandsSlot() != null) {
                	insertStmt.setInt(1, character.getId());
                	insertStmt.setInt(2, character.getEquipment().getHandsSlot().getId());
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

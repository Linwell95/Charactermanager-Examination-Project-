package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.SQLConstants;
import model.CharacterClass;
import model.Species;
import model.Character;


// Package private -> Service has the DAOs and uses their methods.
class CharactersDAO {

	// =============================================
	// LOAD all characters off the SQLite database
	// =============================================
	List<Character> loadAllCharacters() {
		List<Character> characterList = new ArrayList<>();
		
		try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(SQLConstants.SELECT_ALL_CHARACTERS);
				ResultSet rs = pstmt.executeQuery()) {
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				Species species = Species.valueOf(rs.getString("species"));
				CharacterClass characterClass = CharacterClass.valueOf(rs.getString("class"));
				int strength = rs.getInt("strength");
				int dexterity = rs.getInt("dexterity");
				int constitution = rs.getInt("constitution");
				int intelligence = rs.getInt("intelligence");
				int wisdom = rs.getInt("wisdom");
				int charisma = rs.getInt("charisma");
				
				Character character = new Character(id, name, species, characterClass, strength, dexterity, constitution, intelligence, wisdom, charisma);
				characterList.add(character);
			}
			
		} catch (SQLException e) {
			System.err.println("Fehler: Laden der Charakterliste fehlgeschlagen.");
			e.printStackTrace();
		}

		return characterList;
		
	}
	
	
	// =============================================
	// SAFE a new character to the SQLite database
	// =============================================
	void addCharacter(Character character) {
		try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(SQLConstants.INSERT_INTO_CHARACTERS)) {
			
			pstmt.setString(1, character.getName());
			pstmt.setString(2, character.getSpecies().name());
			pstmt.setString(3, character.getCharacterClass().name());
			pstmt.setInt(4, character.getStrength());
			pstmt.setInt(5, character.getDexterity());
			pstmt.setInt(6, character.getConstitution());
			pstmt.setInt(7, character.getIntelligence());
			pstmt.setInt(8, character.getWisdom());
			pstmt.setInt(9, character.getCharisma());
			
			pstmt.executeUpdate();
			
			try (ResultSet keys = pstmt.getGeneratedKeys()) {
				if (keys.next()) {
					long dbId = keys.getLong(1);
					character.setId((int) dbId);
				}
			}
			
		} catch (SQLException e) {
			System.err.println("Fehler: Hinzufügen des Charakters '" + character.getName() + "' fehlgeschlagen.");
			e.printStackTrace();
		}
		
	}
	
	
	// =============================================
	// UPDATE existing character
	// =============================================
	void updateCharacter(Character character) {
		try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(SQLConstants.UPDATE_CHARACTER)) {
			
			// SET
			pstmt.setString(1, character.getName());
			pstmt.setString(2, character.getSpecies().name());
			pstmt.setString(3, character.getCharacterClass().name());
			pstmt.setInt(4, character.getStrength());
			pstmt.setInt(5, character.getDexterity());
			pstmt.setInt(6, character.getConstitution());
			pstmt.setInt(7, character.getIntelligence());
			pstmt.setInt(8, character.getWisdom());
			pstmt.setInt(9, character.getCharisma());
			
			// WHERE id
			pstmt.setInt(10, character.getId());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.err.println("Fehler: Aktualisieren von Charakter '" + character.getName() + "' fehlgeschlagen.");
			e.printStackTrace();
		}
		
	}
	
	
	// =============================================
	// DELETE a character
	// =============================================
	void deleteCharacter(Character character) {
		try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(SQLConstants.DELETE_CHARACTER)) {
			
			pstmt.setInt(1, character.getId());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.err.println("Fehler: Löschen des Charakters '" + character.getName() + "' fehltgeschlagen.");
			e.printStackTrace();
		}
		
	}
	
}

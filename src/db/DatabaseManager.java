package db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import config.SQLConstants;


public class DatabaseManager {

	private static Connection conn;
	
	// =============================================
	// Singleton constructor.
	// =============================================
	private DatabaseManager() {
		// Singleton
	}
	
	
	// =============================================
	// Method to return the connection.
	// =============================================
	public static Connection getConnection() {
		// Check if connection already exists. If not -> initialize.
		try {
			if (conn == null || conn.isClosed()) {
				conn = DriverManager.getConnection(SQLConstants.DB_URL);
			}
		} catch (SQLException e) {
			System.err.println("Fehler: Datenbankverbindung konnte nicht hergestellt werden.");
			e.printStackTrace();
		}

		return conn;
	}
	
	
	// =============================================
	// Initialize database structure.
	// =============================================
	public static void initDatabase() {
		// Creates the table structure
		try (Statement stmt = getConnection().createStatement()) {
			stmt.execute(SQLConstants.CREATE_ITEMS);
			stmt.execute(SQLConstants.CREATE_ITEMS_WEAPON);
			stmt.execute(SQLConstants.CREATE_ITEMS_ARMOR);
			stmt.execute(SQLConstants.CREATE_CHARACTERS);
			stmt.execute(SQLConstants.CREATE_CHARACTERS_INVENTORIES);
			stmt.execute(SQLConstants.CREATE_CHARACTERS_EQUIPMENTS);
			
		} catch (SQLException e) {
			System.err.println("Fehler: Initialisierung der Datenbankstruktur fehlgeschlagen.");
			e.printStackTrace();
		}
		
		// Boolean to check if items entries already exists.
		boolean isInitialized = false;
		
		try (Statement stmt = getConnection().createStatement();
				ResultSet rs = stmt.executeQuery(SQLConstants.CHECK_FOR_ITEM_ENTRIES)) {
			// If entries exist -> switch boolean state to true.
			if (rs.next()) {
				isInitialized = true;
			}
			
		} catch (SQLException e) {
			System.err.println("Fehler: Prüfen auf Item-Inhalte fehlgeschlagen.");
		}
		
		 if (!isInitialized)importCSVToDatabase();
		 
	}
	
	
	// =============================================
	// Fill database items, weapons and armor with CSV.
	// =============================================
	private static void importCSVToDatabase() {
		// Method is used in initDatabase(), if items table has no entries jet.
		// ITEMS
		try (BufferedReader br = new BufferedReader(new FileReader(SQLConstants.ITEMS_CSV_PATH));
				PreparedStatement stmt = getConnection().prepareStatement(SQLConstants.INSERT_INTO_ITEMS)) {
			
			String line;
			boolean firstLine = true;
			
			// As long as there are lines
			while ((line = br.readLine()) != null) {
				// Skip first line (headers)
				if(firstLine) {
					firstLine = false;
					continue;
				}
				
				// Split the line to get the entities values
				String[] parts = line.split(";");
				
				stmt.setInt(1, Integer.parseInt(parts[0])); 	// id
				stmt.setString(2, parts[1]);					// name
				stmt.setString(3, parts[2]);					// type
				stmt.setString(4, parts[3]);					// description
				
				stmt.executeUpdate();
			}
			
		} catch (Exception e) {
			System.err.println("Fehler: Laden der Items aus der CSV fehlgeschlagen.");
			e.printStackTrace();
		}
		
		// WEAPONS
		try (BufferedReader br = new BufferedReader(new FileReader(SQLConstants.WEAPON_CSV_PATH));
				PreparedStatement stmt = getConnection().prepareStatement(SQLConstants.INSERT_INTO_WEAPONS)) {
			
			String line;
			boolean firstLine = true;
			
			// As long as there are lines
			while ((line = br.readLine()) != null) {
				// Skip first line (headers)
				if(firstLine) {
					firstLine = false;
					continue;
				}
				
				// Split the line to get the entities values
				String[] parts = line.split(";");
				
				stmt.setInt(1, Integer.parseInt(parts[0])); 	// id
				stmt.setInt(2, Integer.parseInt(parts[1]));		// damage
				stmt.setString(3, parts[2]);					// range
				stmt.setString(4, parts[3]);					// slot
				stmt.setString(5, parts[4]);	   				// weapon_type
				
				stmt.executeUpdate();
			}
			
		} catch (Exception e) {
			System.err.println("Fehler: Laden der Waffen aus der CSV fehlgeschlagen.");
			e.printStackTrace();
		}
		
		// ARMOR
		try (BufferedReader br = new BufferedReader(new FileReader(SQLConstants.ARMOR_CSV_PATH));
				PreparedStatement stmt = getConnection().prepareStatement(SQLConstants.INSERT_INTO_ARMOR)) {
			
			String line;
			boolean firstLine = true;
			
			// As long as there are lines
			while ((line = br.readLine()) != null) {
				// Skip first line (headers)
				if(firstLine) {
					firstLine = false;
					continue;
				}
				
				// Split the line to get the entities values
				String[] parts = line.split(";");
				
				stmt.setInt(1, Integer.parseInt(parts[0])); 	// id
				stmt.setInt(2, Integer.parseInt(parts[1]));		// defense
				stmt.setString(3, parts[2]);					// slot
				
				stmt.executeUpdate();
			}
			
		} catch (Exception e) {
			System.err.println("Fehler: Laden der Waffen aus der CSV fehlgeschlagen.");
			e.printStackTrace();
		}
				
	}

	
	// =============================================
	// Drop all tables DEVTOOL
	// =============================================
	public static void dropAllTables() {
		try (Statement stmt = getConnection().createStatement()) {
			stmt.execute(SQLConstants.DROP_CHARACTERS_EQUIPMENT);
			stmt.execute(SQLConstants.DROP_CHARACTERS_INVENTORY);
			stmt.execute(SQLConstants.DROP_CHARACTERS);
			stmt.execute(SQLConstants.DROP_ITEMS_WEAPONS);
			stmt.execute(SQLConstants.DROP_ITEMS_ARMOR);
			stmt.execute(SQLConstants.DROP_ITEMS);
			
		} catch (SQLException e) {
			System.err.println("Fehler: Löschen der Tabellen fehlgeschlagen.");
			e.printStackTrace();
		}
	}
}

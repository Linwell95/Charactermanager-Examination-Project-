package model;

public enum Slot {
	CHEST("Brustrüstung"),
	HEAD("Kopfrüstung"),
	LEGS("Beinrüstung"),
	HANDS("Handschuhe"),
	MAINHAND("Haupthand"),
	OFFHAND("Nebenhand");
	
	private String displayName;
	
	private Slot(String displayName) {
		this.displayName = displayName;
	}
	
	public String getDisplayName() {
		return this.displayName;
	}
	
	@Override
	public String toString() {
		return this.displayName;
	}
}

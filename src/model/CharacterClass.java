package model;

public enum CharacterClass {
	BARBARIAN("Barbar"),
	CLERIC("Kleriker"),
	BARD("Barde"),
	DRUID("Druide"),
	FIGHTER("Kämpfer"),
	MONK("Mönch"),
	PALADIN("Paladin"),
	RANGER("Waldläufer"),
	ROGUE("Schurke"),
	SORCERER("Zauberer"),
	WARLOCK("Hexenmeister"),
	WIZARD("Magier");
	
	private String displayName;
	
	private CharacterClass(String displayName) {
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

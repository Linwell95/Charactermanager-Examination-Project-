package model;

public enum Species {
	HUMAN("Mensch"),
	ELF("Elf"),
	HALFORC("Halbork"),
	HALFELF("Halbelf"),
	HALFLING("Halbling"),
	GNOME("Gnom"),
	DWARF("Zwerg"),
	DRAGONBORN("Drachenblütiger"),
	TIEFLING("Tiefling");
	
	private String displayName;
	
	private Species(String displayName) {
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

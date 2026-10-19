package model;

public enum Range {
	MELEE("Nahkampfwaffe"),
	RANGED("Fernkampfwaffe");
	
	private String displayName;
	
	private Range(String displayName) {
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

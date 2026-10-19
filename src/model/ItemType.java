package model;

public enum ItemType {
	WEAPON("Waffe"),
	ARMOR("Rüstung");
	
	private String displayName;
	
	private ItemType(String displayName) {
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

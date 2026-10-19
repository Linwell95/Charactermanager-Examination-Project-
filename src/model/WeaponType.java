package model;

public enum WeaponType {
	ONE_HANDED("Einhändig"),
	TWO_HANDED("Zweihändig");
	
	private String displayName;
	
	private WeaponType(String displayName) {
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

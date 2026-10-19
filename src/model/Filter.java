package model;

public enum Filter {

	ALL("Alles"),
	WEAPON("Waffen"),
	ARMOR("Rüstungen");
	
	private String displayName;
	
	private Filter(String displayName) {
		this.displayName = displayName;
	}
	
	@Override
	public String toString() {
		return this.displayName;
	}
}

package model;

public class Armor extends Item {

	private int defense;
	private Slot slot;
	
	public Armor(int id, String name, ItemType type, String description, int defense, Slot slot) {
		super(id, name, type, description);
		this.defense = defense;
		this.slot = slot;
	}

	public int getDefense() { return this.defense; }
	public Slot getSlot() { return this.slot; }
}

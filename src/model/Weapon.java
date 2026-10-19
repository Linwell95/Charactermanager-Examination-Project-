package model;

public class Weapon extends Item {
	private int damage;
	private Range range;
	private Slot slot;
	private WeaponType weaponType;

	public Weapon(int id, String name, ItemType type, String description, int damage, Range range, Slot slot, WeaponType weaponType) {
		super(id, name, type, description);
		this.damage = damage;
		this.range = range;
		this.slot = slot;
		this.weaponType = weaponType;
	}
	
	public int getDamage() { return this.damage; }
	public Range getRange() { return this.range; }
	public Slot getSlot() { return this.slot; }
	public WeaponType getWeaponType() { return this.weaponType; }
}

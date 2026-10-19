package model;

import java.util.Objects;

public class Item {
	private final int id;
	private final String name;
	private final ItemType type;
	private final String description;
	
	// Constructor
	public Item(int id, String name, ItemType type, String description) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.description = description;
	}
	
	// Getter
	public int getId() { return this.id; }
	public String getName() { return this.name; }
	public ItemType getType() { return this.type; }
	public String getDescription() { return this.description; }
	
	// Overrides
	// Items are considered equal if they share the same ID
	// It is needed to find an equal item in the characters inventory to change the quantity
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Item i)) return false;
		return this.id == i.id;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}

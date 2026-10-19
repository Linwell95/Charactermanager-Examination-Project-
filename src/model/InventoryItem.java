package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class InventoryItem {
	private Item item;
	private IntegerProperty quantity = new SimpleIntegerProperty();
	
	// Constructor
	public InventoryItem(Item item, int quantity) {
		this.item = item;
		this.quantity.set(quantity);
	}

	// Getter
	public Item getItem() { return this.item; }
	public int getQuantity() { return this.quantity.get(); }
	
	// Setter
	public void setQuantity(int quantity) { this.quantity.set(quantity); }
	
	// UI-Binding
	public IntegerProperty quantityProperty() { return this.quantity; }
}

package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Equipment {

	// ObjectProperty is an observable field. JavaFX automatically refreshes UI and informs listener bind to that field.
	private ObjectProperty<Armor> headSlot = new SimpleObjectProperty<>(null);
	private ObjectProperty<Armor> handsSlot = new SimpleObjectProperty<>(null);
	private ObjectProperty<Armor> chestSlot = new SimpleObjectProperty<>(null);
	private ObjectProperty<Armor> legsSlot = new SimpleObjectProperty<>(null);
	
	private ObjectProperty<Weapon> mainHandSlot = new SimpleObjectProperty<>(null);
	private ObjectProperty<Weapon> offHandSlot = new SimpleObjectProperty<>(null);
	
	// Getter (Values)
	public Armor getHeadSlot() { return headSlot.get(); }
	public Armor getHandsSlot() { return handsSlot.get(); }
	public Armor getChestSlot() { return chestSlot.get(); }
	public Armor getLegsSlot() { return legsSlot.get(); }
	
	public Weapon getMainHandSlot() { return mainHandSlot.get(); }
	public Weapon getOffHandSlot() { return offHandSlot.get(); }
	
	// --- Setter (Values) ---
    public void setHeadSlot(Armor a) { headSlot.set(a); }
    public void setHandsSlot(Armor a) { handsSlot.set(a); }
    public void setChestSlot(Armor a) { chestSlot.set(a); }
    public void setLegsSlot(Armor a) { legsSlot.set(a); }

    public void setMainHandSlot(Weapon w) { mainHandSlot.set(w); }
    public void setOffHandSlot(Weapon w) { offHandSlot.set(w); }
    
	// Getter for UI-Binding
	public ObjectProperty<Armor> headSlotProperty() { return headSlot; }
	public ObjectProperty<Armor> handsSlotProperty() { return handsSlot; }
	public ObjectProperty<Armor> chestSlotProperty() { return chestSlot; }
	public ObjectProperty<Armor> legsSlotProperty() { return legsSlot; }	
	
	public ObjectProperty<Weapon> mainHandSlotProperty() { return mainHandSlot; }
	public ObjectProperty<Weapon> offHandSlotProperty() { return offHandSlot; }
	
}

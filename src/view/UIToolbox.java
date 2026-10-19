package view;

import javafx.scene.control.Spinner;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.Armor;
import model.Item;
import model.Weapon;

public class UIToolbox {

	private static final String HEAD_ARMOR_IMG_PATH = "/Icons/Items/item_armor_head.png";
	private static final String CHEST_ARMOR_IMG_PATH = "/Icons/Items/item_armor_chest.png";
	private static final String HANDS_ARMOR_IMG_PATH = "/Icons/Items/item_armor_hands.png";
	private static final String LEGS_ARMOR_IMG_PATH = "/Icons/Items/item_armor_legs.png";
	private static final String MAIN_WEAPON_IMG_PATH = "/Icons/Items/item_weapon_mainhand.png";
	private static final String OFF_WEAPON_IMG_PATH = "/Icons/Items/item_weapon_offhand.png";
	
	 public ImageView createIconOf(String path, int size) {
        ImageView iconView = new ImageView(new Image(getClass().getResourceAsStream(path)));
        iconView.setFitHeight(size);
        iconView.setFitWidth(size);
        iconView.setPreserveRatio(true);

        return iconView;
    }
	 
	 public Image getSlotIcon(Item item) {
		if(item instanceof Armor armor) {
			return switch (armor.getSlot()) {
				case HEAD -> new Image(getClass().getResourceAsStream(HEAD_ARMOR_IMG_PATH));
				case CHEST -> new Image(getClass().getResourceAsStream(CHEST_ARMOR_IMG_PATH));
				case HANDS -> new Image(getClass().getResourceAsStream(HANDS_ARMOR_IMG_PATH));
				case LEGS -> new Image(getClass().getResourceAsStream(LEGS_ARMOR_IMG_PATH));
				default -> null;
			};
			
		} else if (item instanceof Weapon weapon) {
			return switch (weapon.getSlot()) {
				case  MAINHAND -> new Image(getClass().getResourceAsStream(MAIN_WEAPON_IMG_PATH));
				case OFFHAND -> new Image(getClass().getResourceAsStream(OFF_WEAPON_IMG_PATH));
				default -> null;
				
			};
			
		} else {
			return null;
		}
	}
	 
    public Spinner<Integer> createAttributeSpinner() {
        Spinner<Integer> spin = new Spinner<>(1, 20, 10);

        spin.setEditable(true);

        return spin;
    }
    
    public Tooltip createTooltip(String text) {
    	Tooltip tooltip = new Tooltip(text);
    	tooltip.setShowDuration(Duration.INDEFINITE);
    	tooltip.setShowDelay(Duration.ZERO);
    	
    	return tooltip;
    }
    
    public String createTooltipString(Item item) {
    	if (item instanceof Armor a) {
    		return a.getName() + "\nVerteidigung: " + a.getDefense();  
    		
    	} else if (item instanceof Weapon w) {
    		return 	w.getName() + 
    				"\nTyp: " + w.getWeaponType().toString() +
    				"\nEntfernung: " + w.getRange().toString() + 
    				"\nSchaden: " + w.getDamage();
    	}
    	
    	return null;
    }

}

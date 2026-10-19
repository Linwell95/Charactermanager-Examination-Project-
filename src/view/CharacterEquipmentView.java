package view;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import model.Equipment;
import model.Slot;

public class CharacterEquipmentView extends VBox {

	private static final int TILE_SIZE = 50;
	private static final String SLOT_HEAD	 	= "/Icons/Slots/slot_head_white.png";
	private static final String SLOT_HANDS 		= "/Icons/Slots/slot_hands_white.png";
	private static final String SLOT_CHEST 		= "/Icons/Slots/slot_chest_white.png";
	private static final String SLOT_LEGS 		= "/Icons/Slots/slot_legs_white.png";
	private static final String SLOT_MAINHAND 	= "/Icons/Slots/slot_mainhand_white.png";
	private static final String SLOT_OFFHAND 	= "/Icons/Slots/slot_shieldhand_white.png";
	
	private ImageView itemHead;
	private ImageView itemChest;
	private ImageView itemHands;
	private ImageView itemLegs;
	private ImageView itemMainHand;
	private ImageView itemOffHand;
	
	private StackPane head;
	private StackPane chest;
	private StackPane hands;
	private StackPane legs;
	private StackPane mainHand;
	private StackPane offHand;
	
	private Tooltip headTip;
	private Tooltip chestTip;
	private Tooltip handsTip;
	private Tooltip legsTip;
	private Tooltip mainHandTip;
	private Tooltip offHandTip;
			
	private UIToolbox tool = new UIToolbox();
	
	public CharacterEquipmentView() {
		this.getChildren().addAll(
				createEquipmentBox());
		
		this.getStyleClass().add("character-equipment-view");
	}
	
	public void bindEquipment(Equipment equipment) {		
		// HEAD
		itemHead.imageProperty().bind(
		    Bindings.createObjectBinding(() -> 
		        tool.getSlotIcon(equipment.getHeadSlot()),
		        equipment.headSlotProperty()
		    )
		);		
		
		headTip.textProperty().bind(
			Bindings.createStringBinding(() -> {
				if (equipment.getHeadSlot() != null) {
					return tool.createTooltipString(equipment.getHeadSlot());
				} else {
					return "Kopfrüstung";
				}
			}, equipment.headSlotProperty())
		);
		
		Tooltip.install(head, headTip);
		
		// CHEST
		itemChest.imageProperty().bind(
		    Bindings.createObjectBinding(() ->
		        tool.getSlotIcon(equipment.getChestSlot()),
		        equipment.chestSlotProperty()
		    )
		);
		
		chestTip.textProperty().bind(
			Bindings.createStringBinding(() -> {
				if (equipment.getChestSlot() != null) {
					return tool.createTooltipString(equipment.getChestSlot());
				} else {
					return "Brustrüstung";
				}
			}, equipment.chestSlotProperty())
		);
			
		Tooltip.install(chest, chestTip);
		
		// HANDS
		itemHands.imageProperty().bind(
		    Bindings.createObjectBinding(() ->
		        tool.getSlotIcon(equipment.getHandsSlot()),
		        equipment.handsSlotProperty()
		    )
		);
		
		handsTip.textProperty().bind(
			Bindings.createStringBinding(() -> {
				if (equipment.getHandsSlot() != null) {
					return tool.createTooltipString(equipment.getHandsSlot());
				} else {
					return "Handschuhe";
				}
			}, equipment.handsSlotProperty())
		);
		
		Tooltip.install(hands, handsTip);
		
		// LEGS
		itemLegs.imageProperty().bind(
		    Bindings.createObjectBinding(() ->
		        tool.getSlotIcon(equipment.getLegsSlot()),
		        equipment.legsSlotProperty()
		    )
		);
		
		legsTip.textProperty().bind(
			Bindings.createStringBinding(() -> {
				if (equipment.getLegsSlot() != null) {
					return tool.createTooltipString(equipment.getLegsSlot());
				} else {
					return "Beinrüstung";
				}
			}, equipment.legsSlotProperty())
		);
		
		Tooltip.install(legs, legsTip);
		
		// MAIN HAND
		itemMainHand.imageProperty().bind(
			Bindings.createObjectBinding(() ->
				tool.getSlotIcon(equipment.getMainHandSlot()),
				equipment.mainHandSlotProperty()
			)
		);
		
		mainHandTip.textProperty().bind(
			Bindings.createStringBinding(() -> {
				if (equipment.getMainHandSlot() != null) {
					return tool.createTooltipString(equipment.getMainHandSlot());
				} else {
					return "Haupthand";
				}
			}, equipment.mainHandSlotProperty())
		);
		
		Tooltip.install(mainHand, mainHandTip);
		
		// OFF HAND
		itemOffHand.imageProperty().bind(
			Bindings.createObjectBinding(() ->
		        tool.getSlotIcon(equipment.getOffHandSlot()),
		        equipment.offHandSlotProperty()
		    )
		);
		
		offHandTip.textProperty().bind(
			Bindings.createStringBinding(() -> {
				if (equipment.getOffHandSlot() != null) {
					return tool.createTooltipString(equipment.getOffHandSlot());
				} else {
					return "Nebenhand";
				}
			}, equipment.offHandSlotProperty())
		);
		
		Tooltip.install(offHand, offHandTip);
	}

	
	// the box has all slots as a stack pane. each stack pane has an image view for
	// background, and an image view for the equipped item. It also has a user data,
	// having the actual slot (enumerator).
	private VBox createEquipmentBox() {
		VBox equipmentBox = new VBox();
		
		TilePane equipmentPane = new TilePane();
		equipmentPane.setMaxWidth(TILE_SIZE);
		equipmentPane.setPrefColumns(1);
		
		UIToolbox tool = new UIToolbox();
		
		// background for empty slots
		ImageView emptyHeadSlot 	= tool.createIconOf(SLOT_HEAD, TILE_SIZE);
		ImageView emptyChestSlot	= tool.createIconOf(SLOT_CHEST, TILE_SIZE);
		ImageView emptyHandsSlot 	= tool.createIconOf(SLOT_HANDS, TILE_SIZE);
		ImageView emptyLegsSlot 	= tool.createIconOf(SLOT_LEGS, TILE_SIZE);
		ImageView emptyMainHandSlot = tool.createIconOf(SLOT_MAINHAND, TILE_SIZE);
		ImageView emptyOffHandSlot 	= tool.createIconOf(SLOT_OFFHAND, TILE_SIZE);
		
		// empty image views for equipped items
		this.itemHead 		= new ImageView();
		this.itemHead.setPreserveRatio(true);
		this.itemHead.setFitHeight(TILE_SIZE);
		this.itemHead.setFitWidth(TILE_SIZE);
		
		
		this.itemChest		= new ImageView();
		this.itemChest.setPreserveRatio(true);
		this.itemChest.setFitHeight(TILE_SIZE);
		this.itemChest.setFitWidth(TILE_SIZE);
		
		this.itemHands 		= new ImageView();
		this.itemHands.setPreserveRatio(true);
		this.itemHands.setFitHeight(TILE_SIZE);
		this.itemHands.setFitWidth(TILE_SIZE);
		
		this.itemLegs 		= new ImageView();
		this.itemLegs.setPreserveRatio(true);
		this.itemLegs.setFitHeight(TILE_SIZE);
		this.itemLegs.setFitWidth(TILE_SIZE);
		
		this.itemMainHand 	= new ImageView();
		this.itemMainHand.setPreserveRatio(true);
		this.itemMainHand.setFitHeight(TILE_SIZE);
		this.itemMainHand.setFitWidth(TILE_SIZE);
		
		this.itemOffHand 	= new ImageView();
		this.itemOffHand.setPreserveRatio(true);
		this.itemOffHand.setFitHeight(TILE_SIZE);
		this.itemOffHand.setFitWidth(TILE_SIZE);
		
		// the actual slot, as a stack pane
		this.head 		= new StackPane();
		this.chest 		= new StackPane();
		this.hands 		= new StackPane();
		this.legs 		= new StackPane();
		this.mainHand 	= new StackPane();
		this.offHand 	= new StackPane();
		
		this.headTip = tool.createTooltip("Kopfrüstung");
		this.chestTip = tool.createTooltip("Brustrüstung");
		this.handsTip = tool.createTooltip("Handschuhe");
		this.legsTip = tool.createTooltip("Beinrüstung");
		this.mainHandTip = tool.createTooltip("Haupthand");
		this.offHandTip = tool.createTooltip("Nebenhand");
		
		Tooltip.install(head, headTip);
		Tooltip.install(chest, chestTip);
		Tooltip.install(hands, handsTip);
		Tooltip.install(legs, legsTip);
		Tooltip.install(mainHand, mainHandTip);
		Tooltip.install(offHand, offHandTip);
		
		this.head.getStyleClass().add("slot-pane");
		this.chest.getStyleClass().add("slot-pane");
		this.hands.getStyleClass().add("slot-pane");
		this.legs.getStyleClass().add("slot-pane");
		this.mainHand.getStyleClass().add("slot-pane");
		this.offHand.getStyleClass().add("slot-pane");
		
		// give the panes their slots, to recognize via user data, witch slot is clicked
		this.head.setUserData(Slot.HEAD);
		this.chest.setUserData(Slot.CHEST);
		this.hands.setUserData(Slot.HANDS);
		this.legs.setUserData(Slot.LEGS);
		this.mainHand.setUserData(Slot.MAINHAND);
		this.offHand.setUserData(Slot.OFFHAND);
		
		this.head.getChildren().addAll(
				emptyHeadSlot,
				this.itemHead);
		
		this.chest.getChildren().addAll(
				emptyChestSlot,
				this.itemChest);
		
		this.hands.getChildren().addAll(
				emptyHandsSlot,
				this.itemHands);
		
		this.legs.getChildren().addAll(
				emptyLegsSlot,
				this.itemLegs);
		
		this.mainHand.getChildren().addAll(
				emptyMainHandSlot,
				this.itemMainHand);
		
		this.offHand.getChildren().addAll(
				emptyOffHandSlot,
				this.itemOffHand);
		
		equipmentPane.getChildren().addAll(
				this.head,
				this.chest,
				this.hands,
				this.legs,
				this.mainHand,
				this.offHand);
		
		equipmentPane.setHgap(5);
		equipmentPane.setVgap(5);
		
		equipmentBox.getChildren().add(equipmentPane);
		
		return equipmentBox;
	}
	
	public StackPane getHeadPane() { return this.head; }
	public StackPane getChestPane() { return this.chest; }
	public StackPane getHandsPane() { return this.hands; }
	public StackPane getLegsPane() { return this.legs; }
	public StackPane getMainHandPane() { return this.mainHand; }
	public StackPane getOffHandPane() { return this.offHand; }
	
}

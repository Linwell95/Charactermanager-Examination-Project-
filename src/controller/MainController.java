package controller;

import db.DatabaseService;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Character;
import view.CharacterEquipmentView;
import view.CharacterInventoryView;
import view.CharacterListView;
import view.CharacterStatsView;
import view.ItemShopWindow;
import view.MainWindow;


public class MainController {

	private final MainWindow mainView;
	
	// Views
	private final CharacterListView characterListView = new CharacterListView();
	private final CharacterStatsView characterStatsView = new CharacterStatsView();
	private final CharacterEquipmentView characterEquipmentView = new CharacterEquipmentView();
	private final CharacterInventoryView characterInventoryView = new CharacterInventoryView();
	
	private final ItemShopWindow itemShopWindow = new ItemShopWindow();
	
	
	// Controller
	private final CharacterListController characterListController;
	private final CharacterStatsController characterStatsController;
	private final CharacterEquipmentController characterEquipmentController;
	private final CharacterInventoryController characterInventoryController;
	
	private final ItemShopController itemShopController;
	
	private ObservableList<Character> masterList = FXCollections.observableArrayList(DatabaseService.loadCharactersData());
	private final ObjectProperty<Character> selectedCharacterProperty = new SimpleObjectProperty<>();
	
	// =============================================
	// Constructor
	// =============================================
	public MainController() {
		
		this.characterListController = new CharacterListController(this);
		this.characterStatsController = new CharacterStatsController(this);
		this.characterEquipmentController = new CharacterEquipmentController(this);
		this.characterInventoryController = new CharacterInventoryController(this);
		
		this.itemShopController = new ItemShopController(this);
		
		this.mainView = new MainWindow(
				this.characterListView,
				this.characterStatsView,
				this.characterEquipmentView,
				this.characterInventoryView);
		
		setupEventHandlers();
	}
	
	private void setupEventHandlers() {
		this.characterStatsView.getBtnShop().setOnAction(e -> {
			this.itemShopController.openItemShop();
		});
		
		this.mainView.getCenter().visibleProperty().bind(Bindings.isNotNull(selectedCharacterProperty));
		this.mainView.getRight().visibleProperty().bind(Bindings.isNotNull(selectedCharacterProperty));
	}
	
	public void setSelectedCharacter(Character character) {
		this.selectedCharacterProperty.set(character);
		this.characterEquipmentController.setSelectedCharacter(character);
		this.characterStatsController.setSelectedCharacter(character);
		this.characterInventoryController.setSelectedCharacter(character);
		this.itemShopController.setSelectedCharacter(character);
	}
	
	
	// =============================================
	// Getter
	// =============================================
	public MainWindow getMainView() { return this.mainView; }
	
	public CharacterListView getCharacterListview() { return this.characterListView; }
	public CharacterStatsView getCharacterStatsView() { return this.characterStatsView; }
	public CharacterEquipmentView getCharacterEquipmentView() { return this.characterEquipmentView; }
	public CharacterInventoryView getCharacterInventoryView() { return this.characterInventoryView; }
	
	public ItemShopWindow getItemShopWindow() { return this.itemShopWindow; }
	
	public ObservableList<Character> getMasterList() { return this.masterList; }
}

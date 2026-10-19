package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Armor;
import model.Filter;
import model.Item;
import model.Weapon;

public class ItemShopWindow extends Stage {

    private TextField txtSearch;
    private ComboBox<Filter> comboTypeFilter;
    private ComboBox<String> comboSubFilter;
    private TableView<Item> itemTable;
    private Button btnAddToInventory;
    private Button btnReset;


    // =============================================
    // Constructor
    // =============================================
    public ItemShopWindow() {
        this.setTitle("Shop");

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setMinWidth(900);
        root.getStyleClass().add("shop-window");

        root.getChildren().addAll(
            createFilterBox(),
            createTableView(),
            createButtonBox()
        );
        
        root.getStyleClass().add("item-shop-window");

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/CSS/default.css").toExternalForm());
        this.setScene(scene);
    }


    // =============================================
    // UI-Building
    // =============================================
    // Box with filter options - Returns filled HBox 
    private HBox createFilterBox() {
        txtSearch = new TextField();
        txtSearch.setPromptText("Gegenstand suchen...");
        HBox.setHgrow(txtSearch, Priority.ALWAYS);

        comboTypeFilter = new ComboBox<>();
        comboTypeFilter.setItems(FXCollections.observableArrayList(Filter.values()));
        comboTypeFilter.getSelectionModel().selectFirst();

        comboSubFilter = new ComboBox<>();
        comboSubFilter.setDisable(true);
        
        btnReset = new Button("Filter zurücksetzen");

        HBox filterBox = new HBox(5, txtSearch, comboTypeFilter, comboSubFilter, btnReset);
        filterBox.setAlignment(Pos.CENTER_LEFT);

        return filterBox;
    }
    
    // Table View - Returns configured view
    private TableView<Item> createTableView() {
        itemTable = new TableView<>();

        TableColumn<Item, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Item, String> typeCol = new TableColumn<>("Typ");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Item, String> detailsCol = new TableColumn<>("Details");
        detailsCol.setCellValueFactory(cell -> {
        	Item item = cell.getValue();
        	if (item instanceof Weapon w) {
        		return new SimpleStringProperty(
        				"Schaden: " + w.getDamage());
        	}
        	if (item instanceof Armor a) {
        		return new SimpleStringProperty(
        				"Schutz: " + a.getDefense());
        	}
        	return new SimpleStringProperty("-");
        });
        
        TableColumn<Item, String> descriptionCol = new TableColumn<>("Beschreibung");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        itemTable.getColumns().add(nameCol);
        itemTable.getColumns().add(typeCol);
        itemTable.getColumns().add(detailsCol);
        itemTable.getColumns().add(descriptionCol);
        
        VBox.setVgrow(itemTable, Priority.ALWAYS);
        return itemTable; 
    }

    // Box for lower Add Button - Returns HBox with the Button inside
    private HBox createButtonBox() {
        btnAddToInventory = new Button("In Rucksack legen");

        HBox actionBox = new HBox(5, btnAddToInventory);
        actionBox.setAlignment(Pos.BASELINE_RIGHT);

        return actionBox;
    }

    
    // =============================================
    // Getter
    // =============================================
    public TextField getTxtSearch() { return txtSearch; }
    public ComboBox<Filter> getComboTypeFilter() { return comboTypeFilter; }
    public ComboBox<String> getComboSubFilter() { return comboSubFilter; }
    public TableView<Item> getItemTable() { return itemTable; }
    public Button getBtnAddToInventory() { return btnAddToInventory; }
    public Button getBtnReset() { return btnReset; }
}

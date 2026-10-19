package view;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.CharacterClass;
import model.Species;

public class CharacterEditWindow extends Stage {

    private TextField txtName;
    private Spinner<Integer> spinStr, spinDex, spinCon, spinInt, spinWis, spinCha;
    private ComboBox<CharacterClass> classComboBox;
    private ComboBox<Species> raceComboBox;

    private Button btnApply;
    private Button btnCancel;


    // =============================================
    // Constructor
    // =============================================
    public CharacterEditWindow() {

        this.initModality(Modality.APPLICATION_MODAL);
        this.setTitle("Charakter bearbeiten");

        VBox root = new VBox(20);
        root.getChildren().addAll(
            createHeaderBox(),
            createInputGrid(),
            createButtonBox()
        );

        root.getStyleClass().add("character-edit-window");
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/CSS/default.css").toExternalForm());
        this.setScene(scene);
    }


    // =============================================
    // UI-Methods
    // =============================================
    private HBox createHeaderBox() {
        HBox headerBox = new HBox();
        Label titleLable = new Label("Charakterbearbeitung");
        titleLable.getStyleClass().add("label-bold");

        headerBox.getChildren().add(titleLable);

        return headerBox;
    }

    private HBox createButtonBox() {
        HBox buttonBox = new HBox(5);
        this.btnApply = new Button("Übenehmen");
        this.btnCancel = new Button("Abbrechen");

        buttonBox.getChildren().addAll(
            btnCancel,
            btnApply
        );

        return buttonBox;
    }

    private GridPane createInputGrid() {
        UIToolbox tool = new UIToolbox();
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(5);

        // Row 0: Name
        Label lblName = new Label("Name:");
        txtName = new TextField();
        txtName.setPromptText("Name des Helden...");
        grid.add(lblName, 0, 0);
        grid.add(txtName, 1, 0);

        // Row 1: Class and Race
        Label lblRace = new Label("Volk:");
        raceComboBox = new ComboBox<>();
        raceComboBox.setItems(FXCollections.observableArrayList(Species.values()));
        raceComboBox.getSelectionModel().selectFirst();
        grid.add(lblRace, 0, 1);
        grid.add(raceComboBox, 1, 1);

        Label lblClass = new Label("Klasse:");
        classComboBox = new ComboBox<>();
        classComboBox.setItems(FXCollections.observableArrayList(CharacterClass.values()));
        classComboBox.getSelectionModel().selectFirst();
        grid.add(lblClass,2 ,1);
        grid.add(classComboBox, 3, 1);

        // Row 2: STR & DEX
        Label lblStr = new Label("Stärke (STR):");
        spinStr = tool.createAttributeSpinner();
        grid.add(lblStr, 0, 2);
        grid.add(spinStr, 1, 2);

        Label lblDex = new Label("Geschick (DEX):");
        spinDex = tool.createAttributeSpinner();
        grid.add(lblDex, 2, 2);
        grid.add(spinDex, 3, 2);

        // Row 3: CON & INT
        Label lblCon = new Label("Konstitution (CON):");
        spinCon = tool.createAttributeSpinner();
        grid.add(lblCon, 0, 3);
        grid.add(spinCon, 1, 3);

        Label lblInt = new Label("Intelligenz (INT):");
        spinInt = tool.createAttributeSpinner();
        grid.add(lblInt, 2, 3);
        grid.add(spinInt, 3, 3);

        // Row 4: WIS & CHA
        Label lblWis = new Label("Weisheit (WIS):");
        spinWis = tool.createAttributeSpinner();
        grid.add(lblWis, 0, 4);
        grid.add(spinWis, 1, 4);

        Label lblCha = new Label("Charisma (CHA):");
        spinCha = tool.createAttributeSpinner();
        grid.add(lblCha, 2, 4);
        grid.add(spinCha, 3, 4);

        return grid;
    }


    // =============================================
    // Getter
    // =============================================
    // Getter for Values
    public String getInputName() { return txtName.getText().trim(); }
    public int getInputStr() { return spinStr.getValue(); }
    public int getInputDex() { return spinDex.getValue(); }
    public int getInputCon() { return spinCon.getValue(); }
    public int getInputInt() { return spinInt.getValue(); }
    public int getInputWis() { return spinWis.getValue(); }
    public int getInputCha() { return spinCha.getValue(); }

    // Getter for UI
    public Spinner<Integer> getStrSpinner() { return spinStr; }
    public Spinner<Integer> getDexSpinner() { return spinDex; }
    public Spinner<Integer> getConSpinner() { return spinCon; }
    public Spinner<Integer> getIntSpinner() { return spinInt; }
    public Spinner<Integer> getWisSpinner() { return spinWis; }
    public Spinner<Integer> getChaSpinner() { return spinCha; }
    public TextField getTextFieldName() { return txtName; }
    public Button getBtnApply() { return btnApply; }
    public Button getBtnCancel() { return btnCancel; }
    public ComboBox<Species> getRaceComboBox() { return raceComboBox; }
    public ComboBox<CharacterClass> getClassComboBox() { return classComboBox; }

}

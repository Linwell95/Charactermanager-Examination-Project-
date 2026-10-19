package view;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import model.Character;

public class CharacterListView extends VBox {

	private Button btnNew;
	private Button btnDelete;
	
	private ListView<Character> listView;
	
	public CharacterListView() {
		
		this.getChildren().addAll(
				createListViewBox(),
				createButtonBox());
		
		this.getStyleClass().add("character-list-view");
		this.setSpacing(10);
	}
	
	
	private HBox createButtonBox() {
		HBox buttonBox = new HBox(5);
		
		this.btnDelete = new Button("Löschen");
		this.btnNew = new Button("Neu");
		
		buttonBox.getChildren().addAll(
				this.btnNew,
				this.btnDelete);
		
		return buttonBox;
	}
	
	
	private VBox createListViewBox() {
		VBox listViewBox = new VBox();
		VBox.setVgrow(listViewBox, Priority.ALWAYS);
		
		this.listView = new ListView<>();
		VBox.setVgrow(listView, Priority.ALWAYS);
		
		listViewBox.getChildren().add(listView);
		listViewBox.getStyleClass().add("character-list-view-box");
		
		return listViewBox;		
	}
	
	public ListView<Character> getListView() { return this.listView; }
	public Button getBtnNew() { return this.btnNew; }
	public Button getBtnDelete() { return this.btnDelete; }
	
}

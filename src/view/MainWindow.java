package view;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MainWindow extends BorderPane {
	
	public MainWindow(
			CharacterListView characterListView, 
			CharacterStatsView characterStatsView, 
			CharacterEquipmentView characterEquipmentView, 
			CharacterInventoryView characterInventoryView) {
		
		Image img = new Image(getClass().getResourceAsStream("/Images/background.png"));
		BackgroundImage bg = new BackgroundImage(
				img,
				BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER,
				new BackgroundSize(
						BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true
						)
				);
		
		this.setBackground(new Background(bg));
		
		HBox midContainer = new HBox();
		VBox spacing = new VBox();
		spacing.setMinWidth(30);
		HBox.setHgrow(spacing, Priority.ALWAYS);
		
		midContainer.getChildren().addAll(
				characterStatsView, 
				spacing,
				characterEquipmentView);
		
		this.setCenter(midContainer);
		this.setLeft(characterListView);
		this.setRight(characterInventoryView);
	}	
}

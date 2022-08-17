package model;

import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Cell extends ListCell<String> {

    HBox hBox = new HBox();
    ImageView imageView;

    public Cell(){
        super();
        imageView = new ImageView();
        imageView.setFitWidth(25);
        imageView.setFitHeight(25);

        hBox.getChildren().addAll(imageView);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(20);

    }

    public void updateItem(String s, boolean b) {
        super.updateItem(s, b);
        if(b){
            setGraphic(null);
        }else {
            imageView.setImage(new Image(CardList.getIcon(getItem())));
            setText(s);
            setGraphic(hBox);
        }

    }
}

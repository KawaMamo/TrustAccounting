package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

class EditCell extends ListCell<String> {

    CheckBox checkBox = new CheckBox();
    HBox hBox = new HBox();
    ImageView imageView;

    private final ObjectProperty<ListCell<String>> dragSource = new SimpleObjectProperty<>();


    public EditCell(){
        super();
        imageView = new ImageView();
        imageView.setFitWidth(25);
        imageView.setFitHeight(25);

        hBox.getChildren().add(checkBox);
        hBox.getChildren().addAll(imageView);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(20);

        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                System.out.println(getItem());
            }
        });

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

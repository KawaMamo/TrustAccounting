package com.example.trustaccounting;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import model.CardList;
import model.Detachable;

import java.io.IOException;


public class Cell extends ListCell<String> {

    HBox hBox = new HBox();
    ImageView imageView;

    public Cell(TabPane workArea){
        super();
        imageView = new ImageView();
        imageView.setFitWidth(25);
        imageView.setFitHeight(25);

        hBox.getChildren().addAll(imageView);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(20);

        setOnMouseClicked(mouseEvent -> {

            Detachable newTab = new Detachable(getItem(), CardList.getIcon(getItem()));
            try {
                newTab.setContent(FXMLLoader.load(this.getClass().getResource(CardList.getLocation(getItem()))));
            } catch (IOException e) {
                e.printStackTrace();
            }
            workArea.getTabs().add(newTab);

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


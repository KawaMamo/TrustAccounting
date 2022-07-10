package com.example.trustaccounting;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;


import java.io.IOException;


public class AccountCard {

    @FXML
    private GridPane navControl;

    @FXML
    private GridPane controls;

    @FXML
    private void initialize(){

        FXMLLoader navLoader = new FXMLLoader();
        navLoader.setLocation(getClass().getResource("navigation.fxml"));
        try {
            navControl.getChildren().add(navLoader.load());
            Navigation navigation = navLoader.getController();
            navigation.setTitleLbl("بطاقة حساب");
        } catch (IOException e) {
            e.printStackTrace();
        }

        FXMLLoader controlsLoader = new FXMLLoader();
        controlsLoader.setLocation(getClass().getResource("formControls.fxml"));
        try {
            controls.getChildren().add(controlsLoader.load());
            FormControls formControls = controlsLoader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

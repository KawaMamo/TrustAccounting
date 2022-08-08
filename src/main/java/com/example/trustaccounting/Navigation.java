package com.example.trustaccounting;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.LoginParameter;
import model.Navigator;
import model.WebClient;
import org.controlsfx.control.Notifications;


public class Navigation {

    @FXML
    private Label titleLbl;


    @FXML
    private void initialize(){

    }

    public void setTitleLbl(String title){
        titleLbl.setText(title);
    }



}

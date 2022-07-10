package com.example.trustaccounting;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Navigator;



public class Navigation {

    @FXML
    private Label titleLbl;


    @FXML
    private void initialize(){
        Navigator navigator = new Navigator("test");
        navigator.generateToken();
    }

    public void setTitleLbl(String title){
        titleLbl.setText(title);
    }



}

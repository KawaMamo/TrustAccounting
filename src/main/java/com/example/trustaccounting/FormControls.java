package com.example.trustaccounting;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import model.MyButtonSkin;

public class FormControls {

    @FXML
    MFXButton newBtn = new MFXButton();

    @FXML
    private void initialize(){

        newBtn.setSkin(new MyButtonSkin(newBtn));

    }
}

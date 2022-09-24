package com.example.trustaccounting;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class Navigation {

    public NavigationService navigationService;
    @FXML
    private Label titleLbl;

    @FXML
    private MFXTextField idTF;


    @FXML
    private void initialize(){

    }

    public void setTitleLbl(String title){
        titleLbl.setText(title);
    }

    public void setIdTF(int id){
        idTF.setText(String.valueOf(id));
    }

    @FXML
    void next(ActionEvent event) {

    }

    @FXML
    void nextPage(ActionEvent event) {

    }

    @FXML
    void prePage(ActionEvent event) {

    }

    @FXML
    void previous(ActionEvent event) {
        idTF.setText(String.valueOf(Integer.parseInt(idTF.getText())-1));
        navigationService.setContent(Integer.parseInt(idTF.getText()));
    }

}

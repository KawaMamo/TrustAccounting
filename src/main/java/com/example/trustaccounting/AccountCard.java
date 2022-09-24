package com.example.trustaccounting;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import model.WebClient;
import org.controlsfx.control.Notifications;
import org.json.JSONObject;


import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;


public class AccountCard implements NavigationService{

    @FXML
    private MFXTextField accountCode;
    @FXML
    private MFXTextField accountNameTF;
    @FXML
    private MFXTextField englishNameTF;

    @FXML
    private GridPane navControl;

    @FXML
    private GridPane controls;

    JSONObject accountObj;
    WebClient webClient = new WebClient("app.config");

    @FXML
    private void initialize(){

        FXMLLoader navLoader = new FXMLLoader();
        navLoader.setLocation(getClass().getResource("navigation.fxml"));
        try {
            navControl.getChildren().add(navLoader.load());
            Navigation navigation = navLoader.getController();
            navigation.setTitleLbl("بطاقة حساب");

            webClient.setEndPoint("/account/getLast");
            accountObj = new JSONObject(webClient.sendGetRequest().body().toString());
            System.out.println("webClient.sendGetRequest().body().toString() "+webClient.sendGetRequest().body().toString());
            navigation.setIdTF(accountObj.getInt("id")+1);
            navigation.navigationService = this::setContent;
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


    @Override
    public void setContent(int id) {
        JSONObject currentAccount = getAccountById(id);
        if(currentAccount != null){
            accountCode.setText(currentAccount.getString("account_code"));
            accountNameTF.setText(currentAccount.getString("accountName"));
            englishNameTF.setText(currentAccount.getString("english_name"));
        }else {
            accountCode.setText(null);
            accountNameTF.setText(null);
            englishNameTF.setText(null);
            Notifications.create().title("Missing").text("this id is not in DB").showInformation();
        }

    }

    private JSONObject getAccountById(int id){
        Map<String, String> postParam = new HashMap<>();
        postParam.put("id", String.valueOf(id));
        HttpResponse httpResponse = webClient.sendPostRequest("/account/getAccount", String.valueOf(id));
        String response = httpResponse.body().toString();
        if(response.equals("null")){
            return null;
        }
        JSONObject accountJson = new JSONObject(response);
        return accountJson;
    }
}

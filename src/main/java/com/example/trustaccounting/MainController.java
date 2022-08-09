package com.example.trustaccounting;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import model.Detachable;
import model.LoginParameter;
import model.WebClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class MainController {

    @FXML
    private SplitPane chatPane;

    @FXML
    private TabPane workArea;

    @FXML
    private TreeView structureTree;

    @FXML
    private void initialize(){

        addTabs();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // should be done by dongle
                LoginParameter.setUserName("kawa");LoginParameter.setPassword("kawa");LoginParameter.generateBasicAuthentication();
                // creating web client
                WebClient webClient = new WebClient();
                webClient.authorize();

                Map<String, String> postParam = new HashMap<>();
                postParam.put("id", "22");
                webClient.setEndPoint("trust/getById.php");
                webClient.setPostParameters(postParam);

                System.out.println(webClient.sendPostRequest());
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();



    }

    private void addTabs(){

        Detachable two = new Detachable("Material", "m_64px.png");
        Detachable newTab = new Detachable("Account", "g_32px.png");

        try {
            newTab.setContent(FXMLLoader.load(this.getClass().getResource("accountCard.fxml")));
            two.setContent(FXMLLoader.load(this.getClass().getResource("materialCard.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        workArea.getTabs().add(newTab);
        workArea.getTabs().add(two);
        workArea.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        workArea.setTabMinHeight(26);
        workArea.setTabMinWidth(120);

    }
}
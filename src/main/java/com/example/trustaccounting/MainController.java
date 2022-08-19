package com.example.trustaccounting;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import model.*;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class MainController {

    @FXML
    private SplitPane chatPane;

    @FXML
    public TabPane workArea;

    @FXML
    private GridPane quickAccessGrid;

    public static DraggableListView quickAccessList;

    private boolean isEditable = false;

    @FXML
    private void initialize(){

        try {
            Config cardsData = new Config("cards.config");
            quickAccessList = new DraggableListView(workArea, cardsData);
        } catch (IOException e) {
            e.printStackTrace();
            Notifications.create().text(e.getMessage()).showInformation();
        }

        quickAccessGrid.add(quickAccessList,0, 1);
        addWelcomeTab();
        setWorkAreaStyle();
        //authorizeAndPost();

    }

    @FXML
    private void editQuickList(){
        isEditable = !isEditable;
        quickAccessList.changeCellFactory(isEditable);

    }

    private void addWelcomeTab(){

        Detachable newTab = new Detachable("Welcome", CardList.getIcon("Welcome"));
        try {
            newTab.setContent(FXMLLoader.load(this.getClass().getResource("welcomeCard.fxml")));
            newTab.setClosable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        workArea.getTabs().add(newTab);

    }

    private void setWorkAreaStyle(){
        workArea.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        workArea.setTabMinHeight(26);
        workArea.setTabMinWidth(120);
    }

    private void authorizeAndPost(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // should be done by dongle
                LoginParameter.setUserName("kawa");LoginParameter.setPassword("kawa");LoginParameter.generateBasicAuthentication();
                // creating web client
                String fileName = "app.config";
                WebClient webClient = new WebClient(fileName);
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
}
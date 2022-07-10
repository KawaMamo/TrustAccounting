package com.example.trustaccounting;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import model.Detachable;

import java.io.IOException;


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
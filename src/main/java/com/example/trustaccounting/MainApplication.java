package com.example.trustaccounting;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Config;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("mainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(" Trust");
        stage.setScene(scene);
        setScreenMode(stage);
        stage.getIcons().add(new Image("infinity.png"));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void setScreenMode(Stage stage) throws IOException {

        // to avoid transaction from original width to max, set the width and height to max
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
        // load config file
        String fileName = "app.config";
        Config config = new Config(fileName);
        if(config.getProp().getProperty("fullScreen").equals("true")){
            stage.setMaximized(true);
        }

    }
}
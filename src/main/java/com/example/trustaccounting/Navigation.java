package com.example.trustaccounting;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;


public class Navigation {

    @FXML
    private Label titleLbl;
    // Create client obj
    HttpClient client = HttpClient.newHttpClient();

    @FXML
    private void initialize(){

    }

    public void setTitleLbl(String title){
        titleLbl.setText(title);
    }

    public int getLastId(String tableName){

        JSONObject jsonObject = null;
        
        // Get request
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("localhost/api/getLastId?tableName="+tableName))
                    .GET()
                    .build();

            // Send the request and save the response
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            jsonObject = new JSONObject(response.toString());
            System.out.println();



        } catch (URISyntaxException e) {
            e.printStackTrace();
            Notifications.create().title("URISyntaxException").text(e.getMessage()).showError();
        } catch (IOException e) {
            e.printStackTrace();
            Notifications.create().title("printStackTrace").text(e.getMessage()).showError();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Notifications.create().title("InterruptedException").text(e.getMessage()).showError();
        }

        return Integer.parseInt(jsonObject.getString("lastId"));
    }

}

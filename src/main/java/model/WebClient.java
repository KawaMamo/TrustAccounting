package model;

import javafx.application.Platform;
import org.controlsfx.control.Notifications;
import org.json.JSONObject;
import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static javax.security.auth.callback.ConfirmationCallback.OK;


public class WebClient {

    private static String token = "dummyToken";
    private String endPoint;
    private Map<String, String> postParameters = new HashMap<>();
    HttpClient client = HttpClient.newHttpClient();
    private int requestTries = 0;
    private static String pHPSSIDCookie;

    Config config;

    public WebClient(String fileName){

        try {
            config = new Config(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void authorize(){

        endPoint = "/login";
        postParameters.put("username", LoginParameter.getUserName());
        postParameters.put("password", LoginParameter.getPassword());
        HttpResponse response = sendPostRequest();
        System.out.println(response);

        if(response == null){

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Notifications.create().title("server Error").text("Check the server. It seems like the server is not functioning as it's supposed").showError();
                }
            });

            while (response.body() == null){
                response = sendPostRequest();
                if(response != null){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Notifications.create().title("Server is back online").text("The server is available now").showInformation();
                        }
                    });
                    break;
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }else if(!(response.statusCode() == 200)){

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Notifications.create().title("authorization Error").text("Check your licensing mean").showError();
                }
            });

            while (!(response.statusCode() == 200)){
                response = sendPostRequest();
                if(response.statusCode() == 200){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Notifications.create().title("authorization successful").text("your are authorized to communicate with the server").showInformation();
                        }
                    });
                    break;
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }else {

            JSONObject jsonObject = new JSONObject(response.body().toString());
            this.token = jsonObject.getString("access_token");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Notifications.create().title("authorization successful").text("your are authorized to communicate with the server").showInformation();
                }
            });
        }


    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public void setPostParameters(Map<String, String> postParameters) {
        this.postParameters = postParameters;
    }

    public HttpResponse sendGetRequest(){

        HttpResponse response = null;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(config.getProp().getProperty("domain")+endPoint))
                    .GET()
                    .header("Authorization", "Bearer "+token)
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());


        } catch (URISyntaxException e) {
            e.printStackTrace();
            // TODO: do log4j
        } catch (ConnectException e){
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            // do log4j
        } catch (InterruptedException e) {
            e.printStackTrace();
            // do log4j
        }

        return response;
    }

    public static String getToken() {
        return token;
    }

    public HttpResponse sendPostRequest(){
        JSONObject jsonObject = null;
        postParameters.put("Authorization", "Bearer "+token);
        HttpRequest request = null;
        System.out.println("getFormDataAsString(postParameters)"+getFormDataAsString(postParameters));
        System.out.println(config.getProp().getProperty("domain")+endPoint);
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(config.getProp().getProperty("domain")+endPoint))
                    .POST(HttpRequest.BodyPublishers.ofString(getFormDataAsString(postParameters)))
                    .header("Content-Type",  "application/x-www-form-urlencoded")
                    .header("Authorization", "Bearer "+token)
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        HttpResponse response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;

    }

    public HttpResponse sendPostRequest(String endPoint, String payload){
        setEndPoint(endPoint);
        JSONObject jsonObject = null;
        postParameters.put("Authorization", "Bearer "+token);
        HttpRequest request = null;
        //System.out.println("getFormDataAsString(postParameters)"+getFormDataAsString(postParameters));
        //System.out.println(config.getProp().getProperty("domain")+endPoint);
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(config.getProp().getProperty("domain")+endPoint))
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .header("Content-Type",  "application/json")
                    .header("Authorization", "Bearer "+token)
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        HttpResponse response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;

    }

    private static String getFormDataAsString(Map<String, String> formData) {
        StringBuilder formBodyBuilder = new StringBuilder();
        for (Map.Entry<String, String> singleEntry : formData.entrySet()) {
            if (formBodyBuilder.length() > 0) {
                formBodyBuilder.append("&");
            }
            formBodyBuilder.append(URLEncoder.encode(singleEntry.getKey(), StandardCharsets.UTF_8));
            formBodyBuilder.append("=");
            formBodyBuilder.append(URLEncoder.encode(singleEntry.getValue(), StandardCharsets.UTF_8));
        }
        return formBodyBuilder.toString();
    }
}

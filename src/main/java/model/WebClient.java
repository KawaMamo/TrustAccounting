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
import java.util.Map;


public class WebClient {

    private static String token = "dummyToken";
    private String endPoint;
    private Map<String, String> postParameters;
    HttpClient client = HttpClient.newHttpClient();
    private int requestTries = 0;
    private static String pHPSSIDCookie;

    Config config;

    public WebClient(){

        try {
            config = new Config();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void authorize(){

        endPoint = "trust/auth.php";
        JSONObject response = sendGetRequest();


        if(response == null){

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Notifications.create().title("server Error").text("Check the server. It seems like the server is not functioning as it's supposed").showError();
                }
            });

            while (response == null){
                response = sendGetRequest();
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

        }else if(!response.getString("status").equals("authorized")){

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Notifications.create().title("authorization Error").text("Check your licensing mean").showError();
                }
            });

            while (!response.getString("status").equals("authorized")){
                response = sendGetRequest();
                if(response.getString("status").equals("authorized")){
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

            this.token = response.getString("token");
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

    public JSONObject sendGetRequest(){

        JSONObject jsonObject = null;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(config.getProp().getProperty("domain")+endPoint))
                    .GET()
                    .header("Authorization", LoginParameter.getBasicAuthentication())
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            jsonObject = new JSONObject(response.body().toString());

            String strResponse = URLDecoder.decode(response.headers().toString(), StandardCharsets.UTF_8);
            int phpSsid = strResponse.indexOf("PHPSESSID");
            String ssidCookie = strResponse.substring(phpSsid+10, phpSsid+36);
            pHPSSIDCookie = ssidCookie;

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

        return jsonObject;
    }

    public static String getToken() {
        return token;
    }

    public JSONObject sendPostRequest(){
        JSONObject jsonObject = null;
        postParameters.put("token", token);
        HttpRequest request = null;
        System.out.println("getFormDataAsString(postParameters)"+getFormDataAsString(postParameters));
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(config.getProp().getProperty("domain")+endPoint))
                    .POST(HttpRequest.BodyPublishers.ofString(getFormDataAsString(postParameters)))
                    .header("Content-Type",  "application/x-www-form-urlencoded")
                    .header("Cookie", "PHPSESSID="+pHPSSIDCookie)
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
        System.out.println("response.body().toString()"+response.body().toString());
        jsonObject = new JSONObject(response.body().toString());

        return jsonObject;

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

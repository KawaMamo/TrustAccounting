package model;

import org.controlsfx.control.Notifications;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class WebClient {

    private static String token = "dummyToken";
    private String endPoint;
    private Map<String, String> postParameters;
    HttpClient client = HttpClient.newHttpClient();

    Config config;

    public WebClient(){

        try {
            config = new Config();
        } catch (IOException e) {
            e.printStackTrace();
            // do logging
        }
    }

    public void authorize(){
        endPoint = "trust/auth.php";
        JSONObject response = sendGetRequest();
        this.token = response.getString("token");
        if(response.getString("status").equals("unauthorized")){
            Notifications.create().title("Unauthorized connection").text("get the wright dongle and try again").showError();
        }
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public void setPostParameters(Map<String, String> postParameters) {
        this.postParameters = postParameters;
    }

    public JSONObject sendGetRequest(){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // should be done by dongle

            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

        JSONObject jsonObject = null;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(config.getProp().getProperty("domain")+endPoint))
                    .GET()
                    .header("Authorization", LoginParameter.getBasicAuthentication())
                    .header("token", token)
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            jsonObject = new JSONObject(response.body().toString());

        } catch (URISyntaxException e) {
            e.printStackTrace();
            // TODO: do log4j
        } catch (IOException e) {
            e.printStackTrace();
            // do log4j
        } catch (InterruptedException e) {
            e.printStackTrace();
            // do log4j
        }

        // TODO: if not authorized ask for authorization
        if(jsonObject.getString("status").equals("unauthorized")){
            authorize();
        }

        return jsonObject;
    }

    public static String getToken() {
        return token;
    }
}

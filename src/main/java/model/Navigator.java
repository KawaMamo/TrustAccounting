package model;

import org.controlsfx.control.Notifications;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

/**
 * this class is responsible for navigating through cards
 * @author Kawa Mamo
 * @since 10-7-2022
 */
public class Navigator {

    private int currentId;
    private int lastId;
    private int firstId;
    private String tableName;

    public Navigator(String tableName){
        this.tableName = tableName;
    }

    HttpClient client = HttpClient.newHttpClient();

    public String generateToken(){

        JSONObject jsonObject = null;
        LoginParameter.setUserName("kawa");LoginParameter.setPassword("kawa");LoginParameter.generateBasicAuthentication();

        // Get request
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost/trust/auth.php"))
                    .GET()
                    .header("Authorization", LoginParameter.getBasicAuthentication())
                    .build();

            // Send the request and save the response
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            jsonObject = new JSONObject(response.body().toString());

        } catch (URISyntaxException e) {
            e.printStackTrace();
            Notifications.create().title("URISyntaxException").text(e.getMessage()).showError();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(jsonObject);
        return jsonObject.getString("token");


    }

    public int setLastId(){

        return 0;
    }

    public int setFirstId(){

        return 0;
    }
}

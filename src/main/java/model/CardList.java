package model;

public class CardList {

    public static String getIcon(String name){
        String icon;
        if(name.equals("Account")){
            icon = "Account.png";
        }else if(name.equals("Material")) {
            icon = "m_64px.png";
        }else {
            icon = "c_64px.png";
        }

        return icon;
    }

    public static String getLocation(String name){
        String icon;
        if(name.equals("Account")){
            icon = "accountCard.fxml";
        }else if(name.equals("Material")) {
            icon = "materialCard.fxml";
        }else {
            icon = "Account.png";
        }

        return icon;
    }
}

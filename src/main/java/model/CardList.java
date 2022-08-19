package model;

public class CardList {

    public static String getIcon(String name){
        String icon;
        if(name.equals("بطاقة حساب")){
            icon = "Account.png";
        }else if(name.equals("بطاقة مادة")) {
            icon = "m_64px.png";
        }else if(name.equals("Welcome")){
            icon = "welcome.png";
        }else {
            icon = "circled_c_96px.png";
        }

        return icon;
    }

    public static String getLocation(String name){
        String icon;
        if(name.equals("بطاقة حساب")){
            icon = "accountCard.fxml";
        }else if(name.equals("بطاقة مادة")) {
            icon = "materialCard.fxml";
        }else if(name.equals("Welcome")){
            icon = "welcomeCard.fxml";
        }else {
            icon = "Account.png";
        }

        return icon;
    }
}

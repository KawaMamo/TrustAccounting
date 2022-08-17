package model;

public class CardList {

    public static String getIcon(String name){
        String icon;
        if(name.equals("account")){
            icon = "c_64px.png";
        }else if(name.equals("material")) {
            icon = "m_64px.png";
        }else {
            icon = "Account.png";
        }

        return icon;
    }
}

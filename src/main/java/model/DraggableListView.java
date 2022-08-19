package model;

import com.example.trustaccounting.Cell;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.util.Callback;

public class DraggableListView extends ListView {

    private TabPane workArea;
    private Config cardsData;

    public DraggableListView(TabPane workArea, Config config){
        super();
        this.workArea = workArea;
        setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> list) {
                return new Cell(workArea);
            }
        });
        cardsData = config;
        populate();
    }

    public void changeCellFactory(boolean isEditable){

        setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> list) {
                if(isEditable){
                    return new EditCell();
                }else {
                    return new Cell(workArea);
                }
            }
        });
        populate();

    }

    private void populate(){
        getItems().clear();
        int cardsNumber = Integer.parseInt(cardsData.getProp().getProperty("cardsNumber"));
        for (int i = 1;i<=cardsNumber;i++){
            getItems().add(cardsData.prop.getProperty(String.valueOf(i)));
        }
    }
}








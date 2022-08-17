package model;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.util.Callback;

public class DraggableListView extends ListView {

    TabPane workArea;
    public DraggableListView(TabPane workArea){
        super();
        this.workArea = workArea;
        setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> list) {
                return new com.example.trustaccounting.Cell(workArea);
            }
        });
    }

    public void changeCellFactory(boolean isEditable){

        setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> list) {
                if(isEditable){
                    return new EditCell();
                }else {
                    return new com.example.trustaccounting.Cell(workArea);
                }
            }
        });

    }
}








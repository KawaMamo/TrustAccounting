package model;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class DraggableListView extends ListView {

    public DraggableListView(){
        super();
        setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> list) {
                return new Cell();
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
                    return new Cell();
                }
            }
        });

    }
}








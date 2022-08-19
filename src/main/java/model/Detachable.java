package model;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
/**
 * This class extends Tab class to create a tab which can be open in a new stage
 * by clicking and dragging using the right or the left mouse button
 * @author Kawa Mamo
 * @version 1.0
 * @since 2022-01-09
 */

public class Detachable extends Tab {

    private boolean isDetached = false;
    Stage additionalStage;
    Scene additionalScene;
    GridPane gridpane = new GridPane();

    WritableImage snapshot;
    ImageView imageView;
    Stage tempStage = new Stage();
    GridPane tempGridPane = new GridPane();


    public Detachable(String text, String iconImage){
        super();
        Label title = new Label("  "+text+"  ");
        Image icon = new Image(iconImage, 20, 20, true, true);
        HBox hBox = new HBox(new ImageView(icon), title);
        hBox.setAlignment(Pos.CENTER);
        setGraphic(hBox);

        setClosable(true);

        SnapshotParameters param = new SnapshotParameters();
        param.setDepthBuffer(true);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(0);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(100);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(0);

        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(0);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(100);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(0);

        title.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // 50 is added to mouse Y to decrease sensitivity for bad mouses
                if(mouseEvent.getSceneY() > getGraphic().localToScreen(getGraphic().getBoundsInLocal()).getMinY() || mouseEvent.getSceneY()+50 < getGraphic().localToScreen(getGraphic().getBoundsInLocal()).getMaxY()){
                    if(!isDetached){
                        gridpane.getColumnConstraints().addAll(col1,col2,col3);
                        gridpane.getRowConstraints().addAll(row1, row2,row3);

                        // add tab content to gridPane
                        gridpane.add(getContent(),1, 1);

                        // create new stage and set it always on top
                        additionalStage  = new Stage();
                        additionalStage.getIcons().add(icon);
                        additionalStage.setTitle(text);
                        additionalStage.setAlwaysOnTop(true);

                        additionalScene = new Scene(gridpane);
                        // add the gridPane as scene to the created stage
                        additionalStage.setScene(additionalScene);

                        snapshot = getContent().snapshot(param, null);
                        imageView = new ImageView(snapshot);

                        tempGridPane.getColumnConstraints().addAll(col1,col2,col3);
                        tempGridPane.getRowConstraints().addAll(row1, row2,row3);

                        tempGridPane.add(imageView, 1, 1);
                        Scene tempScene = new Scene(tempGridPane);
                        tempStage.setScene(tempScene);
                        tempStage.setOpacity(0.3);

                        tempStage.show();

                        isDetached = true;

                    }else {
                        tempStage.setX(mouseEvent.getScreenX()-tempStage.getWidth()/2);
                        tempStage.setY(mouseEvent.getScreenY()-(tempStage.getHeight()-tempStage.getScene().getHeight())/2);
                    }
                }

            }
        });

        title.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(isDetached){
                    tempStage.hide();
                    additionalStage.show();
                    additionalStage.setX(mouseEvent.getScreenX()-(additionalStage.getWidth()/2));
                    additionalStage.setY(mouseEvent.getScreenY()-(additionalStage.getHeight()-additionalStage.getScene().getHeight())/2);
                    getTabPane().getTabs().remove(getTabPane().getSelectionModel().getSelectedItem());
                }

            }
        });

    }




}

package model;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.skins.MFXButtonSkin;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;


public class MyButtonSkin extends MFXButtonSkin {

    public MyButtonSkin(MFXButton control) {

        super(control);

        final FadeTransition fadeIn = new FadeTransition(Duration.millis(100));
        fadeIn.setNode(control);
        fadeIn.setToValue(1);
        control.setOnMouseEntered(e -> fadeIn.playFromStart());

        final FadeTransition fadeOut = new FadeTransition(Duration.millis(100));
        fadeOut.setNode(control);
        fadeOut.setToValue(0.75);
        control.setOnMouseExited(e -> fadeOut.playFromStart());

        control.setOpacity(0.75);

        final TranslateTransition expandButton = new TranslateTransition(Duration.millis(120), control);
        expandButton.setNode(control);
        //expandButton.setToX(2);
        expandButton.setToY(5);
        expandButton.setAutoReverse(true);
        expandButton.setCycleCount(2);
        control.setOnMouseClicked(mouseEvent -> expandButton.play());



    }
}

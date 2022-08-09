module com.example.trustaccounting {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires MaterialFX;
    requires java.net.http;
    requires org.json;


    opens com.example.trustaccounting to javafx.fxml;
    exports com.example.trustaccounting;
}
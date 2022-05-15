module com.firefly.firefly {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires javafx.media;
    requires com.jfoenix;
    requires com.google.common;
    requires org.apache.commons.io;
    requires java.sql;


    opens com.firefly.firefly to javafx.fxml;
    exports com.firefly.firefly;
}
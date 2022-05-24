package com.firefly.firefly;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("LoginFrame.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.setFill(Color.TRANSPARENT);
        //Image icon = new Image("src/main/resources/images/Firefly_LOGO_2.png");
        //stage.getIcons().add(icon);

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.resizableProperty().setValue(false);
        stage.setTitle("FireFly");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
package com.firefly.firefly;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("LoginFrame.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //stage.getIcons().add(new Image(<HelloApplication>.class.getResourceAsStream("icon.png"));

        stage.resizableProperty().setValue(false);
        stage.setTitle("FireFly");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
package com.firefly.firefly;

import com.firefly.firefly.Sql.DatabaseConnetion;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static com.firefly.firefly.Sql.Utilizador.sha256;


public class LoginController extends DatabaseConnetion implements Initializable {

    //login Fields
    @FXML
    private JFXTextField LoginUsername;
    @FXML
    private JFXPasswordField LoginPassword;
    @FXML
    private JFXTextField PassSee;
    @FXML
    private ImageView Open;
    @FXML
    private ImageView Close;

    //Slider Stuff

    @FXML
    private ImageView ContentImg;
    @FXML
    private JFXRadioButton Slider;
    @FXML
    private JFXRadioButton Slider1;
    @FXML
    private JFXRadioButton Slider2;

    //Label
    @FXML
    private Label lb1;

    //See Better...-------------------------------------------------------------->
    //Change to 1st image!
    public void changeOne() {

        Image img1 = new Image("src/main/resources/images/slider/img1.png");
        Image img2 = new Image("src/main/resources/images/slider/img2.png");
        Image img3 = new Image("src/main/resources/images/slider/img3.png");

        final ToggleGroup group = new ToggleGroup();
        Slider.setToggleGroup(group);
        Slider1.setToggleGroup(group);
        Slider2.setToggleGroup(group);

        try {
        if (group.getSelectedToggle() != null) {

            if (group.getToggles().equals(Slider)) {
                ContentImg.setImage(img1);

            } else if (group.getToggles().equals(Slider1)) {
                ContentImg.setImage(img2);

            } else if (group.getToggles() == Slider2) {
                ContentImg.setImage(img3);
            }
        }else{
            ContentImg.setImage(img1);
        }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //change to register page!
    public void changeRegistrar1(ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("RegisterFrame1.fxml"));
        Scene TableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(TableViewScene);
        window.show();
    }


    public void Login (ActionEvent event){


        try{

        DatabaseConnetion connetion = new DatabaseConnetion();
        connetion.ligar();
        Connection conectDB = connetion.getCon();

        PreparedStatement ps;

        ps = conectDB.prepareStatement("SELECT * FROM Conta WHERE username=? && Pass=?");


        String usernameD = LoginUsername.getText();
        String passwordD = sha256(LoginPassword.getText());

        ps.setString(1,usernameD);
        ps.setString(2,passwordD);


        ResultSet resultSet = ps.executeQuery();

        if (resultSet.next()){

            Parent tableViewParent = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
            Scene TableViewScene = new Scene(tableViewParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(TableViewScene);
            window.show();

        }else {
            lb1.setVisible(true);
        }

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /*
        img1.setOpacity(0);
        img2.setOpacity(0);
        img3.setOpacity(0);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(img1.opacityProperty(), 100)),
                new KeyFrame(Duration.seconds(3), new KeyValue(img2.opacityProperty(),100)),
                new KeyFrame(Duration.seconds(6), new KeyValue(img3.opacityProperty(),100))
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();*/

    }
}

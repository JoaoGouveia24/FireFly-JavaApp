package com.firefly.firefly;

import com.firefly.firefly.Sql.DatabaseConnetion;
import com.google.common.hash.Hashing;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ResourceBundle;


public class LoginController extends DatabaseConnetion{

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

    //
    public static int USER_SESSION_ID = 0;
    public static String usernameD;
    //Label
    @FXML
    private Label lb1;

    //change to register page!
    public void changeRegistrar1(ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("RegisterFrame1.fxml"));
        Scene TableViewScene = new Scene(tableViewParent);
        TableViewScene.setFill(Color.TRANSPARENT);
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

        ps = conectDB.prepareStatement("SELECT * FROM Conta WHERE Username=? && Pass=?");


        usernameD = LoginUsername.getText();
        String passwordD = Hashing.sha256().hashString(LoginPassword.getText(), StandardCharsets.UTF_8).toString();

        ps.setString(1,usernameD);
        ps.setString(2,passwordD);


        ResultSet resultSet = ps.executeQuery();

        if (resultSet.next()){

            //Stores the id
            PreparedStatement id;
            id = conectDB.prepareStatement("SELECT Conta_Id FROM Conta WHERE Username=?");

            id.setString(1, usernameD);
            ResultSet IdForm = id.executeQuery();
            while(IdForm.next()) {
                USER_SESSION_ID = IdForm.getInt(1);
                System.out.println(USER_SESSION_ID);
            }

            Parent tableViewParent = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
            Scene TableViewScene = new Scene(tableViewParent);
            TableViewScene.setFill(Color.TRANSPARENT);
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
    @FXML
    void Quit(){
        javafx.application.Platform.exit();
    }
}

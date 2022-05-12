package com.firefly.firefly;

import com.firefly.firefly.Sql.DatabaseConnetion;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UploadScript extends DatabaseConnetion implements Initializable {

    @FXML
    private Pane AlbumCreateP;
    @FXML
    private Label FilePathLbl;
    @FXML
    private AnchorPane ap;
    @FXML
    private JFXTextField YearField;
    @FXML
    private JFXTextField AlbumName;
    @FXML
    private JFXToggleButton StatBtn;
    private boolean sta;
    //
    private String UploadFilePath;

    //File chooser--->
    private File file;
    private FileChooser chooser;
    private String FilePath = null;
    private Window window;

    RegisterController R = new RegisterController();

    public static int USID;

    @FXML
    void AlOpen(){
        if (!AlbumCreateP.isVisible()){
            AlbumCreateP.setVisible(true);
        }
    }
    @FXML
    void AlClose(){AlbumCreateP.setVisible(false);}//Album creation close.

    @FXML
    void ChoooseMediaFile(){

        chooser = new FileChooser();
        FileChooser.ExtensionFilter mprFilter = new FileChooser.ExtensionFilter("Media Files (*.mp3)","*.mp3");
        chooser.getExtensionFilters().add(mprFilter);
        Stage stage = (Stage)ap.getScene().getWindow();
        file = chooser.showOpenDialog(stage);

        if(file != null){FilePathLbl.setText(file.getAbsolutePath()+" - Selected"); UploadFilePath = file.getAbsolutePath();}
    }


    @FXML
    void AlbumCreationMet() {
        try {
            DatabaseConnetion connetion = new DatabaseConnetion();
            connetion.ligar();
            Connection conectDB = connetion.getCon();

            if (StatBtn.isSelected()){
                sta = true;
            }else{
                sta = false;
            }
            PreparedStatement Alb;
            Alb = conectDB.prepareStatement("Insert into Album(Album_Name,Album_Year,State,User_Id) values ('"+AlbumName.getText()+"','"+YearField+"','"+sta+"','"+USID+"')");
            ResultSet resultSet = Alb.executeQuery();

            if (resultSet.next()){
                R.showAlert(Alert.AlertType.CONFIRMATION, "Album", "Album created with success!");
                AlbumCreateP.setVisible(false);
            }else{
                R.showAlert(Alert.AlertType.CONFIRMATION, "Album", "Ups something is wrong!");
            }

        } catch (SQLException e) {
            e.getMessage();
        }
    }
        /*RETURN
         * Confirmation lenght etc...!!!!
         * Create Musics statement etc..
         * */

    @FXML
    void ReturnToMain(ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        Scene TableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(TableViewScene);
        window.show();
    }


        @Override
        public void initialize (URL url, ResourceBundle resourceBundle) {

            AlbumCreateP.setVisible(false);

            MainController main = new MainController();
            USID = main.SSID;
        }

}


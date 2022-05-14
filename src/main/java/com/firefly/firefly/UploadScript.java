package com.firefly.firefly;

import com.firefly.firefly.Sql.DatabaseConnetion;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
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
    //
    private String UploadFilePath;

    //File chooser--->
    private File file;
    private FileChooser chooser;
    private String FilePath = null;
    private int Year;
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
    public void AlbumCreationMet() {

        try {
            Year = Integer.parseInt(YearField.getText());
            String ALbName = AlbumName.getText();

            System.out.println("temp 1");

            DatabaseConnetion connetion = new DatabaseConnetion();
            connetion.ligar();
            Connection conectDB = connetion.getCon();

            System.out.println("temp 2");
            System.out.println(ALbName);
            System.out.println(Year);
            System.out.println(USID);

            String Ins = "Insert into Album(Album_Name,Album_Year,User_Id) values ('" +ALbName+"','" +Year+"','" +USID+"')";

            System.out.println("temp 3");

            conectDB.createStatement().executeUpdate(Ins);

                System.out.println("temp 4");
                    AlbumCreateP.setVisible(false);
                    R.showAlert(Alert.AlertType.CONFIRMATION, "Album", "Album created with success!");

        } catch (SQLException e) {
            e.printStackTrace();
            R.showAlert(Alert.AlertType.CONFIRMATION, "Album", "Ups something is wrong!");
        }
    }
        /*RETURN
         * Confirmation lenght etc...!!!!
         * Create Musics statement etc..
         */

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

            LoginController IDLogin = new LoginController();
            USID = IDLogin.USER_SESSION_ID;

            System.out.println(USID);

            AlbumCreateP.setVisible(false);

        }

}


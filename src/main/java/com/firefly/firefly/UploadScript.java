package com.firefly.firefly;

import com.firefly.firefly.Sql.DatabaseConnetion;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
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
    JFXComboBox Album;
    @FXML
    private JFXTextField MusicName;
    //
    String UploadFilePath;
    String AlbName;

    //File chooser--->
    private File file;
    private FileChooser chooser;
    private String FilePath = null;
    private int Year;
    RegisterController R = new RegisterController();
    public String AlFinal;
    public static int USID;
    public static  int AID;


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

        if(file != null){FilePathLbl.setText(file.getAbsolutePath()+" - Selected");
            UploadFilePath = file.getAbsolutePath();}
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

            String Ins = "Insert into Album(Album_Name,Album_Year,User_Id) values ('"+ALbName+"','"+Year+"','"+USID+"')";

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
    void refresh(){
        try{
            DatabaseConnetion connetion = new DatabaseConnetion();
            connetion.ligar();
            Connection conectDB = connetion.getCon();

            PreparedStatement ps;

            ps = conectDB.prepareStatement("SELECT Album_Name FROM Album WHERE User_Id =?");
            ps.setString(1, String.valueOf(USID));

            ResultSet resultSet = ps.executeQuery();
            ObservableList data = FXCollections.observableArrayList();
            while (resultSet.next()){
                data.add((resultSet.getString(1)));
            }
            Album.setItems(data);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void UpLoadM(){

        System.out.println(AlFinal);

        try {
            DatabaseConnetion connetion = new DatabaseConnetion();
            connetion.ligar();
            Connection conectDB = connetion.getCon();

            PreparedStatement ps;
            ps = conectDB.prepareStatement("SELECT Album_Id FROM Album WHERE Album_Name =?");
            ps.setString(1, AlFinal);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                AID = resultSet.getInt(1);
            }

            if (file == null && Album == null) {
                refresh();
                R.showAlert(Alert.AlertType.CONFIRMATION, "Upload", "You must choose one song to upload!");
            }else if (MusicName == null){
                R.showAlert(Alert.AlertType.CONFIRMATION, "Upload", "You must choose a name for the song!");
            }else {

                PreparedStatement Ins;
                Ins = conectDB.prepareStatement("Insert into Tracks(Track_Name,Track_Bin,Album_Id) values (?,?,?)");
                Ins.setString(1,MusicName.getText());
                Ins.setString(2, file.getPath());
                Ins.setString(3, String.valueOf(AID));
                Ins.executeUpdate();
                Ins.close();

                R.showAlert(Alert.AlertType.CONFIRMATION, "UpLoad", "UpLoad with success!");
                MusicName.setText("");
            }

        }catch (Exception e){e.printStackTrace();}
    }

    @FXML
    void ReturnToMain(ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        Scene TableViewScene = new Scene(tableViewParent);
        TableViewScene.setFill(Color.TRANSPARENT);
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

            try {
                DatabaseConnetion connetion = new DatabaseConnetion();
                connetion.ligar();
                Connection conectDB = connetion.getCon();

                PreparedStatement ps;

                ps = conectDB.prepareStatement("SELECT Album_Name FROM Album WHERE User_Id =?");
                ps.setString(1, String.valueOf(USID));

                ResultSet resultSet = ps.executeQuery();
                ObservableList data = FXCollections.observableArrayList();
                while (resultSet.next()){
                    data.add((resultSet.getString(1)));
                }
                Album.setItems(data);

            }catch (SQLException e){
                e.printStackTrace();
            }

            try {
                Platform.runLater(() -> {
                    Album.valueProperty().addListener(op -> {
                        AlFinal = String.valueOf(Album.getValue());
                    });
                });
            }catch (Exception e){e.printStackTrace();}
        }
}
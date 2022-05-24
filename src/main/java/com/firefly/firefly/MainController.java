package com.firefly.firefly;


import com.firefly.firefly.Sql.DatabaseConnetion;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

//===========================================================================//
//==============================Main Class===================================//
//===========================================================================//

public class MainController extends DatabaseConnetion implements Initializable{

    @FXML
    private Pane root;
    //==========Player=========//
    @FXML
    private JFXSlider TimeP;
    @FXML
    private JFXSlider Volume;
    @FXML
    private ImageView PauseImg;
    @FXML
    private ImageView PlayImg;
    @FXML
    private ImageView Forward;
    @FXML
    private ImageView Replay;
    @FXML
    private ImageView Shuffle;
    @FXML
    private ImageView Tumb;
    //=======Media Player=======//
    private URI NullController = URI.create("C:/Users/gouve/Desktop/FireFly/FireFlyPAP/src/main/resources/NullControl.mp3");
    private MediaPlayer mediaPlayer;
    private Media media;
    private Duration duration;
    @FXML
    private JFXButton MainButton;
    //======Log Out Buttons=====//
    @FXML
    private ImageView Log1;
    @FXML
    private ImageView Log2;
    //=====SearchBar=========//
    @FXML
    private ListView<String> listView;
    @FXML
    private JFXTextField SearchBar;
    ObservableList data = FXCollections.observableArrayList();
    //==========================================//
    public static int SSID;
    DatabaseConnetion connetion = new DatabaseConnetion();
    public String OLD = "";
    public int Cont;
    public File Music;

    public String Obj = "C:/Users/gouve/OneDrive/Documentos/Development/GitHub/FireFlyPAP/src/main/java/com/firefly/firefly/NullControl.mp3";

    //===========================================================================//
    //============================Methods Above==================================//
    //===========================================================================//


       //---------------------------------------------------

    public void PlayMusic(){
            if (mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
                //MediaPlaterStuff
                mediaPlayer = new MediaPlayer(new Media(""));

                PlayImg.setVisible(false);
                PauseImg.setVisible(true);
                mediaPlayer.play();
            } else {
                PauseImg.setVisible(false);
                PlayImg.setVisible(true);
                mediaPlayer.pause();
            }
    }

    //Methods to the animation of the Log out button...
    public void ChangeLogEnt() {
        Log1.setVisible(false);
        Log2.setVisible(true);
    }
    public void ChangeLogExi() {
        Log2.setVisible(false);
        Log1.setVisible(true);
    }

    //Methods to start the logout animation panel...
    public void LogOut() {
        root.setDisable(false);
        root.setVisible(true);

        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(2));
        transition.setToY(25);
        transition.setNode(root);
        root.setOpacity(100);
        //Play animation
        transition.play();
    }
    public void noth() {
        root.setVisible(false);
        root.setDisable(true);
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(2));
        transition.setToY(0);
        transition.setNode(root);

        transition.play();
    }
    //Change scene to LoginFrame....
    public void Logout(ActionEvent event) throws IOException {
        try {
            mediaPlayer.stop();
        }catch(NullPointerException null2){
            System.out.println("Media is null - Not Critical");
        }
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("LoginFrame.fxml"));
        Scene TableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(TableViewScene);
        window.show();
    }

    public void UpldoadPage(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("UploadPage.fxml"));
        Scene TableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(TableViewScene);
        window.show();
    }
    public void ProfilePage(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("ProfileFrame.fxml"));
        Scene TableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(TableViewScene);
        window.show();
    }

    //Search bar

    //Work here !Important
    @FXML
    public void searchS (){

       connetion.ligar();
       Connection conectDB = connetion.getCon();

        try {
            PreparedStatement pr;
            pr = conectDB.prepareStatement("SELECT Track_Name,Track_Id from Tracks WHERE Track_Name like ?");
            pr.setString(1,"%"+SearchBar.getText()+"%");
            ResultSet rs = pr.executeQuery();
            //
            while(rs.next()){
                String Track = rs.getString(1);
                data.add(Track);

            }
            data.remove(1);

            rs.close();
            pr.close();
            listView.setItems(data);
        }catch (SQLException e){
            System.out.println("");
        }catch (IndexOutOfBoundsException e){
            System.out.println("");
        }catch(RuntimeException e){
            System.out.println("");
        }
    }

    //Work here !Important
    void RetrieveData() throws SQLException, IOException {

        System.out.println("Retrieve Data Starts - 01");

        connetion.ligar();
        Connection conectDB = connetion.getCon();
        PreparedStatement Load;

        System.out.println(01);
        Load = conectDB.prepareStatement("SELECT Track_Bin FROM Tracks where Track_Name = ?");
        Load.setString(1, listView.getSelectionModel().getSelectedItem());
        ResultSet rs = Load.executeQuery();

        System.out.println(02);

        while (rs.next()){
            Music = new File(rs.getString(1));
        }
        System.out.println(03);

        MainButton.setDisable(false);
        mediaPlayer = new MediaPlayer(new Media(Music.toURI().toURL().toExternalForm()));
        mediaPlayer.play();
        PlayImg.setVisible(false);
        PauseImg.setVisible(true);
    }

    //Methods to
    public void initialize(URL url, ResourceBundle resourceBundle) {

        LoginController IDLogin = new LoginController();
        SSID = IDLogin.USER_SESSION_ID;
        //check->
        System.out.println(SSID);
        MainButton.setDisable(true);
        listView.setVisible(false);
        root.setDisable(true);

        File music = new File(String.valueOf(url));

        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent click) {

                if (click.getClickCount() == 1) {
                    //Use ListView's getSelected Item
                    var currentItemSelected = listView.getSelectionModel().getSelectedItem();

                    try {
                        RetrieveData();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        SearchBar.textProperty().addListener(txt ->{
            int check = SearchBar.getLength();
            if (check == 0){
                data.clear();
                listView.setVisible(false);
            }else{listView.setVisible(true);}
        });

        try {
                mediaPlayer = new MediaPlayer(new Media(Music.toURI().toURL().toExternalForm()));
                mediaPlayer.setVolume(100);
                mediaPlayer.volumeProperty().bindBidirectional(Volume.valueProperty());


            listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                @Override
                public ListCell<String> call(ListView<String> stringListView) {
                    try {
                        RetrieveData();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            });

            Platform.runLater(() -> {

                Volume.valueProperty().addListener(ov -> {
                    if (Volume.isValueChanging()) {
                        mediaPlayer.setVolume(Volume.getValue());
                    }
                });
                mediaPlayer.currentCountProperty().addListener(ov -> {
                    duration = mediaPlayer.getMedia().getDuration();
                    actvalores();
                });
                TimeP.valueProperty().addListener(ov -> {
                    if (TimeP.isPressed()) {
                        mediaPlayer.seek(mediaPlayer.getMedia().getDuration().multiply(TimeP.getValue() / 100));
                    }
                });
            });
        }catch (NullPointerException | MalformedURLException e){
            System.out.println("Media is null - Not Critical");
        }
    }

        //Responsible to stop the Threads running...
        public void stop () {
            Platform.exit();
        }

        //actvalores refreshs the values on the current media slider...
        public void actvalores () {
            if (TimeP != null) {
                Platform.runLater(() -> {
                    if (!TimeP.isDisable() && duration.greaterThan(Duration.ZERO) && !TimeP.isValueChanging()) {
                        TimeP.setValue(mediaPlayer.getCurrentTime().divide(duration.toMillis()).toMillis() * 100.0);
                    }
                });
            }
        }
    }

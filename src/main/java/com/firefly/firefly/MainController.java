package com.firefly.firefly;


import com.firefly.firefly.SESSION.Session_Class;
import com.firefly.firefly.Sql.DatabaseConnetion;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import eu.hansolo.tilesfx.tools.ImageParticle;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
    private Label TimeRight;
    @FXML
    private ImageView Forward;
    @FXML
    private ImageView Replay;
    @FXML
    private ImageView Shuffle;
    @FXML
    private ImageView Tumb;
    //=======Media Player=======//
    private MediaPlayer mediaPlayer;
    private Media media;
    private Duration duration;
    private String Music ="C:/Users/gouve/OneDrive/Ambiente de Trabalho/mii.mp3";
    //======Log Out Buttons=====//
    @FXML
    private ImageView Log1;
    @FXML
    private ImageView Log2;
    //======MenuButtons========//
    @FXML
    private JFXButton ProfileBtn;
    @FXML
    private JFXButton FavoritesBtn;
    //=====SearchBar=========//
    @FXML
    private ListView<String> listView;
    @FXML
    private JFXTextField SearchBar;
    public ArrayList<String> words;
    //==========================================//
    public static int SSID;


    //===========================================================================//
    //============================Methods Above==================================//
    //===========================================================================//


    @FXML
    void search(ActionEvent event){
        listView.getItems().clear();
        listView.getItems().addAll(searchList(SearchBar.getText(), words));
    }

    void DatabaseMusic(){

    }

    private List<String> searchList(String searchWords, List<String> listOfStrings){

        List<String> searchWordsArray = Arrays.asList(searchWords.trim().split(""));

        return  listOfStrings.stream().filter(input ->{
            return searchWordsArray.stream().allMatch(word ->
                    input.toLowerCase().contains(word.toLowerCase()));
        }).collect(Collectors.toList());
    }


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


    //Methods to
    public void initialize(URL url, ResourceBundle resourceBundle) {

        LoginController IDLogin = new LoginController();
        SSID = IDLogin.USER_SESSION_ID;
        //check->
        System.out.println(SSID);


        //SearchBar...
        // listView.getItems().addAll(words);
        try {
            if (media!=null) {
                mediaPlayer = new MediaPlayer(new Media(Music.toString()));
                mediaPlayer.setVolume(100);
                mediaPlayer.volumeProperty().bindBidirectional(Volume.valueProperty());
            }

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
        }catch (NullPointerException nullMed){
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

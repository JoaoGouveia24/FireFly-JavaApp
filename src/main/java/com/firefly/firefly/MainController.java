package com.firefly.firefly;

import com.firefly.firefly.Sql.DatabaseConnetion;
import javafx.animation.*;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;


public class MainController implements Initializable{

    @FXML
    private Pane root;
    @FXML
    private Slider TimeP;
    @FXML
    private Label TimeLeft;
    @FXML
    private Label TimeRight;

    //MediaPlayer
    @FXML
    private ImageView Play;
    @FXML
    private ImageView Pause;
    @FXML
    private ImageView Back;
    @FXML
    private ImageView Forward;
    @FXML
    private ImageView Replay;
    @FXML
    private ImageView Shuffle;
    //Media
    private MediaPlayer mediaPlayer;
    private Media media;
    @FXML
    private MediaView mediaView;
    @FXML
    private StackPane ViewContainer;

    //Logout button
    @FXML
    private ImageView Log1;
    @FXML
    private ImageView Log2;

    //MediaPlayer
    @FXML
    private Slider Volume;
    @FXML
    private Label VolumeRate;
    @FXML
    private AudioClip audio;

    private URL Path;

    private boolean EndVideo = false;
    private boolean isPlaying = false;
    private boolean isMuted = false;


    public void play(){

        if(Play.isVisible()) {
            Play.setVisible(false);
            Pause.setVisible(true);
            PlayMedia();

        }else{
            Pause.setVisible(false);
            Play.setVisible(true);
            PauseMedia();
        }
    }



    //Change color

    public void ChangeLogEnt() {
        Log1.setVisible(false);
        Log2.setVisible(true);
    }

    public void ChangeLogExi() {
        Log2.setVisible(false);
        Log1.setVisible(true);
    }


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
        //root.setOpacity(0);
    }

    public void Logout(ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("LoginFrame.fxml"));
        Scene TableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(TableViewScene);
        window.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        media = new Media("https://www.youtube.com/watch?v=aOPcCIuuMhg");
        mediaPlayer = new MediaPlayer(media);
        mediaView = new MediaView(mediaPlayer);
        mediaPlayer.setVolume(100);
    }
    void PlayMedia(){
        mediaPlayer.play();
    }
    void PauseMedia(){
        mediaPlayer.pause();
    }

}

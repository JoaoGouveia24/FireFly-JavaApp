package com.firefly.firefly;

import com.jfoenix.controls.JFXButton;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;


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
    private URI Media_URL = URI.create("https://www.dropbox.com/s/cxwq6qne7g4thg3/uchiha-prod-jurrivh.mp3?dl=0");
    @FXML
    private ImageView Tumb;



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
    @FXML
    private JFXButton PlayBTN;

    public void play(){
        mediaPlayer.play();
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING){
            PlayBTN.setText("||");
        }else{
            mediaPlayer.pause();
            PlayBTN.setText(">");
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
        media = new Media(Media_URL.toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(100);
        //Tumb = new ImageView(new Image(TumbURL));


        PlayBTN.setOnAction(event->{
            if(mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING){
                mediaPlayer.play();
            }
        });


    }

}

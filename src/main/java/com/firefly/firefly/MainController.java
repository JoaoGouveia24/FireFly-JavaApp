package com.firefly.firefly;


import com.jfoenix.controls.JFXSlider;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;


//===========================================================================//
//==============================Main Class===================================//
//===========================================================================//

public class MainController implements Initializable{

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
    private String Media_URL = "C:/Users/gouve/Desktop/the-hills-official-video.mp3";
    private Duration duration;
    //======Log Out Buttons=====//
    @FXML
    private ImageView Log1;
    @FXML
    private ImageView Log2;

    //===========================================================================//
    //============================Methods Above==================================//
    //===========================================================================//

    public void PlayMusic(){
            if (mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
                PlayImg.setVisible(false);
                PauseImg.setVisible(true);
                mediaPlayer.play();
            } else {
                PauseImg.setVisible(false);
                PlayImg.setVisible(true);
                mediaPlayer.pause();
            }
    }

    //Methods to ensere the animation of the Log out button...
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
        mediaPlayer.stop();
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("LoginFrame.fxml"));
        Scene TableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(TableViewScene);
        window.show();
    }

    //Methods to
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        File file = new File(Media_URL);

        try {
            media = new Media(file.toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(100);

        mediaPlayer.volumeProperty().bindBidirectional(Volume.valueProperty());

        Platform.runLater(() -> {

            Volume.valueProperty().addListener(ov -> {
                if(Volume.isValueChanging()) {
                    mediaPlayer.setVolume(Volume.getValue());
                }
            });
            mediaPlayer.currentCountProperty().addListener(ov -> {
                duration = mediaPlayer.getMedia().getDuration();
                actvalores();
            });
            TimeP.valueProperty().addListener(ov -> {
                if(TimeP.isPressed()) {
                    mediaPlayer.seek(mediaPlayer.getMedia().getDuration().multiply(TimeP.getValue()/100));
                }
            });
        });
    }

    //Responsible to stop the Threads running...
    public void stop() {
        Platform.exit();
    }

    //actvalores refreshs the values on the current media slider...
    public void actvalores() {
        if(TimeP != null) {
            Platform.runLater(() -> {
                if(!TimeP.isDisable() && duration.greaterThan(Duration.ZERO) && !TimeP.isValueChanging()){
                    TimeP.setValue(mediaPlayer.getCurrentTime().divide(duration.toMillis()).toMillis()*100.0);
                }
            });
        }
    }
}
package com.firefly.firefly;


import com.firefly.firefly.Sql.DatabaseConnetion;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

//===========================================================================//
//==============================Main Class===================================//
//===========================================================================//

public class MainController extends DatabaseConnetion implements Initializable{

    @FXML
    private Pane root;
    //==========Player=========//
    @FXML
    private Slider TimeP;
    @FXML
    private Slider Volume;
    @FXML
    private ImageView PauseImg;
    @FXML
    private ImageView PlayImg;
    @FXML
    private ImageView Forward;
    @FXML
    private ImageView Back;
    @FXML
    private ImageView Replay;
    @FXML
    private ImageView Shuffle;
    @FXML
    private ImageView Tumb;
    //=======Media Player=======//
    private File NullController = new File("C:/Users/gouve/Desktop/FireFly/FireFlyPAP/src/main/resources/NullControl.mp3");
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
    ObservableList Likeddata = FXCollections.observableArrayList();
    ObservableList MuiscsS = FXCollections.observableArrayList();
    ObservableList MusicPath = FXCollections.observableArrayList();
    ObservableList MusicID = FXCollections.observableArrayList();
    //==========================================//
    public static int SSID;
    DatabaseConnetion connetion = new DatabaseConnetion();
    public String OLD = "";
    public int Cont;
    public File Music;

    @FXML
    private Pane LikedPane;
    @FXML
    private Label LikeLbs;
    @FXML
    private ImageView Like;
    @FXML
    private ImageView Unlike;
    @FXML
    private JFXListView ListLike;
    @FXML
    private VBox listBox;

    @FXML
    private Label MusicNameLb;
    @FXML
    private Label PlayingLb;


    private int TrackPlay;
    private Boolean MusicStatBoll;
    private Boolean MediaCheck = false;
    private int TracksId;
    public int countArray;

    public int XCD = 0;
    public int compare;

    List<String> MusicArraysPath = new ArrayList<>();
    List<String> MusicArraysName = new ArrayList<>();

    public List<Integer> MusicIDs = new ArrayList<>();

    @FXML
    private ImageView ReplayON;

    public int track_Id;

    public boolean MusicCheck = false;

    //===========================================================================//
    //============================Methods Above==================================//
    //===========================================================================//


    @FXML
    void LikedShowUp(){

        ListLike.getItems().clear();
        GetLiked();

        if (LikedPane.isVisible() == false){
            LikeLbs.setVisible(true);
            LikedPane.setVisible(true);
        }else {
            LikeLbs.setVisible(false);
            LikedPane.setVisible(false);
        }

    }

       //---------------------------------------------------

    public void PlayMusic(){
            if (mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
                //MediaPlaterStuff

                PlayImg.setVisible(false);
                PauseImg.setVisible(true);
                mediaPlayer.play();
                Unlike.setDisable(false);
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

        if (MusicCheck == true){
            mediaPlayer.stop();
        }
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("LoginFrame.fxml"));
        Scene TableViewScene = new Scene(tableViewParent);
        TableViewScene.setFill(Color.TRANSPARENT);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(TableViewScene);
        window.show();
    }

    public void UpldoadPage(ActionEvent event) throws IOException {

        if (MusicCheck == true){
            mediaPlayer.stop();
        }
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("UploadPage.fxml"));
        Scene TableViewScene = new Scene(tableViewParent);
        TableViewScene.setFill(Color.TRANSPARENT);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(TableViewScene);
        window.show();

    }
    public void ProfilePage(ActionEvent event) throws IOException {
        if (MusicCheck == true){
            mediaPlayer.stop();
        }
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("ProfileFrame.fxml"));
        Scene TableViewScene = new Scene(tableViewParent);
        TableViewScene.setFill(Color.TRANSPARENT);
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
            listView.getItems().clear();
            pr.setString(1,"%"+SearchBar.getText()+"%");
            ResultSet rs = pr.executeQuery();
            //
            while(rs.next()){
                String Track = rs.getString(1);
                track_Id = rs.getInt(2);
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

        Forward.setDisable(true);
        Back.setDisable(true);
        Shuffle.setDisable(true);
        Replay.setDisable(false);

            System.out.println("Retrieve Data Starts - 01");

            connetion.ligar();
            Connection conectDB = connetion.getCon();
            PreparedStatement Load;

            if (MusicCheck == true){

                mediaPlayer.stop();

            }

                System.out.println(01);
                Load = conectDB.prepareStatement("SELECT Track_Id, Track_Bin, Track_Name FROM Tracks where Track_Id = ?");
                Load.setString(1, String.valueOf(track_Id));
                ResultSet rs = Load.executeQuery();

                System.out.println(02);

                while (rs.next()) {
                    TrackPlay = rs.getInt(1);
                    Music = new File(rs.getString(2));
                    MusicNameLb.setText(rs.getString(3));
                }
                System.out.println(03);

                MainButton.setDisable(false);
                mediaPlayer = new MediaPlayer(new Media(Music.toURI().toURL().toExternalForm()));

                mediaPlayer.play();

                PlayImg.setVisible(false);
                PauseImg.setVisible(true);
                VolumeChang();
                MediaCheck = true;

                MusicNameLb.setVisible(true);
                PlayingLb.setVisible(true);


                System.out.println("Check Likes start!");

                boolean Fav = false;

                PreparedStatement s;
                s = conectDB.prepareStatement("SELECT Fav_Track FROM Fav WHERE Fav_Account = ?");
                s.setString(1, String.valueOf(SSID));
                ResultSet ret = s.executeQuery();

                while (ret.next()){
                    if(ret.getInt(1)== TrackPlay){
                        Fav = true;
                    }
                }

                if (Fav == true){
                    Unlike.setVisible(false);
                    Unlike.setDisable(true);
                    Like.setVisible(true);
                    Like.setDisable(false);
                    System.out.println("Yes");
                }else{
                    Like.setVisible(false);
                    Like.setDisable(true);
                    Unlike.setVisible(true);
                    Unlike.setDisable(false);
                    System.out.println("No");
                }

            MusicCheck = true;
    }


    void VolumeChang(){
        Volume.valueProperty().addListener(observable -> {
            mediaPlayer.setVolume(Volume.getValue());
        });

        mediaPlayer.totalDurationProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                TimeP.setMax(t1.toSeconds());
            }
        });
        TimeP.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                double currentTime = mediaPlayer.getCurrentTime().toSeconds();
                if (Math.abs(currentTime - t1.doubleValue())> 0.5){
                    mediaPlayer.seek(Duration.seconds(t1.doubleValue()));
                }
            }
        });

        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration newTime) {
                if (!TimeP.isValueChanging()){
                    TimeP.setValue(newTime.toSeconds());
                }
            }
        });
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

        //Liked Pane Settings...
        LikeLbs.setVisible(false);
        LikedPane.setVisible(false);
        Unlike.setDisable(true);

        MusicNameLb.setVisible(false);
        PlayingLb.setVisible(false);

        Shuffle.setDisable(true);
        Replay.setDisable(true);

        //
        GetLiked();

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

            mediaPlayer.stop();

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

                mediaPlayer.currentCountProperty().addListener(ov2 -> {
                    duration = mediaPlayer.getMedia().getDuration();
                    actvalores();
                });
                TimeP.valueProperty().addListener(ov3 -> {
                    if (TimeP.isPressed()) {
                        mediaPlayer.seek(mediaPlayer.getMedia().getDuration().multiply(TimeP.getValue() / 100));
                    }
                });
            });


        }catch (NullPointerException e){
            System.out.println("Media Null - Not Critical");
        }
    }

        //Responsible to stop the Threads running...

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

    public void stop () {
        Platform.exit();
    }



    @FXML
    void Close(){javafx.application.Platform.exit();}


    public void GetLiked(){

        MusicArraysPath.clear();
        MusicArraysName.clear();


        connetion.ligar();

        Connection conectDB = connetion.getCon();
        PreparedStatement Mus;

        try {
            Mus = conectDB.prepareStatement("SELECT Fav_Track From fav WHERE Fav_Account = ?");
            Mus.setString(1, String.valueOf(SSID));

            ResultSet rs = Mus.executeQuery();


            while (rs.next()) {

                TracksId = rs.getInt(1);
                MusicID.add(TracksId);

                MusicIDs.add(TracksId);

                PreparedStatement Tracks;
                Tracks = conectDB.prepareStatement("SELECT Track_Name, Track_Id, Track_Bin FROM Tracks WHERE Track_Id = ?");
                Tracks.setString(1, String.valueOf(TracksId));

                ResultSet r = Tracks.executeQuery();
                while (r.next()) {

                    String dataL = r.getString(1);
                    Likeddata.add(dataL);

                    //Adding to an array...
                    String Mname = r.getString(1);
                    String Mpath = r.getString(3);

                    MusicArraysName.add(Mname);
                    MusicArraysPath.add(Mpath);

                }

                ListLike.setItems(Likeddata);


            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void ADD(){
        connetion.ligar();
        Connection conectDB = connetion.getCon();

        PreparedStatement InsMusc;

        try {
            InsMusc = conectDB.prepareStatement("INSERT INTO Fav(Fav_Account,Fav_Track) values('"+SSID+"','"+TrackPlay+"') ");
            InsMusc.executeUpdate();

            Unlike.setVisible(false);
            Unlike.setDisable(true);
            Like.setVisible(true);
            Like.setDisable(false);

            System.out.println("Music linked with sucess!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void RemoveM(){
        connetion.ligar();
        Connection conectDB = connetion.getCon();

        PreparedStatement InsMusc;

        try {
            InsMusc = conectDB.prepareStatement("DELETE FROM Fav WHERE Fav_Account = ? and Fav_Track = ?");
            InsMusc.setString(1, String.valueOf(SSID));
            InsMusc.setString(2, String.valueOf(TrackPlay));
            InsMusc.executeUpdate();

            Like.setVisible(false);
            Like.setDisable(true);
            Unlike.setVisible(true);
            Unlike.setDisable(false);

            System.out.println("Music removed with sucess!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void shuffle(){

        if (MusicCheck == true){
            mediaPlayer.stop();
        }


        Shuffle.setDisable(false);

        int number = MusicArraysName.size();
        Random rand = new Random();

        int finalRand = rand.nextInt(number);

        Forward.setDisable(false);
        Back.setDisable(false);

        if (MusicArraysName.size()<=0){

        }else {

            Music = new File(MusicArraysPath.get(finalRand));
            try {

                mediaPlayer.stop();

                mediaPlayer = new MediaPlayer(new Media(Music.toURI().toURL().toExternalForm()));

                mediaPlayer.play();
                MainButton.setDisable(false);
                MusicNameLb.setText(MusicArraysName.get(finalRand));

                PlayImg.setVisible(false);
                PauseImg.setVisible(true);
                VolumeChang();
                MusicNameLb.setVisible(true);
                PlayingLb.setVisible(true);


                Unlike.setVisible(false);
                Unlike.setDisable(true);
                Like.setVisible(true);
                Like.setDisable(false);

                MusicCheck = true;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }

       System.out.println(finalRand);
    }

    public void playLikedMusics(){

        if (MusicCheck == true){
            mediaPlayer.stop();
        }

        Forward.setDisable(false);
        Back.setDisable(false);

        Shuffle.setDisable(false);

        if (MusicArraysName.size()<=0){

        }else {

                Music = new File(MusicArraysPath.get(XCD));
                try {
                    mediaPlayer = new MediaPlayer(new Media(Music.toURI().toURL().toExternalForm()));

                    mediaPlayer.play();
                    MainButton.setDisable(false);
                    MusicNameLb.setText(MusicArraysName.get(XCD));

                    PlayImg.setVisible(false);
                    PauseImg.setVisible(true);
                    VolumeChang();
                    MusicNameLb.setVisible(true);
                    PlayingLb.setVisible(true);


                    Unlike.setVisible(false);
                    Unlike.setDisable(true);
                    Like.setVisible(true);
                    Like.setDisable(false);

                    MediaCheck = true;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }

    @FXML
    void nextM(){

        XCD = XCD+1;

        try {
            if (XCD > MusicArraysPath.size()) {

            } else {
                Music = new File(MusicArraysPath.get(XCD));
                try {
                    mediaPlayer.stop();

                    mediaPlayer = new MediaPlayer(new Media(Music.toURI().toURL().toExternalForm()));

                    mediaPlayer.play();
                    MusicNameLb.setText(MusicArraysName.get(XCD));

                    PlayImg.setVisible(false);
                    PauseImg.setVisible(true);
                    VolumeChang();
                    MusicNameLb.setVisible(true);
                    PlayingLb.setVisible(true);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }catch (IndexOutOfBoundsException e){
            System.out.println("Array Null");
        }
    }

    @FXML
    void BackM(){
        XCD = XCD-1;


        try {
            if (MusicArraysPath.size() <= 0) {

            } else {
                Music = new File(MusicArraysPath.get(XCD));
                try {
                    mediaPlayer.stop();

                    mediaPlayer = new MediaPlayer(new Media(Music.toURI().toURL().toExternalForm()));

                    mediaPlayer.play();
                    MusicNameLb.setText(MusicArraysName.get(XCD));

                    PlayImg.setVisible(false);
                    PauseImg.setVisible(true);
                    VolumeChang();
                    MusicNameLb.setVisible(true);
                    PlayingLb.setVisible(true);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }catch (IndexOutOfBoundsException e){
        System.out.println("Array Null");
    }
    }


    // Repeat function...
    @FXML
    void repeatStart() {

        if (ReplayON.isVisible() == false){
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

            VolumeChang();
            ReplayON.setVisible(true);
        }else{
            mediaPlayer.setAutoPlay(false);
            mediaPlayer.setCycleCount(0);
            ReplayON.setVisible(false);
        }
    }
}
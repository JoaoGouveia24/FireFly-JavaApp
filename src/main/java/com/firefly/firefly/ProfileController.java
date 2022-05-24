package com.firefly.firefly;

import com.firefly.firefly.Sql.DatabaseConnetion;
import com.google.common.hash.Hashing;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    private JFXListView ListviewAlb;
    @FXML
    private JFXTextField Username;
    @FXML
    private JFXTextField Email;
    @FXML
    private JFXButton ChangeButn;
    @FXML
    private Pane Ok;
    @FXML
    private Pane NotOk;
    @FXML
    private JFXPasswordField password;
    @FXML
    private  Pane PassCheck;
    @FXML
    private JFXButton Edit;
    @FXML
    private JFXButton Save;
    //
    @FXML
    private Label AlbumName;
    @FXML
    private Pane AlbumPaneDel;
    @FXML
    private JFXButton Del;


    public boolean PChek;

    public static int USIDL;
    public int AlbumID;

    public DatabaseConnetion DBConn = new DatabaseConnetion();
    public LoginController LgI = new LoginController();


    void RetrieveData(){ //Done

        DBConn.ligar();
        Connection conectDB = DBConn.getCon();

        PreparedStatement Pr;
        try {
            Pr = conectDB.prepareStatement("SELECT username,Email FROM Conta WHERE Conta_Id = ?");
            Pr.setString(1, String.valueOf(USIDL));

            ResultSet res = Pr.executeQuery();
            while (res.next()){
                Username.setText(res.getString(1));
                Email.setText(res.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void PassCheckDatabase(){
        DBConn.ligar();
        Connection conectDB = DBConn.getCon();

        String HashedPass = Hashing.sha256().hashString(password.getText(), StandardCharsets.UTF_8).toString();
        try {
            PreparedStatement passC;
            passC = conectDB.prepareStatement("SELECT Pass FROM Conta WHERE Conta_Id = ? AND Pass = ?");
            passC.setString(1, String.valueOf(USIDL));
            passC.setString(2, HashedPass);
            ResultSet passRes = passC.executeQuery();

            if (passRes.next()){
                ProfileUpdate();
            }else{
                PassCheck.setVisible(false);
                NotOk.setVisible(true);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
     void Main(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        Scene TableViewScene = new Scene(tableViewParent);
        TableViewScene.setFill(Color.TRANSPARENT);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(TableViewScene);
        window.show();
    }


    void ProfileUpdate(){
        DBConn.ligar();
        Connection conectDB = DBConn.getCon();


            PreparedStatement Pr;
            try {
                Pr = conectDB.prepareStatement("UPDATE Conta SET username = '" + Username.getText() + "', Email = '" + Email.getText() + "'  WHERE Conta_Id = ?; ");
                Pr.setString(1, String.valueOf(USIDL));
                Pr.executeUpdate();
                PassCheck.setVisible(false);
                Ok.setVisible(true);

            } catch (SQLException ex) {
                ex.printStackTrace();
                NotOk.setVisible(true);
            }
    }

    @FXML
    void Close(){
        Ok.setVisible(false);
        NotOk.setVisible(false);
    }

    @FXML
    void Edit(){
        Edit.setVisible(false);
        Save.setVisible(true);
        Username.setEditable(true);
        Email.setEditable(true);
    }
    @FXML
    void Save(){
        Save.setVisible(false);
        Username.setEditable(false);
        Email.setEditable(false);
        Edit.setVisible(true);
        PassCheck.setVisible(true);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        USIDL = LgI.USER_SESSION_ID;

        RetrieveData();
        //
        try {
            DatabaseConnetion connetion = new DatabaseConnetion();
            connetion.ligar();
            Connection conectDB = connetion.getCon();

            PreparedStatement ps;

            ps = conectDB.prepareStatement("SELECT Album_Name, Album_Id FROM Album WHERE User_Id =?");
            ps.setString(1, String.valueOf(USIDL));


            ResultSet resultSet = ps.executeQuery();
            ObservableList data = FXCollections.observableArrayList();
            while (resultSet.next()){
                data.add((resultSet.getString(1)));
                AlbumID = resultSet.getInt(2);
            }
            ListviewAlb.setItems(data);

        }catch (SQLException e){
            e.printStackTrace();
        }

        ListviewAlb.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent click) {

                if (click.getClickCount() == 1) {
                    //Use ListView's getSelected Item
                    var currentItemSelected = ListviewAlb.getSelectionModel().getSelectedItem();
                    //use this to do whatever you want to. Open Link etc.
                    AlbumPaneDel.setLayoutY(ListviewAlb.getLayoutY()+5);
                    AlbumPaneDel.setVisible(true);
                }
            }
        });

    }


}

package com.firefly.firefly;

import com.firefly.firefly.Sql.Utilizador;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController {

    //Register Fields
    @FXML
    private JFXTextField Email;
    @FXML
    private JFXTextField Username;
    @FXML
    private JFXPasswordField Password;
    @FXML
    private JFXPasswordField PasswordC;
    @FXML
    private JFXTextField SCP;
    @FXML
    private JFXTextField SCP1;

    //Labels
    @FXML
    private Label User;
    @FXML
    private Label emailt;
    @FXML
    private Label Pass;
    @FXML
    private Label PassN;
    @FXML
    private Label Declare;
    @FXML
    private Label Lengh;
    @FXML
    private  Label mail;
    @FXML
    private boolean ver = false;
    //Alert
    private Alert alert;

    //Finish Button
    @FXML
    private JFXCheckBox SeePass;
    @FXML
    private JFXCheckBox DCL;


    //method to see the passwords...
    public void See(){

        if(SeePass.isSelected()){
            SCP.setText(PasswordC.getText());
            SCP1.setText(Password.getText());
            Password.setVisible(false);
            PasswordC.setVisible(false);
            SCP.setVisible(true);
            SCP1.setVisible(true);
        }else{
            Password.setText(SCP1.getText());
            PasswordC.setText(SCP.getText());
            SCP.setVisible(false);
            SCP1.setVisible(false);
            Password.setVisible(true);
            PasswordC.setVisible(true);
        }
    }

    private boolean validateEmail() {
        Pattern p = Pattern.compile("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher m = p.matcher(Email.getText());
        if(m.find() && m.group().equals(Email.getText())) {
            return true;
        }else {
            return false;
        }
    }


    //Method to regist into the Database...
    public void Regist(ActionEvent event) throws IOException {

        int btn = 0;
        String usernameD = Username.getText();
        String emailD = Email.getText();
        String passwordD = Password.getText();

        btn++;

        if (usernameD.equals("")) {
            Declare.setVisible(true);
            Declare.setText("Username field is Empty!");
        } else if (emailD.equals("")) {
            Declare.setVisible(true);
            Declare.setText("Email field is Empty!");
        } else if(passwordD.equals("") && PasswordC.getText().equals("")) {
            Declare.setVisible(true);
            Declare.setText("Password field is Empty!");
       } else if(!passwordD.equals(PasswordC.getText())) {
            Declare.setVisible(true);
            Declare.setText("Passwords do not match!");
        }else if(passwordD.length()<9){
            Declare.setVisible(true);
            Declare.setText("Password must be at least 9 characters long!");
        }else if(!DCL.isSelected()){
            Declare.setVisible(true);
            Declare.setText("You need to check the terms and conditions before...");
        }else if(validateEmail() != true){
            Declare.setVisible(true);
            Declare.setText("Invalid Email");
        }else {

        Utilizador utilizador = new Utilizador(usernameD, emailD, passwordD);

        if (utilizador.inserir(usernameD, emailD, passwordD)) {
            showAlert(Alert.AlertType.CONFIRMATION, "Registo", "Conta criada com sucesso");

            try {

                Parent tableViewParent = FXMLLoader.load(getClass().getResource("LoginFrame.fxml"));
                Scene TableViewScene = new Scene(tableViewParent);
                TableViewScene.setFill(Color.TRANSPARENT);
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(TableViewScene);
                window.show();

            }catch(Exception e) {
                System.out.println("Não foi possível abrir Janela Login!");
            }
            return;
        } else {
            showAlert(Alert.AlertType.ERROR, "ERROR!", "Conta já existente!\nEscolha um username diferente.");
            return;
        }
    }
    }


    public void showAlert(Alert.AlertType error, String string, String string2) {
        Alert alert = new Alert(error);
        alert.setTitle(string);
        alert.getDialogPane().setMaxWidth(400);
        alert.getDialogPane().setMaxHeight(200);
        alert.setHeaderText(null);
        alert.setContentText(string2);
        alert.show();
    }

    //Change to Login Page!
    public void changeLogin(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("LoginFrame.fxml"));
        Scene TableViewScene = new Scene(tableViewParent);
        TableViewScene.setFill(Color.TRANSPARENT);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(TableViewScene);
        window.show();
    }
}

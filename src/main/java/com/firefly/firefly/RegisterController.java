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
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;

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


    //Method to regist into the Database...
    public void Regist(ActionEvent event) throws IOException {

        int btn = 0;
        String usernameD = Username.getText();
        String emailD = Email.getText();
        String passwordD = Password.getText();

        btn++;

        if (usernameD.equals("")) {
            Lengh.setVisible(false);
            Declare.setVisible(false);
            emailt.setVisible(false);
            Pass.setVisible(false);
            User.setVisible(true);
            PassN.setVisible(false);
        } else if (emailD.equals("")) {
            Lengh.setVisible(false);
            Declare.setVisible(false);
            User.setVisible(false);
            Pass.setVisible(false);
            emailt.setVisible(true);
            PassN.setVisible(false);
        } else if(passwordD.equals("") && PasswordC.getText().equals("")) {
            Lengh.setVisible(false);
            Declare.setVisible(false);
            Pass.setVisible(false);
            emailt.setVisible(false);
            User.setVisible(false);
            PassN.setVisible(true);
       } else if(!passwordD.equals(PasswordC.getText())) {
            Lengh.setVisible(false);
            Declare.setVisible(false);
            emailt.setVisible(false);
            User.setVisible(false);
            PassN.setVisible(false);
            Pass.setVisible(true);
        }else if(passwordD.length()<9){
            Declare.setVisible(false);
            emailt.setVisible(false);
            User.setVisible(false);
            PassN.setVisible(false);
            Pass.setVisible(false);
            Lengh.setVisible(true);
        }else if(!DCL.isSelected()){
            Lengh.setVisible(false);
            emailt.setVisible(false);
            User.setVisible(false);
            PassN.setVisible(false);
            Pass.setVisible(false);
            Declare.setVisible(true);

        }else {

        Utilizador utilizador = new Utilizador(usernameD, emailD, passwordD);

        if (utilizador.inserir(usernameD, emailD, passwordD)) {
            showAlert(Alert.AlertType.CONFIRMATION, "Registo", "Conta criada com sucesso");

            try {

                Parent tableViewParent = FXMLLoader.load(getClass().getResource("LoginFrame.fxml"));
                Scene TableViewScene = new Scene(tableViewParent);
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
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(TableViewScene);
        window.show();
    }

}

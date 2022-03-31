package com.firefly.firefly;


import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import java.sql.*;



public class HelloController {

    public static void loginMethod(ActionEvent event, String username, String Password){

        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckEx = null;
        ResultSet resultSet = null;

        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/FireFly","root","");
            psCheckEx = connection.prepareStatement("SELECT * FROM Conta WHERE username = ?");
            psCheckEx.setString(1, username);
            resultSet = psCheckEx.executeQuery();

            if(!resultSet.isBeforeFirst()){
                System.out.println("User exists!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username!");
                alert.show();
            }else{
                psInsert = connection.prepareStatement("INSERT into conta(username, Pass, VALUES(?,?)");
                psInsert.setString(1,username);
                psInsert.setString(2, Password);
                psInsert.executeUpdate();

            }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public static void LogIn (ActionEvent event, String username, String Password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/FireFly","root","");
            preparedStatement = connection.prepareStatement("SELECT Pass FROM Conta WHERE username = ?");
            preparedStatement.setString(1,username);
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.isBeforeFirst()){
                System.out.println("User not found in the database!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect!");
                alert.show();
            }else{
                while (resultSet.next()){
                    String retrievedPassword = resultSet.getString("pass");
                    if(retrievedPassword.equals(Password)){

                    }else{
                        System.out.println("Passwords do not match!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Passwords do not match!");
                        alert.show();
                    }
                }

            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
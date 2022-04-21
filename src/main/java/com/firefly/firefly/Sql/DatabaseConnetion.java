package com.firefly.firefly.Sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnetion {

    private String url;
    private String user;
    private String password;

    public DatabaseConnetion() {
        url = "jdbc:mysql://35.189.86.65:3306/FireFly?useSSL=false";
        user="rootBase";
        password="StrongPassword1234*";
        con = null;
    }

    public Connection getCon() {
        return con;
    }


    public void ligar() {
        try {
            con = DriverManager.getConnection(this.url, user, password);
        } catch (SQLException e) {
            System.out.println("ligar()- ERRO- " + e.getMessage());
        }
    }

    private Connection con;

    public void desligar() {
        try {
            this.con.close();
        } catch (SQLException ex) {
            System.out.println("desligar()- ERRO- " + ex.getMessage());
        }
    }
}
package com.firefly.firefly.Sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnetion {

    private String url;
    private String user;
    private String password;
    private Connection con;


    public DatabaseConnetion() {
        url = "jdbc:mysql://localhost:3306/firefly?useSSL=false";
        user="root";
        password="";
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



    public void desligar() {
        try {
            this.con.close();
        } catch (SQLException ex) {
            System.out.println("desligar()- ERRO- " + ex.getMessage());
        }
    }
}
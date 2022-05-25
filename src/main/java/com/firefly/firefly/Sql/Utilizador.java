package com.firefly.firefly.Sql;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.SQLException;



public class Utilizador extends DatabaseConnetion{

    private String username;
    private String email;
    private String password;

    public Utilizador(String user ,String email, String pass) {
        super();
        this.username = user;
        this.email = email;
        this.password = pass;
        ligar();
    }


    public boolean inserir(String user, String email, String pass) {

        String HashedPass = Hashing.sha256().hashString(pass, StandardCharsets.UTF_8).toString();

        String queryUser =("Insert into Conta(Username, Email, Pass) Values('" + user + "','" + email + "','" + HashedPass + "');");

        System.out.println("Query INSERIR -> " + queryUser);

        ligar();
        try {
            getCon().createStatement().executeUpdate(queryUser);
            return true;
        } catch (SQLException ex) {
            System.out.println("inserir()- ERRO- " + ex.getMessage());
            return false;
        }
    }
}


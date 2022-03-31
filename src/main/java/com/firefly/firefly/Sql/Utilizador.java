package com.firefly.firefly.Sql;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.SQLException;



public class Utilizador extends DatabaseConnetion{

    private String username;
    private String email;
    private String password;

    public static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return java.util.Base64.getEncoder().encodeToString(hash);
        }catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    public Utilizador(String user ,String email, String pass) {
        super();
        this.username = user;
        this.email = email;
        this.password = pass;
        ligar();
    }

    public boolean inserir(String user, String email, String pass) {
        String query =("Insert into Conta(username, Email, Pass) Values('" + user + "','" + email + "','" + sha256(pass) + "');");

        System.out.println("Query INSERIR -> " + query);

        ligar();
        try {
            getCon().createStatement().executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            System.out.println("inserir()- ERRO- " + ex.getMessage());
            return false;
        }
    }

}


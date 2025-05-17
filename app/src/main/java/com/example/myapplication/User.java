package com.example.myapplication;

public class User {
    private long id;
    private String login;
    private String password; // зашифрованный
    private String encryptionKey; // ключ для шифрования

    public User(String login, String password, String encryptionKey) {
        this.login = login;
        this.password = password;
        this.encryptionKey = encryptionKey;
    }

    public User(long id, String login, String password, String encryptionKey) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.encryptionKey = encryptionKey;
    }

    public long getId() { return id; }
    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public String getEncryptionKey() { return encryptionKey; }
}
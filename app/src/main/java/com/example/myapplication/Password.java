package com.example.myapplication;

public class Password {
    private long id;
    private long userId;
    private String serviceName;
    private String login;
    private String encryptedPassword;
    private String notes;

    public Password(long userId, String serviceName, String login, String encryptedPassword, String notes) {
        this.userId = userId;
        this.serviceName = serviceName;
        this.login = login;
        this.encryptedPassword = encryptedPassword;
        this.notes = notes;
    }

    public Password(long id, long userId, String serviceName, String login, String encryptedPassword, String notes) {
        this.id = id;
        this.userId = userId;
        this.serviceName = serviceName;
        this.login = login;
        this.encryptedPassword = encryptedPassword;
        this.notes = notes;
    }

    public long getId() { return id; }
    public long getUserId() { return userId; }
    public String getServiceName() { return serviceName; }
    public String getLogin() { return login; }
    public String getEncryptedPassword() { return encryptedPassword; }
    public String getNotes() { return notes; }
}
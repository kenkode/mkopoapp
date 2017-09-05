package com.example.lixnet.mkopo.models;

public class UserAuth {

    private String response;
    private String pin;
    private User user;
    private String token;

    public UserAuth(String response, User user, String token) {
        this.response = response;
        //this.pin = pin;
        this.user = user;
        this.token = token;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

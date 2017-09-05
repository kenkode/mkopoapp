package com.example.lixnet.mkopo.models;

import com.google.gson.annotations.Expose;

/**
 * Created by Kenkode PC on 9/4/2017.
 */

public class Password {
    @Expose
    private String id;
    @Expose
    private String current_password;
    @Expose
    private String confirm_password;
    @Expose
    private String response;

    public Password(String current_password, String confirm_password) {
        this.current_password = current_password;
        this.confirm_password = confirm_password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrent_password() {
        return current_password;
    }

    public void setCurrent_password(String current_password) {
        this.current_password = current_password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}

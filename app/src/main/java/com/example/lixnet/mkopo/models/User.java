package com.example.lixnet.mkopo.models;

import com.google.gson.annotations.Expose;

public class User {

    @Expose
    private String id;
    @Expose
    private String full_name;
    @Expose
    private String email;
    @Expose
    private String id_number;
    @Expose
    private String gender;
    @Expose
    private String phone_number;
    @Expose
    private String pin;

    public User(String full_name, String email, String phone_number, String id_number, String gender, String pin) {
        this.full_name = full_name;
        this.email = email;
        this.phone_number = phone_number;
        this.id_number = id_number;
        this.gender = gender;
        this.pin = pin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}

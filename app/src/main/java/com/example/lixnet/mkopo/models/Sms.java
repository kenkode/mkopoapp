package com.example.lixnet.mkopo.models;

import com.google.gson.annotations.Expose;

/**
 * Created by Lixnet on 2017-08-03.
 */

public class Sms{

    @Expose
    private String userid;
    @Expose
    private String name;
    @Expose
    private String transactionID;
    @Expose
    private String phone;
    @Expose
    private String amount;
    @Expose
    private String timestamp;
    @Expose
    private String balance;
    @Expose
    private String type;
    @Expose
    private String transactiontype;

    public Sms(String userid, String name, String transactionID, String phone, String amount, String timestamp, String balance, String type, String transactiontype) {
        this.userid = userid;
        this.name = name;
        this.transactionID = transactionID;
        this.phone = phone;
        this.amount = amount;
        this.timestamp = timestamp;
        this.balance = balance;
        this.type = type;
        this.transactiontype = transactiontype;
    }


    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTransactiontype() {
        return transactiontype;
    }

    public void setTransactiontype(String transactiontype) {
        this.transactiontype = transactiontype;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
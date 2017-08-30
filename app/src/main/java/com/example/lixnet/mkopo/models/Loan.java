package com.example.lixnet.mkopo.models;

import com.google.gson.annotations.Expose;

/**
 * Created by Lixnet on 2017-08-28.
 */

public class Loan {
    @Expose
    private String userid;
    @Expose
    private double amount;

    public Loan(String userid, double amount) {
        this.userid = userid;
        this.amount = amount;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

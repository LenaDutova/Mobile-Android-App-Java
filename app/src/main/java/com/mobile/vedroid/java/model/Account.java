package com.mobile.vedroid.java.model;

import java.io.Serializable;

public class Account implements Serializable {

    private String login;
    private boolean gender;

    public Account(String login, boolean gender) {
        this.login = login;
        this.gender = gender;
    }

    public String getLogin() {
        return login;
    }

    public boolean isGender() {
        return gender;
    }
}

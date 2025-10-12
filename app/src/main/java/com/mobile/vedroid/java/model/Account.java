package com.mobile.vedroid.java.model;

import java.io.Serializable;

public class Account
        implements Serializable {

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

    public void setLogin (String login) {
        if (login != null && login.isBlank()) this.login = login;
    }

    public void setGender (boolean gender) {
        this.gender = gender;
    }
}

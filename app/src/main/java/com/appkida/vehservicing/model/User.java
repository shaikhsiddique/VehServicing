package com.appkida.vehservicing.model;
public class User {
    String useruId;
    public String username;
    public String email;
    public String password;
    public boolean isCustomer;
    public User() {
    }

    public User(String useruId, String username, String email, String password, boolean isCustomer) {
        this.useruId = useruId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isCustomer = isCustomer;
    }

    public String getUseruId() {

        return useruId;
    }

    public void setUseruId(String useruId) {

        this.useruId = useruId;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public boolean isCustomer() {

        return isCustomer;
    }

    public void setCustomer(boolean customer) {

        isCustomer = customer;
    }
}
package com.bbarke.hackathon.iterations;

public class LoginScenario {
    private final String description;
    private final String username;
    private final String password;
    private final boolean expectedToBeLoggedIn;
    private final String alert;

    public LoginScenario(String description, String username, String password, boolean expectedToBeLoggedIn, String alert) {
        this.description = description;
        this.username = username;
        this.password = password;
        this.expectedToBeLoggedIn = expectedToBeLoggedIn;
        this.alert = alert;
    }

    public String getDescription() {
        return description;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isExpectedToBeLoggedIn() {
        return expectedToBeLoggedIn;
    }

    public String getAlert() {
        return alert;
    }
}

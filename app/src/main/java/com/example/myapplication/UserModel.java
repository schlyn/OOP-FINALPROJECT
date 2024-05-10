package com.example.myapplication;

public class UserModel {
    private String usName;
    private String email;
    // Add other fields as needed

    public UserModel() {
        // Default constructor required for SQLite's automatic data mapping
    }

    public UserModel(String usName, String email) {
        this.usName = usName;
        this.email = email;
        // Initialize other fields as needed
    }

    // Getters and setters for the fields
    public String getUsName() {
        return usName;
    }

    public void setUsName(String usName) {
        this.usName = usName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    // Add getters and setters for other fields
}




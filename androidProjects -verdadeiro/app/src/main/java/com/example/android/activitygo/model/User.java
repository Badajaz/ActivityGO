package com.example.android.activitygo.model;

import java.util.ArrayList;

public class User {

    private String firstName;
    private String lastName;
    private String date;
    private String gender;
    private String country;
    private String email;
    private String weight;
    private String hight;
    private String username;
    private String password;
    private ArrayList<String> sports;

    public User() {

    }

    public User(String firstName, String lastName, String date, String gender, String country, String email, String weight, String hight, String username, String password, ArrayList<String> sports) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.gender = gender;
        this.country = country;
        this.email = email;
        this.weight = weight;
        this.hight = hight;
        this.username = username;
        this.password = password;
        this.sports = sports;
    }

    public ArrayList<String> getSports() {
        return sports;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDate() {
        return date;
    }

    public String getGender() {
        return gender;
    }

    public String getCountry() {
        return country;
    }

    public String getEmail() {
        return email;
    }

    public String getWeight() {
        return weight;
    }

    public String getHight() {
        return hight;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
package com.example.android.activitygo.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class User {

    private String firstName;
    private String lastName;
    private String date;
    private String gender;
    private String country;
    private String email;
    private String weight;
    private String hight;
    @PrimaryKey
    @NonNull
    private String username;
    private String password;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setHight(String hight) {
        this.hight = hight;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
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

package com.example.fundforchangetest.Models;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class pendingModel implements Serializable {


    @Exclude
    public String id;
    String name, description,location, email,phone, NID, uEmail;
    int goal;

    public pendingModel(String name, String description, String location, int goal,
                        String email, String phone, String NID, String uEmail) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.goal = goal;
        this.email = email;
        this.phone = phone;
        this.NID = NID;
        this.uEmail = uEmail;
    }

    public pendingModel(){

    }




    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNID() {
        return NID;
    }

    public void setNID(String NID) {
        this.NID = NID;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }
}

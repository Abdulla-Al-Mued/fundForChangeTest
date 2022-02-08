package com.example.fundforchangetest.Models;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Model implements Serializable {

    @Exclude public String id;
    String name, email;



    public Model(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Model(){

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

}

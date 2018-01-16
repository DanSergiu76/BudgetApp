package com.example.dan.budgetapp.model;

import io.realm.RealmObject;

/**
 * Created by Dan on 14.01.2018.
 */

public class Category extends RealmObject{
    private String name;
    private String descrpition;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescrpition() {
        return descrpition;
    }

    public void setDescrpition(String descrpition) {
        this.descrpition = descrpition;
    }

    public String toString(){
        return name + " | " + descrpition;
    }
}

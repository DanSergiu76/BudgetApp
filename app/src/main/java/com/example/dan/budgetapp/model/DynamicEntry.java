package com.example.dan.budgetapp.model;

import io.realm.RealmObject;
import java.util.*;
/**
 * Created by Dan on 13.01.2018.
 */

public class DynamicEntry extends RealmObject{
    private int ID;
    private String description;
    private int cost;
    private int time;
    private String name = "";

    public int getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int time() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
    @Override
    public String toString(){
        java.util.Date timeN = new java.util.Date((long)time*1000);
        return  " | "+ name +" | " + description + " | "+
                cost  + " | " + timeN + " | ";
    }

}

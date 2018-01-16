package com.example.dan.budgetapp.model;

import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by Dan on 14.01.2018.
 */

public class FixedEntry extends RealmObject{

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
            return  " | "+ name +" | " + cost  + " | " + timeN + " | ";
        }





}

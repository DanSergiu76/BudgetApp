package com.example.dan.budgetapp;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Dan on 13.01.2018.
 */

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config =  new RealmConfiguration.Builder().name("Database").build();
        Realm.setDefaultConfiguration(config);
    }
}

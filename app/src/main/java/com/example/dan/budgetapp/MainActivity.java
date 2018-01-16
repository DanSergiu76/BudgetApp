package com.example.dan.budgetapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ScrollingTabContainerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dan.budgetapp.model.Category;
import com.example.dan.budgetapp.model.DynamicEntry;

import java.util.ArrayList;
import java.util.List;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "MainActivity";

    EditText description, cost;
    TextView display;
    Button addButton;
    Spinner spinner;
    Realm realm;
    Button viewFixed;
    Button viewCategory;
    Button refresh;
    String name;
    TextView total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewFixed = findViewById(R.id.viewFixed);
        viewCategory = findViewById(R.id.viewCategories);
        description = findViewById(R.id.description);
        cost =  findViewById(R.id.cost);
        addButton =  findViewById(R.id.addButton);
        display =  findViewById(R.id.display);
        spinner = findViewById(R.id.category);
        refresh = findViewById(R.id.refreshButton);
        total = findViewById(R.id.total);


        Log.d(TAG, "onCreate: View Initialization Done");

        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        //realm.deleteAll();
        realm.commitTransaction();


        viewFixed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Fixed.class);
                startActivity(intent);
            }
        });


        viewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, com.example.dan.budgetapp.CategoryList.class);
                startActivity(intent);
            }
        });
        try{
            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    readData();
                    saveSpinnerData();
                }
            });


              }catch(NullPointerException e){
           Log.d(TAG, "onCreate: null pointer");
            }
        addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveData();
                    readData();
                }
            });


        spinner.setOnItemSelectedListener(this);
        readData();
        saveSpinnerData();
        total.setText(String.valueOf(setTotalCost()));
    }

    private void saveData(){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                DynamicEntry entry = bgRealm.createObject(DynamicEntry.class);
                Log.d(TAG, "execute: entry created");
                entry.setName(name);
                entry.setCost(Integer.parseInt(cost.getText().toString().trim()));
                Log.d(TAG, "execute: cost set");
                entry.setDescription(description.getText().toString().trim());
                Log.d(TAG, "execute: description set");
                entry.setTime((int)(System.currentTimeMillis()/1000L));
                Log.d(TAG, "execute: time set");
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                Log.d(TAG, "onSuccess: Data Written Succesfully");
                readData();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Log.d(TAG, "onError: Error Occured");
            }
        });

        readData();
        saveSpinnerData();
    }

    private void readData(){
        RealmResults<DynamicEntry>entries = realm.where(DynamicEntry.class).findAll();
        display.setText("");
        String data = "";
        int i = 0;
        for (DynamicEntry entry: entries){
            try {
                Log.d(TAG, "readData: Reading Data");
                data =i + entry.toString()+ "\n" + data ;
                i++;
            }catch(NullPointerException e){
                e.printStackTrace();
            }
        }
        display.setText(data);
        total.setText(String.valueOf(setTotalCost()));
    }

    public void saveSpinnerData(){
        final List<String> list = new ArrayList<>();
        RealmResults<Category> entries = realm.where(Category.class).findAll();
        for (Category cat: entries) {
            list.add(cat.getName());
        }
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.support_simple_spinner_dropdown_item,list
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        name = String.valueOf(spinner.getSelectedItem());
        Log.d(TAG, "onItemSelected: selected item from spinner");
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public int setTotalCost(){
        RealmResults<DynamicEntry>entries = realm.where(DynamicEntry.class).findAll();
        int totalCost = 0;
        for (DynamicEntry e:entries
             ) {
            totalCost += e.getCost();

        }
        return totalCost;
    }
}

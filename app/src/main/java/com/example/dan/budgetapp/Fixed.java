package com.example.dan.budgetapp;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.sql.Timestamp;
import com.example.dan.budgetapp.model.DynamicEntry;
import com.example.dan.budgetapp.model.FixedEntry;
import java.text.ParseException;
import java.time.LocalTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import io.realm.Realm;
import io.realm.RealmResults;

public class Fixed extends AppCompatActivity {
    private static final String TAG = " entry done";
    EditText name, cost, datetime;
    TextView viewFixed;
    Button addFixed;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed);

        name = findViewById(R.id.Name);
        cost = findViewById(R.id.cost);
        viewFixed = findViewById(R.id.display);
        addFixed = findViewById(R.id.addButtonFixed);
        datetime = findViewById(R.id.datetime);

        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.commitTransaction();

        addFixed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                readData();
            }
        });
        readData();

    }

    private void saveData() {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                FixedEntry entry = bgRealm.createObject(FixedEntry.class);
                Log.d(TAG, "execute: fixed entry created");
                entry.setName(name.getText().toString().trim());
                Log.d(TAG, "execute: fixed name set");
                entry.setCost(Integer.parseInt(cost.getText().toString().trim()));
                Log.d(TAG, "execute: fixed cost set");
                entry.setTime(parseStringTime());
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
        parseStringTime();
        readData();
    }

    private void readData() {
        RealmResults<FixedEntry> entries = realm.where(FixedEntry.class).findAll();
        viewFixed.setText("");
        String data = "";
        int i = 0;
        for (FixedEntry entry : entries) {
            try {
                Log.d(TAG, "readData: Reading Data");
                data = i + entry.toString() + "\n" + data;
                i++;
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        viewFixed.setText(data);
    }

    public int parseStringTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
        String  time = datetime.getText().toString();
        try {
            Date parsedTimeStamp = dateFormat.parse(time);
            Timestamp timestamp = new Timestamp(parsedTimeStamp.getTime());
            return (int)timestamp.getTime();
        }catch(java.text.ParseException e){
            return 0;
        }
    }
}


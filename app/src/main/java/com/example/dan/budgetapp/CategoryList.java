package com.example.dan.budgetapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.dan.budgetapp.model.Category;
import com.example.dan.budgetapp.model.DynamicEntry;

import org.w3c.dom.Text;

import io.realm.Realm;
import io.realm.RealmResults;

public class CategoryList extends AppCompatActivity {
    private static final String TAG = "CategoryList";

    EditText name, description;
    TextView display;
    Button addCategory;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        name = findViewById(R.id.Name);
        description = findViewById(R.id.description);
        display = findViewById(R.id.displayCategories);
        addCategory = findViewById(R.id.addCategory);

        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.commitTransaction();

        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                readData();
            }
        });
        readData();
    }

    private void saveData(){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Category entryCat = bgRealm.createObject(Category.class);
                Log.d(TAG, "execute: entry created");
                entryCat.setName(name.getText().toString().trim());
                Log.d(TAG, "execute: Category name is set");
                entryCat.setDescrpition(description.getText().toString().trim());
                Log.d(TAG, "execute: Description is set");
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

    }

    private void readData(){
        RealmResults<Category> entries = realm.where(Category.class).findAll();
        display.setText("");
        String data = "";
        int i = 0;
        for (Category entry: entries){
            try {
                Log.d(TAG, "readData: Reading Data");
                data = i + " | " + entry.toString()+ "\n" + data ;
                i++;
            }catch(NullPointerException e){
                e.printStackTrace();
            }
        }

        display.setText(data);

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

}

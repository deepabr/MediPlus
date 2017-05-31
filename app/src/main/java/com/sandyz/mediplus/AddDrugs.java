package com.sandyz.mediplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.sandyz.mediplus.databes.dbHelper;

public class AddDrugs extends AppCompatActivity {

    dbHelper mydb;
    EditText name_et;
    EditText description_et;
    EditText price_et;
    String name_value;
    String description_value;
    String price_value;
    Boolean isInserted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drugs);
        name_et = (EditText) findViewById(R.id.drug_name);
        description_et = (EditText) findViewById(R.id.description);
        price_et = (EditText) findViewById(R.id.price);
        mydb = new dbHelper(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_drug_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void addData() {
        name_value = name_et.getText().toString();
        description_value = description_et.getText().toString();
        price_value = price_et.getText().toString();
        mydb.openDb();
        isInserted = mydb.insertData(name_value, description_value, price_value);
        if (isInserted == true) {
            Toast.makeText(getApplicationContext(), "Inserted to Db", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(AddDrugs.this,HomeActivity.class);
            startActivity(intent);

        } else {
            Toast.makeText(getApplicationContext(), "Insertion Failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.save) {
            addData();
        }
        return super.onOptionsItemSelected(item);
    }
}

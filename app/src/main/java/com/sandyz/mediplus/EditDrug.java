package com.sandyz.mediplus;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sandyz.mediplus.databes.dbHelper;

public class EditDrug extends AppCompatActivity {
    dbHelper db;
    SQLiteDatabase database;

    int id;


    String share;


    EditText name,desp,price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_drug);

        db = new dbHelper(this);
        dbHelper handler = new dbHelper(this);
        database = handler.getWritableDatabase();

        name= (EditText)findViewById(R.id.drug_name_edit);
        desp=(EditText)findViewById(R.id.description_edit);
        price=(EditText)findViewById(R.id.price_edit);

        String name_bund = getIntent().getExtras().getString("name");
        String descp_bund = getIntent().getExtras().getString("Descp");
        String price_bund = getIntent().getExtras().getString("Price");
       id =getIntent().getExtras().getInt("Id");

        share= name_bund + " " + descp_bund + " " + price_bund;

        name.setText(name_bund);
        desp.setText(descp_bund);
        price.setText(price_bund);
    }

    public void save(View view) {


        db.openDb();
        db.updateDrug(id,name.getText().toString(),desp.getText().toString(),price.getText().toString());
        db.close();

        Toast.makeText(getApplicationContext(),"Succesfully Updated",Toast.LENGTH_LONG).show();

        Intent goback = new Intent(EditDrug.this,HomeActivity.class);
        startActivity(goback);



    }

    public void delete(View view) {

        db.deleteDrug(id);
        Intent goback = new Intent(EditDrug.this,HomeActivity.class);
        startActivity(goback);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.share){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, share);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }

        return super.onOptionsItemSelected(item);
    }

}

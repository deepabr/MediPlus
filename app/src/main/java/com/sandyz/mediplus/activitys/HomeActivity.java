package com.sandyz.mediplus.activitys;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.sandyz.mediplus.R;
import com.sandyz.mediplus.adapters.Recyler_Click;
import com.sandyz.mediplus.adapters.Recycler_Adapter;
import com.sandyz.mediplus.beans.DrugsData;
import com.sandyz.mediplus.databes.dbHelper;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private Palette mPalette;
    private RecyclerView recyclerView;
    private Recycler_Adapter adapter;
    private DrugsData data;
    private ArrayList<DrugsData> datas;
    dbHelper db;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //   setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = new dbHelper(this);

        dbHelper handler = new dbHelper(this);


        database = handler.getWritableDatabase();

        db.openDb();

        datas = db.getAllData();

        adapter = new Recycler_Adapter(datas, this);

        recyclerView = (RecyclerView) findViewById(R.id.Recycle);
        recyclerView.setHasFixedSize(true);


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //click listner to edit data
        recyclerView.addOnItemTouchListener(new Recyler_Click(getApplicationContext(), new Recyler_Click.OnItemClickListener() {

            @Override
            public void onItemClick(View childView, int childPosition) {


                DrugsData data = datas.get(childPosition);

                String name = data.getName();
                String Descrip = data.getDescription();
                String price = data.getPrice();
                int id = data.getId();

                Intent edit = new Intent(getApplicationContext(), EditDrug.class);
                Bundle editdata = new Bundle();
                editdata.putString("name", name);
                editdata.putString("Descp", Descrip);
                editdata.putString("Price", price);
                editdata.putInt("Id", id);
                edit.putExtras(editdata);
                startActivity(edit);


            }

        }

        ) {

        });

        recyclerView.addOnItemTouchListener(new Recyler_Click(getApplicationContext(), new Recyler_Click.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                DrugsData data = datas.get(position);

                Toast.makeText(getApplicationContext(),data.getName(),Toast.LENGTH_LONG).show();


            }
        }));

    }

    private void refreshlist() {

        datas = db.getAllData();
        // Collections.sort(taskArraylist, new DateComparator());
        adapter.setmTaskArrayList(datas);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addDrug) {
            Toast.makeText(getApplicationContext(), "Clicked Add", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeActivity.this, AddDrugs.class);
            startActivity(intent);
            refreshlist();


        } else if (id == R.id.alarmList) {
            Intent alrm = new Intent(HomeActivity.this, AlarmList.class);
            startActivity(alrm);

        } else if (id == R.id.shopping) {
            Toast.makeText(getApplicationContext(), "Clicked Shopping", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.sandyz.mediplus.databes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sandyz.mediplus.beans.DrugsData;

import java.util.ArrayList;

/**
 * Created by Sumeet on 20-05-2017.
 */

public class dbHelper extends SQLiteOpenHelper {

    private SQLiteDatabase databse = null;
    //Db Version
    private static final int database_VERSIONS = 7;
    //Db Name
    private static final String DATABASE_NAMES = "contents1.db";
    //Db name
    private static final String TABLE_NAMES = "MediplusDatabases";
    //rows
    private static final String Col_Drug_ID = "id";
    private static final String Col_Drug_Name = "name";
    private static final String Col_Drug_Description = "description";
    private static final String Col_Drug_Price = "price";

    //  SQL Querry tocreate table
    private static final String CREATE_TABLES =
            "CREATE TABLE " + TABLE_NAMES + " (" +
                    Col_Drug_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Col_Drug_Name + " TEXT,"
                    + Col_Drug_Description + " TEXT,"
                    + Col_Drug_Price + " TEXT );";

    //Execute CreateTable onCreate


    public dbHelper(Context context) {

        super(context, DATABASE_NAMES, null, database_VERSIONS);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i("Tag", "Oncreate of dataase");
        sqLiteDatabase.execSQL(CREATE_TABLES);

    }

    public void openDb() throws SQLException

    {
        Log.i("openDB", "Checking sqliteDb...");
        if (this.databse == null) {
            Log.i("open db", "creating db instance");
            this.databse = this.getWritableDatabase();


        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMES);
        onCreate(sqLiteDatabase);
    }

    public Boolean insertData(String name, String description, String price) {
        //  SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        //Log.i("Tag","entered Name");
        values.put("description", description);
        // Log.i("Tag","entered Name");
        values.put("price", price);
        //Log.i("Tag","entered Name");
        long result = this.databse.insert(TABLE_NAMES, null, values);
        return result != -1;
    }


    public ArrayList<DrugsData> getAllData() {
        ArrayList<DrugsData> datas = new ArrayList<>();

        databse = this.getReadableDatabase();

        String selectQuery = "select * from " + TABLE_NAMES;
        Cursor cursor = databse.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                DrugsData data = new DrugsData();
                data.setId(cursor.getInt(0));
                data.setName(cursor.getString(1));
                data.setDescription(cursor.getString(2));
                data.setPrice(cursor.getString(6));
                datas.add(data);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        databse.close();
        return datas;
    }

    public int updateDrug(int id, String name, String description, String price) {

        databse = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        //Log.i("Tag","entered Name");
        values.put("description", description);
        // Log.i("Tag","entered Name");
        values.put("price", price);

        int count = databse.update(TABLE_NAMES, values, Col_Drug_ID + "=?", new String[]{String.valueOf(id)});

        databse.close();
        return count;
    }

    public boolean deleteDrug(int id) {
        databse = this.getWritableDatabase();

        databse.delete(TABLE_NAMES, Col_Drug_ID + "=?", new String[]{String.valueOf(id)});

        databse.close();

        return true;
    }


}

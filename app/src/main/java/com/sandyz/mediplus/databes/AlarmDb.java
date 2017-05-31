package com.sandyz.mediplus.databes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.sandyz.mediplus.beans.DrugsData;

import java.util.ArrayList;

/**
 * Created by santosh on 31-05-2017.
 */

public class AlarmDb extends SQLiteOpenHelper {

    private Context ctx;

    private SQLiteDatabase databse = null;
    //Db Version
    private static final int database_VERSIONS = 8;
    //Db Name
    private static final String DATABASE_NAMES = "alarms.db";
    //Db name
    private static final String TABLE_NAMES = "AlarmsDatabases";
    //rows
    private static final String Col_Alarm_Id= "id";
    private static final String Col_ALARM_NAME = "alarm_name";
    private static final String Col_ALARM_TIME = "alarm_time";
    private static final String Col_Alarm_Date = "date";


    private static final String CREATE_TABLES =
            "CREATE TABLE " + TABLE_NAMES + " (" +
                    Col_Alarm_Id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Col_ALARM_NAME + " TEXT,"
                    + Col_ALARM_TIME + " TEXT,"
                    + Col_Alarm_Date + " TEXT );";

    public AlarmDb(Context context){
        super(context,DATABASE_NAMES,null,database_VERSIONS);
        ctx= context;

    }



    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_TABLES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {



        String upgrade = "Drop Table if exists " + TABLE_NAMES;
        db.execSQL(upgrade);

    }

    public void open() {

        Log.i("opendb", "Checking weather database is null..........");
        if (this.databse == null) {
            Log.i("Database null", "getting Writable database......");
            this.databse = this.getWritableDatabase();
            Toast.makeText(ctx, "DATABASE is open now", Toast.LENGTH_SHORT).show();
        }

    }

    public void close() {

        if (this.databse.isOpen()) {
            this.databse.close();
            Log.i("Close db", "Database closed..........");
        }

    }
    public long insertAlarm(String name,String date,String time){

        databse=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("alarm_name",name);
        values.put("date",date);
        values.put("alarm_time",time);

        return this.databse.insert(TABLE_NAMES,null,values);

    }

    public ArrayList<DrugsData> getAlarmData(){
        ArrayList<DrugsData> datas = new ArrayList<>();

        databse = this.getReadableDatabase();

        String selectQuery = "select * from " + TABLE_NAMES;
        Cursor cursor = databse.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do{
                DrugsData data =new DrugsData();
                data.setId(cursor.getInt(0));
                data.setAlarm_name(cursor.getString(1));
                data.setDate(cursor.getString(3));
                data.setTime(cursor.getString(2));
                datas.add(data);
            }
            while (cursor.moveToNext());
        }
        databse.close();
        return datas;
    }
}

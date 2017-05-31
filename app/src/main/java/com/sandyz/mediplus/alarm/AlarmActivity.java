package com.sandyz.mediplus.alarm;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.sandyz.mediplus.AlarmList;
import com.sandyz.mediplus.R;
import com.sandyz.mediplus.databes.AlarmDb;
import com.sandyz.mediplus.databes.dbHelper;

public class AlarmActivity extends AppCompatActivity {

    AlarmDb db;
    SQLiteDatabase sqLiteDatabase;
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private static AlarmActivity inst;
    private TextView alarmTextView, textView_heading;
    EditText name, date_et, time_et;
    String selected;
    String time;
    Long time_mills;
    TimePicker timePicker;


    public static AlarmActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        db = new AlarmDb(this);
        dbHelper handler = new dbHelper(this);


        sqLiteDatabase = handler.getWritableDatabase();

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent myIntent = new Intent(AlarmActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, 0);





        name = (EditText) findViewById(R.id.name_alarm);
        date_et = (EditText) findViewById(R.id.date_alarm);
        time_et = (EditText) findViewById(R.id.time_alarm);

    }

    private void updateTimeEt() {
        time_et.setText(time);
    }



    public void updateEt() {
        date_et.setText(selected);

    }

    public void setDate(View view) {


        final AlertDialog.Builder date = new AlertDialog.Builder(AlarmActivity.this);
        LinearLayout linearLayout = new LinearLayout(AlarmActivity.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        DatePicker datePicker = new DatePicker(getApplicationContext());
        getDateFromDatePicker(datePicker);
        linearLayout.addView(datePicker);
        date.setView(linearLayout);
        date.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateEt();
            }
        });
        date.show();
    }

    public void setTime(View view) {

        final AlertDialog.Builder time = new AlertDialog.Builder(AlarmActivity.this);
        LinearLayout linearLayout = new LinearLayout(AlarmActivity.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TimePicker timePicker = new TimePicker(getApplicationContext());
        getTimefromPicker(timePicker);

        linearLayout.addView(timePicker);
        time.setView(linearLayout);
        time.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateTimeEt();
            }
        });
        time.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_drug_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.save) {
            addData();


            Intent myIntent = new Intent(AlarmActivity.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, 0);
            alarmManager.set(AlarmManager.RTC, time_mills, pendingIntent);

            Intent goBack = new Intent(AlarmActivity.this,AlarmList.class);
            startActivity(goBack);

        }
        return super.onOptionsItemSelected(item);
    }

    public String getDateFromDatePicker(DatePicker datePicker) {

        final java.util.Calendar calendar = java.util.Calendar.getInstance();

        datePicker.init(calendar.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH), calendar.get(java.util.Calendar.DATE), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                selected = String.format("%02d", view.getDayOfMonth()) + "/" +
                        String.format("%02d", Integer.valueOf(view.getMonth() + 1)) + "/" + view.getYear();

                //  selected = String.valueOf(dayOfMonth) + " / " + String.valueOf(monthOfYear + 1) + " / " + String.valueOf(year);

            }

        });
        return selected;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private String getTimefromPicker(final TimePicker timePicker) {


        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                Calendar calendar = Calendar.getInstance();

                time_mills= calendar.getTimeInMillis();
                String AM_PM;
                if (hourOfDay < 12) {
                    AM_PM = "AM";
                } else {
                    AM_PM = "PM";
                }

                int hour = 0;
                if (hourOfDay>12){
                    hour= hourOfDay-12;
                }else {
                    hour=hourOfDay;
                }

                time = String.format("%02d", hour) + "-" +
                        String.format("%02d", Integer.valueOf(view.getMinute() + 1)) + "-" + AM_PM;


            }
        });


        return time;
    }

    public void addData() {
      String  name_value = name.getText().toString();

        db.open();
       db.insertAlarm(name_value,selected,time);

    }
}

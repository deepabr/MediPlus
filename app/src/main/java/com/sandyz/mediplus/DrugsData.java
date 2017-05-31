package com.sandyz.mediplus;

/**
 * Created by Sumeet on 20-05-2017.
 */

public class DrugsData {

    int id;
    String name;
    String description;
    String price;
    String alarm_name;
    String date;
    String time;

    public String getAlarm_name() {
        return alarm_name;
    }

    public void setAlarm_name(String alarm_name) {
        this.alarm_name = alarm_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    //Default Constructor
    public DrugsData() {
    }

    public DrugsData(int id, String name, String description, String price, String alarm_name, String date, String time) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.alarm_name = alarm_name;
        this.date = date;
        this.time = time;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

package com.example.atakanyenel.myapplication.model;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Date;

/**
 * Created by atakanyenel on 16/05/2017.
 */

public class Event {
    private int id;
    private String startdate;
    private String enddate;
    private String name;
    private int isJoined=0;
    private int isFull=0;

    public int getIsFull() {
        return isFull;
    }

    public void setIsFull(int isFull) {
        this.isFull = isFull;
    }

    public int getIsJoined() {
        return isJoined;
    }

    public void setIsJoined(int isJoined) {
        this.isJoined = isJoined;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

public Event(JSONArray ja)
{
    try
    {
        this.id=ja.getInt(0);
        this.startdate=ja.getString(1);
        this.enddate=ja.getString(2);
        this.name=ja.getString(3);
        this.isFull=ja.getInt(4);
        this.isJoined=ja.getInt(5);
    }catch (JSONException e)
    {
        e.printStackTrace();
    }
}
}

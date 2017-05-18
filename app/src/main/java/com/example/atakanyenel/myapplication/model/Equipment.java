package com.example.atakanyenel.myapplication.model;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by eylulyurdakul on 5/17/2017.
 */

public class Equipment {

    private int id;
    private String name;
    private int status;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Equipment(JSONArray ja)
    {

        try {
            this.id=ja.getInt(0);
            this.name=ja.getString(1);
            this.status=ja.getInt(2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

package com.example.atakanyenel.myapplication.model;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;

/**
 * Created by atakanyenel on 29/04/2017.
 */

public class Task implements Serializable{

    protected int id;
    protected String name;
    protected String info;
    protected int status;

    public Task()
    {

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Task(JSONArray ja)
    {

        try {
            this.id=ja.getInt(0);
            this.name=ja.getString(1);
            this.info=ja.getString(2);
            this.status=ja.getInt(3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

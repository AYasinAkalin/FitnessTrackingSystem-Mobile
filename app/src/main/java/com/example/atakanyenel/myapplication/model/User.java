package com.example.atakanyenel.myapplication.model;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;

public class User implements Serializable {

    protected int id;
    protected String password;
    protected int role;
    protected String telephone;
    protected String email;
    protected String name;
    protected String surname;


    public User(int id,String password,int role,String telephone,String email,String name,String surname)
    {
        this.id=id;
        this.password=password;
        this.role=role;
        this.telephone=telephone;
        this.email=email;
        this.name=name;
        this.surname=surname;
    }
    public User(JSONArray ja)
    {
        try {
            this.id=ja.getInt(0);
            this.name=ja.getString(1);
            this.surname=ja.getString(2);
            this.email=ja.getString(3);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public User()
    {}
    public User(User u) //creates an instance of itself to pass to subclasses
    {
        this.email=u.email;
        this.id=u.id;
        this.password=u.password;
        this.role=u.role;
        this.telephone=u.telephone;
        this.name=u.name;
        this.surname=u.surname;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public int getRole() {
        return role;
    }


    public void setRole(int role) {
        this.role = role;
    }


    public String getTelephone() {
        return telephone;
    }


    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

}

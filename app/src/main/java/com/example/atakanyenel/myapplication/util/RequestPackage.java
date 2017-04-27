package com.example.atakanyenel.myapplication.util;

/**
 * Created by eralpsahin on 26.02.2017.
 */

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestPackage {

    private String endPoint;
    private String method = "GET";
    private JSONObject jsonObject;

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public RequestPackage(String endPoint, String method, JSONObject jsonObject) {
        this.endPoint = endPoint;
        this.method = method;
        this.jsonObject = jsonObject;
    }

    public RequestPackage(String endPoint, String method) {
        this.endPoint = endPoint;
        this.method = method;
    }

    public RequestPackage(String endPoint, String method, String json) {
        this.endPoint = endPoint;
        this.method = method;
        try {
            this.jsonObject = new JSONObject(new Gson().toJson(json));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
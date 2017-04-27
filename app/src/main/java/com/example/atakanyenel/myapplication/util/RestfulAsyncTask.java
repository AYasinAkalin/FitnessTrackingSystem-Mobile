package com.example.atakanyenel.myapplication.util;

import android.os.AsyncTask;

import com.example.atakanyenel.myapplication.util.HttpHelper;
import com.example.atakanyenel.myapplication.util.RequestPackage;

/**
 * Created by eralpsahin on 1.03.2017.
 */

public class RestfulAsyncTask extends AsyncTask<RequestPackage, String, String> {

    private OnRestfulAsyncTaskListener restfulAsyncTaskListener;

    public void setRestfulAsyncTaskListener(OnRestfulAsyncTaskListener restfulAsyncTaskListener) {
        this.restfulAsyncTaskListener = restfulAsyncTaskListener;
    }

    @Override
    protected String doInBackground(RequestPackage... params) {
        return HttpHelper.getData(params[0]);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        if(restfulAsyncTaskListener != null)
            restfulAsyncTaskListener.onProgressUpdateListener(values);
    }

    @Override
    protected void onPreExecute() {
        if(restfulAsyncTaskListener != null)
            restfulAsyncTaskListener.onPreExecuteListener();
    }

    @Override
    protected void onPostExecute(String result) {
        if(restfulAsyncTaskListener != null)
            restfulAsyncTaskListener.onPostExecuteListener(result);
    }


    public interface OnRestfulAsyncTaskListener {
        void onPreExecuteListener();

        void onProgressUpdateListener(String... values);

        void onPostExecuteListener(String result);
    }
}
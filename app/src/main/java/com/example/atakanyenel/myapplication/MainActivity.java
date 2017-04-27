package com.example.atakanyenel.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.atakanyenel.myapplication.model.User;
import com.example.atakanyenel.myapplication.util.RequestPackage;
import com.example.atakanyenel.myapplication.util.RestfulAsyncTask;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button Loginbutton=(Button) findViewById(R.id.btnLogin);
        final EditText email=(EditText) findViewById(R.id.email);
        final EditText password=(EditText) findViewById(R.id.password);
        User u=new User();
        Loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject json=new JSONObject();


                try {
                    json.accumulate("email",email.getText());
                     json.accumulate("password",password.getText());

                    System.out.println(json);
                    requestPackage(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    private void requestPackage(JSONObject json)
    {

        RequestPackage pack=new RequestPackage("http://10.0.2.2:8080/FitnessTraining/WebService/login","POST");
            pack.setJsonObject(json);
            RestfulAsyncTask task=new RestfulAsyncTask();

            task.setRestfulAsyncTaskListener(new RestfulAsyncTask.OnRestfulAsyncTaskListener() {
                @Override
                public void onPreExecuteListener() {

                }

                @Override
                public void onProgressUpdateListener(String... values) {

                }

                @Override
                public void onPostExecuteListener(String result) {

                    User user=new Gson().fromJson(result,User.class);
                    System.out.println(user.getName());
                    Intent i=new Intent(getApplicationContext(), HomeActivity.class);
                    i.putExtra("user", (Serializable) user);
                    startActivity(i);
                }
            });
            task.execute(pack);

    }
}

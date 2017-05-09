package com.example.atakanyenel.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.atakanyenel.myapplication.model.User;
import com.example.atakanyenel.myapplication.util.RequestPackage;
import com.example.atakanyenel.myapplication.util.RestfulAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button Loginbutton=(Button) findViewById(R.id.btnLogin);
        final EditText email=(EditText) findViewById(R.id.email);
        final EditText password=(EditText) findViewById(R.id.password);
        User u=new User();
        Loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject json=new JSONObject();


                try {
                    //json.accumulate("email",email.getText());
                    //json.accumulate("password",password.getText());
                    json.accumulate("email","berke@berke");
                    json.accumulate("password","berke"); //FIXME

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

        RequestPackage pack=new RequestPackage("http://10.0.2.2:5000/ws/login","POST");
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

                    Log.d( "onPostExecuteListener: ",result);
                    JSONObject json=null;
                    try {
                        json=new JSONObject(result);
                        String resp=json.getString("response");
                        if (resp.equals("OK"))
                        {
                            JSONArray userArray=json.getJSONArray("user");
                            User user=new User(userArray);
                            System.out.println(user.getName());
                            //Intent i=new Intent(getApplicationContext(), HomeActivity.class); //FIXME:
                            Intent i=new Intent(getApplicationContext(),TabbedActivity.class);
                            i.putExtra("user", (Serializable) user);
                            startActivity(i);
                        }
                        else if(resp.equals("NT"))
                        {
                            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                            alertDialog.setTitle("Fitness Training");
                            alertDialog.setMessage("You are not a trainer");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                        else if(resp.equals(("NA")))
                        {
                            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                            alertDialog.setTitle("Fitness Training");
                            alertDialog.setMessage("Not authorized");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                            }

                        else
                        {

                            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                            alertDialog.setTitle("Fitness Training");
                            alertDialog.setMessage("Unexpected Error");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });
            task.execute(pack);

    }
}

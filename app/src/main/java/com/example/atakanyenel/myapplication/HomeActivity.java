package com.example.atakanyenel.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.atakanyenel.myapplication.model.User;
import com.example.atakanyenel.myapplication.model.Task;
import com.example.atakanyenel.myapplication.util.RequestPackage;
import com.example.atakanyenel.myapplication.util.RestfulAsyncTask;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
public class HomeActivity extends AppCompatActivity {

    private RecyclerView TaskListView;
    private TextView userName;
    User u;
    List<Task> tasksList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tasksList=new ArrayList<Task>();


        u= (User) getIntent().getSerializableExtra("user");
        userName=(TextView) findViewById(R.id.userName);
        userName.setText(u.getName()+ " "+u.getSurname());



        requestPackage(null);


    }



    private void requestPackage(JSONObject json)
    {

        RequestPackage pack=new RequestPackage("http://10.0.2.2:5000/ws/tasks/"+u.getId(),"GET");
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

                Log.d("onPostExecuteListener: ", result);
                try {
                    JSONObject json=new JSONObject(result);
                    JSONArray tasks=json.getJSONArray("tasks");
                    for (int i=0;i<tasks.length();i++) {

                        Log.d("New tasks",tasks.getJSONArray(i).toString());
                        Task newTask=new Task(tasks.getJSONArray(i));
                        Log.d("TASK",newTask.toString());
                        tasksList.add(newTask);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                TaskListView=(RecyclerView) findViewById(R.id.list_task);
                TaskListView.setHasFixedSize(true);

                RVAdapter adapter=new RVAdapter(tasksList);
                TaskListView.setAdapter(adapter);
                RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getApplicationContext());
                TaskListView.setLayoutManager(mLayoutManager);


                RecyclerView.ItemDecoration mDividerItemDecoration = new DividerItemDecoration(TaskListView.getContext(),DividerItemDecoration.VERTICAL );
                TaskListView.addItemDecoration(mDividerItemDecoration);

            }
        });
        task.execute(pack);

    }

}

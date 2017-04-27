package com.example.atakanyenel.myapplication;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.atakanyenel.myapplication.model.User;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView TaskListView;
    private TextView userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TaskListView=(RecyclerView) findViewById(R.id.list_task);
        TaskListView.setHasFixedSize(true);
        List<String> tasks=asList("task1","task2","task3");

        RVAdapter adapter=new RVAdapter(tasks);
        TaskListView.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getApplicationContext());
        TaskListView.setLayoutManager(mLayoutManager);

        User u= (User) getIntent().getSerializableExtra("user");
        userName=(TextView) findViewById(R.id.userName);
        userName.setText(u.getName()+ " "+u.getSurname());

        RecyclerView.ItemDecoration mDividerItemDecoration = new DividerItemDecoration(TaskListView.getContext(),DividerItemDecoration.VERTICAL );
        TaskListView.addItemDecoration(mDividerItemDecoration);


    }

}

package com.example.atakanyenel.myapplication;

/**
 * Created by berkedifyeli on 07/05/2017.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atakanyenel.myapplication.model.Task;
import com.example.atakanyenel.myapplication.model.User;
import com.example.atakanyenel.myapplication.util.RequestPackage;
import com.example.atakanyenel.myapplication.util.RestfulAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TasksFragment extends Fragment{

    private RecyclerView TaskListView;

    List<Task> tasksList;

    public TasksFragment()
    {

    }

    public static TasksFragment newInstance(User u)
    {
        TasksFragment tf=new TasksFragment();
        Bundle args=new Bundle();
        args.putSerializable("user",u);
        tf.setArguments(args);

        return tf;
    }
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tasks, container, false);
        tasksList=new ArrayList<>();
/*

        u= (User) getActivity().getIntent().getSerializableExtra("user");
        userName=(TextView) getView().findViewById(R.id.userName);
        userName.setText(u.getName()+ " "+u.getSurname());

*/
        User u=(User)getArguments().getSerializable("user");
        requestPackage(null,u);

        Log.d("FRAGMENT","task fragment created");
        return rootView;
    }

    public void requestPackage(JSONObject json,User u)
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

                TaskListView=(RecyclerView) getView().findViewById(R.id.list_task);
                TaskListView.setHasFixedSize(true);

                RVAdapter adapter=new RVAdapter(tasksList);
                TaskListView.setAdapter(adapter);
                RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getActivity().getApplicationContext());
                TaskListView.setLayoutManager(mLayoutManager);


                RecyclerView.ItemDecoration mDividerItemDecoration = new DividerItemDecoration(TaskListView.getContext(),DividerItemDecoration.VERTICAL );
                TaskListView.addItemDecoration(mDividerItemDecoration);

            }
        });
        task.execute(pack);

    }
}

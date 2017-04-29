package com.example.atakanyenel.myapplication;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.atakanyenel.myapplication.model.Task;
import com.example.atakanyenel.myapplication.util.RequestPackage;
import com.example.atakanyenel.myapplication.util.RestfulAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by atakanyenel on 18/04/2017.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.TaskViewHolder> {

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView taskTitle;
        TextView taskdata;
        Button btn;

        TaskViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            taskTitle = (TextView) itemView.findViewById(R.id.person_name);
            taskdata = (TextView) itemView.findViewById(R.id.person_age);
            btn = (Button) itemView.findViewById(R.id.button);
        }
    }
        List<Task> tasks;

        RVAdapter(List<Task> tasks)
        {
            this.tasks=tasks;
        }

        public int getItemCount()
        {
            return tasks.size();
        }

        public TaskViewHolder onCreateViewHolder(ViewGroup viewGroup,int i)
        {
            View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task,viewGroup,false);
            TaskViewHolder tvh=new TaskViewHolder(v);
            return tvh;
        }
        public void onBindViewHolder(final TaskViewHolder tvh,final int i)
        {

            tvh.taskTitle.setText(tasks.get(i).getName());
            tvh.taskdata.setText(tasks.get(i).getInfo());

            if (tasks.get(i).getStatus()==0) {
                tvh.btn.setText("Complete");
                tvh.btn.setBackgroundColor(Color.GREEN);
                tvh.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        JSONObject json=new JSONObject();
                        try {
                            json.accumulate("link","/ws/task/"+tasks.get(i).getId()+"/complete");
                            requestPackage(json,tvh.btn);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
            else {
                tvh.btn.setText("Done");
                tvh.btn.setBackgroundColor(Color.RED);
                tvh.btn.setClickable(false);
            }
            }

        public void onAttachedRecyclerView(RecyclerView rv)
        {
            super.onAttachedToRecyclerView(rv);
        }

    private void requestPackage(JSONObject json,final Button btn)
    {


                RequestPackage pack=null;
        try {
            String link="http://10.0.2.2:5000"+json.getString("link");
            Log.d("Link",link);
           pack = new RequestPackage(link,"GET");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        pack.setJsonObject(null);
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

                if (result.equals("OK"))
                {
                    btn.setBackgroundColor(Color.RED);
                    btn.setText("Done");
                    btn.setClickable(false);
                }
            }
        });
        task.execute(pack);

    }


}


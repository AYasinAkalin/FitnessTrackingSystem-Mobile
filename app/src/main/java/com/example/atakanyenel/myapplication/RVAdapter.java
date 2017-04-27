package com.example.atakanyenel.myapplication;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
        List<String> tasks;

        RVAdapter(List<String> tasks)
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
        public void onBindViewHolder(TaskViewHolder tvh,int i)
        {
            tvh.taskTitle.setText(tasks.get(i));
            tvh.taskdata.setText("task data");
            tvh.btn.setText("Done");
        }

        public void onAttachedRecyclerView(RecyclerView rv)
        {
            super.onAttachedToRecyclerView(rv);
        }
    }


package com.example.atakanyenel.myapplication;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.atakanyenel.myapplication.model.Event;
import com.example.atakanyenel.myapplication.model.Task;
import com.example.atakanyenel.myapplication.util.RequestPackage;
import com.example.atakanyenel.myapplication.util.RestfulAsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by atakanyenel on 18/04/2017.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder> {

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView taskTitle;
        TextView taskdata;
        Button btn;

        EventViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            taskTitle = (TextView) itemView.findViewById(R.id.person_name);
            taskdata = (TextView) itemView.findViewById(R.id.person_age);
            btn = (Button) itemView.findViewById(R.id.button);
        }
    }
        List<Event> events;

        EventsAdapter(List<Event> events)
        {
            this.events=events;
        }

        public int getItemCount()
        {
            return events.size();
        }

        public EventViewHolder onCreateViewHolder(ViewGroup viewGroup,int i)
        {
            View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_event,viewGroup,false);
            EventViewHolder tvh=new EventViewHolder(v);
            return tvh;
        }
        public void onBindViewHolder(final EventViewHolder tvh,final int i)
        {

            tvh.taskTitle.setText(events.get(i).getName());
            tvh.taskdata.setText(events.get(i).getStartdate());

            if (events.get(i).getIsJoined()==0) {
                tvh.btn.setText("Join");
                tvh.btn.setBackgroundColor(Color.GREEN);
                tvh.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        JSONObject json=new JSONObject();
                        try {
                            json.accumulate("link","/ws/task/"+events.get(i).getId()+"/complete");
                            requestPackage(json,tvh.btn);
                            Snackbar.make(v, "You are doing great !!!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
            else {
                tvh.btn.setText("Leave");
                tvh.btn.setBackgroundColor(Color.RED);

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


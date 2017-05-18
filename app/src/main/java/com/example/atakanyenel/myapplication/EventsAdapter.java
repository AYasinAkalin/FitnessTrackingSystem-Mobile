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
import com.example.atakanyenel.myapplication.model.User;
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
    User u;

        EventsAdapter(List<Event> events,User u)
        {
            this.events=events;
            this.u=u;
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


            Log.d( "EVENT FULL",Integer.toString(events.get(i).getIsFull()));
            Log.d("EVENT JOIN",Integer.toString(events.get(i).getIsJoined()));

            tvh.taskTitle.setText(events.get(i).getName());
            tvh.taskdata.setText(events.get(i).getStartdate());

           if(events.get(i).getIsJoined()==1) { //joined

               tvh.btn.setText("LEAVE");
               tvh.btn.setBackgroundColor(Color.BLUE);
           }
           else if(events.get(i).getIsJoined()==0 && events.get(i).getIsFull()==0) //not joined and there is place in the event
           {
                    tvh.btn.setText("JOIN");
                    tvh.btn.setBackgroundColor(Color.GREEN);
           }
           else if(events.get(i).getIsFull()==1)
           {
               tvh.btn.setText("FULL");
               tvh.btn.setBackgroundColor(Color.RED);
               tvh.btn.setClickable(false);
           }

                    tvh.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        JSONObject json = new JSONObject();
                        if (tvh.btn.getText().equals("JOIN")) {
                            try {
                                json.accumulate("link", "/ws/events/join/" + events.get(i).getId() + "/" + u.getId());
                                requestPackage(json, tvh.btn);
                                Snackbar.make(v, "Joined " + events.get(i).getName(), Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        else
                        {
                            try
                            {
                                json.accumulate("link","/ws/events/leave/"+events.get(i).getId()+"/"+u.getId());
                                requestPackage(json,tvh.btn);
                                Snackbar.make(v,"Left "+events.get(i).getName(),Snackbar.LENGTH_LONG).setAction("Action",null).show();

                            }catch (JSONException e)
                            {
                                e.printStackTrace();
                            }

                        }
                    }
                });
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

                if (result.equals("joined"))
                {
                    btn.setText("LEAVE");
                    btn.setBackgroundColor(Color.BLUE);

                }
                if(result.equals("left"))
                {
                    btn.setText("JOIN");
                    btn.setBackgroundColor(Color.GREEN);
                }
            }
        });
        task.execute(pack);

    }


}


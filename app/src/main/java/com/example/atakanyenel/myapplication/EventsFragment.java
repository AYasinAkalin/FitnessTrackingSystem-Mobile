package com.example.atakanyenel.myapplication;

import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.atakanyenel.myapplication.model.Event;
import com.example.atakanyenel.myapplication.model.User;
import com.example.atakanyenel.myapplication.util.RequestPackage;
import com.example.atakanyenel.myapplication.util.RestfulAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by berkedifyeli on 08/05/2017.
 */

public class EventsFragment extends Fragment {

    private RecyclerView EventListView;
    List<Event> eventslist;
    public EventsFragment()
    {

    }

    public static EventsFragment newInstance(User u)
    {
        EventsFragment ef=new EventsFragment();
        Bundle args=new Bundle();
        args.putSerializable("user",u);
        ef.setArguments(args);
        return ef;
    }

@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView=inflater.inflate(R.layout.events,container,false);
        eventslist=new ArrayList<>();

    User u=(User)getArguments().getSerializable("user");
        requestPackage(null,u);

    return rootView;
    }
    public void requestPackage(Object o,final User u)
    {
        RequestPackage pack=new RequestPackage("http://10.0.2.2:5000/ws/events/"+u.getId(),"GET");
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
                Log.d("Event task",result);
                try
                {
                    JSONObject json=new JSONObject(result);
                    JSONArray events=json.getJSONArray("events");
                    for(int i=0;i<events.length();i++)
                    {
                        Event newevent=new Event(events.getJSONArray(i));
                        eventslist.add(newevent);
                    }

                }catch (JSONException e)
                {
                    e.printStackTrace();
                }

                EventListView=(RecyclerView) getView().findViewById(R.id.list_event);
                EventListView.setHasFixedSize(true);
                EventsAdapter adapter=new EventsAdapter(eventslist,u);
                EventListView.setAdapter(adapter);
                RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getActivity().getApplicationContext());
                EventListView.setLayoutManager(mLayoutManager);
                RecyclerView.ItemDecoration mDividerItemDecoration=new DividerItemDecoration(EventListView.getContext(),DividerItemDecoration.VERTICAL);
                EventListView.addItemDecoration(mDividerItemDecoration);



            }
        });
        task.execute(pack);
    }
}

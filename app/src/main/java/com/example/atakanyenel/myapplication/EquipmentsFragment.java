package com.example.atakanyenel.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.atakanyenel.myapplication.model.Equipment;
import com.example.atakanyenel.myapplication.model.Task;
import com.example.atakanyenel.myapplication.model.User;
import com.example.atakanyenel.myapplication.util.RequestPackage;
import com.example.atakanyenel.myapplication.util.RestfulAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eylulyurdakul on 16/05/2017.
 */

public class EquipmentsFragment extends Fragment {

    private RecyclerView EquipmentListView;
    List<Equipment> equipmentList;

    public EquipmentsFragment()
    {

    }

    public static EquipmentsFragment newInstance(User u)
    {
        EquipmentsFragment ef=new EquipmentsFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("user",u);
        ef.setArguments(bundle);

        return ef;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.equipments, container, false);
        equipmentList=new ArrayList<>();
/*

        u= (User) getActivity().getIntent().getSerializableExtra("user");
        userName=(TextView) getView().findViewById(R.id.userName);
        userName.setText(u.getName()+ " "+u.getSurname());

*/
        User u=(User)getArguments().getSerializable("user");
        requestPackage(null,u);

        Log.d("FRAGMENT","equipmen fragment created");
        return rootView;
    }
    public void requestPackage(JSONObject json, final User u)
    {

        RequestPackage pack=new RequestPackage("http://10.0.2.2:5000/ws/equipments","GET");

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
                    JSONArray equipments=json.getJSONArray("equipments");
                    for (int i=0;i<equipments.length();i++) {

                        Log.d("New equipment",equipments.getJSONArray(i).toString());
                        Equipment newEquip=new Equipment(equipments.getJSONArray(i));
                            Log.d("Equipment",newEquip.toString());
                        equipmentList.add(newEquip);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                EquipmentListView=(RecyclerView) getView().findViewById(R.id.list_equipments);
                EquipmentListView.setHasFixedSize(true);

                EquipmentsAdapter adapter=new EquipmentsAdapter(equipmentList,u);
                EquipmentListView.setAdapter(adapter);
                RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getActivity().getApplicationContext());
                EquipmentListView.setLayoutManager(mLayoutManager);


                RecyclerView.ItemDecoration mDividerItemDecoration = new DividerItemDecoration(EquipmentListView.getContext(),DividerItemDecoration.VERTICAL );
                EquipmentListView.addItemDecoration(mDividerItemDecoration);

            }
        });
        task.execute(pack);

    }
}


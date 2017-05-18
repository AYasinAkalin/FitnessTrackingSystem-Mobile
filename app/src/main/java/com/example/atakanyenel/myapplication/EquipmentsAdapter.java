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

import com.example.atakanyenel.myapplication.model.Equipment;
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

public class EquipmentsAdapter extends RecyclerView.Adapter<EquipmentsAdapter.EquipmentViewHolder> {

    public static class EquipmentViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView equipmentName;
        TextView taskdata;
        Button btn;

        EquipmentViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            equipmentName = (TextView) itemView.findViewById(R.id.person_name);
            taskdata = (TextView) itemView.findViewById(R.id.person_age);
            btn = (Button) itemView.findViewById(R.id.button);
        }
    }
        List<Equipment> equipments;
        User u;

        EquipmentsAdapter(List<Equipment> equipments, User u)
        {
            this.equipments=equipments;
            this.u = u;
        }

        public int getItemCount()
        {
            return equipments.size();
        }

        public EquipmentViewHolder onCreateViewHolder(ViewGroup viewGroup,int i)
        {
            View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task,viewGroup,false);
            EquipmentViewHolder tvh=new EquipmentViewHolder(v);
            return tvh;
        }
        public void onBindViewHolder(final EquipmentViewHolder tvh,final int i)
        {

            tvh.equipmentName.setText(equipments.get(i).getName());
            tvh.taskdata.setText("EQUIP DATA");

            if (equipments.get(i).getStatus()==0) {
                tvh.btn.setText("USE");
                tvh.btn.setBackgroundColor(Color.GREEN);
            }
            else if(equipments.get(i).getStatus()==u.getId()) {
                tvh.btn.setText("RELEASE");
                tvh.btn.setBackgroundColor(Color.BLUE);
            }
            else {
                tvh.btn.setText("OCCUPIED");
                tvh.btn.setBackgroundColor(Color.RED);
                tvh.btn.setEnabled(false);
            }
                tvh.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(tvh.btn.getText().equals("USE")) {

                        JSONObject json=new JSONObject();
                        try {
                            json.accumulate("link","/ws/equipments/use/"+equipments.get(i).getId()+ "/"+ u.getId());
                            requestPackage(json,tvh.btn);
                            Snackbar.make(v, "You are doing great !!!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        tvh.btn.setText("RELEASE");
                            tvh.btn.setBackgroundColor(Color.BLUE);
                        }
                        else
                        {
                            JSONObject json=new JSONObject();
                            try {
                                json.accumulate("link","/ws/equipments/release/"+equipments.get(i).getId()+ "/"+ u.getId());
                                requestPackage(json,tvh.btn);
                                Snackbar.make(v, "You are doing great !!!", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            tvh.btn.setText("USE");
                            tvh.btn.setBackgroundColor(Color.GREEN);
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


            }
        });
        task.execute(pack);

    }


}


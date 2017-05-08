package com.example.atakanyenel.myapplication;


import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
public class HomeActivity extends AppCompatActivity {

    /*private RecyclerView TaskListView;
    private TextView userName;
    User u;
    List<Task> tasksList;*/
    // Tab başlıkları
    private String[] tabs = { "Tasks", "Events", "Equipments" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabContainer);
        tabLayout.addTab(tabLayout.newTab().setText("Tasks"));
        tabLayout.addTab(tabLayout.newTab().setText("Events"));
        tabLayout.addTab(tabLayout.newTab().setText("Equipments"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PageAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        /*
        tasksList=new ArrayList<Task>();


        u= (User) getIntent().getSerializableExtra("user");
        userName=(TextView) findViewById(R.id.userName);
        userName.setText(u.getName()+ " "+u.getSurname());



        requestPackage(null);

        */

    }




    /*private void requestPackage(JSONObject json)
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

    }*/

}

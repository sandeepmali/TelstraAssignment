package com.example.sandeepmali.myapplication;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import API.LoadItemData;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dataAdapter.RecyclerAdapter;
import pojo.ItemDataObj;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Callback<ArrayList<ItemDataObj>> {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter recyclerAdapter;
    private Unbinder unbinder;
    private Call<ArrayList<ItemDataObj>> apiCall;
    private static ArrayList<ItemDataObj> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(MainActivity.this);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        if (itemList.size() == 0)
            loadListDetails();
        else {
            progressBar.setVisibility(View.GONE);
            setRecyclerData(itemList);

        }
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
                recyclerView.removeAllViews();
                if (recyclerAdapter != null)
                    recyclerAdapter.clearData();
                loadListDetails();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        unbinder = null;

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.search:
                refreshLayout.setRefreshing(false);
                recyclerView.removeAllViews();
                if (recyclerAdapter != null)
                    recyclerAdapter.clearData();
                loadListDetails();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }


    @Override
    public void onResponse(Call<ArrayList<ItemDataObj>> call, Response<ArrayList<ItemDataObj>> response) {
        progressBar.setVisibility(View.GONE);
        itemList = response.body();
        setRecyclerData(itemList);
    }

    @Override
    public void onFailure(Call<ArrayList<ItemDataObj>> call, Throwable t) {
        if (unbinder != null) {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
        }
        Toast.makeText(this, "Smoeting went wromg", Toast.LENGTH_SHORT).show();
    }

    /**
     * set Recycler Adapter
     *
     * @param itemList
     */
    private void setRecyclerData(ArrayList<ItemDataObj> itemList) {
        /**
         * Set Action bar Title. Currently Json data is not working so, hard coded.
         */
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Telstra");
        recyclerAdapter = new RecyclerAdapter(MainActivity.this, itemList);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(recyclerAdapter);
    }

    /**
     * Call Api and load data from server
     */
    private void loadListDetails() {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.androidhive.info")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoadItemData service = retrofit.create(LoadItemData.class);
        apiCall = service.loadData();
        apiCall.enqueue(MainActivity.this);

    }

}

package view;

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

import com.example.sandeepmali.myapplication.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dataAdapter.RecyclerAdapter;
import model.Row;
import presenter.MainActivityPresenter;

public class MainActivity extends AppCompatActivity implements MainActivityPresenter.View {

    private static ArrayList<Row> itemList = new ArrayList<>();
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter recyclerAdapter;
    private Unbinder unbinder;
    private MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * Initial set Up
         */
        setUpMVP();
        setUpViews();
        getListDetails();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearRecyclerData();
                getListDetails();
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
        switch (item.getItemId()) {
            case R.id.refresh:
                clearRecyclerData();
                getListDetails();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    /**
     * Show error message
     * @param errorMessage
     */
    public void onFailure(String errorMessage) {
        if (unbinder != null) {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
        }
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    /**
     * set Recycler Adapter
     *
     * @param dataObjs
     */
    private void setRecyclerData(ArrayList<Row> dataObjs) {

        itemList = dataObjs;
        progressBar.setVisibility(View.GONE);
        recyclerAdapter = new RecyclerAdapter(MainActivity.this, itemList);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(recyclerAdapter);
    }

    /**
     * MVP set up
     */
    private void setUpMVP() {

        presenter = new MainActivityPresenter(this);

    }

    /**
     * UI set up
     */
    private void setUpViews() {

        unbinder = ButterKnife.bind(MainActivity.this);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


    }

    private void getListDetails() {

        if (itemList.size() == 0)
            presenter.loadRecyclerData();
        else {
            setRecyclerData(itemList);
        }
    }

    /**
     * Clear Recycler Data to load latest updated data
     */
    void clearRecyclerData() {
        itemList.clear();
        recyclerView.removeAllViews();
        if (recyclerAdapter != null)
            recyclerAdapter.clearData();
    }

    @Override
    public void loadListDetails(ArrayList<Row> dataObjs,String mainTitleName) {

        setRecyclerData(dataObjs);
        /**
         * Set Action bar Title.
         */
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(mainTitleName);

    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
        refreshLayout.setRefreshing(false);
        progressBar.setVisibility(View.VISIBLE);
    }

}

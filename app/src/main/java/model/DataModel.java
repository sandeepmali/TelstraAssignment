package model;

import com.example.sandeepmali.myapplication.Constant;

import java.util.ArrayList;

import API.LoadItemData;
import presenter.MainActivityPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataModel {

    private MainActivityPresenter presenter;
    private Call<ItemDataObj> apiCall;
    public DataModel(MainActivityPresenter instance) {

        this.presenter = instance;
    }

    public void getListData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoadItemData service = retrofit.create(LoadItemData.class);

        apiCall = service.loadData();
        apiCall.enqueue(new Callback<ItemDataObj>() {
            @Override
            public void onResponse(Call<ItemDataObj> call, Response<ItemDataObj> response) {
                System.out.print("Sucess");
                presenter.onSucess(response.body().getRows(),response.body().getTitle());
            }

            @Override
            public void onFailure(Call<ItemDataObj> call, Throwable t) {
                presenter.onFailure(t.getMessage());
            }
        });
    }

    public interface modelImpl {

        void onSucess(ArrayList<Row> response, String mainTitleName);

        void onFailure(String failureMessage);

    }
}

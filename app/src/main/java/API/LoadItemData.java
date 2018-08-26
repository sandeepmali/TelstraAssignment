package API;

import pojo.ItemDataObj;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by sandeepmali on 25/08/18.
 */

public interface LoadItemData {

    @GET("json/movies.json")
    Call<ArrayList<ItemDataObj>> loadData();

}


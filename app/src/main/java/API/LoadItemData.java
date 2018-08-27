package API;

import model.ItemDataObj;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by sandeepmali on 25/08/18.
 */

public interface LoadItemData {

    @GET("facts.js")
    Call<ItemDataObj> loadData();

}


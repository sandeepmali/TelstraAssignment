package presenter;

import java.util.ArrayList;

import model.DataModel;
import model.ItemDataObj;
import model.Row;
import view.MainActivity;

/**
 * Created by sandeepmali on 27/08/18.
 */

public class MainActivityPresenter implements DataModel.modelImpl {
    private ItemDataObj itemDataObj;
    private MainActivity mainActivity;
    private DataModel dataModel;

    public MainActivityPresenter(MainActivity view) {
        this.itemDataObj = new ItemDataObj();
        this.mainActivity = view;
        dataModel = new DataModel(MainActivityPresenter.this);
    }

    public void loadRecyclerData() {
        dataModel.getListData();
        mainActivity.showProgressBar();
    }

    @Override
    public void onSucess(ArrayList<Row> response,String mainTitleName) {
        mainActivity.hideProgressBar();
        mainActivity.loadListDetails(response,mainTitleName);

    }

    @Override
    public void onFailure(String failureMessage) {
        mainActivity.hideProgressBar();
        mainActivity.onFailure(failureMessage);
    }


    public interface View {

        void loadListDetails(ArrayList<Row> dataObjs,String mainTitleName);

        void hideProgressBar();

        void showProgressBar();

    }
}

package dataAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sandeepmali.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import model.Row;

/**
 * Created by sandeepmali on 25/08/18.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<Row> dataList;
    private Context context;

    public RecyclerAdapter(Context context, ArrayList<Row> dataList) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());

        View view = inflater.inflate(R.layout.row_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        if (holder != null) {

            Row row = dataList.get(position);
            if (row.getTitle() != null)
                holder.txt_main_header.setText(row.getTitle());
            if (row.getDescription() != null)
                holder.txt_sub_header.setText(row.getDescription());
            if (row.getImageHref() != null)
                Picasso.with(context)
                        .load(row.getImageHref().toString())
                        .placeholder(R.drawable.camera)
                        .error(R.drawable.camera)
                        .into(holder.img_movie);
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /**
     * Empty Recycler data and again load by pull to refresh
     */
    public void clearData() {
        dataList.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_main_header)
        TextView txt_main_header;
        @BindView(R.id.txt_sub_header)
        TextView txt_sub_header;
        @BindView(R.id.img_movie)
        ImageView img_movie;

        public ViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

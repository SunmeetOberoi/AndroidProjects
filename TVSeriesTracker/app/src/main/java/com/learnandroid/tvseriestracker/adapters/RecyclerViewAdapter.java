package com.learnandroid.tvseriestracker.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.learnandroid.tvseriestracker.R;
import com.learnandroid.tvseriestracker.activities.MainActivity;
import com.learnandroid.tvseriestracker.model.Series;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    List<Series> seriesList;
    Context context;

    public  RecyclerViewAdapter(Context context, List<Series> seriesList)
    {
        this.context = context;
        this.seriesList = seriesList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_series, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Series series = seriesList.get(position);
        holder.tvTitle.setText(series.getTitle());
        holder.btnSeason.setText(String.valueOf(series.getSeason()));
        holder.btnEpisode.setText(String.valueOf(series.getEpisode()));
    }

    @Override
    public int getItemCount() {
        return seriesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView tvTitle;
        Button btnSeason;
        Button btnEpisode;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            btnSeason = itemView.findViewById(R.id.btnSeason);
            btnEpisode = itemView.findViewById(R.id.btnEpisode);
        }

        @Override
        public boolean onLongClick(View v) {
            //TODO: Open Edit Activity
            return true;
        }
    }
}

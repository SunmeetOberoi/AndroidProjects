package com.learnandroid.tvseriestracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.learnandroid.tvseriestracker.R;
import com.learnandroid.tvseriestracker.activities.Add_EditActivity;
import com.learnandroid.tvseriestracker.activities.MainActivity;
import com.learnandroid.tvseriestracker.database.DataBaseDAO;
import com.learnandroid.tvseriestracker.helper.ItemTouchHelperAdapter;
import com.learnandroid.tvseriestracker.model.Series;

import java.util.Collections;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
        implements ItemTouchHelperAdapter {

    public List<Series> seriesList;
    Context context;
    DataBaseDAO dao;

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
    public void onBindViewHolder(@NonNull final RecyclerViewAdapter.ViewHolder holder, final int position) {
        final Series series = seriesList.get(position);
        holder.tvTitle.setText(series.getTitle());
        holder.btnSeason.setText(String.valueOf(series.getSeason()));
        holder.btnEpisode.setText(String.valueOf(series.getEpisode()));
        dao = new DataBaseDAO(context);
        final Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);


        //Listener for updating Episode
        holder.btnEpisode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao.open();
                int newEpisode = Integer.valueOf(holder.btnEpisode.getText().toString()) + 1;
                Series updatedSeries = new Series(seriesList.get(position).getTitle(),
                        Integer.valueOf(holder.btnSeason.getText().toString()),
                        newEpisode);

                dao.updateSeries(series.getTitle(), updatedSeries);
                holder.btnEpisode.setText(String.valueOf(newEpisode));
                vibrator.vibrate(100);
                dao.close();
            }
        });
        //Listener for updating season and resetting episode
        holder.btnSeason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao.open();
                int newSeason = Integer.valueOf(holder.btnSeason.getText().toString()) + 1;
                Series updatedSeries = new Series(seriesList.get(position).getTitle(),
                        newSeason,
                        0);

                dao.updateSeries(series.getTitle(), updatedSeries);
                holder.btnSeason.setText(String.valueOf(newSeason));
                holder.btnEpisode.setText("0");
                vibrator.vibrate(100);
                dao.close();
            }
        });
    }

    @Override
    public int getItemCount() {
        return seriesList.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(seriesList, i, i+1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(seriesList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);

        return true;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;
        Button btnSeason;
        Button btnEpisode;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            btnSeason = itemView.findViewById(R.id.btnSeason);
            btnEpisode = itemView.findViewById(R.id.btnEpisode);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, Add_EditActivity.class);
            intent.putExtra("title", this.tvTitle.getText().toString());
            intent.putExtra("season", Integer.valueOf(this.btnSeason.getText().toString()));
            intent.putExtra("episode", Integer.valueOf(this.btnEpisode.getText().toString()));
            context.startActivity(intent);
        }
    }
}

package com.learnandroid.tvseriestracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.VibrationEffect;
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
import com.learnandroid.tvseriestracker.model.Series;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    List<Series> seriesList;
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
        holder.btnEpisode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao.open();
                Series updatesSeries = new Series(seriesList.get(position).getTitle(),
                        seriesList.get(position).getSeason(),
                        Integer.valueOf(holder.btnEpisode.getText().toString()) + 1);

                dao.updateSeries(series.getTitle(), updatesSeries);
                holder.btnEpisode.setText(String.valueOf(Integer.valueOf(holder.btnEpisode.getText().toString()) + 1));
                Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                dao.close();
            }
        });
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
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            Intent intent = new Intent(context, Add_EditActivity.class);
            intent.putExtra("title", this.tvTitle.getText().toString());
            intent.putExtra("season", Integer.valueOf(this.btnSeason.getText().toString()));
            intent.putExtra("episode", Integer.valueOf(this.btnEpisode.getText().toString()));
            context.startActivity(intent);
            return true;
        }
    }
}

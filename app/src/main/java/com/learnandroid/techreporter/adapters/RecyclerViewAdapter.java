package com.learnandroid.techreporter.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.learnandroid.techreporter.R;
import com.learnandroid.techreporter.models.News;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    List<News> news;
    Context context;

    public RecyclerViewAdapter(Context context, List<News> news){
        this.context = context;
        this.news = news;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News techNews = news.get(position);
        holder.tvTitle.setText(techNews.getTitle());
        holder.tvDescription.setText(techNews.getDescription());
        holder.tvAuthor.setText(techNews.getAuthor());
        holder.tvDatePublished.setText(techNews.getDate_time());

        Picasso.get()
                .load(techNews.getPath_to_image())
                .into(holder.ivNewsImage);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivNewsImage)
        ImageView ivNewsImage;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvDescription)
        TextView tvDescription;
        @BindView(R.id.tvAuthor)
        TextView tvAuthor;
        @BindView(R.id.tvDatePublished)
        TextView tvDatePublished;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

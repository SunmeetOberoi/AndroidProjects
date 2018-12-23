package com.learnandroid.chatapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.learnandroid.chatapplication.R;
import com.learnandroid.chatapplication.dataClasses.MessagesModel;

import java.util.List;

public class MessagesRecyclerViewAdapter extends RecyclerView.Adapter<MessagesRecyclerViewAdapter.ViewHolder> {

    List<MessagesModel> messages;
    Context mContext;
    String friendName;

    public MessagesRecyclerViewAdapter(List<MessagesModel> messages, Context context, String friendName) {
        this.messages = messages;
        this.mContext = context;
        this.friendName = friendName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int itemType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemType, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvMessage.setText(messages.get(position).getValue());
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getFrom().equals(friendName))
            return R.layout.item_not_my_message;
        else
            return R.layout.item_my_message;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            tvMessage = (TextView) itemView.findViewById(R.id.tvMessage);
        }
    }
}

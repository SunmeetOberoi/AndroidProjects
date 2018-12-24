package com.learnandroid.chatapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.learnandroid.chatapplication.R;
import com.learnandroid.chatapplication.activities.ChatActivity;
import com.learnandroid.chatapplication.dataClasses.ContactsModel;

import java.util.List;

import static com.learnandroid.chatapplication.dataClasses.ApplicationClass.isAChatOpened;
import static com.learnandroid.chatapplication.dataClasses.ApplicationClass.mAuth;

public class ContactsRecyclerViewAdapter extends RecyclerView.Adapter<ContactsRecyclerViewAdapter.ViewHolder> {

    List<ContactsModel> contacts;
    Context mContext;

    public ContactsRecyclerViewAdapter(List<ContactsModel> contacts, Context mContext) {
        this.contacts = contacts;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContactsModel contact = contacts.get(position);
        holder.tvFriendName.setText(contact.getName());
        if (contact.getStatus().equals("Online"))
            holder.ivstatus.setImageResource(R.drawable.ic_online);
        else
            holder.ivstatus.setImageResource(R.drawable.ic_offline);
        if (!contact.getLastMsg().equals("nothing"))
            holder.tvLastMsg.setText(contact.getLastMsg());
        else
            holder.tvLastMsg.setText("");
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvFriendName;
        ImageView ivstatus;
        TextView tvLastMsg;

        ViewHolder(View itemView) {
            super(itemView);
            tvFriendName = (TextView) itemView.findViewById(R.id.tvFriendName);
            ivstatus = (ImageView) itemView.findViewById(R.id.ivStatus);
            tvLastMsg = (TextView) itemView.findViewById(R.id.tvLastMsg);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, ChatActivity.class);
            String chatCode, userid = mAuth.getCurrentUser().getEmail().replace('.', ','),
                    name = this.tvFriendName.getText().toString();
            if (userid.compareTo(name) < 0)
                chatCode = name + "___" + userid;
            else
                chatCode = userid + "___" + name;
            intent.putExtra("ChatCode", chatCode);
            intent.putExtra("Friend", name);
            isAChatOpened = true;
            mContext.startActivity(intent);

        }
    }
}

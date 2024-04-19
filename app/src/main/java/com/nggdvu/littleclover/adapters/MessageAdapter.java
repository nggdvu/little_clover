package com.nggdvu.littleclover.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
//import com.nggdvu.littleclover.listeners.UserListener;
import com.nggdvu.littleclover.models.Campaign;
import com.nggdvu.littleclover.models.Message;
import com.nggdvu.littleclover.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MessageAdapter extends FirebaseRecyclerAdapter<Message, MessageAdapter.viewHolder> {

    //UserListener userListener;

    public MessageAdapter(@NonNull FirebaseRecyclerOptions<Message> options) {
        super(options);
    }

    @NonNull
    @Override
    public MessageAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        return new viewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.viewHolder viewHolder, int position, @NonNull Message message) {
        viewHolder.nameTxt.setText(message.getName());
        viewHolder.emailTxt.setText(message.getEmail());

        Picasso.get()
                .load(message.getAvatar())
                .into(viewHolder.avatar);
    }

    static class viewHolder extends RecyclerView.ViewHolder{

        ImageView avatar;
        TextView nameTxt, emailTxt;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            avatar = itemView.findViewById(R.id.avatar);
            nameTxt = itemView.findViewById(R.id.nameTxt);
            emailTxt = itemView.findViewById(R.id.emailTxt);
        }
    }
}

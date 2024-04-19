package com.nggdvu.littleclover.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.nggdvu.littleclover.models.Campaign;
import com.nggdvu.littleclover.R;
import com.nggdvu.littleclover.models.Story;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {

    Context context;
    ArrayList<Story> storyArrayList;

    public StoryAdapter(Context context, ArrayList<Story> storyArrayList) {
        this.context = context;
        this.storyArrayList = storyArrayList;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Story story = storyArrayList.get(position);
        viewHolder.hashtag.setText(story.getHashtag());

        Picasso.get()
                .load(story.getPhoto())
                .into(viewHolder.imageView);
    }

    @NonNull
    @Override
    public StoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount(){
        return storyArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView hashtag;
        ImageView imageView;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hashtag = itemView.findViewById(R.id.hashtagTxt);
            imageView = itemView.findViewById(R.id.storyImg);
            cardView = itemView.findViewById(R.id.storyCard);
        }
    }
}
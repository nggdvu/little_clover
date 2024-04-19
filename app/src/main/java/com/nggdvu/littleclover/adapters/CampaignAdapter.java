package com.nggdvu.littleclover.adapters;

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
import com.nggdvu.littleclover.fragments.HomeFragment;
import com.nggdvu.littleclover.models.Campaign;
import com.nggdvu.littleclover.R;
import com.squareup.picasso.Picasso;

public class CampaignAdapter extends FirebaseRecyclerAdapter<Campaign, CampaignAdapter.ViewHolder> {

    public CampaignAdapter(@NonNull FirebaseRecyclerOptions<Campaign> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int position, @NonNull Campaign campaign) {
        viewHolder.title.setText(campaign.getTitle());
        viewHolder.aiming.setText(campaign.getAiming());
        viewHolder.location.setText(campaign.getLocation());
        viewHolder.sort.setText(campaign.getSort());
        viewHolder.description.setText(campaign.getDescription());
        viewHolder.time.setText(campaign.getTime());

        Picasso.get()
                .load(campaign.getImage())
                .into(viewHolder.imageView);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.campaign_item, parent, false);
        return new ViewHolder(view);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, aiming, location, sort, description, time;
        ImageView imageView;
        Button button;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.getImg);
            title = itemView.findViewById(R.id.titleId);
            location = itemView.findViewById(R.id.locationId);
            aiming = itemView.findViewById(R.id.aimingId);
            sort = itemView.findViewById(R.id.sortId);
            description = itemView.findViewById(R.id.descriptionId);
            time = itemView.findViewById(R.id.timeId);
            //button = itemView.findViewById(R.id.supportBtn);
            cardView = itemView.findViewById(R.id.homeItem);
        }
    }
}
package com.nggdvu.littleclover.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.nggdvu.littleclover.models.Campaign;
import com.nggdvu.littleclover.R;
import com.nggdvu.littleclover.fragments.DonateFragment;
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

        viewHolder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView likeButton = (ImageView) view;
                if (campaign.isLiked()) {
                    campaign.setLiked(false);
                    likeButton.setImageResource(R.drawable.heart);
                } else {
                    campaign.setLiked(true);
                    likeButton.setImageResource(R.drawable.heartfill);
                }
            }
        });

        viewHolder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle share button click
            }
        });

        viewHolder.donateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DonateFragment donateFragment = new DonateFragment();
                FragmentTransaction fragmentTransaction = ((AppCompatActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom);
                fragmentTransaction.replace(R.id.containerId, donateFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.campaign_item, parent, false);
        return new ViewHolder(view);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, aiming, location, sort, description, time;
        ImageView imageView, likeButton;
        Button donateButton;
        ImageButton shareButton;
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
            donateButton = itemView.findViewById(R.id.supportBtn);
            cardView = itemView.findViewById(R.id.homeItem);
            likeButton = itemView.findViewById(R.id.likeBtn);
            shareButton = itemView.findViewById(R.id.shareBtn);
        }
    }
}
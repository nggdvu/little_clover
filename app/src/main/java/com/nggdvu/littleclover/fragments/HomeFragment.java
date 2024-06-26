package com.nggdvu.littleclover.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.nggdvu.littleclover.R;
import com.nggdvu.littleclover.adapters.CampaignAdapter;
import com.nggdvu.littleclover.adapters.StoryAdapter;
import com.nggdvu.littleclover.models.Campaign;
import com.nggdvu.littleclover.models.Story;

import java.util.ArrayList;

public class HomeFragment extends Fragment{

    RecyclerView recyclerView, storyrv;
    DatabaseReference databaseReference;
    ImageButton addStoryBtn, allStoryBtn;
    CampaignAdapter campaignAdapter;
    StoryAdapter storyAdapter;
    String image, title, aiming, location, sort, description, time;
    String photo, hashtag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_home, container, false);

        addStoryBtn = fragmentView.findViewById(R.id.addStoryBtn);
        allStoryBtn = fragmentView.findViewById(R.id.allStoryBtn);
        recyclerView = fragmentView.findViewById(R.id.rv);
        storyrv = fragmentView.findViewById(R.id.storyrv);

        //Chiến dịch
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        FirebaseRecyclerOptions<Campaign> options =
                new FirebaseRecyclerOptions.Builder<Campaign>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("campaigns"), Campaign.class)
                        .build();
        campaignAdapter = new CampaignAdapter(options);
        recyclerView.setAdapter(campaignAdapter);

        //Hoạt động
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        storyrv.setHasFixedSize(false);
        storyrv.setLayoutManager(linearLayoutManager1);
        storyrv.setNestedScrollingEnabled(false);
        FirebaseRecyclerOptions<Story> stories =
                new FirebaseRecyclerOptions.Builder<Story>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("stories"), Story.class)
                        .build();
        storyAdapter = new StoryAdapter(stories);
        storyrv.setAdapter(storyAdapter);

        addStoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddStoryFragment addStoryFragment = new AddStoryFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom);
                fragmentTransaction.replace(R.id.containerId, addStoryFragment);
                fragmentTransaction.commit();
            }
        });

        allStoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllStoryFragment allStoryFragment = new AllStoryFragment("photo", "hashtag");
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom);
                fragmentTransaction.replace(R.id.containerId, allStoryFragment);
                fragmentTransaction.commit();
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("campaigns");
        databaseReference = FirebaseDatabase.getInstance().getReference("stories");

        return fragmentView;
    }

    public HomeFragment(String photo, String hashtag, String image, String title, String aiming, String location, String sort, String description, String time){
        //
        this.image = image;
        this.title = title;
        this.aiming = aiming;
        this.location = location;
        this.sort = sort;
        this.description = description;
        this.time = time;
        this.photo = photo;
        this.hashtag = hashtag;
    }

    @Override
    public void onStart(){
        super.onStart();
        campaignAdapter.startListening();
        storyAdapter.startListening();
    }

    @Override
    public void onStop(){
        super.onStop();
        campaignAdapter.stopListening();
        storyAdapter.startListening();
    }
}
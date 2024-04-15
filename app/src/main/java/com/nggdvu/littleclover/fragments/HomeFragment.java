package com.nggdvu.littleclover.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.DatabaseReference;
import com.nggdvu.littleclover.R;
import com.nggdvu.littleclover.adapters.CampaignAdapter;
import com.nggdvu.littleclover.models.Campaign;

public class HomeFragment extends Fragment{

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    CampaignAdapter campaignAdapter;
    String image, title, aiming, location, sort, description, time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = fragmentView.findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        FirebaseRecyclerOptions<Campaign> options =
                new FirebaseRecyclerOptions.Builder<Campaign>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("campaigns"), Campaign.class)
                        .build();

        campaignAdapter = new CampaignAdapter(options);
        recyclerView.setAdapter(campaignAdapter);

        
        databaseReference = FirebaseDatabase.getInstance().getReference("campaigns");

        Toolbar mainToolbar = fragmentView.findViewById(R.id.mainToolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mainToolbar);
        activity.getSupportActionBar().setTitle("");
        setHasOptionsMenu(true);
        return fragmentView;
    }


    public HomeFragment(String image, String title, String aiming, String location, String sort, String description, String time){
        this.image = image;
        this.title = title;
        this.aiming = aiming;
        this.location = location;
        this.sort = sort;
        this.description = description;
        this.time = time;

    }

    @Override
    public void onStart(){
        super.onStart();
        campaignAdapter.startListening();
    }

    @Override
    public void onStop(){
        super.onStop();
        campaignAdapter.stopListening();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notificationBtn:
                // Handle notification button click
                return true;
            case R.id.messageBtn:
                // Load message fragment
                loadMessageFragment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if  (item.getItemId() == R.id.notificationBtn) {
            return true;
        } else if (item.getItemId() == R.id.messageBtn) {
            loadMessageFragment();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void loadMessageFragment() {
        // Replace this with the code to load your message fragment
        // For example:
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new MessageFragment());
        fragmentTransaction.addToBackStack(null); // Add this line to enable back navigation
        fragmentTransaction.commit();
    }*/
}
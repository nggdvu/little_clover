package com.nggdvu.littleclover.unusedpackages;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nggdvu.littleclover.R;
import com.nggdvu.littleclover.adapters.CampaignAdapter;
import com.nggdvu.littleclover.models.Campaign;

import java.util.List;

//Chưa hoạt động
public class SearchFragment extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    CampaignAdapter campaignAdapter;
    EditText itemSearch;
    List<Campaign> dataList;
    ValueEventListener eventListener;
    SearchView searchView;
    String image, title, aiming, location, sort, description, time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = fragmentView.findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        Toolbar toolbar = fragmentView.findViewById(R.id.search_toolbar);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("");
        setHasOptionsMenu(true);

        searchView = fragmentView.findViewById(R.id.itemSearch);

        FirebaseRecyclerOptions<Campaign> options =
                new FirebaseRecyclerOptions.Builder<Campaign>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("campaigns"), Campaign.class)
                        .build();

        campaignAdapter = new CampaignAdapter(options);
        campaignAdapter.startListening();
        recyclerView.setAdapter(campaignAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("campaigns");
        return fragmentView;
    }

    public SearchFragment(String image, String title, String aiming, String location, String sort, String description, String time){
        this.image = image;
        this.title = title;
        this.aiming = aiming;
        this.location = location;
        this.sort = sort;
        this.description = description;
        this.time = time;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.search_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void searchCampaigns(String query) {
        FirebaseRecyclerOptions<Campaign> options =
                new FirebaseRecyclerOptions.Builder<Campaign>()
                        .setQuery(databaseReference.orderByChild("campaigns/").startAt(query).endAt(query + "\uf8ff"), Campaign.class)
                        .build();

        campaignAdapter.updateOptions(options);
    }
}
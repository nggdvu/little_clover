package com.nggdvu.littleclover.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nggdvu.littleclover.R;
import com.nggdvu.littleclover.adapters.StoryAdapter;
import com.nggdvu.littleclover.models.Story;

public class AllStoryFragment extends Fragment {

    RecyclerView storyrv;
    StoryAdapter storyAdapter;
    DatabaseReference databaseReference;
    String photo, hashtag;
    ImageButton backBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_all_story, container, false);

        storyrv = fragmentView.findViewById(R.id.allstoryrv);
        backBtn = fragmentView.findViewById(R.id.backBtn);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        storyrv.setHasFixedSize(true);
        storyrv.setLayoutManager(gridLayoutManager);
        storyrv.setNestedScrollingEnabled(false);

        FirebaseRecyclerOptions<Story> stories =
                new FirebaseRecyclerOptions.Builder<Story>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("stories"), Story.class)
                        .build();

        storyAdapter = new StoryAdapter(stories);
        storyrv.setAdapter(storyAdapter);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment("photo", "hashtag", "image", "title", "aiming", "location", "description", "description", "time");
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom);
                fragmentTransaction.replace(R.id.containerId, homeFragment);
                fragmentTransaction.commit();
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("stories");

        return fragmentView;
    }

    public AllStoryFragment(String photo, String hashtag){
        this.photo = photo;
        this.hashtag = hashtag;
    }

    @Override
    public void onStart(){
        super.onStart();
        storyAdapter.startListening();
    }

    @Override
    public void onStop(){
        super.onStop();
        storyAdapter.startListening();
    }
}
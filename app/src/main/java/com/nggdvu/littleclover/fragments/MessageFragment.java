package com.nggdvu.littleclover.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nggdvu.littleclover.R;
import com.nggdvu.littleclover.adapters.CampaignAdapter;
import com.nggdvu.littleclover.adapters.MessageAdapter;
import com.nggdvu.littleclover.models.Campaign;
import com.nggdvu.littleclover.models.Message;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

//Tin nhắn chưa hoạt động, nhưng người dùng thì có
public class MessageFragment extends Fragment {

    String name, email, avatar;
    MessageAdapter messageAdapter;
    RecyclerView msrv;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_message, container, false);

        msrv = fragmentView.findViewById(R.id.msrv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        msrv.setLayoutManager(linearLayoutManager);
        msrv.setNestedScrollingEnabled(false);

        //Truyền dữ liệu
        FirebaseRecyclerOptions<Message> options =
                new FirebaseRecyclerOptions.Builder<Message>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users"), Message.class)
                        .build();

        messageAdapter = new MessageAdapter(options);
        msrv.setAdapter(messageAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        /*Bundle bundle = getArguments();
        if (bundle != null) {
            Uid = bundle.getString("Uid");
            name = bundle.getString("name");
            // Use the retrieved values here
        }

        msrv.setHasFixedSize(true);
        msrv.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messageLists.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    final String getUid = snapshot.getKey();

                    if (!getUid.equals(Uid)) {
                        final String getName = snapshot.child("name").getValue(String.class);
                        final String getAvatar = snapshot.child("profile").getValue(String.class);

                        Message messageList = new Message(getName, getUid, "", getAvatar, 0);
                        messageLists.add(messageList);
                    }
                }
                msrv.setAdapter(new MessageAdapter(messageLists, getContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the cancellation here
            }
        });*/

        return fragmentView;
    }
    public MessageFragment(String name, String email, String avatar){
        this.name = name;
        this.email = email;
        this.avatar = avatar;

    }

    @Override
    public void onStart(){
        super.onStart();
        messageAdapter.startListening();
    }

    @Override
    public void onStop(){
        super.onStop();
        messageAdapter.stopListening();
    }

}
package com.nggdvu.littleclover.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.nggdvu.littleclover.R;

public class NotificationFragment extends Fragment {

    ImageButton backBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_notification, container, false);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment("photo", "hashtag","image", "title", "aiming", "location", "description", "description","time");
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom);
                fragmentTransaction.replace(R.id.containerId, homeFragment);
                fragmentTransaction.commit();
            }
        });
        return fragmentView;
    }
}
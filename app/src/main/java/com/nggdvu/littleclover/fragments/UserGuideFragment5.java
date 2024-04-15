package com.nggdvu.littleclover.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.nggdvu.littleclover.R;
public class UserGuideFragment5 extends Fragment {

    ImageButton backBtn;
    Button  doneBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_user_guide5, container, false);

        backBtn = fragmentView.findViewById(R.id.backBtn);
        doneBtn = fragmentView.findViewById(R.id.doneBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserFragment userFragment = new UserFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerId, userFragment);
                fragmentTransaction.commit();
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserFragment userFragment = new UserFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerId, userFragment);
                fragmentTransaction.commit();
            }
        });
        return fragmentView;
    }
}
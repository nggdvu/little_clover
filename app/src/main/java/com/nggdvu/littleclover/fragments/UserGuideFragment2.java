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
public class UserGuideFragment2 extends Fragment {

    ImageButton backBtn;
    Button  nextBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_user_guide2, container, false);

        backBtn = fragmentView.findViewById(R.id.backBtn);
        nextBtn = fragmentView.findViewById(R.id.nextBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserFragment userFragment = new UserFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom);
                fragmentTransaction.replace(R.id.containerId, userFragment);
                fragmentTransaction.commit();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserGuideFragment3 userGuideFragment3 = new UserGuideFragment3();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out,
                        android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.containerId, userGuideFragment3);
                fragmentTransaction.commit();
            }
        });
        return fragmentView;
    }
}
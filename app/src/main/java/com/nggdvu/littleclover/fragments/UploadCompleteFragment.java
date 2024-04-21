package com.nggdvu.littleclover.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nggdvu.littleclover.R;

public class UploadCompleteFragment extends Fragment {

    Button newCampaignBtn, toHomeBtn;
    ImageView clover;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView =  inflater.inflate(R.layout.fragment_upload_complete, container, false);

        clover = fragmentView.findViewById(R.id.clover);
        newCampaignBtn = fragmentView.findViewById(R.id.newCampaignBtn);
        toHomeBtn = fragmentView.findViewById(R.id.toHomeBtn);

        //Ảnh gif
        Glide.with(this).load(R.drawable.gif_image).into(clover);

        newCampaignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadFragment uploadFragment = new UploadFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom);
                fragmentTransaction.replace(R.id.containerId, uploadFragment);
                fragmentTransaction.commit();
            }
        });

        toHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment("photo", "hashtag", "image", "title", "aiming", "location", "description", "description", "time");
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom);
                fragmentTransaction.replace(R.id.containerId, homeFragment);
                fragmentTransaction.commit();
            }
        });
        return fragmentView;
    }
}
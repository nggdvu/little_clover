package com.nggdvu.littleclover.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.nggdvu.littleclover.R;

public class WelcomeFragment extends AppCompatActivity {

    Button tologinBtn, tosignupBtn;
    ImageView imageVw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome_fragment);

        tologinBtn = findViewById(R.id.tologinBtn);
        tosignupBtn = findViewById(R.id.tosignupBtn);
        imageVw = findViewById(R.id.imageVw);

        //Ảnh gif
        Glide.with(this).load(R.drawable.gif_image).into(imageVw);

        //Đăng nhập
        tologinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeFragment.this, LoginFragment.class);
                startActivity(intent);
            }
        });

        //Đăng ký
        tosignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeFragment.this, SignupFragment.class);
                startActivity(intent);
            }
        });
    }
}

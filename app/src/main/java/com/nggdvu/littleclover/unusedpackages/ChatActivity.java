package com.nggdvu.littleclover.unusedpackages;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.auth.User;
import com.nggdvu.littleclover.R;
import com.nggdvu.littleclover.databinding.ActivityChatBinding;

import java.util.List;

//Chưa hoạt động
public class ChatActivity extends AppCompatActivity {

    //ActivityChatBinding binding;
    //User receiveUser;
    //List<ChatMessage> chatMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        }
    }
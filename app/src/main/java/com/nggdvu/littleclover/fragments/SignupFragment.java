package com.nggdvu.littleclover.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.nggdvu.littleclover.MainActivity;
import com.nggdvu.littleclover.R;

public class SignupFragment extends AppCompatActivity {

    EditText signupEmail, signupPassword;
    Button signupBtn;
    ImageButton backBtn;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup_fragment);

        backBtn = findViewById(R.id.backBtn);
        signupEmail = findViewById(R.id.emailSignup);
        signupPassword = findViewById(R.id.passwordSignup);
        signupBtn = findViewById(R.id.signupBtn);
        auth = FirebaseAuth.getInstance();

        //Đăng ký bằng email và password
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = signupEmail.getText().toString().trim();
                String password = signupPassword.getText().toString().trim();
                if (email.isEmpty()){
                    signupEmail.setError("Vui lòng nhập email");
                }
                if (password.isEmpty()){
                    signupPassword.setError("Vui lòng nhập mật khẩu");
                } else {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(SignupFragment.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignupFragment.this, MainActivity.class));
                            } else {
                                Toast.makeText(SignupFragment.this, "Đăng ký thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
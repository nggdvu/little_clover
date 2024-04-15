package com.nggdvu.littleclover.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.nggdvu.littleclover.MainActivity;
import com.nggdvu.littleclover.R;

import java.util.HashMap;

public class LoginFragment extends AppCompatActivity {

    EditText loginEmail;
    EditText loginPassword;
    Button loginBtn, googleBtn;
    ImageButton backBtn;
    FirebaseAuth mAuth, gAuth;
    FirebaseDatabase db;
    FirebaseUser user;
    SignInClient oneTapClient;
    GoogleSignInClient googleSignInClient;
    LottieAnimationView animationView;
    BeginSignInRequest signInRequest;
    static final int REQ_ONE_TAP = 100;
    int RC_SIGN_IN = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_fragment);

        loginEmail = findViewById(R.id.emailLogin);
        loginPassword = findViewById(R.id.passwordLogin);
        loginBtn = findViewById(R.id.loginBtn);
        backBtn = findViewById(R.id.backBtn);
        googleBtn = findViewById(R.id.googleBtn);
        mAuth = FirebaseAuth.getInstance();
        gAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        backBtn = findViewById(R.id.backBtn);
        oneTapClient = Identity.getSignInClient(this);
        animationView = findViewById(R.id.cloverAnimation);
        animationView.setVisibility(View.GONE);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail().build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();
                animationView.setVisibility(View.VISIBLE);

                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if (!password.isEmpty()) {
                        mAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(LoginFragment.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(LoginFragment.this, MainActivity.class));
                                            finish();
                                        } else {
                                            Toast.makeText(LoginFragment.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else {
                        loginPassword.setError("Vui lòng nhập mật khẩu");
                    }
                } else if (email.isEmpty()) {
                    loginEmail.setError("Vui lòng nhập email");
                } else {
                    loginEmail.setError("Vui lòng nhập đúng địa chỉ email");
                }
            }
        });

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
                animationView.setVisibility(View.VISIBLE);
            }
        });
    }
    private void googleSignIn(){
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(account.getIdToken());
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void firebaseAuth(String idToken){
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        gAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            user = gAuth.getCurrentUser();

                            HashMap<String, Object> map = new HashMap<>();
                            map.put("email",user.getEmail());
                            map.put("name",user.getDisplayName());
                            map.put("avatar",user.getPhotoUrl().toString());

                            db.getReference().child("users").child(user.getUid()).setValue(map);

                            Toast.makeText(LoginFragment.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginFragment.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(LoginFragment.this, "Có lỗi đã xảy ra :(", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
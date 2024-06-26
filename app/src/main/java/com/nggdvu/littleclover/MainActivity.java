package com.nggdvu.littleclover;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.nggdvu.littleclover.fragments.HomeFragment;
import com.nggdvu.littleclover.fragments.MapFragment;
import com.nggdvu.littleclover.fragments.MessageFragment;
import com.nggdvu.littleclover.fragments.UploadFragment;
import com.nggdvu.littleclover.fragments.UserFragment;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNav);
        frameLayout = findViewById(R.id.containerId);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int itemId = menuItem.getItemId();

                if (itemId == R.id.homeBtn) {
                    loadFragment(new HomeFragment("photo", "hashtag", "image", "title", "aiming", "location", "description", "description", "time"), false);

                } else if (itemId == R.id.mapBtn) {
                    loadFragment(new MapFragment(), false);

                } else if (itemId == R.id.uploadBtn) {
                    loadFragment(new UploadFragment(), false);

                } else if (itemId == R.id.messageBtn) {
                    loadFragment(new MessageFragment("name","email", "avatar"), false);

                } else if (itemId == R.id.userBtn) {
                    loadFragment(new UserFragment(), false);

                }
                return true;
            }
        });
        loadFragment(new HomeFragment("photo", "hashtag", "image", "title", "aiming", "location", "description", "description", "time"),true);
    }

    private void loadFragment(Fragment fragment, boolean isAppInitialized){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(isAppInitialized){
            fragmentTransaction.add(R.id.containerId, fragment);
        } else {
            fragmentTransaction.replace(R.id.containerId, fragment);
        }
        fragmentTransaction.replace(R.id.containerId, fragment);
        fragmentTransaction.commit();
    }
}
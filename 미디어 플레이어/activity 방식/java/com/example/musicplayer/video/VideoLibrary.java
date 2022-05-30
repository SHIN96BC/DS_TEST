package com.example.musicplayer.video;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicplayer.BottomNavigationChange;
import com.example.musicplayer.BottomNavigationEvent;
import com.example.musicplayer.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class VideoLibrary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activit_library_video);


    }

    @Override
    protected void onResume() {
        super.onResume();
        // 강제적으로 bottom navigation 을 video 으로 강제로 선택한다.
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationChange());
        bottomNavigationView.setSelectedItemId(R.id.menu_video);

        NavigationBarView navigationBarView = findViewById(R.id.bottom_navigation_view);
        BottomNavigationEvent bottomNavigationEvent = new BottomNavigationEvent(this, R.id.menu_video);
        navigationBarView.setOnItemSelectedListener(bottomNavigationEvent);
    }
}

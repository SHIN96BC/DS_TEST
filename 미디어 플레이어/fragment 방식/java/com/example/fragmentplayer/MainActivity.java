package com.example.fragmentplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationBarView navigationBarView = findViewById(R.id.bottom_navigation_view);
        BottomNavigationEvent bottomNavigationEvent = new BottomNavigationEvent(this);
        navigationBarView.setOnItemSelectedListener(bottomNavigationEvent);

    }
}
package com.example.musicplayer.music;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicplayer.BottomNavigationChange;
import com.example.musicplayer.BottomNavigationEvent;
import com.example.musicplayer.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.File;

public class MusicLibrary extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activit_library_music);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 강제적으로 bottom navigation 을 music 으로 강제로 선택한다.
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationChange());
        bottomNavigationView.setSelectedItemId(R.id.menu_music);

        NavigationBarView navigationBarView = findViewById(R.id.bottom_navigation_view);
        BottomNavigationEvent bottomNavigationEvent = new BottomNavigationEvent(this, R.id.menu_music);
        navigationBarView.setOnItemSelectedListener(bottomNavigationEvent);

        File file = new File("/data/data");
        System.out.println("sbc file = " + file);
        File[] fileList = file.listFiles();
        System.out.println("sbc fileList = " + fileList);

        String path = getFilesDir().getAbsolutePath();
        System.out.println("sbc path = " + path);
    }

}
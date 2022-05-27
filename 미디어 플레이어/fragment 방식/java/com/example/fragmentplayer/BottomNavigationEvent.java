package com.example.fragmentplayer;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fragmentplayer.music.MusicLibrary;
import com.example.fragmentplayer.radio.RadioMenu;
import com.example.fragmentplayer.video.VideoLibrary;
import com.google.android.material.navigation.NavigationBarView;

public class BottomNavigationEvent implements NavigationBarView.OnItemSelectedListener {
    AppCompatActivity activity;

    public BottomNavigationEvent(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_music:
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, new MusicLibrary()).commit();
                return true;
            case R.id.menu_video:
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, new VideoLibrary()).commit();
                return true;
            case R.id.menu_radio:
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, new RadioMenu()).commit();
                return true;
            case R.id.menu_search:

                return true;
            case R.id.menu_more:

        }
        return false;
    }
}

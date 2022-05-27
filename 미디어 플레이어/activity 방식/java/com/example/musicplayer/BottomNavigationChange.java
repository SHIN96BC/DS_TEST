package com.example.musicplayer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationChange implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                item.setIcon(R.drawable.ic_home);
                break;
            case R.id.menu_music:
                item.setIcon(R.drawable.ic_music_library);
                break;
            case R.id.menu_video:
                item.setIcon(R.drawable.ic_video_library);
                break;
            case R.id.menu_search:
                item.setIcon(R.drawable.ic_search);
                break;
            case R.id.menu_more:
                break;
        }
        return true;
    }
}

package com.example.fragmentplayer.music;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.fragmentplayer.R;

public class MusicLibrary extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup mainActivity, Bundle bundle) {
        return inflater.inflate(R.layout.activit_library_music, mainActivity, false);
    }
}
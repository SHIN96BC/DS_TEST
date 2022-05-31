package com.example.musicplayer.music;

import android.os.Bundle;
import android.os.Environment;
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



        // 외부 저장소 절대경로
        // 출력 결과: /storage/sdcard
        String topPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        System.out.println("sbc topPath = " + topPath);

        // 내부 저장소 절대경로(현재 애플리케이션의 경로만 접근이 가능하다)
        // 출력 결과: /data/data/com.example.musicplayer/files
        String path = getFilesDir().getPath();
        System.out.println("sbc path = " + path);

        // 외부 저장소 뮤직폴더 절대경로
        // 출력 결과: /storage/sdcard/Music
        String musicPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getPath();
        System.out.println("sbc musicPath = " + musicPath);

        File f = new File(musicPath);
        if(f.exists()) {
            System.out.println("sbc 존재함");
            if(f.isDirectory()) {
                System.out.println("sbc 디렉토리 이다");
                String[] flist = f.list();
                System.out.println("sbc flist = " + flist);

                File f2 = new File("/storage/sdcard/Music/1");
                if(f2.exists()) {
                    System.out.println("sbc 1 존재함");
                }else {
                    System.out.println("sbc 1 존재하지 않음");
                }

                /*
                for(String f1: flist) {
                    System.out.println("sbc music 파일 안 = " + f1);
                }
                */

            }else {
                System.out.println("sbc 디렉토리가 아니다");
            }

        }else {
            System.out.println("sbc 존재하지 않음");
        }

    }

}
package com.example.musicplayer;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.musicplayer.music.MusicLibrary;
import com.example.musicplayer.radio.RadioMenu;
import com.example.musicplayer.video.VideoLibrary;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class BottomNavigationEvent implements NavigationBarView.OnItemSelectedListener {
    Activity activity;
    int menuId;

    public BottomNavigationEvent(Activity activity, int menuId) {
        this.activity = activity;
        this.menuId = menuId;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        // 이벤트 객체를 호출했을 때 id 값을 저장해놓고 같은 메뉴를 눌렀을 때 아무것도 하지 않는다.
        if(menuId == item.getItemId()) return false;

        switch(item.getItemId()) {
            case R.id.menu_music:
                intent = new Intent(activity, MusicLibrary.class);
                break;
            case R.id.menu_video:
                intent = new Intent(activity, VideoLibrary.class);
                break;
            case R.id.menu_radio:
                intent = new Intent(activity, RadioMenu.class);
                break;
            case R.id.menu_search:

                break;
            case R.id.menu_more:

        }

        if(intent != null) {
//            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);
            // activity 전환 시에 애니메이션을 제거하는 메서드
            activity.overridePendingTransition(0, 0);
            activity.finish();
            return true;
        }
        return false;
    }
}

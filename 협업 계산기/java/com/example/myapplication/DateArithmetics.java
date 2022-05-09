package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class DateArithmetics extends AppCompatActivity {

    private Toolbar mainToolBar;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        // 수정한 부분 시작(shin 2022.05.06)
        // toolbar
        mainToolBar = (Toolbar)findViewById(R.id.main_tool_bar);
        setSupportActionBar(mainToolBar);
// 수정한 부분 끝

// 수정한 부분 시작(shin 2022.05.09)
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mainToolBar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        MenuBarEvent menuBarEvent = new MenuBarEvent(this);
        navigationView.setNavigationItemSelectedListener(menuBarEvent);
// 수정한 부분 끝
    }
}

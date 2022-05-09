package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DateArithmetics extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mainToolBar;
    private ActionBarDrawerToggle drawerToggle;

    // 안드로이드 os 버전 때문에 발생하는 LocalDate 에러를 해결하는 Annotation
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        // 현재 날짜 세팅
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        // 내일 날짜 세팅
        calendar.add(Calendar.DATE, +1);
        Date tomorrow = calendar.getTime();
        // 날짜 표시형식 변경
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        // 날짜 스트링으로 변환
        String startDay = simpleDateFormat.format(today);
        String lastDay = simpleDateFormat.format(tomorrow);
        
        // 처음화면에 날짜를 세팅
        TextView startView = (TextView)findViewById(R.id.startDay);
        startView.setText(startDay);
        TextView lastView = (TextView)findViewById(R.id.lastDay);
        lastView.setText(lastDay);


        // toolbar 와 drawerLayout 세팅
        mainToolBar = (Toolbar)findViewById(R.id.main_tool_bar);
        setSupportActionBar(mainToolBar);

        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mainToolBar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        MenuBarEvent menuBarEvent = new MenuBarEvent(this);
        navigationView.setNavigationItemSelectedListener(menuBarEvent);

        // 클릭 이벤트
        Button btnStart = (Button)findViewById(R.id.btn_date_start);
        Button btnLast = (Button)findViewById(R.id.btn_date_last);
        btnStart.setOnClickListener(this);
        btnLast.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_date_start:
                startActivity(new Intent(this, CalendarView.class));
                break;
            case R.id.btn_date_last:

        }
    }
}

package com.example.myapplication;

import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationView;

public class MenuBarEvent implements NavigationView.OnNavigationItemSelectedListener {
    // 객체를 부모타입으로 받아서 여러 activity에서 사용할 수 있도록 함
    private AppCompatActivity activity;

    public MenuBarEvent(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.btn_arithmetics:
                Toast.makeText(activity, "Arithmetics", Toast.LENGTH_SHORT).show();
                intent = new Intent(activity.getApplicationContext(), Arithmetics.class);
                break;
            case R.id.btn_binary:
                Toast.makeText(activity, "Binary", Toast.LENGTH_SHORT).show();
                intent = new Intent(activity.getApplicationContext(), Arithmetics_Change.class);
                break;
            case R.id.btn_data:
                Toast.makeText(activity, "Data", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_date:
                Toast.makeText(activity, "Date", Toast.LENGTH_SHORT).show();
                intent = new Intent(activity.getApplicationContext(), DateArithmetics.class);
                break;
        }
        activity.startActivity(intent);
        activity.finish();
        return true;
    }
}

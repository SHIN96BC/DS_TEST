package com.example.myapplication;

import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.navigation.NavigationView;

public class MenuBarEvent implements NavigationView.OnNavigationItemSelectedListener {
    private Arithmetics arithmetics;

    public MenuBarEvent(Arithmetics arithmetics) {
        this.arithmetics = arithmetics;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_arithmetics:
                Toast.makeText(arithmetics, "계산기", Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_binary:
                Toast.makeText(arithmetics, "2진법", Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_data:
                Toast.makeText(arithmetics, "데이터", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }
}

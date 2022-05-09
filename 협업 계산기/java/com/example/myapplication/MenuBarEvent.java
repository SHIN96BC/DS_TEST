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
                Toast.makeText(arithmetics, "Arithmetics", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_binary:
                Toast.makeText(arithmetics, "Binary", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_data:
                Toast.makeText(arithmetics, "Data", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}

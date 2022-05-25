package com.example.testb;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ClickEvent implements View.OnClickListener {

    private AppCompatActivity activity;

    public ClickEvent(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch(view.getId()) {
            case R.id.btn_move_sub:
                intent = new Intent(activity, ActivityLifeCycleTestSub.class);
                break;
            case R.id.btn_move_main:
                intent = new Intent(activity, ActivityLifeCycleTestMain.class);
        }

        if(intent != null && activity != null) {
            activity.startActivity(intent);
            activity.finish();
            return;
        }
    }
}

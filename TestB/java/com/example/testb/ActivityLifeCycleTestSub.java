package com.example.testb;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

// Android Life Cycle Test
public class ActivityLifeCycleTestSub extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.lifecycle_sub);
        Log.i("LifeCycleCheck","onCreate() Sub");

        button = (Button)findViewById(R.id.btn_move_main);
        ClickEvent clickEvent = new ClickEvent(this);
        button.setOnClickListener(clickEvent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("LifeCycleCheck","onStart() Sub");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("LifeCycleCheck","onResume() Sub");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("LifeCycleCheck","onPause() Sub");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("LifeCycleCheck","onStop() Sub");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("LifeCycleCheck","onRestart() Sub");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("LifeCycleCheck","onDestroy() Sub");
    }
}

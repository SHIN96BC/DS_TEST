package com.example.testb;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

// Android Life Cycle Test
public class ActivityLifeCycleTestMain extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.lifecycle_main);
        Log.i("LifeCycleCheck","onCreate() Main");

        button = (Button)findViewById(R.id.btn_move_sub);
        ClickEvent clickEvent = new ClickEvent(this);
        button.setOnClickListener(clickEvent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("LifeCycleCheck","onStart() Main");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("LifeCycleCheck","onResume() Main");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("LifeCycleCheck","onPause() Main");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("LifeCycleCheck","onStop() Main");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("LifeCycleCheck","onRestart() Main");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("LifeCycleCheck","onDestroy() Main");
    }
}

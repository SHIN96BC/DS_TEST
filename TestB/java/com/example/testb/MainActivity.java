package com.example.testb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 버튼에 클릭 이벤트 세팅
        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, TestActivity.class);
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(1);
        arr.add(2);
        intent.putExtra("스트링", "벨류값");
        intent.putExtra("기본형", 10);
        intent.putExtra("배열", arr);
        startActivity(intent);


        startActivityForResult(intent, 0);
    }

    // Result 값을 받는 메서드
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode == 0) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    Log.e("Log", "Ok!");
                    break;
                case Activity.RESULT_CANCELED:
                    Log.e("Log", "Canceled");
                    break;
                case Activity.RESULT_FIRST_USER:
                    Log.e("Log", "first user");
            }
        }
    }
}
package com.example.testb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Button btnOk = (Button)findViewById(R.id.btn_ok);
        Button btnCanceled = (Button)findViewById(R.id.btn_canceled);
        Button btnFirstUser = (Button)findViewById(R.id.btn_firstUser);

        btnOk.setOnClickListener(this);
        btnCanceled.setOnClickListener(this);
        btnFirstUser.setOnClickListener(this);


        Intent intent = getIntent();
        String value = intent.getStringExtra("스트링");
        int valueInt = intent.getIntExtra("기본형", -1);
        String[] valueArr = intent.getStringArrayExtra("배열");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                setResult(Activity.RESULT_OK);
                break;
            case R.id.btn_canceled:
                setResult(Activity.RESULT_CANCELED);
                break;
            case R.id.btn_firstUser:
                setResult(Activity.RESULT_FIRST_USER);
        }
        finish();
    }
}
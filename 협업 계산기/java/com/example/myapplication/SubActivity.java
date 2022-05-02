package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class SubActivity extends AppCompatActivity {
    ImageButton backBtn1;
    ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        backBtn1 = findViewById(R.id.backBtn1);

        listView = findViewById(R.id.list_item);
        String[] list = {"Music", "original", "BTS","EXO","Audio"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        adapter.addAll(list);
        listView.setAdapter(adapter);

        backBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListView listView1 = (ListView) adapterView;
                String Click = (String) listView1.getItemAtPosition(i);
                Toast.makeText(SubActivity.this,Click +"을(를) 클릭하셨습니다.",Toast.LENGTH_LONG).show();
            }
        });
    }
}

package com.example.myapplication;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {
    ListView list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstenceState){
        super.onCreate(savedInstenceState);
        setContentView(R.layout.setting);

        list = findViewById(R.id.list);
        String[] item = {"Media Xpander", "EQ Presets", "Fader/Balance/Bass/Treble/SubW.",
                "Graphic EQ", "Time Corection","X-Over"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        adapter.addAll(item);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListView list = (ListView) adapterView;
                String click = (String) list.getItemAtPosition(i);
                Toast.makeText(SettingActivity.this, click +"을(를) 선택하셨습니다.",Toast.LENGTH_LONG).show();
            }
        });

    }

}

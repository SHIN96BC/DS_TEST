package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity{

    private Button addBtn1, minusBtn1;
    private Button textBtn1;
    private ImageButton imgbtn1, settingBtn1;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            /*textBtn1.setText(count+"");*/
            addBtn1 = findViewById(R.id.addBtn1);
            minusBtn1 = findViewById(R.id.minusBtn1);
            textBtn1 = findViewById(R.id.textBtn1);
            imgbtn1 = findViewById(R.id.imgbtn1);
            settingBtn1 = findViewById(R.id.settingBtn1);

            Button button[] = new Button[6];
            Integer[] texbtn={R.id.footBtn1,R.id.footBtn2,R.id.footBtn3,
                    R.id.footBtn4, R.id.footBtn5, R.id.footBtn6};

            Button buttons[] = new Button[6];
            Integer[] numbut={ R.id.numBtn1, R.id.numBtn2, R.id.numBtn3,
                    R.id.numBtn4,R.id.numBtn5,R.id.numBtn6};
            for (int i=0; i<6; i++){
                buttons[i] = findViewById(numbut[i]);
                button[i] = findViewById(texbtn[i]);
            }
           /* numBtn1.setOnClickListener(this);
            numBtn2.setOnClickListener(this);
            numBtn3.setOnClickListener(this);
            numBtn4.setOnClickListener(this);
            numBtn5.setOnClickListener(this);
            numBtn6.setOnClickListener(this);*/

        /*footer Button*/
        for(int i =0; i<button.length; i++) {
            int dex;
            dex = i;
            buttons[dex].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        /*주파수 Button*/
        for(int i =0; i<buttons.length; i++) {
            int INDEX;
            INDEX = i;
            buttons[INDEX].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buttons[INDEX].setSelected(true);
                    textBtn1.setText(buttons[INDEX].getText()+"MHz");
                }
            });


            addBtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int j = INDEX+1;
                    if(j>=7) {
                        j=1;
                    }
                    if(j<7) {
                        switch (j) {
                            case 1:
                                buttons[1].setOnClickListener(this::onClick);
                            case 2:
                                buttons[2].setOnClickListener(this::onClick);
                            case 3:
                                buttons[3].setOnClickListener(this::onClick);
                            case 4:
                                buttons[4].setOnClickListener(this::onClick);
                            case 5:
                                buttons[5].setOnClickListener(this::onClick);
                            case 6:
                                buttons[0].setOnClickListener(this::onClick);
                        }
                    }
                }
                   /* buttons[INDEX+count].setOnClickListener(this);
                    textBtn1.setText(count + "");
                    buttons[count].setOnClickListener(this);*/
            });
        }
            minusBtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(count>0) {
                        count--;
                        textBtn1.setText(count + "");
                    }
                }
            });
            /*버튼 클릭시 SettingActivity로 이동*/
            imgbtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                    startActivity(intent);
                }
            });
            /*버튼 클릭시 SubActivity로 이동*/
            settingBtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), SubActivity.class);
                    startActivity(intent);
                }
            });
            /*numBtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textBtn1.setText(numBtn1.getText());
                }
            });
            numBtn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textBtn1.setText(numBtn2.getText());
                }
            });
            numBtn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textBtn1.setText(numBtn3.getText());
                }
            });
            numBtn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textBtn1.setText(numBtn4.getText());
                }
            });
            numBtn5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textBtn1.setText(numBtn5.getText());
                }
            });
            numBtn6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textBtn1.setText(numBtn6.getText());
                }
            });*/

       /*     textBtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if() {
                        textBtn1.setText("No Data");
                    }
                    if(textBtn1.getText() == "No Data"){
                        textBtn1;
                    }
                }
            });
        }

    /*    @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.numBtn1:
                    textBtn1.setText(numBtn1.getText());
                case R.id.numBtn2:
                    textBtn1.setText(numBtn2.getText());
                case R.id.numBtn3:
                    textBtn1.setText(numBtn3.getText());
                case R.id.numBtn4:
                    textBtn1.setText(numBtn4.getText());
                case R.id.numBtn5:
                    textBtn1.setText(numBtn5.getText());
                case R.id.numBtn6:
                    textBtn1.setText(numBtn6.getText());
            }
        }*/
    }
}
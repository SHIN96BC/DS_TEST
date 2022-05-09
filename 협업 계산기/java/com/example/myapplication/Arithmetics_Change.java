package com.example.myapplication;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.math.BigInteger;
import java.util.ArrayList;

public class Arithmetics_Change extends AppCompatActivity implements View.OnClickListener {
    private ImageView[] result = new ImageView[10];
    private TextView process, arith;
    Button numBtn0, numBtn1, addBtn, subBtn, mulBtn, divBtn, equal, backBtn, rollBackBtn, homeBtn, andBtn, orBtn, xorBtn;
    int count = 0;
    String num1;
    String resultNum = "";

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arithmetics_change);

        arith = findViewById(R.id.arith);
        process = findViewById(R.id.process);
        numBtn0 = findViewById(R.id.numBtn0);
        numBtn1 = findViewById(R.id.numBtn1);
        addBtn = findViewById(R.id.addBtn);
        subBtn = findViewById(R.id.subBtn);
        divBtn = findViewById(R.id.divBtn);
        mulBtn = findViewById(R.id.mulBtn);
        equal = findViewById(R.id.equla);
        backBtn = findViewById(R.id.backBtn);
        rollBackBtn = findViewById(R.id.rollBackBtn);
        homeBtn = findViewById(R.id.homeBtn);
        andBtn = findViewById(R.id.andBtn);
        orBtn = findViewById(R.id.orBtn);
        xorBtn = findViewById(R.id.xorBtn);

        arith.setOnClickListener(this);
        process.setOnClickListener(this);
        numBtn0.setOnClickListener(this);
        numBtn1.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        subBtn.setOnClickListener(this);
        divBtn.setOnClickListener(this);
        mulBtn.setOnClickListener(this);
        equal.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        rollBackBtn.setOnClickListener(this);
        homeBtn.setOnClickListener(this);
        andBtn.setOnClickListener(this);
        orBtn.setOnClickListener(this);
        xorBtn.setOnClickListener(this);

        Integer[] res = {R.id.result0, R.id.result1, R.id.result2,
                R.id.result3, R.id.result4, R.id.result5,
                R.id.result6, R.id.result7, R.id.result8, R.id.result9};

        for (int i = 0; i < result.length; i++) {
            result[i] = findViewById(res[i]);
        }
    }

    // Activity 종료 시 효과 제거
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onClick(View view) {
        String[] num2;
        resultNum = "";
        switch (view.getId()) {
            case R.id.numBtn0:
                if (count < 10) {                                            //10자리까지만 이미지 나열
                    result[count].setImageResource(R.drawable.zero);
                    count++;
                }
                process.append("0");
                break;

            case R.id.numBtn1:
                if (count < 10) {
                    result[count].setImageResource(R.drawable.one);
                    count++;
                }
                process.append("1");
                break;
            case R.id.addBtn:
                num1 = process.getText().toString();
                arith.setText("+");
                process.append("+");
                roll(count);
                break;
            case R.id.subBtn:
                num1 = process.getText().toString();
                arith.setText("-");
                process.append("-");
                roll(count);
                break;
            case R.id.mulBtn:
                num1 = process.getText().toString();
                arith.setText("*");
                process.append("*");
                roll(count);
                break;
            case R.id.divBtn:
                num1 = process.getText().toString();
                arith.setText("/");
                process.append("/");
                roll(count);
                break;
            case R.id.andBtn:
                num1 = process.getText().toString();
                arith.setText("AND");
                process.append("AND");
                roll(count);
                break;
            case R.id.orBtn:
                num1 = process.getText().toString();
                arith.setText("OR");
                process.append("OR");
                roll(count);
                break;
            case R.id.xorBtn:
                num1 = process.getText().toString();
                arith.setText("XOR");
                process.append("XOR");
                roll(count);
                break;
            case R.id.equla:
                int Re = 0;                             //계산 값 저장할 변수
                num2 = process.getText().toString().split("\\+|-|\\*|/|AND|OR|XOR");    //연산자로 문자열을 분할
                String num3 = num2[1];                              //2번째 문자열을 저장
                int binary1 = Integer.parseInt(num1, 2);     //2진수 문자열을 10진수 숫자로 변환
                int binary2 = Integer.parseInt(num3, 2);

                String binaryOne = Integer.toBinaryString(binary1); // 입력된 첫 번째 수를 2진수로 변환
                String binaryTwo = Integer.toBinaryString(binary2); // 입력된 두 번째 수를 2진수로 변환
                int lengthOne = binaryOne.length();                 // 2진수로 변환된 값의 길이 Check
                int lengthTwo = binaryTwo.length();                 // 2진수로 변환된 값의 길이 Check

                if (arith.getText() == "+") {                     //10진수로 바꾼 숫자를 연산자에 따라 계산
                    Re = binary1 + binary2;
                }
                if (arith.getText() == "-") {
                    Re = binary1 - binary2;
                }
                if (arith.getText() == "*") {
                    Re = binary1 * binary2;
                }
                if (arith.getText() == "/") {
                    Re = binary1 / binary2;
                }
                if (arith.getText() == "AND") {
                    Re = binary1 & binary2;
                }
                if (arith.getText() == "OR") {
                    Re = binary1 | binary2;
                }
                if (arith.getText() == "XOR") {
                    Re = binary1 ^ binary2;
                }

                change(Re);                                 //10진수를 2진수로 바꾸는 메소드

                // 계산 결과 값 ImageView 표현(2진수)
                String[] binaryArray = resultNum.split("");

                if (lengthOne >= lengthTwo) {
                    if (binaryArray.length >= lengthOne) {
                        for (count = 0; count <= binaryArray.length; count++) {
                            result[count].setImageResource(0);
                        }
                    } else {
                        for (count = 0; count <= lengthOne; count++) {
                            result[count].setImageResource(0);
                        }
                    }
                } else {
                    if (binaryArray.length >= lengthTwo) {
                        for (count = 0; count <= binaryArray.length; count++) {
                            result[count].setImageResource(0);
                        }
                    } else {
                        for (count = 0; count <= lengthTwo; count++) {
                            result[count].setImageResource(0);
                        }
                    }
                }

                for (count = 0; count < binaryArray.length; count++) {
                    if (binaryArray[count].equals("0")) {
                        result[count].setImageResource(R.drawable.zero);
                    } else {
                        result[count].setImageResource(R.drawable.one);
                    }
                }

                process.setText(resultNum);
                break;
            case R.id.homeBtn:
                Intent intent = new Intent(getApplicationContext(), Arithmetics.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // Activity 전환 시 효과 제거
                startActivity(intent);
                break;
            case R.id.backBtn:
                if (count > 0) {
                    result[count - 1].setImageResource(0);
                    count--;
                }
                int size = process.getText().length();
                if (size >= 1) {
                    process.setText(process.getText().toString().substring(0, size - 1));
                }
                break;
            case R.id.rollBackBtn:
                roll(count);
                process.setText("");
                arith.setText("");
        }
    }

    public void roll(int c) {                        //이미지 초기화
        for (int i = 0; i <= count; i++) {               //for문으로 1-10까지 저장된 이미지 초기화
            if (count > 9) {
                for (int k = 0; k < i; k++)
                    result[k].setImageResource(0);
            } else {
                result[i].setImageResource(0);
            }
        }
        count = 0;
    }

    public void change(int n) {      //10진수를 2진수로 변환
        String num = "";
        int c = 0;
        while (true) {                   //매개변수로 받은 10진수가 0이 될때까지 몫과 나머지를 받아서 2진수로 변환
            num += String.valueOf(n % 2);
            n = n / 2;
            c++;
            if (n <= 0) {
                break;
            }
        }
        for (int i = num.length() - 1; i >= 0; i--) {         //계산된 2진수 문자열을 거꾸로 다시 저장
            resultNum += num.charAt(i);
        }
    }

    public void showToast(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }

    // 화면 회전시 표시
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            showToast("방향: ORIENTATION_LANDSCAPE");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            showToast("방향: ORIENTATION_PORTRAIT");
        }
    }
}
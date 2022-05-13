package com.example.myapplication;

import android.content.DialogInterface;
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
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Arithmetics_Change extends AppCompatActivity implements View.OnClickListener {
    private ImageView[] result = new ImageView[10];
    private TextView process, arith;
    private View iv;
    private Button numBtn0, numBtn1, addBtn, subBtn, mulBtn, divBtn, remainBtn, equal, backBtn, rollBackBtn, homeBtn, andBtn, orBtn, xorBtn, empty;
    private int count = 0;
    private String num1, substr, operator;
    private String resultNum = "";
    // 추가한 부분(shin 2022.05.12)
    private Toolbar mainToolBar;
    private ActionBarDrawerToggle drawerToggle;
// 추가한 부분 끝

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arithmetics_change);

// 수정한 부분 시작(shin 2022.05.12)
        // toolbar
        mainToolBar = (Toolbar)findViewById(R.id.main_tool_bar);
        setSupportActionBar(mainToolBar);
// 수정한 부분 끝

// 수정한 부분 시작(shin 2022.05.12)
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mainToolBar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        MenuBarEvent menuBarEvent = new MenuBarEvent(this);
        navigationView.setNavigationItemSelectedListener(menuBarEvent);
// 수정한 부분 끝

        arith = findViewById(R.id.arith);
        process = findViewById(R.id.process);
        numBtn0 = findViewById(R.id.numBtn0);
        numBtn1 = findViewById(R.id.numBtn1);
        addBtn = findViewById(R.id.addBtn);
        subBtn = findViewById(R.id.subBtn);
        divBtn = findViewById(R.id.divBtn);
        remainBtn = findViewById(R.id.remainBtn);
        mulBtn = findViewById(R.id.mulBtn);
        equal = findViewById(R.id.equla);
        backBtn = findViewById(R.id.backBtn);
        rollBackBtn = findViewById(R.id.rollBackBtn);
        homeBtn = findViewById(R.id.homeBtn);
        andBtn = findViewById(R.id.andBtn);
        orBtn = findViewById(R.id.orBtn);
        xorBtn = findViewById(R.id.xorBtn);
        empty = findViewById(R.id.empty);

        arith.setOnClickListener(this);
        process.setOnClickListener(this);
        numBtn0.setOnClickListener(this);
        numBtn1.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        subBtn.setOnClickListener(this);
        divBtn.setOnClickListener(this);
        remainBtn.setOnClickListener(this);
        mulBtn.setOnClickListener(this);
        equal.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        rollBackBtn.setOnClickListener(this);
        homeBtn.setOnClickListener(this);
        andBtn.setOnClickListener(this);
        orBtn.setOnClickListener(this);
        xorBtn.setOnClickListener(this);
        empty.setOnClickListener(this);

        iv = null;

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
        select(view);
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
                operator = arith.getText().toString();
                substr = num1.substring(num1.length() - 1);
                if (substr.equals("D") || substr.equals("R") || substr.equals("+") || substr.equals("-") || substr.equals("*") || substr.equals("/") || substr.equals("%"))  {
                    AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(Arithmetics_Change.this);
                    myAlertBuilder.setTitle("Alert");
                    myAlertBuilder.setMessage("Enter the Number Behind Operator.");
                    myAlertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // OK 버튼을 눌렸을 경우
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            roll(0);
                            process.setText(num1);
                            num1 = process.getText().toString().replaceAll("\\+|-|\\*|/|AND|OR|XOR|%","");
                            arith.setText(operator);
                            select(empty);
                        }
                    });
                    myAlertBuilder.show();
                    break;
                } else {
                    arith.setText("+");
                    process.append("+");
                    roll(count);
                    break;
                }
            case R.id.subBtn:
                num1 = process.getText().toString();
                operator = arith.getText().toString();
                substr = num1.substring(num1.length() - 1);
                if (substr.equals("D") || substr.equals("R") || substr.equals("+") || substr.equals("-") || substr.equals("*") || substr.equals("/") || substr.equals("%")) {
                    AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(Arithmetics_Change.this);
                    myAlertBuilder.setTitle("Alert");
                    myAlertBuilder.setMessage("Enter the Number Behind Operator.");
                    myAlertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // OK 버튼을 눌렸을 경우
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            roll(0);
                            process.setText(num1);
                            num1 = process.getText().toString().replaceAll("\\+|-|\\*|/|AND|OR|XOR|%","");
                            arith.setText(operator);
                            select(empty);
                        }
                    });
                    myAlertBuilder.show();
                    break;
                } else {
                    arith.setText("-");
                    process.append("-");
                    roll(count);
                    break;
                }

            case R.id.mulBtn:
                num1 = process.getText().toString();
                Log.v("num1", "num1 값 : " + num1);
                operator = arith.getText().toString();
                Log.v("operator", "operator 값 : " + operator);
                substr = num1.substring(num1.length() - 1);
                Log.v("substr", "substr 값 : " + substr);
                if (substr.equals("D") || substr.equals("R") || substr.equals("+") || substr.equals("-") || substr.equals("*") || substr.equals("/") || substr.equals("%")){
                    AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(Arithmetics_Change.this);
                    myAlertBuilder.setTitle("Alert");
                    myAlertBuilder.setMessage("Enter the Number Behind Operator.");
                    myAlertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // OK 버튼을 눌렸을 경우
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            roll(0);
                            process.setText(num1);
                            num1 = process.getText().toString().replaceAll("\\+|-|\\*|/|AND|OR|XOR|%","");
                            arith.setText(operator);
                            select(empty);
                        }
                    });
                    myAlertBuilder.show();
                    break;
                } else {
                    arith.setText("*");
                    process.append("*");
                    roll(count);
                    break;
                }
            case R.id.divBtn:
                num1 = process.getText().toString();
                operator = arith.getText().toString();
                substr = num1.substring(num1.length() - 1);
                if (substr.equals("D") || substr.equals("R") || substr.equals("+") || substr.equals("-") || substr.equals("*") || substr.equals("/") || substr.equals("%")) {
                    AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(Arithmetics_Change.this);
                    myAlertBuilder.setTitle("Alert");
                    myAlertBuilder.setMessage("Enter the Number Behind Operator.");
                    myAlertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // OK 버튼을 눌렸을 경우
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            roll(0);
                            process.setText(num1);
                            num1 = process.getText().toString().replaceAll("\\+|-|\\*|/|AND|OR|XOR|%","");
                            arith.setText(operator);
                            select(empty);
                        }
                    });
                    myAlertBuilder.show();
                    break;
                } else {
                    arith.setText("/");
                    process.append("/");
                    roll(count);
                    break;
                }
            case R.id.remainBtn:
                num1 = process.getText().toString();
                operator = arith.getText().toString();
                substr = num1.substring(num1.length() - 1);
                if (substr.equals("D") || substr.equals("R") || substr.equals("+") || substr.equals("-") || substr.equals("*") || substr.equals("/") || substr.equals("%")) {
                    AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(Arithmetics_Change.this);
                    myAlertBuilder.setTitle("Alert");
                    myAlertBuilder.setMessage("Enter the Number Behind Operator.");
                    myAlertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // OK 버튼을 눌렸을 경우
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            roll(0);
                            process.setText(num1);
                            num1 = process.getText().toString().replaceAll("\\+|-|\\*|/|AND|OR|XOR|%","");
                            arith.setText(operator);
                            select(empty);
                        }
                    });
                    myAlertBuilder.show();
                    break;
                } else {
                    arith.setText("%");
                    process.append("%");
                    roll(count);
                    break;
                }
            case R.id.andBtn:
                num1 = process.getText().toString();
                operator = arith.getText().toString();
                substr = num1.substring(num1.length() - 1);
                if (substr.equals("D") || substr.equals("R") || substr.equals("+") || substr.equals("-") || substr.equals("*") || substr.equals("/") || substr.equals("%")) {
                    AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(Arithmetics_Change.this);
                    myAlertBuilder.setTitle("Alert");
                    myAlertBuilder.setMessage("Enter the Number Behind Operator.");
                    myAlertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // OK 버튼을 눌렸을 경우
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            roll(0);
                            process.setText(num1);
                            num1 = process.getText().toString().replaceAll("\\+|-|\\*|/|AND|OR|XOR|%","");
                            arith.setText(operator);
                            select(empty);
                        }
                    });
                    myAlertBuilder.show();
                    break;
                } else {
                    arith.setText("AND");
                    process.append("AND");
                    roll(count);
                    break;
                }
            case R.id.orBtn:
                num1 = process.getText().toString();
                operator = arith.getText().toString();
                substr = num1.substring(num1.length() - 1);
                if (substr.equals("D") || substr.equals("R") || substr.equals("+") || substr.equals("-") || substr.equals("*") || substr.equals("/") || substr.equals("%")) {
                    AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(Arithmetics_Change.this);
                    myAlertBuilder.setTitle("Alert");
                    myAlertBuilder.setMessage("Enter the Number Behind Operator.");
                    myAlertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // OK 버튼을 눌렸을 경우
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            roll(0);
                            process.setText(num1);
                            num1 = process.getText().toString().replaceAll("\\+|-|\\*|/|AND|OR|XOR|%","");
                            arith.setText(operator);
                            select(empty);
                        }
                    });
                    myAlertBuilder.show();
                    break;
                } else {
                    arith.setText("OR");
                    process.append("OR");
                    roll(count);
                    break;
                }
            case R.id.xorBtn:
                num1 = process.getText().toString();
                operator = arith.getText().toString();
                substr = num1.substring(num1.length() - 1);
                if (substr.equals("D") || substr.equals("R") || substr.equals("+") || substr.equals("-") || substr.equals("*") || substr.equals("/") || substr.equals("%")) {
                    AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(Arithmetics_Change.this);
                    myAlertBuilder.setTitle("Alert");
                    myAlertBuilder.setMessage("Enter the Number Behind Operator.");
                    myAlertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // OK 버튼을 눌렸을 경우
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            roll(0);
                            process.setText(num1);
                            num1 = process.getText().toString().replaceAll("\\+|-|\\*|/|AND|OR|XOR|%","");
                            arith.setText(operator);
                            select(empty);
                        }
                    });
                    myAlertBuilder.show();
                    break;
                } else {
                    arith.setText("XOR");
                    process.append("XOR");
                    roll(count);
                    break;
                }

            case R.id.equla:
                int Re = 0;                             //계산 값 저장할 변수
                num2 = process.getText().toString().split("\\+|-|\\*|/|AND|OR|XOR|%");    //연산자로 문자열을 분할
                Log.v("num2", "num2 : " + Arrays.toString(num2));
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

                    // 2진수 계산 값이 소수값으로 나올 때, 계산 불가 및 Reset 처리
                    if (Re == 0) {
                        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(Arithmetics_Change.this);
                        myAlertBuilder.setTitle("Alert");
                        myAlertBuilder.setMessage("Cannot Calculate Double Data.");
                        myAlertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // OK 버튼을 눌렸을 경우
                                Toast.makeText(getApplicationContext(), "Double", Toast.LENGTH_SHORT).show();
                                roll(0);
                                process.setText("");
                                arith.setText("");
                            }
                        });
                        myAlertBuilder.show();
                    }
                }
                if (arith.getText() == "%") {
                    Re = binary1 % binary2;
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
                // 2진수 계산 값이 음수일 때, 계산 불가 및 Reset 처리
                if (Re < 0) {
                    AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(Arithmetics_Change.this);
                    myAlertBuilder.setTitle("Alert");
                    myAlertBuilder.setMessage("Cannot Calculate Negative Data.");
                    myAlertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // OK 버튼을 눌렸을 경우
                            roll(0);
                            process.setText("");
                            arith.setText("");
                            Toast.makeText(getApplicationContext(), "Negative Number", Toast.LENGTH_SHORT).show();
                        }
                    });
                    myAlertBuilder.show();
                }

                change(Re);                                 //10진수를 2진수로 바꾸는 메소드

                // 계산 결과 값 ImageView 표현(2진수)
                String[] binaryArray = resultNum.split("");

                Log.v("binaryArray","binaryArray : " + Arrays.toString(binaryArray));
                // 배열의 Empty Data 지우는 함수 호출
                String[] resultArray = deleteEmpty(binaryArray);
                Log.v("resultArray","resultArray : " + Arrays.toString(resultArray));

                if (lengthOne >= lengthTwo) {
                    if (resultArray.length >= lengthOne) {
                        for (count = 0; count <= resultArray.length; count++) {
                            result[count].setImageResource(0);
                        }
                    } else {
                        for (count = 0; count <= lengthOne; count++) {
                            result[count].setImageResource(0);
                        }
                    }
                } else {
                    if (resultArray.length >= lengthTwo) {
                        for (count = 0; count <= resultArray.length; count++) {
                            result[count].setImageResource(0);
                        }
                    } else {
                        for (count = 0; count <= lengthTwo; count++) {
                            result[count].setImageResource(0);
                        }
                    }
                }

                for (count = 0; count < resultArray.length; count++) {
                    if (resultArray[count].equals("0")) {
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

    // 10진수를 2진수로 변환
    public void change(int n) {
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

    // 버튼 style 유지 셀렉터
    public void select(View ew){
        if(iv != null){                     //저장된 View가 있을 시
            if(iv.getId() != ew.getId()){   //저장된 View와 받은 View를 비교
                iv.setSelected(false);      //다를시 이전 View를 false로 변환
            }
        }
        ew.setSelected(true);               //받은 View를 true로 변환
        iv = ew;                            //다음 View와 받은 View를 비교하기 위해 다시 저장
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

    // String[]의 Empty Data 삭제
    public static String[] deleteEmpty(final String[] array) {
        List<String> list = new ArrayList<String>(Arrays.asList(array));
        // list에서 Data가 ""인 것을 찾아서 모두 제거
        list.removeAll(Collections.singleton(""));
        return list.toArray(new String[list.size()]);
    }
}
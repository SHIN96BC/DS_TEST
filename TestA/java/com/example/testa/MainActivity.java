package com.example.testa;

import androidx.appcompat.app.AppCompatActivity;
 
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import testa.calculator.event.ClickEvent;
 
public class MainActivity extends AppCompatActivity {
    EditText textView;
    Button btnRemove;
    Button btnRemoveAll;
    Button btn7;
    Button btn8;
    Button btn9;
    Button btnPlus;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btnMinus;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btnMultiply;
    Button btn0;
    Button btnDot;
    Button btnEquals;
    Button btnDivision;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // text
        textView = (EditText)findViewById(R.id.textView);
        // 버튼
        btnRemove = (Button)findViewById(R.id.btnRemove);
        btnRemoveAll = (Button)findViewById(R.id.btnRemoveAll);
        btn7 = (Button)findViewById(R.id.btn7);
        btn8 = (Button)findViewById(R.id.btn8);
        btn9 = (Button)findViewById(R.id.btn9);
        btnPlus = (Button)findViewById(R.id.btnPlus);
        btn4 = (Button)findViewById(R.id.btn4);
        btn5 = (Button)findViewById(R.id.btn5);
        btn6 = (Button)findViewById(R.id.btn6);
        btnMinus = (Button)findViewById(R.id.btnMinus);
        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);
        btnMultiply = (Button)findViewById(R.id.btnMultiply);
        btn0 = (Button)findViewById(R.id.btn0);
        btnDot = (Button)findViewById(R.id.btnDot);
        btnEquals = (Button)findViewById(R.id.btnEquals);
        btnDivision = (Button)findViewById(R.id.btnDivision);

        // 숫자 터치 이벤트
        ClickEvent clickEvent = new ClickEvent(this);
        btnRemove.setOnClickListener(clickEvent);
        btnRemoveAll.setOnClickListener(clickEvent);
        btn7.setOnClickListener(clickEvent);
        btn8.setOnClickListener(clickEvent);
        btn9.setOnClickListener(clickEvent);
        btnPlus.setOnClickListener(clickEvent);
        btn4.setOnClickListener(clickEvent);
        btn5.setOnClickListener(clickEvent);
        btn6.setOnClickListener(clickEvent);
        btnMinus.setOnClickListener(clickEvent);
        btn1.setOnClickListener(clickEvent);
        btn2.setOnClickListener(clickEvent);
        btn3.setOnClickListener(clickEvent);
        btnMultiply.setOnClickListener(clickEvent);
        btn0.setOnClickListener(clickEvent);
        btnDot.setOnClickListener(clickEvent);
        btnEquals.setOnClickListener(clickEvent);
        btnDivision.setOnClickListener(clickEvent);
    }
    public EditText getTextView() {
        return textView;
    }
}
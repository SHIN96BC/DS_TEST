package com.example.ds_calculator;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Arithmetics_Change extends Activity implements View.OnClickListener {
    private final ImageView[] binaryView = new ImageView[10];               // ��� ��� �� Image ǥ��
    private View selectView;                                                // ���õ� ��ư ǥ��
    private TextView process, operator;                                     // ���� ����, ������
    private Button numBtn0, numBtn1, addBtn, subBtn, mulBtn, divBtn, remainBtn, equalBtn, backBtn, clearBtn, homeBtn, andBtn, orBtn, xorBtn, leftShiftBtn, rightShiftBtn;
    private int count = 0;                                                  // Image View �� ���� Count
    private int result = 0;
    public String inputNum = "";                                            // �Է� �� ������ ���� ����
    public String firstNum = "";                                            // ù ��° �Է°�
    public String secondNum = "";                                           // �� ��° �Է°�
    public String firstResultNum = "";                                      // ���� ��� ��� ���� ���� ��� ������ ����
    public String te_firstResultNum = "";                                   // Image View ǥ���� ���� firstResultNum String ���� �ݴ�� �ۼ��� ����
    public String operaTor = "";                                            // ������
    public String resultNum = "";
    public String te_resultNum = "";
    public String firstNumNo = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arithmetics_change);


        operator = findViewById(R.id.operator);
        process = findViewById(R.id.process);
        numBtn0 = findViewById(R.id.numBtn0);
        numBtn1 = findViewById(R.id.numBtn1);
        addBtn = findViewById(R.id.addBtn);
        subBtn = findViewById(R.id.subBtn);
        divBtn = findViewById(R.id.divBtn);
        remainBtn = findViewById(R.id.remainBtn);
        mulBtn = findViewById(R.id.mulBtn);
        equalBtn = findViewById(R.id.equalBtn);
        backBtn = findViewById(R.id.backBtn);
        clearBtn = findViewById(R.id.clearBtn);
        homeBtn = findViewById(R.id.homeBtn);
        andBtn = findViewById(R.id.andBtn);
        orBtn = findViewById(R.id.orBtn);
        xorBtn = findViewById(R.id.xorBtn);
        leftShiftBtn = findViewById(R.id.leftShiftBtn);
        rightShiftBtn = findViewById(R.id.rightShiftBtn);

        operator.setOnClickListener(this);
        process.setOnClickListener(this);
        numBtn0.setOnClickListener(this);
        numBtn1.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        subBtn.setOnClickListener(this);
        divBtn.setOnClickListener(this);
        remainBtn.setOnClickListener(this);
        mulBtn.setOnClickListener(this);
        equalBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);
        homeBtn.setOnClickListener(this);
        andBtn.setOnClickListener(this);
        orBtn.setOnClickListener(this);
        xorBtn.setOnClickListener(this);
        leftShiftBtn.setOnClickListener(this);
        rightShiftBtn.setOnClickListener(this);

        selectView = null;

        Integer[] res = {R.id.result0, R.id.result1, R.id.result2,
                R.id.result3, R.id.result4, R.id.result5,
                R.id.result6, R.id.result7, R.id.result8, R.id.result9};

        for (int i = 0; i < binaryView.length; i++) {
            binaryView[i] = findViewById(res[i]);
        }
    }

    // Activity ���� �� ȿ�� ����
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);            // ���� ��Ÿ���� ȭ���� ���ؾ� �ϴ� Animation, ���� ȭ���� ���ϴ� Animation
    }

    @Override
    public void onClick(View view) {
        clear();
        select(view);
        resultNum = "";
        te_resultNum = "";
        switch (view.getId()) {
            case R.id.numBtn0:
                inputNum = inputNum + "0";                                                  // Button Click ���� Image �ʱ�ȭ ������ ���� �Է� �� ����
                String[] zeroArray = inputNum.split("");
                String[] realZeroArray = deleteEmpty(zeroArray);                            // Array ������ [0] index �� ������ null �� ����
                String[] reverseZeroArray = new String[realZeroArray.length];               // ImageView ǥ���� ���� �迭 Reverse ����

                for (int i = 0; i < realZeroArray.length; i++) {
                    String temp = realZeroArray[i];
                    reverseZeroArray[i] = realZeroArray[realZeroArray.length - i - 1];
                    realZeroArray[i] = temp;
                }
                Log.v("realZeroArray", "realZeroArray : " + Arrays.toString(realZeroArray));
                Log.v("reverseZeroArray", "reverseZeroArray : " + Arrays.toString(reverseZeroArray));
                if (realZeroArray.length > 10) {
                    Toast.makeText(getApplicationContext(), "Excess", Toast.LENGTH_SHORT).show();// �Է� ���� 10�ڸ� �Ѿ ��� Alert
                    clear();
                } else {
                    for (count = 0; count < realZeroArray.length; count++) {
                        if (reverseZeroArray[count].equals("0")) {
                            binaryView[count].setImageResource(R.drawable.zero);
                        } else {
                            binaryView[count].setImageResource(R.drawable.one);
                        }
                    }
                }
                process.append("0");
                break;

            case R.id.numBtn1:
                inputNum = inputNum + "1";
                String[] oneArray = inputNum.split("");
                String[] realOneArray = deleteEmpty(oneArray);
                String[] reverseOneArray = new String[realOneArray.length];
                for (int i = 0; i < realOneArray.length; i++) {
                    String temp = realOneArray[i];
                    reverseOneArray[i] = realOneArray[realOneArray.length - i - 1];
                    realOneArray[i] = temp;
                }
                Log.v("realOneArray", "realOneArray : " + Arrays.toString(realOneArray));
                Log.v("reverseOneArray", "reverseOneArray : " + Arrays.toString(reverseOneArray));
                if (realOneArray.length > 10) {
                    Toast.makeText(getApplicationContext(), "Excess", Toast.LENGTH_SHORT).show();
                    clear();
                } else {
                    for (count = 0; count < realOneArray.length; count++) {
                        if (reverseOneArray[count].equals("0")) {
                            binaryView[count].setImageResource(R.drawable.zero);
                        } else {
                            binaryView[count].setImageResource(R.drawable.one);
                        }
                    }
                }
                process.append("1");
                break;

            case R.id.andBtn:
                inputNum = "";
                firstNum = process.getText().toString();
                if (firstNum.equals("")) {                                                  // ���� ��� ��� ���� ���� ���
                    if (firstResultNum.equals("")) {
                        firstNum = "0";
                        firstNumNo = firstNum + "AND";
                    } else {                                                              // ���� ��� ��� ���� ���� ���
                        firstNum = firstResultNum;
                        firstNumNo = firstResultNum + "AND";
                    }
                    process.setText(firstNumNo);
                } else {
                    process.append("AND");
                }
                operator.setText("AND");
                clear();
                break;

            case R.id.orBtn:
                inputNum = "";
                firstNum = process.getText().toString();
                if (firstNum.equals("")) {
                    if (firstResultNum.equals("")) {
                        firstNum = "0";
                        firstNumNo = firstNum + "OR";
                    } else {
                        firstNum = firstResultNum;
                        firstNumNo = firstResultNum + "OR";
                    }
                    process.setText(firstNumNo);
                } else {
                    process.append("OR");
                }
                operator.setText("OR");
                clear();
                break;

            case R.id.xorBtn:
                inputNum = "";
                firstNum = process.getText().toString();
                if (firstNum.equals("")) {
                    if (firstResultNum.equals("")) {
                        firstNum = "0";
                        firstNumNo = firstNum + "XOR";
                    } else {
                        firstNum = firstResultNum;
                        firstNumNo = firstResultNum + "XOR";
                    }
                    process.setText(firstNumNo);
                } else {
                    process.append("XOR");
                }
                operator.setText("XOR");
                clear();
                break;

            case R.id.addBtn:
                inputNum = "";
                firstNum = process.getText().toString();
                if (firstNum.equals("")) {
                    if (firstResultNum.equals("")) {
                        firstNum = "0";
                        firstNumNo = firstNum + "+";
                    } else {
                        firstNum = firstResultNum;
                        firstNumNo = firstResultNum + "+";
                    }
                    process.setText(firstNumNo);
                } else {
                    process.append("+");
                }
                operator.setText("+");
                clear();
                break;

            case R.id.subBtn:
                inputNum = "";
                firstNum = process.getText().toString();
                if (firstNum.equals("")) {
                    if (firstResultNum.equals("")) {
                        firstNum = "0";
                        firstNumNo = firstNum + "-";
                    } else {
                        firstNum = firstResultNum;
                        firstNumNo = firstResultNum + "-";
                    }
                    process.setText(firstNumNo);
                } else {
                    process.append("-");
                }
                operator.setText("-");
                clear();
                break;

            case R.id.mulBtn:
                inputNum = "";
                firstNum = process.getText().toString();
                if (firstNum.equals("")) {
                    if (firstResultNum.equals("")) {
                        firstNum = "0";
                        firstNumNo = firstNum + "*";
                    } else {
                        firstNum = firstResultNum;
                        firstNumNo = firstResultNum + "*";
                    }
                    process.setText(firstNumNo);
                } else {
                    process.append("*");
                }
                operator.setText("*");
                clear();
                break;

            case R.id.divBtn:
                inputNum = "";
                firstNum = process.getText().toString();
                if (firstNum.equals("")) {
                    if (firstResultNum.equals("")) {
                        firstNum = "0";
                        firstNumNo = firstNum + "/";
                    } else {
                        firstNum = firstResultNum;
                        firstNumNo = firstResultNum + "/";
                    }
                    process.setText(firstNumNo);
                } else {
                    process.append("/");
                }
                operator.setText("/");
                clear();
                break;

            case R.id.remainBtn:
                inputNum = "";
                firstNum = process.getText().toString();
                if (firstNum.equals("")) {
                    if (firstResultNum.equals("")) {
                        firstNum = "0";
                        firstNumNo = firstNum + "%";
                    } else {
                        firstNum = firstResultNum;
                        firstNumNo = firstResultNum + "%";
                    }
                    process.setText(firstNumNo);
                } else {
                    process.append("%");
                }
                operator.setText("%");
                clear();
                break;

            case R.id.leftShiftBtn:
                inputNum = "";
                firstNum = process.getText().toString();
                if (firstNum.equals("")) {
                    if (firstResultNum.equals("")) {
                        firstNum = "0";
                        firstNumNo = firstNum + "<<";
                    } else {
                        firstNum = firstResultNum;
                        firstNumNo = firstResultNum + "<<";
                    }
                    process.setText(firstNumNo);
                } else {
                    process.append("<<");
                }
                operator.setText("<<");
                clear();
                break;

            case R.id.rightShiftBtn:
                inputNum = "";
                firstNum = process.getText().toString();
                if (firstNum.equals("")) {
                    if (firstResultNum.equals("")) {
                        firstNum = "0";
                        firstNumNo = firstNum + ">>";
                    } else {
                        firstNum = firstResultNum;
                        firstNumNo = firstResultNum + ">>";
                    }
                    process.setText(firstNumNo);
                } else {
                    process.append(">>");
                }
                operator.setText(">>");
                clear();
                break;

            case R.id.backBtn:
                inputNum = "";
                int size = process.getText().length();
                if (count > 0) {
                    binaryView[count - 1].setImageResource(0);
                    count--;
                }
                if (size >= 1) {
                    process.setText(process.getText().toString().substring(0, size - 1));
                }
                break;

            case R.id.clearBtn:
                clear();
                inputNum = "";
                firstResultNum = "";
                te_firstResultNum = "";
                process.setText("");
                operator.setText("");
                break;

            case R.id.homeBtn:
                Intent homeIntent = new Intent(getApplicationContext(), Arithmetics.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);     // Activity ��ȯ �� ȿ�� ����
                startActivity(homeIntent);
                break;

            case R.id.equalBtn:
                clear();
                inputNum = "";
                String extraNum = "";
                String[] processArray = process.getText().toString().split(("\\+|-|\\*|/|AND|OR|XOR|%|<<|>>"));
                Log.v("processArray", "processArray = " + Arrays.toString(processArray));
                Log.v("firstNum", "firstNum = " + firstNum);

                if (processArray.length == 1) {
                    if (firstResultNum.equals("")) {
                        secondNum = firstNum;
                        Log.v("secondNum1", "secondNum1 = " + secondNum);
                    } else {
                        secondNum = firstResultNum;
                        Log.v("secondNum2", "secondNum2 = " + secondNum);
                    }
                    Log.v("secondNum3", "secondNum3 = " + secondNum);
                    extraNum = secondNum;
                } else {
                    secondNum = processArray[processArray.length - 1];
                }

                Log.v("secondNum", "secondNum = " + secondNum);

                if (firstResultNum.equals("")) {
                    firstNum = processArray[0];
                } else {
                    firstNum = firstResultNum;
                }

                int firstBinary = Integer.parseInt(firstNum, 2);                               // 2���� ���ڿ��� 10������ ��ȯ
                int secondBinary = Integer.parseInt(secondNum, 2);                             // 2���� ���ڿ��� 10������ ��ȯ
                firstResultNum = "";                                                            // ����Ǿ��ִ� ��� ��� �� �ʱ�ȭ
                operaTor = operator.getText().toString();

                if (operaTor.equals("")) {
                    result = firstBinary;
                }else if(operaTor.equals("+")) {
                    result = firstBinary + secondBinary;
                }else if(operaTor.equals("-")) {
                    result = firstBinary - secondBinary;
                    if (result < 0 && result != 0) {
                        Toast.makeText(getApplicationContext(), "Negative Number", Toast.LENGTH_SHORT).show();
                    }
                }else if(operaTor.equals("*")) {
                    result = firstBinary * secondBinary;
                }else if(operaTor.equals("/")) {
                    result = firstBinary / secondBinary;
                    // 2���� ��� ���� �Ҽ������� ���� ��, ��� �Ұ� �� Reset ó��
                    if (result == 0) {
                        Toast.makeText(getApplicationContext(), "Double", Toast.LENGTH_SHORT).show();
                        clear();
                        process.setText("");
                        operator.setText("");
                        firstResultNum = "";
                    }
                }else if(operaTor.equals("%")) {
                    result = firstBinary % secondBinary;
                }else if(operaTor.equals("AND")) {
                    result = firstBinary & secondBinary;
                }else if(operaTor.equals("OR")) {
                    result = firstBinary | secondBinary;
                }else if(operaTor.equals("XOR")) {
                    result = firstBinary ^ secondBinary;
                }else if(operaTor.equals("<<")) {
                    result = firstBinary << secondBinary;
                }else if(operaTor.equals(">>")) {
                    result = firstBinary >> secondBinary;
                }

                Log.v("result", "result = " + result);
                toBinary(result);                                 //10������ 2������ �ٲٴ� �޼ҵ�
                Log.v("resultNum", "resultNum = " + resultNum);
                Log.v("firstResultNum", "firstResultNum = " + firstResultNum);
                // ��� ��� �� ImageView ǥ��(2����)
                String[] binaryArray = resultNum.split("");

                Log.v("binaryArray", "binaryArray : " + Arrays.toString(binaryArray));
                // �迭�� Empty Data ����� �Լ� ȣ��
                String[] resultArray = deleteEmpty(binaryArray);
                Log.v("resultArray", "resultArray : " + Arrays.toString(resultArray));
                Log.v("resultArrayLength", "resultArrayLength : " + resultArray.length);

                String[] te_binaryArray = te_resultNum.split("");

                Log.v("te_binaryArray", "te_binaryArray : " + Arrays.toString(te_binaryArray));
                // �迭�� Empty Data ����� �Լ� ȣ��
                String[] te_resultArray = deleteEmpty(te_binaryArray);
                Log.v("te_resultArray", "te_resultArray : " + Arrays.toString(te_resultArray));

                // ��� �� 10�ڸ� �Ѿ ��, Reset
                if (resultArray.length > 10) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    clear();
                    process.setText("");
                    operator.setText("");
                    inputNum = "";
                    firstResultNum = "";
                    break;
                }

                for (count = 0; count < resultArray.length; count++) {
                    if (te_resultArray[count].equals("0")) {
                        binaryView[count].setImageResource(R.drawable.zero);
                    } else {
                        binaryView[count].setImageResource(R.drawable.one);
                    }
                }
                String resultProcess = process.getText().toString();
                String resultOperator = resultProcess + extraNum;
                operator.setText(resultOperator);
                process.setText("");
                count = 0;
                inputNum = resultNum;
                break;

        }
    }

    public void clear() {                                        // Image �ʱ�ȭ
        for (int i = 0; i <= 9; i++) {
            binaryView[i].setImageResource(0);
        }
        count = 0;
    }

    public void toBinary(int n) {                                // 10������ 2������ ��ȯ
        String num = "";
        int c = 0;
        do {
            num += String.valueOf(n % 2);
            n = n / 2;
            c++;
        } while (n > 0);
        for (int i = 0; i < num.length(); i++) {
            te_resultNum += num.charAt(i);
            te_firstResultNum += num.charAt(i);
        }
        for (int i = num.length() - 1; i >= 0; i--) {                 // ���� �Ųٷ� ����
            resultNum += num.charAt(i);
            firstResultNum += num.charAt(i);
        }
    }


    public void select(View view) {                                     // ��ư style ���� ������
        if (selectView != null) {                                       //����� View ���� ��
            if (selectView.getId() != view.getId()) {                   //����� View ���� View ��
                selectView.setSelected(false);                          //�ٸ��� ���� View false ��ȯ
            }
        }
        view.setSelected(true);                                         //���� View true ��ȯ
        selectView = view;                                              //���� View ���� View ���ϱ� ���� �ٽ� ����
    }

    public void showToast(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }

    public void onConfigurationChanged(Configuration newConfig) {       // ȭ�� ȸ���� ǥ��
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            showToast("����: ORIENTATION_LANDSCAPE");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            showToast("����: ORIENTATION_PORTRAIT");
        }
    }

    public static String[] deleteEmpty(final String[] array) {              // String[]�� Empty Data ����
        List<String> list = new ArrayList<String>(Arrays.asList(array));
        list.removeAll(Collections.singleton(""));                          // list ���� Data "" ��� ����
        return list.toArray(new String[list.size()]);
    }
}
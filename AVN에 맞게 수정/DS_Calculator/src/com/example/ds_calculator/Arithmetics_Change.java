package com.example.ds_calculator;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.ds_calculator.util.LogTag.SBC_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Arithmetics_Change extends Activity implements View.OnClickListener {
    private final ImageView[] binaryView = new ImageView[10];               // 계산 결과 값 Image 표시
    private View selectView;                                                // 선택된 버튼 표시
    private TextView process, operator;                                     // 연산 과정, 연산자
    private Button numBtn0, numBtn1, addBtn, subBtn, mulBtn, divBtn, remainBtn, equalBtn, backBtn, clearBtn, homeBtn, andBtn, orBtn, xorBtn, leftShiftBtn, rightShiftBtn;
    private int count = 0;                                                  // Image View 를 위한 Count
    private int result = 0;
    public String inputNum = "";                                            // 입력 값 저장을 위한 변수
    public String firstNum = "";                                            // 첫 번째 입력값
    public String secondNum = "";                                           // 두 번째 입력값
    public String firstResultNum = "";                                      // 이전 계산 결과 값이 있을 경우 저장할 변수
    public String te_firstResultNum = "";                                   // Image View 표현을 위해 firstResultNum String 값을 반대로 작성한 변수
    public String operaTor = "";                                            // 연산자
    public String resultNum = "";
    public String te_resultNum = "";
    public String firstNumNo = "";
    private ImageButton mBackBtn;

    protected void onCreate(Bundle savedInstanceState) {
    	ActionBar actionBar = getActionBar();
        actionBar.hide();
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arithmetics_change);
        
        TextView titleText = (TextView)findViewById(R.id.title_bar);
        titleText.setText(R.string.title_bin);
        
        

        operator = (TextView) findViewById(R.id.operator);
        process = (TextView) findViewById(R.id.process);
        numBtn0 = (Button) findViewById(R.id.numBtn0);
        numBtn1 = (Button) findViewById(R.id.numBtn1);
        addBtn = (Button) findViewById(R.id.addBtn);
        subBtn = (Button) findViewById(R.id.subBtn);
        divBtn = (Button) findViewById(R.id.divBtn);
        remainBtn = (Button) findViewById(R.id.remainBtn);
        mulBtn = (Button) findViewById(R.id.mulBtn);
        equalBtn = (Button) findViewById(R.id.equalBtn);
        backBtn = (Button) findViewById(R.id.backBtn);
        clearBtn = (Button) findViewById(R.id.clearBtn);
        homeBtn = (Button) findViewById(R.id.homeBtn);
        andBtn = (Button) findViewById(R.id.andBtn);
        orBtn = (Button) findViewById(R.id.orBtn);
        xorBtn = (Button) findViewById(R.id.xorBtn);
        leftShiftBtn = (Button) findViewById(R.id.leftShiftBtn);
        rightShiftBtn = (Button) findViewById(R.id.rightShiftBtn);
        mBackBtn = (ImageButton) findViewById(R.id.back_btn);
        
     // shin update 스크롤 바 추가
        process.setMovementMethod(new ScrollingMovementMethod());

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
        
        mBackBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Log.d(SBC_TAG, "moveToBack()");
		        finish();
			}
        	
        });

        selectView = null;

        Integer[] res = {R.id.result0, R.id.result1, R.id.result2,
                R.id.result3, R.id.result4, R.id.result5,
                R.id.result6, R.id.result7, R.id.result8, R.id.result9};

        for (int i = 0; i < binaryView.length; i++) {
            binaryView[i] = (ImageView) findViewById(res[i]);
        }
    }

    // Activity 종료 시 효과 제거
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);            // 새로 나타나는 화면이 취해야 하는 Animation, 현재 화면이 취하는 Animation
    }

    @Override
    public void onClick(View view) {
        clear();
        select(view);
        resultNum = "";
        te_resultNum = "";
        switch (view.getId()) {
            case R.id.numBtn0:
                inputNum = inputNum + "0";                                                  // Button Click 마다 Image 초기화 진행을 위한 입력 값 저장
                String[] zeroArray = inputNum.split("");
                String[] realZeroArray = deleteEmpty(zeroArray);                            // Array 생성시 [0] index 에 들어오는 null 값 제거
                String[] reverseZeroArray = new String[realZeroArray.length];               // ImageView 표현을 위해 배열 Reverse 진행

                for (int i = 0; i < realZeroArray.length; i++) {
                    String temp = realZeroArray[i];
                    reverseZeroArray[i] = realZeroArray[realZeroArray.length - i - 1];
                    realZeroArray[i] = temp;
                }
                Log.v("realZeroArray", "realZeroArray : " + Arrays.toString(realZeroArray));
                Log.v("reverseZeroArray", "reverseZeroArray : " + Arrays.toString(reverseZeroArray));
                if (realZeroArray.length > 10) {                                            // 입력 값이 10자리 넘어갈 경우 Alert
                    AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(Arithmetics_Change.this);
                    myAlertBuilder.setTitle("Alert");
                    myAlertBuilder.setMessage("Error.");
                    myAlertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // OK 버튼을 눌렸을 경우
                            clear();
                            process.setText("");
                            operator.setText("");
                            inputNum = "";
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                    clear();
                    process.setText("");
                    operator.setText("");
                    inputNum = "";
                    myAlertBuilder.show();
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
                    AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(Arithmetics_Change.this);
                    myAlertBuilder.setTitle("Alert");
                    myAlertBuilder.setMessage("Error.");
                    myAlertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // OK 버튼을 눌렸을 경우
                            clear();
                            process.setText("");
                            operator.setText("");
                            inputNum = "";
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                    clear();
                    process.setText("");
                    operator.setText("");
                    inputNum = "";
                    myAlertBuilder.show();
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
                if (firstNum.equals("")) {                                                  // 이전 계산 결과 값이 없을 경우
                    if (firstResultNum.equals("")) {
                        firstNum = "0";
                        firstNumNo = firstNum + "AND";
                    } else {                                                              // 이전 계산 결과 값이 있을 경우
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
                finish();
                return;

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

                int firstBinary = Integer.parseInt(firstNum, 2);                               // 2진수 문자열을 10진수로 변환
                int secondBinary = Integer.parseInt(secondNum, 2);                             // 2진수 문자열을 10진수로 변환
                firstResultNum = "";                                                            // 저장되어있는 계산 결과 값 초기화
                operaTor = operator.getText().toString();

                if (operaTor.equals("")) {
                    result = firstBinary;
                }else if(operaTor.equals("+")) {
                    result = firstBinary + secondBinary;
                }else if(operaTor.equals("-")) {
                    result = firstBinary - secondBinary;
                    if (result < 0 && result != 0) {
                        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(Arithmetics_Change.this);
                        myAlertBuilder.setTitle("Alert");
                        myAlertBuilder.setMessage("Cannot Calculate Negative Data.");
                        myAlertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // OK 버튼을 눌렸을 경우
                                clear();
                                process.setText("");
                                operator.setText("");
                                firstResultNum = "";
                                Toast.makeText(getApplicationContext(), "Negative Number", Toast.LENGTH_SHORT).show();
                            }
                        });
                        process.setText("");
                        operator.setText("");
                        firstResultNum = "";
                        myAlertBuilder.show();
                    }
                }else if(operaTor.equals("*")) {
                    result = firstBinary * secondBinary;
                }else if(operaTor.equals("/")) {
                    result = firstBinary / secondBinary;
                 // 2진수 계산 값이 소수값으로 나올 때, 계산 불가 및 Reset 처리
                    if (result == 0) {
                        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(Arithmetics_Change.this);
                        myAlertBuilder.setTitle("Alert");
                        myAlertBuilder.setMessage("Cannot Calculate Double Data.");
                        myAlertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // OK 버튼을 눌렸을 경우
                                Toast.makeText(getApplicationContext(), "Double", Toast.LENGTH_SHORT).show();
                                clear();
                                process.setText("");
                                operator.setText("");
                                firstResultNum = "";
                            }
                        });
                        clear();
                        process.setText("");
                        operator.setText("");
                        firstResultNum = "";
                        myAlertBuilder.show();
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
                toBinary(result);                                 //10진수를 2진수로 바꾸는 메소드
                Log.v("resultNum", "resultNum = " + resultNum);
                Log.v("firstResultNum", "firstResultNum = " + firstResultNum);
                // 계산 결과 값 ImageView 표현(2진수)
                String[] binaryArray = resultNum.split("");

                Log.v("binaryArray", "binaryArray : " + Arrays.toString(binaryArray));
                // 배열의 Empty Data 지우는 함수 호출
                String[] resultArray = deleteEmpty(binaryArray);
                Log.v("resultArray", "resultArray : " + Arrays.toString(resultArray));
                Log.v("resultArrayLength", "resultArrayLength : " + resultArray.length);

                String[] te_binaryArray = te_resultNum.split("");

                Log.v("te_binaryArray", "te_binaryArray : " + Arrays.toString(te_binaryArray));
                // 배열의 Empty Data 지우는 함수 호출
                String[] te_resultArray = deleteEmpty(te_binaryArray);
                Log.v("te_resultArray", "te_resultArray : " + Arrays.toString(te_resultArray));

                if (resultArray.length > 10) {
                    AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(Arithmetics_Change.this);
                    myAlertBuilder.setTitle("Alert");
                    myAlertBuilder.setMessage("Cannot Calculate.");
                    myAlertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // OK 버튼을 눌렸을 경우
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            clear();
                            process.setText("");
                            operator.setText("");
                            inputNum = "";
                            firstResultNum = "";
                        }
                    });
                    clear();
                    process.setText("");
                    operator.setText("");
                    inputNum = "";
                    firstResultNum = "";
                    myAlertBuilder.show();
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

    public void clear() {                                        // Image 초기화
        for (int i = 0; i <= 9; i++) {
            binaryView[i].setImageResource(0);
        }
        count = 0;
    }

    public void toBinary(int n) {                                // 10진수를 2진수로 변환
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
        for (int i = num.length() - 1; i >= 0; i--) {                 // 값을 거꾸로 저장
            resultNum += num.charAt(i);
            firstResultNum += num.charAt(i);
        }
    }


    public void select(View view) {                                     // 버튼 style 유지 셀렉터
        if (selectView != null) {                                       //저장된 View 있을 시
            if (selectView.getId() != view.getId()) {                   //저장된 View 받은 View 비교
                selectView.setSelected(false);                          //다를시 이전 View false 변환
            }
        }
        view.setSelected(true);                                         //받은 View true 변환
        selectView = view;                                              //다음 View 받은 View 비교하기 위해 다시 저장
    }

    public void showToast(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }

    public void onConfigurationChanged(Configuration newConfig) {       // 화면 회전시 표시
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            showToast("방향: ORIENTATION_LANDSCAPE");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            showToast("방향: ORIENTATION_PORTRAIT");
        }
    }

    public static String[] deleteEmpty(final String[] array) {              // String[]의 Empty Data 삭제
        List<String> list = new ArrayList<String>(Arrays.asList(array));
        list.removeAll(Collections.singleton(""));                          // list 내부 Data "" 모두 제거
        return list.toArray(new String[list.size()]);
    }
}
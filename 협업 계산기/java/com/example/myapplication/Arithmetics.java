package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;


public class Arithmetics extends AppCompatActivity implements OnClickListener {  //터치따로
    private View iv;                      //selected를 위해 이전 사용한 View를 저장할 변수
    private EditText result;
    private TextView process, arith;
    String type, mountProcess, oneProcess,twoProcess;                  // 부호, 과정 문자열 , equals 사용시 저장 될 마지막 숫자 문자열
    Double mountNum = 0.0;                            //최초 계산기 때 순서대로 값을 계산 및 저장한 변수
    boolean  equalsort, sqr, bracket;                              // 최초 숫자가 음수일 경우 값을 if를 나누기 위해 사용한 변수
    Button[] button = new Button[10];
    String[] bit = new String[10];              //추가된 과제에서 부호를 용이하게 저장하기 위한 문자열
    int count = 0;                              //숫자와 부호를 순차적으로 저장하기 위해 사용 된  count
    ArrayList<String> numBer = new ArrayList<>();    //부호 없이 오로지 숫자만 저장 될 변수
    private Runnable runnable_up, runnable_down;
    private Handler handler_up, handler_down;
    double sqrnum = 0.0;
    // 추가한 부분(shin 2022.05.12)
    private Toolbar mainToolBar;
    private ActionBarDrawerToggle drawerToggle;
// 추가한 부분 끝

    @Override
    protected void onCreate(@Nullable Bundle saved){        //시작
        super.onCreate(saved);
        setContentView(R.layout.activity_arithmetics);

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

        Button addBtn, subBtn, divBtn, mulBtn, equal, rollBackBtn, comma, backBtn, binary,sort, sqr, bracket, root;

        process = findViewById(R.id.process);
        arith = findViewById(R.id.arith);
        result = findViewById(R.id.result);
        addBtn = findViewById(R.id.addBtn);
        subBtn = findViewById(R.id.subBtn);
        divBtn = findViewById(R.id.divBtn);
        mulBtn = findViewById(R.id.mulBtn);
        equal = findViewById(R.id.equla);
        backBtn = findViewById(R.id.backBtn);
        rollBackBtn = findViewById(R.id.rollBackBtn);
        comma = findViewById(R.id.comma);
        binary = findViewById(R.id.binary);
        sort = findViewById(R.id.sort);
        sqr = findViewById(R.id.sqr);
        bracket = findViewById(R.id.bracket);
        root = findViewById(R.id.root);

        addBtn.setOnClickListener(this);
        subBtn.setOnClickListener(this);
        divBtn.setOnClickListener(this);
        mulBtn.setOnClickListener(this);
        equal.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        rollBackBtn.setOnClickListener(this);
        comma.setOnClickListener(this);
        binary.setOnClickListener(this);
        sort.setOnClickListener(this);
        sqr.setOnClickListener(this);
        bracket.setOnClickListener(this);
        root.setOnClickListener(this);

        type = "";
        mountProcess = "";
        iv = null;
        twoProcess ="";
        for(int i = 0; i <bit.length; i++) {         //부호 초기화
            bit[i] = "";
        }
        // 제 3 클래스로 이벤트 구현
        LongClickEvent longClickEvent = new LongClickEvent(this);
        TouchEvent touchEvent = new TouchEvent(this);
        setHandler(null); // 핸들러 초기 세팅
        Integer[] btn ={R.id.numBtn0, R.id.numBtn1, R.id.numBtn2,
                R.id.numBtn3, R.id.numBtn4, R.id.numBtn5,
                R.id.numBtn6, R.id.numBtn7, R.id.numBtn8, R.id.numBtn9};

        for(int i = 0; i<button.length; i++) {
            button[i] = findViewById(btn[i]);
            button[i].setOnClickListener(this);
            button[i].setOnLongClickListener(longClickEvent);
            button[i].setOnTouchListener(touchEvent);
        }
        backBtn.setOnLongClickListener(longClickEvent);
        backBtn.setOnTouchListener(touchEvent);
    }

// 실험용 소스(shin 2022-05-13)
    List<String> processList = new ArrayList<>();
    @Override
    public void onClick(View view) {
        Button button = (Button)view;
        String buttonStr = button.getText().toString();
        switch(view.getId()) {
            //번호 클릭
            case R.id.numBtn0:
            case R.id.numBtn1:
            case R.id.numBtn2:
            case R.id.numBtn3:
            case R.id.numBtn4:
            case R.id.numBtn5:
            case R.id.numBtn6:
            case R.id.numBtn7:
            case R.id.numBtn8:
            case R.id.numBtn9:
                result.append(buttonStr);
                break;
            //부호
            case R.id.addBtn:
            case R.id.subBtn:
            case R.id.mulBtn:
            case R.id.divBtn:
                setSign(buttonStr);
                break;
            // backButton
            case R.id.backBtn:  //가장 마지막에 적은 문자열 하나 삭제
                remove();
                break;

            //초기화
            case R.id.rollBackBtn:
                removeAll();     //초기화
                break;
            // 연산과정
            case R.id.equla:
                String resultStr = result.getText().toString();
                // 마지막으로 숫자가 입력 되었는지 확인해서 배열에 추가해주고, 입력되지 않았으면 마지막 부호를 지우고 연산한다.
                if(resultStr != null && resultStr.length() != 0) {
                    if(processList.get(processList.size()-1).equals("-")) {
                        // 두번째 전 까지 부호라면 음수라는 의미이다.
                        if(checkSign(processList.get(processList.size()-2),4)) {
                            // 마지막 수가 음수일 경우 처리
                            processList.remove(processList.size()-1);
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append("-");
                            stringBuffer.append(resultStr);
                            resultStr = stringBuffer.toString();
                        }
                    }
                    processList.add(resultStr);
                }else {
                    // 현재 배열에 있는 마지막 값이 부호라면 지워준다.
                    if(checkSign(processList.get(processList.size()-1), 4)) {
                        processList.remove(processList.size()-1);
                    }
                }
                // 마지막 숫자를 넣어서 process 를 세팅한다.
                makeProcessMessage();

                // 연산과정을 거쳐서 한번 더 process 를 세팅한다.
                processList.add("=");
                // 소수점 아래가 있는지 체크
                double CResult = process(process.getText().toString());
                if(checkResultDouble(CResult)) {
                    processList.add(Double.toString(CResult));
                    result.setText(Double.toString(CResult));
                }else {
                    int intResult = (int)CResult;
                    processList.add(Integer.toString(intResult));
                    result.setText(Integer.toString(intResult));
                }
                makeProcessMessage();
                processList.clear();
                break;
            // sort
            case R.id.sort:
                String sortStr = process.getText().toString();
                if(sortStr != null && sortStr.length() != 0) processList = arraySortResultList(sortStr);
                if(processList != null) makeProcessMessage();
        }
    }

    // double 인지 체크하는 메서드
    private boolean checkResultDouble(double result) {
        if((result % 1) == 0) {
            return false;
        }else {
            return true;
        }
    }

    // 입력값이 부호일때 처리하는 메서드
    private void setSign(String sign) {
        String message = result.getText().toString();
        result.setText("");
        // 아무것도 입력값이 없을 때 처리(숫자 없이 부호만 눌렀을 때)
        if(message == null || message.length() == 0) {
            // 이미 process 에 기존 입력값이 있을 때
            if(processList.size() != 0) {
                String processListStr1 = processList.get(processList.size()-1);
                // 배열에 마지막에 들어있는 값을 확인해서 - 이외에 부호일 때 처리
                if(checkSign(processListStr1, 3)) { // number 3 은 - 를 뺀 나머지 부호들인지 검사
                    if(sign.equals("-")) {
                        // 입력받은 부호가 - 라면 음수가 존재하기 때문에 허용한다.
                        result.setText("");
                        result.append(sign);
                        processList.add(sign);
                    }else {
                        result.setText("");
                        // 그외에 부호들이라면 배열의 마지막 부호를 바꿔준다.
                        processList.remove(processList.size()-1);
                        processList.add(sign);
                    }
                }else if(processListStr1.equals("-")) {
                    // 첫번째 부호가 - 일 경우 음수를 나타내는 - 일 수도 있기 때문에 2번째 인덱스 까지 검사합니다.
                    // 배열의 크기가 1보다 큰지 먼저 검사한다.(마지막에서 하나 전 인덱스를 검사해야하는데 크기가 1이면 에러가 발생하기 때문입니다)
                    if(processList.size() > 1) {
                        // 배열의 마지막 부호가 - 일때는 하나 전 인덱스도 부호인지 확인해야한다.(음수가 존재하기 때문이다)
                        String processListStr2 = processList.get(processList.size() - 2);
                        // 마지막 부호가 - 일 때 마지막에서 하나 전 인덱스의 값이 부호인지 검사한다.
                        if(checkSign(processListStr2, 4)) {  // number 4 는 모든 부호 검사
                            if(sign.equals("-")) {
                                processList.remove(processList.size() - 1);
                            } else {
                                result.setText("");
                                // 그외에 부호들이라면 배열의 마지막 부호를 바꿔준다.
                                processList.remove(processList.size() - 1);
                                processList.remove(processList.size() - 1);
                                processList.add(sign);
                            }
                        } else {
                            // 마지막 인덱스에서 하나전의 값이 부호가 아닐 경우 입력받은 - 는 음수를 나타내므로 -를 추가해서 입력한다.
                            if(sign.equals("-")) {
                                processList.add(sign);
                            }else {
                                result.setText("");
                                // list 의 마지막 부호를 바꿔준다.
                                processList.remove(processList.size() - 1);
                                processList.add(sign);
                            }
                        }
                    }else {
                        // 배열이 1보다 작거나 같을 때 처리
                        if (sign.equals("+")) {
                            result.setText("");
                            // 눌린 버튼이 + 라면 음수에서 양수로 변경 된 것이므로 - 부호를 제거한다.
                            processList.remove(processList.size() - 1);
                        } else if (sign.equals("-")) {
                            result.setText("");
                        } else {
                            Toast.makeText(Arithmetics.this, "Null NUMBER", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                }else {
                    // 부호가 아닐 때 처리
                    processList.add(sign);
                }
            }else {
                // process 에 기존 입력값이 없을 때
                if(sign.equals("-")) {
                    processList.add(sign);
                    result.append(sign);
                }else {
                    Toast.makeText(Arithmetics.this, "Null NUMBER", Toast.LENGTH_LONG).show();
                    return;
                } 
            }
        }else {
            // 숫자가 있을 때 음수인지 아닌지 체크
            if(processList.size() != 0) {
                String processListStr1 = processList.get(processList.size() - 1);
                if(processListStr1.equals("-")) {
                    // 맨 처음 - 인지 검사
                    if(processList.size() > 1) {
                        String processListStr2 = processList.get(processList.size() - 2);
                        if(checkSign(processListStr2, 4)) { // number 4 는 모든 부호 검사
                            // 만약 마지막에서 하나 전의 값이 부호라면 입력받은 숫자가 음수라는 의미이므로 숫자에 -를 더해 준다.
                            processList.remove(processList.size() - 1);
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append("-");
                            stringBuffer.append(message);
                            processList.add(stringBuffer.toString());
                            processList.add(sign);
                        }else {
                            // 만약 마지막에서 하나 전의 값이 부호가 아니라면 마이너스라는 의미이기 때문에 숫자만 입력한다.
                            processList.add(message);
                            processList.add(sign);
                        }
                    }else {
                        // 만약 맨 처음 입력된 - 라면 음수 이므로 - 를 붙여서 숫자를 배열에 저장한다.
                        processList.remove(processList.size() - 1);
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("-");
                        stringBuffer.append(message);
                        processList.add(stringBuffer.toString());
                        processList.add(sign);
                    }
                }else {
                    processList.add(message);
                    processList.add(sign);
                }
            }else {
                processList.add(message);
                processList.add(sign);
            }
        }
        // process 새로 세팅
        makeProcessMessage();

    }
    
    // 배열에 있는 값으로 process 의 내용을 작성하는 메서드
    private void makeProcessMessage() {
        StringBuffer stringBuffer = new StringBuffer();
        process.setText("");
        for(String processStr: processList) {
            stringBuffer.append(processStr);
            stringBuffer.append(" ");
        }
        String processStr = stringBuffer.toString();
        processStr = processStr.trim();
        process.setText(processStr);
        System.out.println("processStr = " + processStr);
    }

    // 모두 지우기
    private void removeAll() {
        String message = result.getText().toString();
        // 값이 있을 때는 result 만 비우고 값이 없을 때는 process 까지 초기화
        if(message != null && message.length() != 0) {
            result.setText("");
        }else {
            resetAll();
        }
    }

    // 하나 지우기
    private void remove() {
        String message = result.getText().toString();
        if(message != null && message.length() != 0) {
            result.setText(message.substring(0, message.length()-1));
        }else {
            // result 에 아무것도 없지만 process 에는 값이 있을 때
            if(processList.size() != 0) {
                String processListStr1 = processList.get(processList.size()-1);
                // 배열에 마지막 값이 부호이면 부호만 지워주고 숫자일 경우에는 초기화 시킨다.
                if(checkSign(processListStr1, 4)) { // number 4 는 모든 부호 검사
                    processList.remove(processList.size()-1);
                    makeProcessMessage();
                }else {
                    resetAll();
                }
            }else {
                // process 도 비어 있다면 모두 초기화
                resetAll();
            }
        }
    }
    
    // 배열과 result, process 를 초기화하는 메서드
    private void resetAll() {
        result.setText("");
        process.setText("");
        processList.clear();
    }

    // 부호인지 체크하는 메서드
    private boolean checkSign(String sign, int number) {
        if(number == 4) {
            if (sign.equals("+") || sign.equals("-") || sign.equals("*") || sign.equals("/")) {
                return true;
            }else {
                return false;
            }
        }else if(number == 3) {
            if (sign.equals("+") || sign.equals("*") || sign.equals("/")) {
                return true;
            }else {
                return false;
            }
        }else if(number == 2) {
            if (sign.equals("*") || sign.equals("/")) {
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    // sort 메서드(2022-05-16 제작중)
    // string 을 받아서 값을 처리하고 List 로 반환
    private List<String> arraySortResultList(String str) {
        if(str == null || str.length() == 0) return null;
        int index = 0;
        // 맨앞에 숫자가 음수가 될 수도 있으므로 맨 앞에 공백을 추가해서 음수일때 - 를 넣을 수 있도록 한자리를 확보한다.
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(" ");
        stringBuffer.append(str);

        // split 메서드를 이용해서 공백 기준으로 잘라서 배열로 만든다.
        String[] tempStrArr = stringBuffer.toString().split(" ");
        // 곱셈 나눗셈 먼저 정렬해주는 메서드 만들어서 return 타입을 Map 으로 주고 곱셈 나눗셈이 끝나는 인덱스랑 배열을 넣어서 반환
        // 그 다음 플러스, 마이너스 정렬 시작점 index 에 반환받은 인덱스를 대입한다.
        Map<Integer, String[]> map = mulAndDivSort(tempStrArr);
        Set<Integer> keys = map.keySet();
        for(int key: keys) {
            index = key;
            tempStrArr = map.get(key);
        }

        // 일단 한번 섞어준다.
        tempStrArr = arraySort(index, tempStrArr);
        for(String str1: tempStrArr) {
            System.out.println("tempStrArr1 = " + str1);
        }

        // 부호가 빠진곳은 없는지 체크
        int stop = 0;
        while(checkSignAll(tempStrArr)) {
            // 혹시 뭔가 문제가 생겼을 때 무한루프가 되는 것을 방지한다.(10번까지만 루프)
            if(stop > 10) {
                setErrorMessage();
                return null;
            }
            tempStrArr = changeListIntoStringArray(signCheckStrArray(tempStrArr));
            for(String str1: tempStrArr) {
                System.out.println("tempStrArr 부호 체크 후1 = " + str1);
            }
            stop++;
        }

        // 배열에 첫번째 값이 최대값과 같은지 체크해서 다르다면 한번 더 arraySort를 실행 시켜준다.
        System.out.println("tempStrArr[0] = " + tempStrArr[0]);
        if(!maxNumberStringArray(index, tempStrArr).equals(tempStrArr[0])) {
            // 2022-05-17 일단 문제점 해결 했는데 부호체크 메서드를 너무 많이 사용하는 문제를 해결할 수 없는지 생각해보자
            System.out.println("Sort 한번 더 실행");

            // Sort
            tempStrArr = arraySort(index, tempStrArr);
            for(String str1: tempStrArr) {
                System.out.println("tempStrArr 부호 체크 후(한번더 실행) = " + str1);
            }

            // Sort 하고나서 부호 빠진곳이 없는지 확인
            stop = 0;
            while(checkSignAll(tempStrArr)) {
                // 혹시 뭔가 문제가 생겼을 때 무한루프가 되는 것을 방지한다.(10번까지만 루프)
                if(stop > 10) {
                    setErrorMessage();
                    return null;
                }
                tempStrArr = changeListIntoStringArray(signCheckStrArray(tempStrArr));
                for (String str1 : tempStrArr) {
                    System.out.println("tempStrArr 부호 체크 후1 = " + str1);
                }
                stop++;
            }
        }

        return changeStringArrayIntoList(tempStrArr);
    }

    // 곱셈 나눗셈 먼저 정렬해주는 메서드
    private Map<Integer, String[]> mulAndDivSort(String[] strArray) {
        Deque<String> deque = new ArrayDeque<>();
        int index = 0;
        for(int i = 0; i < strArray.length; i++) {
            // i 번째가 * 이나 / 일때 처리
            if(checkSign(strArray[i], 2)) {
                // 먼저 어디까지 곱셈 나눗셈이 이어져 있는지 확인한다.
                index = i;
                for(int j = i+2; i < strArray.length; j+=2) {
                    if(!checkSign(strArray[j], 2)) break;
                    index = j;
                }
                // * 또는 / 일 경우 먼저 왼쪽 숫자 앞에 부호를 확인하고 - 라면 음수로 바꿔준다.
                if(strArray[i-2].equals("+")) {
                    strArray[i-2] = "";
                }else if(strArray[i-2].equals("-")) {
                    strArray[i-2] = "";
                    strArray[i-1] = "-" + strArray[i-1];
                }

                // / 는 위치가 바뀌면 안된다. 그러므로 * 일때만 위치를 바꿔준다.
                if(strArray[i].equals("*")) {
                    // 오른쪽 숫자가 더 크면 두 숫자의 인덱스를 바꿔준다
                    for(int j = i; j <= index; j+=2) {
                        // 맨 왼쪽 값을 기준으로 두고 오른쪽이 클 때 서로 바꿔준다.
                        if(stringComparison(strArray[i-1], strArray[j+1])) {
                            String strTemp = strArray[i-1];
                            strArray[i-1] = strArray[j+1];
                            strArray[j+1] = strTemp;
                        }
                    }
                }

                // 이제 * / 보다 앞에있는 연산과 위치를 바꿔줘야 하는데
                // * / 보다 앞에 + - 가 몇개나 있을 지 모르는게 문제다.
                //해결: 어차피 음수처리는 되었으니 그냥 곱셈끼리 붙을 때 부호 없으면 + 붙여주면 될거같다.
                //      Deque 을 사용해서 맨 앞에 숫자가 크면 앞으로 붙이고, 작으면 뒤로 붙이면 될거같다.
                if(deque.size() == 0) {
                    for(int j = i-1; j <= index+1; j++) {
                        deque.add(strArray[j]);
                        strArray[j] = "";
                    }
                }else if(stringComparison(deque.peekFirst(), strArray[i-1])){
                    deque.addFirst("+");
                    for(int j = index+1; j >= i-1; j--) {
                        deque.addFirst(strArray[j]);
                        strArray[j] = "";
                    }
                }else {
                    deque.add("+");
                    for(int j = i-1; j <= index+1; j++) {
                        deque.add(strArray[j]);
                        strArray[j] = "";
                    }
                }
                // index 까지는 작업이 끝났으므로 i를 index로 바꿔준다.
                i = index+1;
            }
        }

        // 이제 * / 이 정리된 Deque 과 배열에 남은 + - 값들을 합쳐준다.
        List<String> resultList = new ArrayList<>();
        while(deque.size() != 0) {
            resultList.add(deque.pollFirst());
            System.out.println("resultList.get(resultList.size()-1) = " + resultList.get(resultList.size()-1));
        }
        // 곱셈이 끝나는 인덱스 값
        int mapIndex = resultList.size()+1;
        for(String str: strArray) {
            if(!str.equals("") && !str.equals(" ") && str != null && str.length() != 0) resultList.add(str);
        }

        // 2022-05-18 NullPointerException 발생 List 안에 null 값이 들어가있고, *, / 부호들이 사라짐
        // 실험용 출력
        for(String str: resultList) {
            System.out.println("str = " + str);
        }

        Map<Integer, String[]> map = new HashMap<>();
        map.put(mapIndex, changeListIntoStringArray(resultList));
        return map;
    }

    // 배열을 정렬해주는 메서드
    private String[] arraySort(int index, String[] tempStrArr) {
        System.out.println("index = " + index + ", tempStrArr = " + Arrays.deepToString(tempStrArr));
        for(int i = index; i < tempStrArr.length; i++) {
            // 부호면 continue
            if(checkSign(tempStrArr[i], 4)) continue;
            if(tempStrArr[i].equals(" ")) continue;
            if(tempStrArr[i].equals("=")) break;
            for(int j = i+1; j < tempStrArr.length; j++) {
                // 부호면 continue
                if(checkSign(tempStrArr[j], 4)) continue;
                if(tempStrArr[j].equals(" ")) continue;
                if(tempStrArr[j].equals("=")) break;
                if(stringComparison(tempStrArr[i], tempStrArr[j])) {
                    // 숫자 바꾸기
                    String tempStr = "";
                    // 숫자 왼쪽에 부호도 같이 이동
                    // i 가 0이면 맨 앞에 부호가 없다는 의미이다.
                    if(i == 0) {
                        if(tempStrArr[j-1].equals("-")) {
                            // 바꿔야하는 숫자 앞에 부호가 - 이고 숫자가 음수가 아니라면 - 를 추가해서 이동한다.
                            if(!checkMinus(tempStrArr[j])) {
                                tempStr = tempStrArr[i];
                                tempStrArr[i] = "-" + tempStrArr[j];
                                tempStrArr[j] = tempStr;
                                continue;
                            }
                        }
                        tempStr = tempStrArr[i];
                        tempStrArr[i] = tempStrArr[j];
                        tempStrArr[j] = tempStr;

                        // 남아있는 부호 제거
                        if(checkSign(tempStrArr[j-1], 4)) tempStrArr[j-1] = "";

                    }else {
                        tempStr = tempStrArr[i];
                        tempStrArr[i] = tempStrArr[j];
                        tempStrArr[j] = tempStr;

                        tempStr = tempStrArr[i-1];
                        tempStrArr[i-1] = tempStrArr[j-1];
                        tempStrArr[j-1] = tempStr;
                    }
                }
            }
        }
        return tempStrArr;
    }

    // 배열에서 가장 큰 값을 찾는 메서드(+ - 에서만)
    private String maxNumberStringArray(int index, String[] strArray) {
        double maxNumber = 0.0;
        for(int i= index; i < strArray.length; i++) {
            if(checkSign(strArray[i], 4)) continue;
            if(strArray[i].equals("=")) break;
            try {
                double tempNumber = Double.parseDouble(strArray[i]);
                if(tempNumber > maxNumber) {
                    maxNumber = tempNumber;
                }
            }catch(NumberFormatException nfe) {
            }
        }
        System.out.println("maxNumber = " + Double.toString(maxNumber));
        return Double.toString(maxNumber);
    }

    // 부호가 다 있는지 체크하는 메서드
    private boolean checkSignAll(String[] strArray) {
        int signNumber = 0;
        int numberNumber = 0;

        for(String str: strArray) {
            if(str == null || str.length() == 0 || str.equals(" ")) {
                continue;
            }else if(checkSign(str, 4)) {
                signNumber++;
            }else if(str.equals("=")) {
                break;
            }else {
                numberNumber++;
            }
        }
        System.out.println("numberNumber = " + numberNumber);
        System.out.println("signNumber = " + signNumber);

        if(checkSign(strArray[0], 4)) {
            // 배열 맨 앞에 부호가 - 라면 부호가 숫자와 갯수가 같아야 전부 정상적으로 존재한다는 의미이다.
            return (numberNumber != signNumber)? true:false;
        }else {
            // 숫자 갯수보다 부호가 한개 적으면 부호가 전부 정상적으로 존재한다는 의미이다.
            return (numberNumber-1 != signNumber)? true:false;
        }
    }

    // 작업에 문제가 생겼을 때 에러 메세지를 보여주는 메서드
    private void setErrorMessage() {
        result.setText("");
        process.setText("Error");
        resetAll();
    }

    // 마지막으로 부호가 빠진곳은 없는지, 첫번째 숫자가 음수라면 - 랑 숫자를 붙여주는  메서드
    private List<String> signCheckStrArray(String[] strArray) {
        ArrayList<String> arrayList = new ArrayList<>();

        for(int i = 0; i < strArray.length; i++) {
            // 공백이 있다면 continue
            if(strArray[i].equals(" ") || strArray[i].length()==0) continue;
            // 첫번째 인덱스가 - 라면 음수라는 것이므로 그 다음 인덱스와 합쳐서 저장하고, i를 2로 바꾼다.(합친 숫자는 건너뛴다)
            if(i == 0) {
                if(strArray[i].equals("-")) {
                    arrayList.add(strArray[i] + strArray[i+1]);
                    i += 1;
                    continue;
                }else if(checkSign(strArray[i], 4)) {
                    continue;
                }else {
                    arrayList.add(strArray[i]);
                    continue;
                }
            }
            // 등호라면 마지막이므로 break
            if(strArray[i].equals("=")) {
                arrayList.add(strArray[i]);
                arrayList.add(strArray[i+1]);
                break;
            }
            // 부호가 빠진 곳이 있는지 체크

            if(checkSign(strArray[i], 4)) {
                arrayList.add(strArray[i]);
            }else {
                if(arrayList.size() > 0){
                    // 배열의 i 번째가 숫자라면 List의 마지막 인덱스를 확인해서 부호가 아니라면 + 를 추가해준다.
                    if(checkSign(arrayList.get(arrayList.size()-1), 4)) {
                        // 만약 List의 마지막 인덱스가 - 라면
                        if(arrayList.get(arrayList.size()-1).equals("-")) {
                            // 숫자가 음수인지 확인해서 음수가 아니면 - 를 붙여서 저장한다.
                            if(!checkMinus(strArray[i])) {
                                arrayList.remove(arrayList.size()-1);
                                arrayList.add("-"+strArray[i]);
                                i += 1;
                                continue;
                            }
                        }
                        arrayList.add(strArray[i]);
                    }else {
                        arrayList.add("+");
                        arrayList.add(strArray[i]);
                    }
                }else {
                    arrayList.add(strArray[i]);
                }
            }
        }

        return arrayList;
    }

    // 음수인지 체크하는 메서드
    private boolean checkMinus(String str) {
        try {
            double strD = Integer.parseInt(str);
            if(strD < 0) {
                return true;
            }
        }catch (NumberFormatException nfe) {
        }
        return false;
    }

    // string 으로 받아서 숫자 크기 비교하는 메서드(오른쪽 파라미터로 들어온 값이 더 크면 true)
    private boolean stringComparison(String left, String right) {
        try {
            double leftD = Double.parseDouble(left);
            double rightD = Double.parseDouble(right);
            System.out.println("leftD = " + leftD + ", rightD = " + rightD);
            if(rightD > leftD) {
                return true;
            }
        }catch(NumberFormatException nfe) {
        }
        return false;
    }

    // String 배열을 List 로 바꾸는 메서드
    private List<String> changeStringArrayIntoList(String[] stringArr) {
        List<String> resultArr = new ArrayList<>();
        for(String str: stringArr) {
            if(str.equals(" ")) continue;
            resultArr.add(str);
        }
        return  resultArr;
    }

    // List 를 String 배열로 바꾸는 메서드
    private String[] changeListIntoStringArray(List<String> list) {
        String[] strArray = new String[list.size()];
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).equals(" ")) continue;
            strArray[i] = list.get(i);
        }
        return strArray;
    }


    // # git hub 에서 stack 연산 코드 추가 #
    public static double num1;
    public static double num2;
    public static double resultNumber;

    //사용자의 input을 각각 구분하여 ArrayList에 저장하는 메소드
    private ArrayList splitTokens(String equation) {
        String[] inputData = equation.split(" "); //공백을 기준

        ArrayList inputList = new ArrayList();
        double number = 0;

        boolean flag = false;
        for (String data : inputData) {
            if (data.equals(" ")) {
                continue;
            }
            if (checkNumber(data)) {
                number = number * 10 + Double.parseDouble(data);
                flag = true;
            } else {
                if (flag) {
                    inputList.add(number);
                    number = 0;
                }
                flag = false;
                inputList.add(data);
            }
        }

        if (flag) {
            inputList.add(number);
        }

        return inputList;
    }

    //후위 표기식으로 변형
    private ArrayList infixToPostfix(ArrayList inputData) {
        ArrayList result = new ArrayList();
        HashMap level = new HashMap();
        Stack stack = new Stack();

        //각 기호의 우선순위 레벨. 곱하기, 나누기 > 더하기, 빼기 > 기타
        level.put("*", 3);
        level.put("/", 3);
        level.put("+", 2);
        level.put("-", 2);
        level.put("(", 1);

        System.out.println("inputData = " + inputData);

        for (Object object : inputData) {
            if (object.equals("(")) {
                stack.push(object);
            } else if (object.equals(")")) {
                while (!stack.peek().equals("(")) {
                    Object val = stack.pop();
                    if (!val.equals("(")) {
                        result.add(val);
                    }
                }
                stack.pop();
            } else if (level.containsKey(object)) {
                System.out.println("object = " + object);
                if (stack.isEmpty()) {
                    stack.push(object);
                } else {
                    // 여기서 level 을 나타내는 숫자들로 비교를 하다보니, + 와 - 부호의 순서가 맞지않는 현상이 발생했다.
                    // 해결: - 부호와 + 부호를 비교할때 >= 는 비교식 때문에 -가 배열로 들어가버려서 생기는 문제였다. 비교식을 > 로 변경했다.
                    // 문제: 비교식을 > 로 수정하면 * 와 / 를 비교할때 배열에 남아있게되는 문제가 발생한다.
                    // 해결: if 문을 사용해서 * / 일때와 + - 일때의 처리를 다르게 해준다.
                    System.out.println("stack.peek() = " + stack.peek());
                    if(checkSign(stack.peek().toString(), 2)) {
                        if (Double.parseDouble(level.get(stack.peek()).toString()) >= Double.parseDouble(level.get(object).toString())) {
                            result.add(stack.pop());
                        }
                    }else{
                        if (Double.parseDouble(level.get(stack.peek()).toString()) > Double.parseDouble(level.get(object).toString())) {
                            result.add(stack.pop());
                        }
                    }
                    stack.push(object);
                }
            } else {
                result.add(object);
            }
        }

        // 실험용 출력
        System.out.println("stack = " + stack);

        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }

        return result;
    }

    //후위 표기식을 계산
    private Double postFixEval(ArrayList expr) {
        // 실험용 출력
        for(Object e: expr) {
            System.out.println("e: " + e.toString());
        }
        
        //(shin) stack 하나로 모든 계산을 수행했더니 +, - 가 뒤에서부터 계산되어서 결과값이 맞지않는 문제가 있어서 Deque 과 stack 을 사용한 방법으로 수정
        Stack plusMinusStack = new Stack();
        Deque numberStack = new ArrayDeque();
        for (Object o : expr) {
            if (o instanceof Double) {
                // Deque 은 push 와 pop 을 하게 되면 맨 앞에서부터 넣고 빼고 하므로 addLast 나 add 메서드를 사용해야한다.
                numberStack.addLast(o);
            } else if (o.equals("+") || o.equals("-")) {
                plusMinusStack.push(o);
            } else if (o.equals("*")) {
                num1 = (Double) numberStack.pollLast();
                num2 = (Double) numberStack.pollLast();
                numberStack.addLast(num2 * num1);
                System.out.println(num2 + "*" + num1 + "="  + (num2 * num1));
            } else if (o.equals("/")) {
                num1 = (Double) numberStack.pollLast();
                num2 = (Double) numberStack.pollLast();
                numberStack.addLast(num2 / num1);
                System.out.println(num2 + "/" + num1 + "="  + (num2 / num1));
            }
        }
        System.out.println("numberStack1 = " + numberStack);
        System.out.println("plusMinusStack = " + plusMinusStack);

        // 스텍은 뒤에서부터 꺼내고, 숫자는 앞에서 부터 꺼낸다.(+ - 는 왼쪽부터 계산)
        while (plusMinusStack.size() != 0) {
            Object o = plusMinusStack.pop();
            if (o.equals("+")) {
                num1 = (Double) numberStack.pop();
                num2 = (Double) numberStack.pop();
                numberStack.push(num1 + num2);
                System.out.println(num1 + "+" + num2 + "="  + (num1 + num2));
            } else if (o.equals("-")) {
                num1 = (Double) numberStack.pop();
                num2 = (Double) numberStack.pop();
                numberStack.push(num1 - num2);
                System.out.println(num1 + "-" + num2 + "="  + (num1 - num2));
            }
        }

        resultNumber = (Double) numberStack.pop();

        return resultNumber;
    }

    public Double process(String equation) {
        ArrayList postfix = infixToPostfix(splitTokens(equation));
        Double result = postFixEval(postfix);
        return result;
    }

    public boolean checkNumber(String str) {
        char check;

        if (str.equals(""))
            return false;

        for (int i = 0; i < str.length(); i++) {
            check = str.charAt(i);
            if (check < 48 || check > 58) {
                if (check != '.'){
                    // 음수 구분용 - 인지 체크
                    if(check == '-' && str.length() > 1) {
                        return true;
                    }
                    return false;
                }
            }
        }
        return true;
    }

// 실험용 소스 끝

/* 기존 소스 시작
    @Override
    public void onClick(View v) {                                                               //버튼 어떤거 클릭 하냐에 따라 다른 결과
        double num;             //EditText에 적은 값을 저장하여 부호 버튼 클릭시 calculator()메소드로 값을 넘길 변수
        select(v);              //누른 버튼은 selected = true가 되고 이전 버튼은 false로 만듭니다.
        switch (v.getId()){
            //번호 클릭
            case R.id.numBtn0:
                result.append("0");break;
            case R.id.numBtn1:
                result.append("1");break;
            case R.id.numBtn2:
                result.append("2");break;
            case R.id.numBtn3:
                result.append("3");break;
            case R.id.numBtn4:
                result.append("4");break;
            case R.id.numBtn5:
                result.append("5");break;
            case R.id.numBtn6:
                result.append("6");break;
            case R.id.numBtn7:
                result.append("7");break;
            case R.id.numBtn8:
                result.append("8");break;
            case R.id.numBtn9:
                result.append("9");break;

            //부호
            case R.id.addBtn:
                if(process.getText().toString().equals("") || result.getText().toString().equals("")){
                    Toast.makeText(Arithmetics.this,"Null NUMBER",Toast.LENGTH_LONG).show();
                    return;
                }
                resultNot();                                                 // 결과값 있는 상태로 추가 계산 시
                num = Double.parseDouble(result.getText().toString());      //EditText에 있는 값을 저장
                calculator("+", num); //계산                              //해당 부호와 num값을 calculator()에 넘깁니다.
                break;

            case R.id.subBtn:
                int size = process.getText().length()-1;
                String pro = process.getText().toString().substring(size);
                if(pro.equals("*") || pro.equals("/")){
                    arith.setText("-");
                    process.append("-");
                    result.setText("-");
                    return;
                }
                resultNot();
                num = Double.parseDouble(result.getText().toString());
                calculator("-", num); //계산
                break;

            case R.id.mulBtn:
                if(process.getText().toString().equals("") || result.getText().toString().equals("")){
                    Toast.makeText(Arithmetics.this,"Null NUMBER",Toast.LENGTH_LONG).show();
                    return;
                }
                resultNot();
                num = Double.parseDouble(result.getText().toString());
                calculator("*", num);
                break;

            case R.id.divBtn:
                if(process.getText().toString().equals("") || result.getText().toString().equals("")){
                    Toast.makeText(Arithmetics.this,"Null NUMBER",Toast.LENGTH_LONG).show();
                    return;
                }
                resultNot();
                num = Double.parseDouble(result.getText().toString());
                calculator("/", num);
                break;

            case R.id.sqr:
                if(!sqr){
                    sqrnum = Double.parseDouble(result.getText().toString());
                    sqr = true;
                }
                double sqrnum1 = Double.parseDouble(result.getText().toString());
                double sqrnum2 = sqrnum1 * sqrnum;
                result.setText(String.valueOf(sqrnum2));
                double sqrnum3 = Double.parseDouble(result.getText().toString());
                int i = process.getText().toString().length()-1;
                String sqrstr = process.getText().toString().substring(i);
                if (sqrnum3 > 0) {
                    if(!sqrstr.equals("*") || !sqrstr.equals("/")) {
                        bit[count] = "+";
                        arith.setText("+");
                        process.setText(String.format("%s+", process.getText().toString().substring(0, i)));
                    }
                } else {
                    bit[count] = "-";
                    arith.setText("-");
                    process.setText(String.format("%s-", process.getText().toString().substring(0, i)));
                }
                break;

            case R.id.root:
                double rtnum3 = Double.parseDouble(result.getText().toString());            //받은값
                String rtstr = String.valueOf(rtnum3).replace("-","");      //마이너스 부호 제거
                double rtnum4 = Math.sqrt(Double.parseDouble(rtstr));                       //루트
                String rootResult = String.valueOf(Math.floor(rtnum4*100)/100).replace(".0","");    //소수점 2자리까지만 표시
                if(rtnum3<0){                                                               //받은 값이 음수냐 양수냐
                    Toast.makeText(Arithmetics.this,"No such number exists.",Toast.LENGTH_LONG).show();
//                    result.setText(String.format("-%s", rootResult));
                }else{
                    result.setText(rootResult);
                }
                break;
            case R.id.bracket:
                if(!bracket){
                    result.append("(");
                    process.append("(");
                    bracket = true;
                }else{
                    result.append(")");
                    process.append(")");
                    bracket = false;
                }
                break;
            //콤마
            case R.id.comma:
                result.append(".");
                process.append(".");
                break;

            //equla
            case R.id.equla:
                equals();       //계산 결과
                break;

            // backButton
            case R.id.backBtn:  //가장 마지막에 적은 문자열 하나 삭제
                back();
                break;

            //초기화
            case R.id.rollBackBtn:
                rollBack();     //초기화
                break;

            case R.id.sort:
                sort();     //sort함수 대신 사용할 정렬기능
                break;
            case R.id.binary:        //2진수 액티비티로 전환
                Intent intent = new Intent(getApplicationContext(), Arithmetics_Change.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // Activity 전환 시 효과 제거
                startActivity(intent);
        }
    }

    // 결과값을 받고 난 뒤에 추가로 계산할 시 결과값만 받고 기존에 저장된 배열 및 List 초기화
    public void resultNot(){
        if (process.getText().toString().contains("=")) {
            numBer.clear();
            count = 1;
            Arrays.fill(bit, "");
            mountProcess = "";
            mountNum = 0.0;
            equalsort = false;
            process.setText(result.getText().toString());
        }
    }

    //계산 과정
    public void calculator(String col, Double v) {
        if(!result.getText().toString().equals("0")){                                 // 첫수로 인해 숫자 0을 받을시 문제가 생기기에 일단 if로 빼놓음
            numBer.add(String.valueOf(1*v));
            String doubleStr = String.valueOf(v).replace(".0","");
            String minusnull = doubleStr.replace("-","");
            process.append(minusnull + col);
        }else{
            if(col.equals("*") || col.equals("/")){                 //첫수 = 0 일때 곱하기 나누기 불가
                return;
            }
            process.setText(col);
        }
        sqr = false;
        bit[count] = col;                           //부호 배열로 저장 sort나 equals
        count++;
        arith.setText(col);
        if(col.equals("-")){
            result.setText("-");
        }else{
            result.setText("");
        }
    }

    // 계산 완료( = )
    public void equals(){
        if(arith.getText().toString().equals("")){
            return;
        }
        if(!equalsort){
            numBer.add(result.getText().toString());
        }
        double lastResult = 0.0;
        String lastResultStr = "";
        for(int i = 0; i<numBer.size(); i ++){
            if(bit[i].equals("*")){
                double test1 = Double.parseDouble(numBer.get(i-1));
                double test2 = Double.parseDouble(numBer.get(i));
                lastResult += test1 * test2;
            }
            if(bit[i].equals("/")){
                double test1 = Double.parseDouble(numBer.get(i-1));
                double test2 = Double.parseDouble(numBer.get(i));
                lastResult += test1 / test2;
            }
            if(bit[i] == "-"){
                twoProcess += numBer.get(i);
            }else{
                twoProcess += bit[i] + numBer.get(i);
            }
        }
        ArrayList<String> subNum = new ArrayList<>();
        subNum.addAll(numBer);
        for(int i = subNum.size()-1; i>=0; i--){
            if(bit[i].equals("*") || bit[i].equals("/")) {
                subNum.remove(i);
                subNum.remove(i-1);
            }
        }
        for(int i =0; i<subNum.size(); i++){
            lastResult += Double.parseDouble(subNum.get(i));
        }
        if(equalsort){
            twoProcess = process.getText().toString();
        }
//        lastResult = Math.floor(rtnum4*100)/100).replace(".0","");
        lastResultStr = String.valueOf(lastResult).replace(".0","");
        process.setText(twoProcess.replaceAll(".0","")+ "=" + lastResultStr);
        result.setText(lastResultStr);
        arith.setText("");
    }


    //한칸지우기
    public void back(){
        int size = result.getText().length();
        int size1 = process.getText().length();
        if (size >=1) {
            result.setText(result.getText().toString().substring(0, size - 1));
        }
        if(size1 > 1){
            String getProcess = process.getText().toString().substring(size1-1);
            if (getProcess.equals("+") || getProcess.equals("-")|| getProcess.equals("*")|| getProcess.equals("/")) {
                if(numBer.size() == count){
                    numBer.remove(count-1);
                }
                bit[count - 1] = "";
                count--;
                equalsort= true;
            }
            process.setText(process.getText().toString().substring(0, size1 - 1));
        }
        if(size1 == 1){
            process.setText(process.getText().toString().substring(0, size1 - 1));
        }
        if(process.getText().toString().equals("")){
            rollBack();
        }
    }

    //초기화
    public void rollBack() {
        bracket = false;
        mountNum = 0.0;
        arith.setText("");
        process.setText("0");
        result.setText("0");
        type = "";
        mountProcess = "";
        oneProcess = "";
        twoProcess ="";
        equalsort = false;
        count = 0;
        numBer.clear();
        Arrays.fill(bit, "");
    }

    // 버튼 style 유지 셀렉터
    public void select(View ew){
        if(iv != null){                     //저장된 View가 있을 시
            if(iv.getId() != ew.getId()){   //저장된 View와 받은 View를 비교
                iv.setSelected(false);      //다를시 이전 View를 false로 변환
            }
        }
        ew.setSelected(true);               //받은 View를 ture로 변환
        iv = ew;                            //다음 View와 받은 View를 비교하기 위해 다시 저장
    }

    //숫자 및 타입 배열화 및 정렬                  //여기는 아직 *,/ 우선순위가 구현이 안되어 있습니다.
    public void sort(){
        String[] abc = new String[8];
        String[] str = new String[8];
        double[] b = new double[8];
        String strResult = new String();
        ArrayList<String> bitstr = new ArrayList<>();
        ArrayList<Double> bitb = new ArrayList<>();
        if(!equalsort){                    //이미 결과값을 받은 상태인지 아니면 과정인지 확인
            numBer.add(result.getText().toString());
        }
        for (String s : bit) {
            if (!s.equals("")) {
                bitstr.add(s);
            }
        }
        for(int i=0; i<numBer.size(); i++){                          //숫자와 부호 합칩니다.
            String test;
            test = numBer.get(i);
            b[i] = Double.parseDouble(test);
            bitb.add(b[i]);
        }

        double[] d = new double[8];
        double[] move = new double[8];
        String[] moveStr = new String[8];
        int c = 0;
        for(int i = bitstr.size(); i>=0; i--){      // 곱하기와 나누기 계산 정렬
            if(bit[i].equals("*")){
                if(b[i-1]<b[i]){
                    Double change = b[i];
                    b[i] = b[i-1];
                    b[i-1] = change;
                }
                d[c] = b[i-1];
                if(b[i-1]<0){
                    abc[c] = b[i-1] +"*"+ b[i];
                }else{
                    abc[c] = "+"+ b[i-1] +"*"+ b[i];
                }
                bitstr.remove(i);
                bitstr.remove(i-1);
                bitb.remove(i);
                bitb.remove(i-1);
                c++;
            }
            if(bit[i].equals("/")) {
                if (b[i - 1] < b[i]) {
                    double change = b[i];
                    b[i] = b[i - 1];
                    b[i - 1] = change;
                }
                d[c] = b[i-1];
                if(b[i-1]<0){
                    abc[c] = b[i-1] +"/"+ b[i];
                }else{
                    abc[c] = "+"+ b[i-1] +"/"+ b[i];
                }
                c++;
            }
        }

        for(int i = 0; i< c; i++){                                  //배열로 주면 빈칸이 문제고 ArraysList로 주면 정렬이 문제
            for(int k = 0; k< c; k++){
                if(d[i] != 0.0 || d[k] != 0.0){
                    if(d[i]>d[k]) {
                        double change = d[i];
                        String ab = abc[i];
                        d[i] = d[k];
                        d[k] = change;
                        abc[i] = abc[k];
                        abc[k] = ab;
                    }
                }
            }
        }
        int bcount = 0;
        for(int i =0; i<bitstr.size(); i++){
            move[bcount] = bitb.get(i);
            moveStr[bcount] = bitstr.get(i);
            bcount++;
        }
        for(int i = 0; i<bcount; i++){                         //숫자 크기 내림순 저장
            for(int k = 0; k<bcount; k++) {
                if(move[i] != 0.0 || move[k] != 0.0){
                    if(move[i]>move[k]) {
                        double change = move[i];
                        String change2 = moveStr[i];
                        move[i] = move[k];
                        move[k] = change;
                        moveStr[i] = moveStr[k];
                        moveStr[k] = change2;
                    }
                }
            }
        }
        for(int i =0; i<c; i++){
            str[i] = abc[i];
            strResult += str[i].replaceAll(".0", "");
        }
        for(int i = 0; i<bcount; i++){                        //타입과 숫자 순차적으로 저장
            if(move[i]<0){
                str[c+i] = String.valueOf(move[i]);
            }else{
                str[c+i] = moveStr[i] + move[i];
            }
            strResult += str[c+i].replace(".0", "");
        }
        equalsort = true;      //sort를 사용하면 과정 값이 고정되게 한다. >>sort 누르고 equal 누르면 순서가 다시 돌아가는 문제점 해결하기 위함
        if(process.getText().toString().contains("=")){
            process.setText(String.format("%s=%s", strResult, result.getText().toString()));
            return;
        }
        process.setText(strResult);
    }
*/ //기존 소스 끝

    // 핸들러 세팅
    public void setHandler(Button button) {
        handler_up = new Handler();
        handler_down = new Handler();
        runnable_up = new Runnable() {
            @Override
            public void run() {
                String st = null;
                if(button != null) {
                    st = (String) button.getText();
                }
                if(st != null) {
                    st = st.trim();
                    if(st.length() != 0) {
                        result.append(st);
                        process.append(st);
                    }
                }
                handler_up.postDelayed(this, 100);
            }
        };
        runnable_down = new Runnable() {
            @Override                                                                                   //back버튼과 같은 코드를 사용
            public void run() {                                                                         //버튼을 길게 누를시 0.1초 딜레이로 마지막 숫자 계속 감소
                int size = result.getText().length();
                int size1 = process.getText().length();
                if (size >= 1) {
                    result.setText(result.getText().toString().substring(0, size - 1));
                }
                if(size1 >=1){
                    process.setText(process.getText().toString().substring(0, size1 - 1));
                }
                handler_down.postDelayed(this,100);
            }
        };
    }

    // getter
    public Handler getHandler_up() {
        return handler_up;
    }
    public Handler getHandler_down() {
        return handler_down;
    }
    public Runnable getRunnable_up() {
        return runnable_up;
    }
    public Runnable getRunnable_down() {
        return runnable_down;
    }
}
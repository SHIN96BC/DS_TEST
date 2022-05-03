package com.example.myapplication;

import android.annotation.SuppressLint;     //이 어노테이션은 공부해봐야 할것같습니다. TouchListener의 경고블록을 제거해주는 역할을 해줬습니다.
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class Arithmetics extends AppCompatActivity implements OnClickListener {
    private View iv;                      //selected를 위해 이전 사용한 View를 저장할 변수
    private EditText result;
    private TextView process, arith;
    private Button addBtn, subBtn, divBtn, mulBtn, equal, rollBackBtn, comma, backBtn, touchUp,touchDown, changeBtn,sort;
    String type, mountProcess, oneProcess;                  // 부호, 과정 문자열 , equals 사용시 저장 될 마지막 숫자 문자열
    Double mountNum = 0.0;                            //최초 계산기 때 순서대로 값을 계산 및 저장한 변수
    boolean  minus;                               // 최초 숫자가 음수일 경우 값을 if를 나누기 위해 사용한 변수
    Button[] button = new Button[10];
    String[] bit = new String[10];              //추가된 과제에서 부호를 용이하게 저장하기 위한 문자열
    int count = 1;                              //숫자와 부호를 순차적으로 저장하기 위해 사용 된  count
    ArrayList<String> a = new ArrayList<>();    //부호 없이 오로지 숫자만 저장 될 변수
    
    // 수정한 부분 시작
    private Runnable runnable_up, runnable_down;
    private Handler handler_up, handler_down;
    //수정한 부분 끝
    
    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(@Nullable Bundle saved){        //시작
        super.onCreate(saved);
        setContentView(R.layout.activity_arithmetics);

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
        touchUp = findViewById(R.id.touchUp);
        touchDown = findViewById(R.id.touchDown);
        changeBtn = findViewById(R.id.changeBtn);
        sort = findViewById(R.id.sort);

        /*
        Integer[] btn ={R.id.numBtn0, R.id.numBtn1, R.id.numBtn2,
                R.id.numBtn3, R.id.numBtn4, R.id.numBtn5,
                R.id.numBtn6, R.id.numBtn7, R.id.numBtn8, R.id.numBtn9};

        for(int i = 0; i<button.length; i++) {
            button[i] = findViewById(btn[i]);
            button[i].setOnClickListener(this);
            button[i].setOnLongClickListener();
        }
        */

        process.setOnClickListener(this);
        arith.setOnClickListener(this);
        result.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        subBtn.setOnClickListener(this);
        divBtn.setOnClickListener(this);
        mulBtn.setOnClickListener(this);
        equal.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        rollBackBtn.setOnClickListener(this);
        comma.setOnClickListener(this);
        changeBtn.setOnClickListener(this);
        sort.setOnClickListener(this);
        type = "";
        mountProcess = "";
        iv = null;
        for(int i = 0; i <bit.length; i++){         //부호 초기화 
            bit[i] = "";
        }
        // 수정한 부분 시작
        LongClickEvent longClickEvent = new LongClickEvent(this);
        touchUp.setOnLongClickListener(longClickEvent);
        touchDown.setOnLongClickListener(longClickEvent);
        TouchEvent touchEvent = new TouchEvent(this);
        touchUp.setOnTouchListener(touchEvent);
        touchDown.setOnTouchListener(touchEvent);

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




        //수정한 부분 끝

        /*
        touchUp.setOnLongClickListener(new View.OnLongClickListener() {                             //버튼 LongClick
            @Override
            public boolean onLongClick(View v) {
                    handler_up.post(runnable_up);                                                   //handler_up을 통해 runable_up 실행
                Toast.makeText(Arithmetics.this,"LongClick",Toast.LENGTH_LONG).show();
             return false;
            }
        });

        touchUp.setOnTouchListener(new View.OnTouchListener() {                                     //OnTouchListener
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    Toast.makeText(Arithmetics.this,"down",Toast.LENGTH_LONG).show();
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){                               //handler_up이 runnable_up을 removeCallbacks 함
                    Toast.makeText(Arithmetics.this,"up",Toast.LENGTH_LONG).show();
                    handler_up.removeCallbacks(runnable_up);
                }
                return false;
            }
        });

        touchDown.setOnLongClickListener(new View.OnLongClickListener() {                            //버튼 LongClick
            @Override
            public boolean onLongClick(View v) {
                handler_down.post(runnable_down);
                Toast.makeText(Arithmetics.this,"LongClick",Toast.LENGTH_LONG).show();
                return false;
            }
        });


        touchDown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    Toast.makeText(Arithmetics.this,"down",Toast.LENGTH_LONG).show();
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){                               //handler_down이 runnable_down을 removeCallbacks 함
                    Toast.makeText(Arithmetics.this,"up",Toast.LENGTH_LONG).show();
                    handler_down.removeCallbacks(runnable_down);
                }
                return false;
            }
        });

         */
    }
    /*
    final Handler handler_up = new Handler();
    final Runnable runnable_up = new Runnable() {
        @Override
        public void run() {
                String st = result.getText().toString().substring(result.length()-1);               //버튼을 길게 누를시 0.1초 딜레이로 마지막 숫자 계속 추가
                result.append(st);
                process.append(st);
                handler_up.postDelayed(this,100);
        }
    };
    final Handler handler_down = new Handler();
    final Runnable runnable_down = new Runnable() {
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
    */
        @Override
        public void onClick(View v) {                                                               //버튼 어떤거 클릭 하냐에 따라 다른 결과

            double num;             //EditText에 적은 값을 저장하여 부호 버튼 클릭시 calculator()메소드로 값을 넘길 변수
            select(v);              //누른 버튼은 selected = true가 되고 이전 버튼은 false로 만듭니다.
            switch (v.getId()){
                //번호 클릭
                case R.id.numBtn0:
                    result.append("0");
                    process.append("0");break;
                case R.id.numBtn1:
                    result.append("1");
                    process.append("1");break;
                case R.id.numBtn2:
                    result.append("2");
                    process.append("2");break;
                case R.id.numBtn3:
                    result.append("3");
                    process.append("3");break;
                case R.id.numBtn4:
                    result.append("4");
                    process.append("4");break;
                case R.id.numBtn5:
                    result.append("5");
                    process.append("5");break;
                case R.id.numBtn6:
                    result.append("6");
                    process.append("6");break;
                case R.id.numBtn7:
                    result.append("7");
                    process.append("7");break;
                case R.id.numBtn8:
                    result.append("8");
                    process.append("8");break;
                case R.id.numBtn9:
                    result.append("9");
                    process.append("9");break;
                
                //부호
                case R.id.addBtn:
                    if(process.getText().toString().equals("")){
                        Toast.makeText(Arithmetics.this,"NOT NUMBER",Toast.LENGTH_LONG).show();
                        return;
                    }
                    resultNot();                                                 // 결과값 있는 상태로 추가 계산 시
                    num = Double.parseDouble(result.getText().toString());      //EditText에 있는 값을 저장
                    calculator("+", num); //계산                              //해당 부호와 num값을 calculator()에 넘깁니다.
                    break;

                case R.id.subBtn:
                    if(process.getText().toString().equals("")){  // 만약 첫 수가 음수일시 각 TextView에 표시하고 부호 등을 저장합니다.
                        process.append("-");
                        result.setText("-");
                        arith.setText("-");
                        type ="-";
                        minus = true;
                        bit[0] ="-";
                        return;
                    }
                    resultNot();
                    num = Double.parseDouble(result.getText().toString());
                    calculator("-", num); //계산
                    break;

                case R.id.mulBtn:
                    if(process.getText().toString().equals("")){
                        Toast.makeText(Arithmetics.this,"NOT NUMBER",Toast.LENGTH_LONG).show();
                        return;
                    }
                    resultNot();
                    num = Double.parseDouble(result.getText().toString());
                    calculator("*", num);
                    break;

                case R.id.divBtn:
                    if(process.getText().toString().equals("")){
                        Toast.makeText(Arithmetics.this,"NOT NUMBER",Toast.LENGTH_LONG).show();
                        return;
                    }
                    resultNot();
                    num = Double.parseDouble(result.getText().toString());
                    calculator("/", num);
                    break;


                //콤마
                case R.id.comma:
                    result.append(".");
                    process.append(".");
                    break;
                
                //equla 
                case R.id.equla:
                    equals(type);       //계산 결과
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
                case R.id.changeBtn:        //2진수 액티비티로 전환
                    Intent intent = new Intent(getApplicationContext(), Arithmetics_Change.class);
                    startActivity(intent);
            }
        }

    // 결과값을 받고 난 뒤에 추가로 계산할 시
        public void resultNot(){
            if (process.getText().toString().contains("=")) {
                process.setText(result.getText().toString());
            }
        }

    //계산 과정
    public void calculator(String col, Double v) {
            if(arith.getText().toString().equals("")){  //첫 수가 -가 아닐 경우
                mountNum += Double.parseDouble(process.getText().toString());       //먼저계산
                a.add(String.valueOf(mountNum));                                    //숫자 저장
                mountProcess += v + col;                                            //과정 String
                bit[0] = "+";
                arith.setText(col);
                process.setText(process.getText() + col);
                result.setText("");
                type = col;
               return;
            }
            bit[count] = type;
            if(!minus){                                                         //첫수가 마이너스일 경우
                a.add(String.valueOf(1*v));
            }else{
                String minusV = v.toString().substring(1);
                a.add(minusV);
                bit[count] = col;
                count--;
            }
            if (type.equals("+")) {                                                                 //이전 부호를 저장해뒀다가 연산반응 ex) 9+5<<에서 -클릭시 이전에 눌러서 저장된 + 부호로 9+5계산 이후 -부호 저장
                mountNum += 1 * v;
            }
            if (type.equals("-")) {
                if(minus) {
                    mountNum += 1 * v;
                    minus = false;
                }else{
                    mountNum += -1 * v;
                }
            }
            if (type.equals("*")) {
                mountNum *= 1 * v;
            }
            if (type.equals("/")) {
                mountNum /= 1 * v;
            }
            count++;
            arith.setText(col);
            type = col;                                                                             //클릭한 부호를 저장
            mountProcess += v + col;
            process.setText(process.getText() + col);
            result.setText("");

        }

    // 계산 완료( = )
    public void equals(String t){
        String twoProcess;
        Double lastResult = 0.0;
        if(t.equals("") || result.getText().toString() == ""){
            return;
        }
        oneProcess = result.getText().toString();
        twoProcess = mountProcess + oneProcess;
        if(t.equals("+")){
            lastResult = mountNum + Double.parseDouble(oneProcess);
        }
        if(t.equals("-")){
            lastResult = mountNum - Double.parseDouble(oneProcess);
        }
        if(t.equals("*")){
            lastResult = mountNum * Double.parseDouble(oneProcess);
        }
        if(t.equals("/")){
            lastResult = mountNum / Double.parseDouble(oneProcess);
        }
        process.setText(twoProcess + "=" + lastResult);
        result.setText(String.valueOf(lastResult));
        arith.setText("");
        mountProcess = "";
        mountNum = 0.0;
        minus = false;
        }


    //한칸지우기
    public void back(){
        Integer size = result.getText().length();
        Integer size1 = process.getText().length();
        if (size >= 1) {
            result.setText(result.getText().toString().substring(0, size - 1));
        }
        if(size1 >=1){
            process.setText(process.getText().toString().substring(0, size1 - 1));
        }
    }

    //초기화
    public void rollBack() {
        mountNum = 0.0;
        arith.setText("");
        process.setText("");
        result.setText("");
        type = "";
        mountProcess = "";
        oneProcess = "";
        minus = false;
        count = 1;
        a.clear();
        for(int i = 0; i <bit.length; i++){
            bit[i] = "";
        }
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
        /*String[] a;*/
        String[] str = new String[10];
        Double[] b = new Double[10];
        String[] abc = new String[20];
        String strResult = "";
        if(process.getText().toString().contains("=")){                    //이미 결과값을 받은 상태인지 아니면 과정인지 확인
            int index = process.getText().toString().indexOf("=");
            String subStr = process.getText().toString().substring(0, index);
            /*a = subStr.split("\\+|-|\\*|/");*/
            a.add(oneProcess);
        }else{
            a.add(result.getText().toString());
            /*a = (process.getText().toString().split("\\+|-|\\*|/"));*/
        }
        /*if(process.getText().toString().substring(0,1).equals("-")){
            for(int i = 0; i<a.size()-1; i++){
                a.get(i) = a[i+1];
            }
            a[a.length-1] = "";
        }else{*/
        bit[count] = arith.getText().toString();

        for(int i=0; i<a.size(); i++){                          //숫자와 부호 합칩니다.
            String test;
            if(bit[i].equals("*")||bit[i].equals("/")){
                test = a.get(i);
            }else{
                test = bit[i] + a.get(i);
            }
            b[i] = Double.parseDouble(test);
        }
/*        for (int i =0; i<=a.length; i++) {
            int bitCount = 0;
            if (bit[i] == "*" || bit[i] == "/") {
                abc[bitCount] = b[i-1] + bit[i] + b[i];
                Log.d(abc[bitCount],i+"번째");
                bitCount++;
            }
        }*/
        for(int i =0; i<a.size(); i++){                         //숫자 크기 내림순 저장
            for(int k = 0; k<a.size(); k++) {
            if(b[i]>b[k]){
                    Double change = b[i];
                    String change2 = bit[i];
                    b[i] = b[k];
                    b[k] = change;
                    bit[i] = bit[k];
                    bit[k] = change2;
                }
            }
        }
        for(int i = 0; i<a.size(); i++){                        //타입과 숫자 순차적으로 저장
            if(b[i]<0){
                str[i] = String.valueOf(b[i]);
            }else{
                str[i] = bit[i] + b[i] ;
            }
            strResult += str[i];
        }
        if(process.getText().toString().contains("=")){
            process.setText(strResult+"="+result.getText().toString());
            return;
        }
        process.setText(strResult);
    }

// 수정한 부분 시작
    // 핸들러 세팅
    public void setHandler(Button button) {
        handler_up = new Handler();
        handler_down = new Handler();
        runnable_up = new Runnable() {
            @Override
            public void run() {
                String st = (String)button.getText();               //버튼을 길게 누를시 0.1초 딜레이로 마지막 숫자 계속 추가
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
    public EditText getResult() {
            return result;
    }
    public TextView getProcess() {
            return process;
    }
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

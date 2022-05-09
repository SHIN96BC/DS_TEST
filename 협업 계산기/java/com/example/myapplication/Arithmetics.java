package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
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

import java.util.ArrayList;
import java.util.Arrays;


public class Arithmetics extends AppCompatActivity implements OnClickListener {  //터치따로
    private View iv;                      //selected를 위해 이전 사용한 View를 저장할 변수
    private EditText result;
    private TextView process, arith;
    String type, mountProcess, oneProcess,twoProcess;                  // 부호, 과정 문자열 , equals 사용시 저장 될 마지막 숫자 문자열
    Double mountNum = 0.0;                            //최초 계산기 때 순서대로 값을 계산 및 저장한 변수
    boolean  minus, equalsort;                              // 최초 숫자가 음수일 경우 값을 if를 나누기 위해 사용한 변수
    Button[] button = new Button[10];
    String[] bit = new String[10];              //추가된 과제에서 부호를 용이하게 저장하기 위한 문자열
    int count = 0;                              //숫자와 부호를 순차적으로 저장하기 위해 사용 된  count
    ArrayList<String> numBer = new ArrayList<>();    //부호 없이 오로지 숫자만 저장 될 변수
// 수정한 부분 시작(shin)
    private Runnable runnable_up, runnable_down;
    private Handler handler_up, handler_down;
    private Toolbar mainToolBar;
    private ActionBarDrawerToggle drawerToggle;
//수정한 부분 끝
    @Override
    protected void onCreate(@Nullable Bundle saved){        //시작
        super.onCreate(saved);
        setContentView(R.layout.activity_arithmetics);

// 수정한 부분 시작(shin 2022.05.06)
        // toolbar
        mainToolBar = (Toolbar)findViewById(R.id.main_tool_bar);
        setSupportActionBar(mainToolBar);
// 수정한 부분 끝

// 수정한 부분 시작(shin 2022.05.09)
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        MenuBarEvent menuBarEvent = new MenuBarEvent(this);
        navigationView.setNavigationItemSelectedListener(menuBarEvent);
// 수정한 부분 끝


        Button addBtn, subBtn, divBtn, mulBtn, equal, rollBackBtn, comma, backBtn, touchUp,touchDown, binary, sort;

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
//        touchUp = findViewById(R.id.touchUp);
//        touchDown = findViewById(R.id.touchDown);
        binary = findViewById(R.id.binary);
        sort = findViewById(R.id.sort);

/* 주석처리(shin)
        Integer[] btn ={R.id.numBtn0, R.id.numBtn1, R.id.numBtn2,
                R.id.numBtn3, R.id.numBtn4, R.id.numBtn5,
                R.id.numBtn6, R.id.numBtn7, R.id.numBtn8, R.id.numBtn9};

        for(int i = 0; i<button.length; i++) {
            button[i] = findViewById(btn[i]);
            button[i].setOnClickListener(this);
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
        binary.setOnClickListener(this);
        sort.setOnClickListener(this);

/* 주석처리 (shin)
        touchUp.setOnLongClickListener(this);
        touchUp.setOnTouchListener(this);
        touchDown.setOnLongClickListener(this);
        touchDown.setOnTouchListener(this);
*/
        type = "";
        mountProcess = "";
        iv = null;
        for(int i = 0; i <bit.length; i++){         //부호 초기화
            bit[i] = "";
        }
// 수정한 부분 시작(shin)
        // 제 3 클래스로 이벤트 구현
        LongClickEvent longClickEvent = new LongClickEvent(this);
//        touchUp.setOnLongClickListener(longClickEvent);
//        touchDown.setOnLongClickListener(longClickEvent);
        TouchEvent touchEvent = new TouchEvent(this);
//        touchUp.setOnTouchListener(touchEvent);
//        touchDown.setOnTouchListener(touchEvent);
        setHandler(null); // 핸들러 초기 세팅

        // 버튼 세팅
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
    }

/* 주석처리(shin)
    @Override
    public boolean onLongClick(View view) {
        if(view.getId() == R.id.touchUp){
            handler_up.post(runnable_up);                                                   //handler_up을 통해 runable_up 실행
            Toast.makeText(Arithmetics.this,"LongClick",Toast.LENGTH_LONG).show();
        }
        if(view.getId() == R.id.touchDown){
            handler_down.post(runnable_down);
            Toast.makeText(Arithmetics.this,"LongClick",Toast.LENGTH_LONG).show();
        }
        return false;
    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.getId() == R.id.touchUp){
            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                Toast.makeText(Arithmetics.this,"down",Toast.LENGTH_LONG).show();
            }
            if(motionEvent.getAction() == MotionEvent.ACTION_UP){                               //handler_up이 runnable_up을 removeCallbacks 함
                Toast.makeText(Arithmetics.this,"up",Toast.LENGTH_LONG).show();
                handler_up.removeCallbacks(runnable_up);
            }
        }
        if(view.getId() == R.id.touchDown){
            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                Toast.makeText(Arithmetics.this,"down",Toast.LENGTH_LONG).show();
            }
            if(motionEvent.getAction() == MotionEvent.ACTION_UP){                               //handler_down이 runnable_down을 removeCallbacks 함
                Toast.makeText(Arithmetics.this,"up",Toast.LENGTH_LONG).show();
                handler_down.removeCallbacks(runnable_down);
            }
        }
        return false;
}
 */

    /* 주석처리(shin)
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
        switch (v.getId()) {
            //번호 클릭
            case R.id.numBtn0:
                result.append("0");
                process.append("0");
                break;
            case R.id.numBtn1:
                result.append("1");
                process.append("1");
                break;
            case R.id.numBtn2:
                result.append("2");
                process.append("2");
                break;
            case R.id.numBtn3:
                result.append("3");
                process.append("3");
                break;
            case R.id.numBtn4:
                result.append("4");
                process.append("4");
                break;
            case R.id.numBtn5:
                result.append("5");
                process.append("5");
                break;
            case R.id.numBtn6:
                result.append("6");
                process.append("6");
                break;
            case R.id.numBtn7:
                result.append("7");
                process.append("7");
                break;
            case R.id.numBtn8:
                result.append("8");
                process.append("8");
                break;
            case R.id.numBtn9:
                result.append("9");
                process.append("9");
                break;

            //부호
            case R.id.addBtn:
                if (process.getText().toString().equals("")) {
                    Toast.makeText(Arithmetics.this, "NOT NUMBER", Toast.LENGTH_LONG).show();
                    return;
                }
                resultNot();                                                 // 결과값 있는 상태로 추가 계산 시
                num = Double.parseDouble(result.getText().toString());      //EditText에 있는 값을 저장
                calculator("+", num); //계산                              //해당 부호와 num값을 calculator()에 넘깁니다.
                break;

            case R.id.subBtn:
                int size = process.getText().length() - 1;
                String pro = process.getText().toString().substring(size);
                if (pro.equals("*") || pro.equals("/")) {
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
                if (process.getText().toString().equals("")) {
                    Toast.makeText(Arithmetics.this, "NOT NUMBER", Toast.LENGTH_LONG).show();
                    return;
                }
                resultNot();
                num = Double.parseDouble(result.getText().toString());
                calculator("*", num);
                break;

            case R.id.divBtn:
                if (process.getText().toString().equals("")) {
                    Toast.makeText(Arithmetics.this, "NOT NUMBER", Toast.LENGTH_LONG).show();
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
                startActivity(intent);
                break;
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
            minus = false;
            equalsort = false;
            process.setText(result.getText().toString());
        }
    }

    //계산 과정
    public void calculator(String col, Double v) {
        bit[count] = col;                           //부호 배열로 저장 sort나 equals
        if(!process.getText().toString().equals("0")){                                 // 첫수로 인해 숫자 0을 받을시 문제가 생기기에 일단 if로 빼놓음
            numBer.add(String.valueOf(1*v));
        }else{
            process.setText("");
        }
        count++;
        arith.setText(col);
        process.setText(process.getText() + col);
        if(col.equals("-")){
            mountProcess += v;
            result.setText("-");
        }else{
            mountProcess += v + col;
            result.setText("");
        }
    }

    // 계산 완료( = )
    public void equals(){
        if(result.getText().toString().equals("") || arith.getText().toString().equals("")){
            return;
        }
        if(mountProcess.charAt(0) == '0'){
            mountProcess = mountProcess.substring(3);
        }
        Log.d(String.valueOf(count), String.valueOf(numBer.size()));
        if(!equalsort){
            numBer.add(result.getText().toString());
        }
        double lastResult = 0.0;
        String lastResultStr = "";
        oneProcess = result.getText().toString();
        twoProcess = (mountProcess + oneProcess).replace(".0","");
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
        lastResultStr = String.valueOf(lastResult).replace(".0","");
        if(!equalsort){
            process.setText(twoProcess + "=" + lastResultStr);
        }else{
            process.append("=" + lastResultStr);
        }
        result.setText(lastResultStr);
        arith.setText("");
    }


    //한칸지우기
    public void back(){
        int size = result.getText().length();
        int size1 = process.getText().length();
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
        process.setText("0");
        result.setText("0");
        type = "";
        mountProcess = "";
        oneProcess = "";
        twoProcess ="";
        minus = false;
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
        /*String[] a;*/
        String[] abc = new String[8];
        String[] str = new String[8];
        double[] b = new double[8];
        String strResult = new String();
        ArrayList<String> bitstr = new ArrayList<>();
        ArrayList<Double> bitb = new ArrayList<>();
        if(!process.getText().toString().contains("=")){                    //이미 결과값을 받은 상태인지 아니면 과정인지 확인
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
// 수정한 부분 시작(shin)
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
// 수정한 부분 끝

}
//정렬하고 equal 시 순서가 다시 섞이는 문제
//포커싱과 셀렉터 양립 구현 실패
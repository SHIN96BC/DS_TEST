package com.example.ds_calculator;

import static com.example.ds_calculator.util.LogTag.SBC_TAG;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;




public class Arithmetics extends Activity {  //��ġ����
    private CalculateHelper calculateHelper;                        //��� class

    private boolean isDot, isBracket, isPreview;                    //���� �Ǽ�, ��ȣ, ���ó���� ���� �� ������

    private TextView edit_result, edit_process, edit_arith;         //��� ����, ��� , ��ȣ View

    private String result;                                          //return ��� ��

    private View view;                                              //��ư Ŭ�� ȿ�� ó���� view��

    private Runnable runnable_up, runnable_down;                    //Number Touch Thread

    private Handler handler_up, handler_down;                       //Number Touch Handler

    Button[] button = new Button[10];                               //Button

    private Button addBtn, subBtn, mulBtn, divBtn, clear, bracket, backBtn, dot, equal, 
    				sinBtn, cosBtn, tanBtn, binary, sqr, root, sort, graph;
    
    private ImageButton mBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arithmetics);


        calculateHelper = new CalculateHelper();

        view = null;
        int number = 25;
        int t = String.valueOf(Math.sqrt(number)).length();
        Log.d("test", "" + t + " ? " + String.valueOf(Math.sqrt(number)));

        isPreview = false;
        isBracket = false;
        isDot = false;

        int[][] test = new int[5][4];
        setButton();
        setTextView();
    }
    

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            showToast("����: ORIENTATION_LANDSCAPE");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            showToast("����: ORIENTATION_PORTRAIT");
        }
    }

    public void showToast(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }

    private void setButton() {

        addBtn = (Button) findViewById(R.id.addBtn);
        subBtn = (Button) findViewById(R.id.subBtn);
        mulBtn = (Button) findViewById(R.id.mulBtn);
        divBtn = (Button) findViewById(R.id.divBtn);
        sqr = (Button) findViewById(R.id.sqr);
        root = (Button) findViewById(R.id.root);
        equal = (Button) findViewById(R.id.equal);
        clear = (Button) findViewById(R.id.clear);
        bracket = (Button) findViewById(R.id.bracket);
        binary = (Button) findViewById(R.id.binary);
        sort = (Button) findViewById(R.id.sort);
        backBtn = (Button) findViewById(R.id.backBtn);
        dot = (Button) findViewById(R.id.dot);
        graph = (Button) findViewById(R.id.date);
        sinBtn = (Button) findViewById(R.id.sinBtn);
        cosBtn = (Button) findViewById(R.id.cosBtn);
        tanBtn = (Button) findViewById(R.id.tanBtn);
        mBackBtn = (ImageButton) findViewById(R.id.back_btn);

        // number ClickListener
        Integer[] btn ={R.id.numBtn0, R.id.numBtn1, R.id.numBtn2,
                R.id.numBtn3, R.id.numBtn4, R.id.numBtn5,
                R.id.numBtn6, R.id.numBtn7, R.id.numBtn8, R.id.numBtn9};
        // �� 3 Ŭ������ �̺�Ʈ ����
        LongClickEvent longClickEvent = new LongClickEvent(this);
        TouchEvent touchEvent = new TouchEvent(this);
        setHandler(null); // �ڵ鷯 �ʱ� ����
        for(int i = 0; i<button.length; i++) {
            button[i] = (Button) findViewById(btn[i]);
            button[i].setOnClickListener(numClickListener);
            button[i].setOnLongClickListener(longClickEvent);
            button[i].setOnTouchListener(touchEvent);
        }
        backBtn.setOnLongClickListener(longClickEvent);
        backBtn.setOnTouchListener(touchEvent);

        // mark ClickListener
        addBtn.setOnClickListener(markClickListener);
        subBtn.setOnClickListener(markClickListener);
        mulBtn.setOnClickListener(markClickListener);
        divBtn.setOnClickListener(markClickListener);
        sqr.setOnClickListener(markClickListener);
        root.setOnClickListener(markClickListener);
        equal.setOnClickListener(markClickListener);
        clear.setOnClickListener(markClickListener);
        bracket.setOnClickListener(markClickListener);
        binary.setOnClickListener(markClickListener);
        sort.setOnClickListener(markClickListener);
        backBtn.setOnClickListener(markClickListener);
        dot.setOnClickListener(markClickListener);
        graph.setOnClickListener(markClickListener);
        sinBtn.setOnClickListener(markClickListener);
        cosBtn.setOnClickListener(markClickListener);
        tanBtn.setOnClickListener(markClickListener);
        
        
        mBackBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Log.d(SBC_TAG, "moveToHome()");
				   
		        Intent intent = new Intent(Intent.ACTION_MAIN);
		        intent.addCategory(Intent.CATEGORY_HOME);
		        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		        startActivity(intent);
		        finish();
			}
        	
        });
    }

    // ���� ��ư�� ������ ���
    private final Button.OnClickListener numClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(!edit_process.getText().toString().equals("")) {
                if (edit_process.getText().toString().charAt(0) == 's'
                        || edit_process.getText().toString().charAt(0) == 't'
                        || edit_process.getText().toString().charAt(0) == 'c') {
                    Toast.makeText(Arithmetics.this,"Please click C or <-", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            select(view);
            switch (view.getId()) {
                case R.id.numBtn0:
                    edit_process.append("0");
                    break;
                case R.id.numBtn1:
                    edit_process.append("1");
                    break;
                case R.id.numBtn2:
                    edit_process.append("2");
                    break;
                case R.id.numBtn3:
                    edit_process.append("3");
                    break;
                case R.id.numBtn4:
                    edit_process.append("4");
                    break;
                case R.id.numBtn5:
                    edit_process.append("5");
                    break;
                case R.id.numBtn6:
                    edit_process.append("6");
                    break;
                case R.id.numBtn7:
                    edit_process.append("7");
                    break;
                case R.id.numBtn8:
                    edit_process.append("8");
                    break;
                case R.id.numBtn9:
                    edit_process.append("9");
                    break;
            }

            preview();
        }
    };


    //��ȣ ��ư�� ������ ���
    private final Button.OnClickListener markClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(!(view.getId() == R.id.backBtn ||view.getId() == R.id.clear||view.getId() == R.id.binary||view.getId() == R.id.date)) {
                if (!edit_process.getText().toString().equals("")) {
                    if (edit_process.getText().toString().charAt(0) == 's'
                            || edit_process.getText().toString().charAt(0) == 't'
                            || edit_process.getText().toString().charAt(0) == 'c') {
                        Toast.makeText(Arithmetics.this,"Please click C or <-", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
            if(edit_process.getText().toString().equals("")){
                if(!(view.getId() == R.id.subBtn ||view.getId() == R.id.bracket ||view.getId() == R.id.binary||view.getId() == R.id.date)) {
                    return;
                }
            }
            switch (view.getId()) {
                case R.id.addBtn:
                    edit_arith.setText(" ");
                    edit_process.append(" + ");
                    edit_arith.append(" + ");
                    isPreview = true;
                    break;

                case R.id.subBtn:
                    String[] test_process = edit_process.getText().toString().split(" ");
                    String lastarith = test_process[test_process.length-1];
                    if(edit_process.length() != 0){
                        if(!(lastarith.equals("*") || lastarith.equals("/"))){
                            edit_process.append(" - ");
                            edit_arith.setText(" - ");
                        }else{
                            edit_process.append("-");
                            edit_arith.setText(" - ");
                            return;
                        }
                    }else{
                        edit_process.setText("0 - ");
                        edit_arith.setText(" - ");
                    }
                    isPreview = true;
                    break;

                case R.id.mulBtn:
                    edit_arith.setText(" ");
                    edit_process.append(" * ");
                    edit_arith.append(" * ");
                    isPreview = true;
                    break;

                case R.id.divBtn:
                    edit_arith.setText(" ");
                    edit_process.append(" / ");
                    edit_arith.append(" / ");
                    isPreview = true;
                    break;

                case R.id.sinBtn:
                    if(edit_process.getText().toString().equals("")){
                        return;
                    }
                    edit_arith.setText(" ");
                    String sin = edit_process.getText().toString();
                    String sinProcess = "sin(" + sin + ")";
                    edit_process.setText(sinProcess);
                    edit_arith.append(" sin ");
                    Double sinValue = Math.sin(Double.parseDouble(sin) * Math.PI / 180);
                    String sinResult = sinValue.toString();
                    edit_result.setText(sinResult);
                    isPreview = true;
                    break;

                case R.id.cosBtn:
                    if(edit_process.getText().toString().equals("")){
                        return;
                    }
                    edit_arith.setText(" ");
                    String cos = edit_process.getText().toString();
                    String cosProcess = "cos(" + cos + ")";
                    edit_process.setText(cosProcess);
                    edit_arith.append(" cos ");
                    Double cosValue = Math.cos(Double.parseDouble(cos) * Math.PI / 180);
                    String cosResult = cosValue.toString();
                    edit_result.setText(cosResult);
                    isPreview = true;
                    break;

                case R.id.tanBtn:
                    if(edit_process.getText().toString().equals("")){
                        return;
                    }
                    edit_arith.setText(" ");
                    String tan = edit_process.getText().toString();
                    String tanProcess = "tan(" + tan + ")";
                    edit_process.setText(tanProcess);
                    edit_arith.append(" tan ");
                    Double tanValue = Math.cos(Double.parseDouble(tan) * Math.PI / 180);
                    String tanResult = tanValue.toString();
                    edit_result.setText(tanResult);
                    isPreview = true;
                    break;

                case R.id.clear:
                    edit_process.setText("");
                    edit_result.setText("");
                    edit_arith.setText("");
                    isDot = false;
                    isBracket = false;

                    calculateHelper = new CalculateHelper();

                    isPreview = false;

                    break;

                case R.id.bracket:
                    if (!isBracket) {
                        edit_process.append("( ");
                        isBracket = true;
                    } else {
                        edit_process.append(" )");
                        isBracket = false;
                    }

                    isPreview = true;

                    break;

                case R.id.backBtn:
                    if(edit_process.getText().toString().charAt(0)=='s'
                            ||edit_process.getText().toString().charAt(0)=='t'
                            || edit_process.getText().toString().charAt(0)=='c'){
                        edit_process.setText("");
                        edit_result.setText("");
                        edit_arith.setText("");
                    }
                    int size = edit_process.getText().length();
                    if (size != 0)
                        edit_process.setText(edit_process.getText().toString().substring(0, size - 1));

                    if (size > 1) {
                        if (calculateHelper.checkNumber(edit_process.getText().toString().substring(size - 2)))
                            preview();
                        else {
                            isPreview = false;
                            edit_result.setText("");
                        }
                    }
                    break;

                case R.id.dot:
                    edit_process.append(".");
                    isDot = true;
                    break;

                case R.id.sqr:
                    String[] processSqr;                   //��ü process �迭
                    String lastNumSqr = "";                //root ������ ��
                    result = "";                        //����� ��
                    processSqr = edit_process.getText().toString().split(" ");
                    lastNumSqr = processSqr[processSqr.length-1];
                    double resultSqr = Double.parseDouble(lastNumSqr);
                    if (!isDot) {                                       //������ ������ �Ǽ��� �Ǽ��� Ÿ�� ��ȯ�� ��� �� ���
                        result = String.valueOf((int) resultSqr);
                    } else {
                        double sqr = Math.floor(resultSqr*100)/100;
                        result = String.valueOf(sqr);
                        isDot = true;
                    }
                    edit_process.append(" * " + result);
                    isPreview = true;
                    preview();
                    break;

                case R.id.root:
                    String[] processRoot;                   //��ü process �迭
                    String lastNumRoot = "";                //root ������ ��
                    result = "";                        //����� ��
                    processRoot = edit_process.getText().toString().split(" ");
                    lastNumRoot = processRoot[processRoot.length-1];
                    double root = Math.sqrt(Double.parseDouble(lastNumRoot));
                    root = Math.floor(root*100)/100;
                    int intNum = (int) root;                //root�� ���� ��ȯ
                    double booleanNum = root - intNum;      //root���� �������� ���� ���� 0.0�̸� ���� �Ҽ��� �ڿ� ���� ������ �Ǽ�
                    if (!isDot && !(booleanNum > 0.0)) {    //isDot�� false�̰� root���� �����̸� int�� ����
                        processRoot[processRoot.length-1] = String.valueOf((int) root);
                    }else {
                        processRoot[processRoot.length-1] = String.valueOf(root);
                        isDot = true;
                    }
                    for(String date : processRoot){
                        result += date + " ";
                    }
                    edit_process.setText(result);
                    isPreview = true;
                    preview();
                    break;

                case R.id.sort:
                    String sortStr = edit_process.getText().toString();
                    String sortresult = calculateHelper.sorted(sortStr);
                    if(calculateHelper.checkError(sortresult)){
                        return;
                    }
                    edit_process.setText(sortresult);
                    preview();
                    break;

                case R.id.equal:
                    result = edit_process.getText().toString();
                    double r = calculateHelper.process(result);

                    if (!isDot)
                        edit_process.setText(String.valueOf((int) r));
                    else
                        edit_process.setText(String.valueOf(r));

                    edit_result.setText("");
                    edit_arith.setText("");
                    isDot = false;
                    isPreview = false;
                    break;

                case R.id.binary:
                    Intent intent = new Intent(getApplicationContext(), Arithmetics_Change.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    break;

                case R.id.date:
                    Intent intentGra = new Intent(getApplicationContext(), DateArithmetics.class);
                    intentGra.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intentGra);
                    break;
            }
        }
    };

    private void preview() {
        if (isPreview) {
            result = edit_process.getText().toString();
            double r = calculateHelper.process(result);

            if (!isDot) {
                edit_result.setText(String.valueOf((int) r));
            }else {
                r = Math.floor(r * 100) / 100;
                edit_result.setText(String.valueOf(r));
            }
        }
    }

    private void setTextView() {
        edit_process = (TextView) findViewById(R.id.edit_process);
        edit_result = (TextView) findViewById(R.id.edit_result);
        edit_arith = (TextView) findViewById(R.id.edit_arith);
    }

    private void select(View view2) {
        if (view != null) {
            if (view.getId() != view2.getId()) {
                view.setSelected(false);
            }
        }
        view2.setSelected(true);
        view = view2;
    }

    // �ڵ鷯 ����
    public void setHandler(final Button button) {
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
                        edit_result.append(st);
                        edit_process.append(st);
                    }
                }
                handler_up.postDelayed(this, 100);
            }
        };
        runnable_down = new Runnable() {
            @Override
            public void run() {
                int size = edit_result.getText().length();
                int size1 = edit_process.getText().length();
                if (size >= 1) {
                    edit_result.setText(edit_result.getText().toString().substring(0, size - 1));
                }
                if(size1 >=1){
                    edit_process.setText(edit_process.getText().toString().substring(0, size1 - 1));
                }
                handler_down.postDelayed(this,100);
            }
        };
    }

    // getter
    public Handler getHandler_up() { return handler_up; }
    public Handler getHandler_down() { return handler_down; }
    public Runnable getRunnable_up() { return runnable_up; }
    public Runnable getRunnable_down() { return runnable_down; }
}

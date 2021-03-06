package com.example.ds_calculator;

import static com.example.ds_calculator.util.LogTag.SBC_TAG;

import com.dseltec.micom.key.MicomKeyManager;
import com.dseltec.micom.key.MicomKeyStatusListener;
import com.dseltec.micom.mode.MicomModeConsts;
import com.dseltec.micom.protocol.key.KEY;
import com.dseltec.micom.system.MicomSystemListener;
import com.dseltec.micom.system.MicomSystemManager;
import com.dseltec.micom.mode.MicomModeListener;
import com.dseltec.micom.mode.MicomModeManager;
import com.dseltec.HardwareServiceManager;
import android.view.KeyEvent;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;




public class Arithmetics extends Activity {  //터치따로
    private CalculateHelper calculateHelper;                        //계산 class

    private boolean isDot, isBracket, isPreview;                    //정수 실수, 괄호, 계산처리에 대한 논리 연산자

    private TextView edit_result, edit_process, edit_arith;         //계산 과정, 결과 , 부호 View

    private String result;                                          //return 결과 값

    private View view;                                              //버튼 클릭 효과 처리할 view값

    private Runnable runnable_up, runnable_down;                    //Number Touch Thread

    private Handler handler_up, handler_down;                       //Number Touch Handler

    Button[] button = new Button[10];                               //Button

    private Button addBtn, subBtn, mulBtn, divBtn, clear, bracket, backBtn, dot, equal, 
    				sinBtn, cosBtn, tanBtn, binary, sqr, root, sort, graph;
    
    private ImageButton mBackBtn;
    
    private HardwareServiceManager mHardwareServiceManager = null;
    private MicomKeyManager mKeyManager;
    private MicomSystemManager mSystemManager = null;
    private MicomModeManager mModeManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	ActionBar actionBar = getActionBar();
        actionBar.hide();
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arithmetics);

        // shin update 타이틀 바 추가
        TextView titleText = (TextView)findViewById(R.id.title_bar);
        titleText.setText(R.string.title);
        
        // shin update micon 추가
        mKeyManager = new MicomKeyManager(this);
        mKeyManager.addListener(mMicomKeyStatusListener);
        
        mSystemManager = new MicomSystemManager(this);
        mSystemManager.addListener(mMicomSystemListener);
        mSystemManager.getModel();
        
        mModeManager = new MicomModeManager(this);
        mModeManager.addListener(mMicomModesListener);
        mModeManager.getMicomMode();
        mModeManager.getAuxStatus();
        
        // shin update 하드키 추가
        mHardwareServiceManager = new HardwareServiceManager(this);
        mHardwareServiceManager.addListener(mHwListener);

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
            showToast("방향: ORIENTATION_LANDSCAPE");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            showToast("방향: ORIENTATION_PORTRAIT");
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
        // 제 3 클래스로 이벤트 구현
        LongClickEvent longClickEvent = new LongClickEvent(this);
        TouchEvent touchEvent = new TouchEvent(this);
        setHandler(null); // 핸들러 초기 세팅
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
        
        // shin update
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

    // 숫자 버튼이 눌렸을 경우
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
            // shin update
            if(edit_result.getText().toString().equals("ERROR")) {
            	edit_result.setText("");
            	edit_process.setText("");
            	edit_arith.setText("");
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


    //기호 버튼이 눌렸을 경우
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
            
         // shin update
            String errorCheckStr = "";
            
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
                 // shin update 괄호가 마지막으로 오면 발생하는 에러 처리
                    errorCheckStr = edit_process.getText().toString();
                    if(!bracketCheck(errorCheckStr)) return;
                    
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
                 // shin update 괄호가 마지막으로 오면 발생하는 에러 처리
                    errorCheckStr = edit_process.getText().toString();
                    if(!bracketCheck(errorCheckStr)) return;
                    
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
                 // shin update 괄호가 마지막으로 오면 발생하는 에러 처리
                    errorCheckStr = edit_process.getText().toString();
                    if(!bracketCheck(errorCheckStr)) return;
                    
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
                	// shin update 괄호가 마지막으로 오면 발생하는 에러 처리
                    errorCheckStr = edit_process.getText().toString();
                    if(!bracketCheck(errorCheckStr)) return;
                    
                    String[] processSqr;                   //전체 process 배열
                    String lastNumSqr = "";                //root 적용할 값
                    result = "";                        //출력할 값
                    processSqr = edit_process.getText().toString().split(" ");
                    lastNumSqr = processSqr[processSqr.length-1];
                    double resultSqr = Double.parseDouble(lastNumSqr);
                    if (!isDot) {                                       //정수면 정수로 실수면 실수로 타입 변환후 결과 값 출력
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
                	// shin update 괄호가 마지막으로 오면 발생하는 에러 처리
                    errorCheckStr = edit_process.getText().toString();
                    if(!bracketCheck(errorCheckStr)) return;
                    
                    String[] processRoot;                   //전체 process 배열
                    String lastNumRoot = "";                //root 적용할 값
                    result = "";                        //출력할 값
                    processRoot = edit_process.getText().toString().split(" ");
                    lastNumRoot = processRoot[processRoot.length-1];
                    double root = Math.sqrt(Double.parseDouble(lastNumRoot));
                    root = Math.floor(root*100)/100;
                    int intNum = (int) root;                //root값 정수 변환
                    double booleanNum = root - intNum;      //root값과 버림값을 빼서 값이 0.0이면 정수 소수점 뒤에 값이 있으면 실수
                    if (!isDot && !(booleanNum > 0.0)) {    //isDot가 false이고 root값이 정수이면 int로 변경
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
                	// shin update 괄호가 마지막으로 오면 발생하는 에러 처리
                    errorCheckStr = edit_process.getText().toString();
                    if(!bracketCheck(errorCheckStr)) return;
                    
                    String sortStr = edit_process.getText().toString();
                    String sortresult = calculateHelper.sorted(sortStr);
                    if(calculateHelper.checkError(sortresult)){
                        return;
                    }
                    edit_process.setText(sortresult);
                    preview();
                    break;

                case R.id.equal:
                	// shin update 괄호가 마지막으로 오면 발생하는 에러 처리
                    errorCheckStr = edit_process.getText().toString();
                    if(!bracketCheck(errorCheckStr)) return;
                	
                    result = edit_process.getText().toString();
                    double r = calculateHelper.process(result);
                    
                    if(r <= 2147483647) {
                    	if (!isDot)
                            edit_process.setText(String.valueOf((int) r));
                        else
                            edit_process.setText(String.valueOf(r));
                    }else {
                    	edit_result.setText("ERROR");
                    }
                    

                    edit_process.setText("");
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

    // shin update 괄호를 체크하는 메서드
    private boolean bracketCheck(String errorCheckStr) {
    	// 괄호가 마지막으로 오면 발생하는 에러 처리
        // 아스키코드 ( == 40,  ) == 41
        if(errorCheckStr.charAt(errorCheckStr.length()-1) == 41) {
        	// 괄호가 닫혀있을 때 바로 전에 있는 값이 숫자면 true 아니면 false 를 return 합니다.
        	// 아스키코드 0 ~ 9 == 48 ~ 57
        	if(errorCheckStr.charAt(errorCheckStr.length()-1) > 47
        			&& errorCheckStr.charAt(errorCheckStr.length()-1) < 58) return true;
        }
        if(errorCheckStr.charAt(errorCheckStr.length()-1) > 47
    			&& errorCheckStr.charAt(errorCheckStr.length()-1) < 58) return true;
        return false;   
    }
    
    // shin update micom 추가
    private MicomKeyStatusListener mMicomKeyStatusListener = new MicomKeyStatusListener() {
    	

        @Override
        public void onHardKeyDown(int arg0) {
            // TODO Auto-generated method stub
            
        }
 
        @Override
        public void onHardKeyUp(int arg0) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void onIRKeyDown(int arg0, int arg1) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void onIRKeyUp(int arg0, int arg1) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void onSWRCKeyDown(int arg0) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onSWRCKeyUp(int keycode) {
            // TODO Auto-generated method stub
            Log.v(SBC_TAG, "onSWRCKeyUp ");
            
        }
        
    };
    // Added by chkim 20180316 Add DOP Model Function ->
    private MicomSystemListener mMicomSystemListener = new MicomSystemListener(){

    	
        @Override
        public void onModelInfo(int model) {
            Log.v(SBC_TAG, "onModelInfo() model = " + model);
            
        }
    };
    
    private MicomModeListener mMicomModesListener = new MicomModeListener() {

        @Override
        public void onMicomMode(int micomMode) {
        	Log.v(SBC_TAG, "micom mode start>>>>>>>>>>>>>>>>>");
        }
    };
    
    // shin update 하드키 추가
    private HardwareServiceManager.Listener mHwListener = new HardwareServiceManager.Listener() {
		@Override
		public void onHardKeyDown(int keyCode) {
			Log.v(SBC_TAG, "HardwareServiceManager.Listener onHardKeyDown() key=" + keyCode);
			if (keyCode == KeyEvent.KEYCODE_FBDJR_LCD_FUNC3) {
				// VR Key
				moveToHome();
			} else if (keyCode == KeyEvent.KEYCODE_FBDJR_LCD_FUNC4) {
				// HOME Key
			} else if (keyCode == KeyEvent.KEYCODE_FBDJR_LCD_FUNC5) {
				// FAVORITE Key
			} else if (keyCode == KeyEvent.KEYCODE_FBDJR_LCD_FUNC7) {
				// Track Down
			} else if (keyCode == KeyEvent.KEYCODE_FBDJR_LCD_FUNC6) {
				// Track Up
			}
		}
    };
    
    // shin update 
    private void moveToHome() {
    	Log.d(SBC_TAG, "moveToHome()");
		   
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
    
    // sbc update 계산된 결과 값이 int 의 범위를 초과하면 ERROR 를 보여주도록 수정
    private void preview() {
        if (isPreview) {
            result = edit_process.getText().toString();
            double r = calculateHelper.process(result);
            
            if(r < 2147483647) {
            	if (!isDot) {
            		edit_result.setText(String.valueOf((int) r));
                    edit_process.setText(String.valueOf((int) r));
            	}else {
            		edit_result.setText(String.valueOf(r));
                    edit_process.setText(String.valueOf(r));
            	}
            }else {
            	edit_result.setText("ERROR");
            	edit_process.setText("");
            	isPreview = false;
            	edit_arith.setText("");
                isDot = false;
            }
        }
    }

    private void setTextView() {
        edit_process = (TextView) findViewById(R.id.edit_process);
        // shin update 스크롤 바 추가
        edit_process.setMovementMethod(new ScrollingMovementMethod());
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

    // 핸들러 세팅
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
                    // shin update
                    if(edit_result.getText().toString().equals("ERROR")) edit_result.setText("");
                    if(st.length() != 0) {
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
                //shin update
                if(isPreview) {
                	edit_process.setText("");
                	edit_arith.setText("");
                }else {
                	if(size1 >=1){
                        edit_process.setText(edit_process.getText().toString().substring(0, size1 - 1));
                        edit_arith.setText("");
                    }
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

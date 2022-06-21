package com.example.ds_calculator;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import static com.example.ds_calculator.util.LogTag.SBC_TAG;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DateArithmetics extends Activity implements View.OnClickListener {

    private TextView startView;
    private TextView lastView;
    private TextView resultView;
    private ImageButton mBackBtn;

    // �ȵ���̵� os ���� ������ �߻��ϴ� LocalDate ������ �ذ��ϴ� Annotation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        
        TextView titleText = (TextView)findViewById(R.id.title_bar);
        titleText.setText(R.string.title_date);

        // ���� ��¥ ����
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        // ���� ��¥ ����
        calendar.add(Calendar.DATE, +1);
        Date tomorrow = calendar.getTime();
        // ��¥ ǥ������ ����
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
        // ��¥ ��Ʈ������ ��ȯ
        String startDay = simpleDateFormat.format(today);
        String lastDay = simpleDateFormat.format(tomorrow);
        
        // ó��ȭ�鿡 ��¥�� ����
        startView = (TextView)findViewById(R.id.startDay);
        startView.setText(startDay);
        lastView = (TextView)findViewById(R.id.lastDay);
        lastView.setText(lastDay);
        resultView = (TextView)findViewById(R.id.resultView);
        resultView.setText("1Day");
        mBackBtn = (ImageButton) findViewById(R.id.back_btn);


        // Ŭ�� �̺�Ʈ
        Button btnStart = (Button)findViewById(R.id.btn_date_start);
        Button btnLast = (Button)findViewById(R.id.btn_date_last);
        Button btnHome = (Button)findViewById(R.id.btn_date_home);
        btnStart.setOnClickListener(this);
        btnLast.setOnClickListener(this);
        btnHome.setOnClickListener(this);
        
        mBackBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Log.d(SBC_TAG, "moveToBack()");
		        finish();
			}
        	
        });
    }
    
    @Override
    public void onClick(View view) {
        DatePickerDialog datePickerDialog = null;
        int[] intArr = null;
        switch (view.getId()) {
            case R.id.btn_date_start:
                String startDateStr = startView.getText().toString();
                intArr = setDate(startDateStr);
                if(intArr != null) {
                    datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            // ���� 0���� �����ϹǷ� +1 ���ش�(ex: 1������ ��� ���� 0���� ���´�)
                            month += 1;
                            startView.setText(year + "-" + month + "-" + day);
                            long resultDay = calculatorDate(startView.getText().toString(), lastView.getText().toString());
                            if(resultDay != -1) {
                                resultView.setText(resultDay+"Day");
                            }else {
                                resultView.setText("Error");
                            }
                        }
                    }, intArr[0], intArr[1]-1, intArr[2]); // ���� +1 �Ǽ� ���� -1 ������Ѵ�.
                }
                break;
            case R.id.btn_date_last:
                String lastDateStr = lastView.getText().toString();
                intArr = setDate(lastDateStr);
                if(intArr != null) {
                    datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            // ���� 0���� �����ϹǷ� +1 ���ش�(ex: 1������ ��� ���� 0���� ���´�)
                            month+=1;
                            lastView.setText(year + "-" + month + "-" + day);
                            long resultDay = calculatorDate(startView.getText().toString(), lastView.getText().toString());
                            if(resultDay != -1) {
                                resultView.setText(resultDay+"Day");
                            }else {
                                resultView.setText("Error");
                            }
                        }
                    }, intArr[0], intArr[1]-1, intArr[2]); // ���� +1 �Ǽ� ���� -1 ������Ѵ�.
                }
                break;
            case R.id.btn_date_home:
            	finish();
            	return;
        }
        if(datePickerDialog != null) {
            datePickerDialog.show();
        }
    }
    // ���� ǥ�õ� ��¥�� �����ͼ� int �迭�� ����� �޼���
    private int[] setDate(String dateStr) {
        int[] resultArr = new int[3];
        if(dateStr != null) {
            if(dateStr.length() != 0) {
                int startIndex = 0;
                for(int i=0; i<3; i++) {
                    String tempStr = "";
                    if(dateStr.indexOf("-", startIndex) != -1) {
                        tempStr = dateStr.substring(startIndex, dateStr.indexOf("-", startIndex));
                    }else {
                        tempStr = dateStr.substring(startIndex, dateStr.length());
                    }
                    try {
                        resultArr[i] = Integer.parseInt(tempStr);
                    }catch(NumberFormatException nfe) {
                        return null;
                    }
                    startIndex = dateStr.indexOf("-", startIndex)+1;
                }
                return resultArr;
            }
        }
        return null;
    }

    //
    private long calculatorDate(String startStr, String lastStr) {
        if(startStr != null && lastStr != null) {
            if(startStr.length() != 0 && lastStr.length() != 0) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                try {
                    Date startDate = sdf.parse(startStr);
                    Date lastDate = sdf.parse(lastStr);
                    long resultDay = (lastDate.getTime() - startDate.getTime()) / (24*60*60*1000);
                    // startDate�� �� ũ�� ������ ��쿡 ������ �����°� �������� �ڵ�
                    if(resultDay < 0) {
                        resultDay += resultDay*(-2);
                    }
                    return resultDay;
                } catch (ParseException e) {
                }
            }
        }
        return -1L;
    }
}

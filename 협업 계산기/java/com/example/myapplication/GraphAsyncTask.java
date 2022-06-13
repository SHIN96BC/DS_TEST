package com.example.calculator;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

/*
 AsyncTask 는 비동기 작업을 수행하기위해 사용됩니다.
 AsyncTask 의 형태 == AsyncTask<Params, Progress, Result>
  1. Params: doInBackground 메서드의 파라미터 타입이 되며, execute 메서드 인자 값이 됩니다.
  2. progress: doInBackground 작업 시 진행 단위의 타입으로 onProgressUpdate 파라미터 타입입니다.
  3. Result: doInBackground 리턴값으로 onPostExecute 파라미터 타입입니다.
 */
public class GraphAsyncTask extends AsyncTask<String, Void, ArrayList<Entry>> {

    String function;
    Context graphContext;

    public GraphAsyncTask(String function, Context graphContext) {
        this.function = function;
        this.graphContext = graphContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Entry> doInBackground(String... strings) {
        if(function != null && graphContext != null && function.length() != 0) {
            ArrayList<Entry> entryList = new ArrayList<>();
            Log.v("functionThird", "function : " + function);
            String functionThirdRemove = function.replaceFirst("y=", "");
            Log.v("functionThirdRemove", "functionThirdRemove : " + functionThirdRemove);
            String functionThirdOperator = functionThirdRemove.substring(1, 2);
            Log.v("functionThirdOperator", "functionThirdOperator : " + functionThirdOperator);
            String functionThirdNum = functionThirdRemove.substring(2);
            Log.v("functionThirdNum", "functionThirdNum : " + functionThirdNum);

            if (functionThirdOperator.equals("+")) {
                for (float i = -10.00f; i < 20; i += 0.01f) {
                    float y = i + Integer.parseInt(functionThirdNum);
                    float x = i;
                    entryList.add(new Entry(x, y));
                }
            } else if (functionThirdOperator.equals("-")) {
                for (float i = -10.00f; i < 20; i += 0.01f) {
                    float y = i - Integer.parseInt(functionThirdNum);
                    float x = i;
                    entryList.add(new Entry(x, y));
                }
            } else if (functionThirdOperator.equals("*")) {
                for (float i = -10.00f; i < 20; i += 0.01f) {
                    float y = i * Integer.parseInt(functionThirdNum);
                    float x = i;
                    entryList.add(new Entry(x, y));
                }
            } else if (functionThirdOperator.equals("/")) {
                for (float i = -10.00f; i < 20; i += 0.01f) {
                    float y = i / Integer.parseInt(functionThirdNum);
                    float x = i;
                    entryList.add(new Entry(x, y));
                }
            }

        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Entry> result) {

    }
}

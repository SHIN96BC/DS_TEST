package com.example.calculator;

import static com.example.calculator.GraphActivity.FUNCTION_1;
import static com.example.calculator.GraphActivity.FUNCTION_2;
import static com.example.calculator.GraphActivity.FUNCTION_3;
import static com.example.calculator.GraphActivity.X_LINE;
import static com.example.calculator.GraphActivity.Y_LINE;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

/*
 AsyncTask 는 비동기 작업을 수행하기위해 사용됩니다.
 AsyncTask 의 형태 == AsyncTask<Params, Progress, Result>
  1. Params: doInBackground 메서드의 파라미터 타입이 되며, execute 메서드 인자 값이 됩니다.
  2. progress: doInBackground 작업 시 진행 단위의 타입으로 onProgressUpdate 파라미터 타입입니다.
  3. Result: doInBackground 리턴값으로 onPostExecute 파라미터 타입입니다.
 */
public class GraphAsyncTask extends AsyncTask<String, Void, ArrayList<Entry>> {

    private String function;
    private Context graphContext;
    private int position;

    public GraphAsyncTask(String function, Context graphContext, int position) {
        this.function = function;
        this.graphContext = graphContext;
        this.position = position;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Entry> doInBackground(String... strings) {
        ArrayList<Entry> entryList = null;
        if(function != null && graphContext != null && function.length() != 0) {
            if(position == X_LINE) {
                for (float i = -10.00f; i < 20; i += 0.01f) {
                    float y = i;
                    float x = 0;
                    entryList.add(new Entry(x, y));
                }
            }else if(position == Y_LINE) {
                for (float i = -10.00f; i < 20; i += 0.01f) {
                    float y = 0;
                    float x = i;
                    entryList.add(new Entry(x, y));

                }
            }else {
                entryList = new ArrayList<>();
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

        }
        return entryList;
    }

    @Override
    protected void onPostExecute(ArrayList<Entry> result) {
        if(result != null) {
            LineDataSet lineDataSet = new LineDataSet(result, function);
            int setColor = -1;
            switch(position) {
                case X_LINE:
                case Y_LINE:
                    setColor = Color.LTGRAY;
                    break;
                case FUNCTION_1:
                    setColor = Color.DKGRAY;
                    break;
                case FUNCTION_2:
                    setColor = Color.GREEN;
                    break;
                case FUNCTION_3:
                    setColor = Color.BLUE;
                    break;
            }
            if(setColor != -1) lineDataSet.setColor(setColor);
            GraphActivity graphActivity = null;
            if(graphContext != null) graphActivity = (GraphActivity) graphContext;
            List<ILineDataSet> dataSets = graphActivity.getDataSets();
            dataSets.add(lineDataSet);
            graphActivity.getChart().invalidate();
        }
    }
}

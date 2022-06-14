package com.example.calculator;

import static com.example.calculator.util.Constants.CHECK_LOG;
import static com.example.calculator.util.Constants.GRAPH_LOG_TAG;
import static com.example.calculator.util.Constants.FUNCTION_1;
import static com.example.calculator.util.Constants.FUNCTION_2;
import static com.example.calculator.util.Constants.FUNCTION_3;
import static com.example.calculator.util.Constants.RANGE;
import static com.example.calculator.util.Constants.START_NUM;
import static com.example.calculator.util.Constants.STEP;
import static com.example.calculator.util.Constants.X_String;
import static com.example.calculator.util.Constants.Y_LINE;
import static com.example.calculator.util.Constants.X_LINE;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.tan;

import android.app.Activity;
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
    private Activity activity;
    private int position;
    private String xStringMinus;

    public GraphAsyncTask(String function, Activity activity, int position) {
        this.function = function;
        this.activity = activity;
        this.position = position;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Entry> doInBackground(String... strings) {
        if(CHECK_LOG) Log.i(GRAPH_LOG_TAG, "doInBackground() " + position);

        ArrayList<Entry> entryList = new ArrayList<>();
        if(function != null && activity != null && function.length() != 0) {
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
                if (function != null && function.length() != 0) {
                    /* 전체 함수에서 y=을 제거한 String */
                    String functionDeleteEqualY = function.replaceFirst("y=", "");
                    Log.v("deleteEqualY", "firstFunctionDeleteEqualY : " + functionDeleteEqualY);

                    String functionOperator = functionDeleteEqualY.substring(1, 2);
                    Log.v("functionOperator", "functionOperator : " + functionOperator);
                    String firstNum = functionDeleteEqualY.substring(2);
                    Log.v("firstNum", "firstNum : " + firstNum);

                    /* 전체 함수에서 y=을 제거 후, x의 index 확인 */
                    int firstFindXTest = functionDeleteEqualY.indexOf(X_String);
                    Log.v("firstFindXTest", "firstFindXTest : " + firstFindXTest);

                    /* x앞에 붙어 있는 숫자 및 문자열 확인 */
                    String xFirstFront = functionDeleteEqualY.substring(0, firstFindXTest);
                    Log.v("xFirstFront", "xFirstFront : " + xFirstFront);

                    /* x뒤에 붙어 있는 계산 및 연산 String 확인 */
                    String xFirstBehind = functionDeleteEqualY.substring(firstFindXTest + 1);
                    String xFirstBehindNumber = xFirstBehind.replaceAll("[^0-9]", "");
                    Log.v("xFirstBehind", "xFirstBehind : " + xFirstBehind);
                    Log.v("xFirstBehindNumber", "xFirstBehindNumber : " + xFirstBehindNumber);

                    /* 연산 String 을 Parsing 하여 계산 */
                    String[] xFirstBehindArray = xFirstBehind.split("");
                    String xFirstBehindCalculate = "";
                    int behindCalculateResult = 0;
                    if (xFirstBehindArray.length >= 4) {
                        switch (xFirstBehindArray[3]) {
                            case "+":
                                behindCalculateResult = Integer.parseInt(xFirstBehindArray[2]) + Integer.parseInt(xFirstBehindArray[4]);
                                break;
                            case "-":
                                behindCalculateResult = Integer.parseInt(xFirstBehindArray[2]) - Integer.parseInt(xFirstBehindArray[4]);
                                break;
                            case "*":
                                behindCalculateResult = Integer.parseInt(xFirstBehindArray[2]) * Integer.parseInt(xFirstBehindArray[4]);
                                break;
                            case "/":
                                behindCalculateResult = Integer.parseInt(xFirstBehindArray[2]) / Integer.parseInt(xFirstBehindArray[4]);
                                break;
                        }
                    } else if (xFirstBehindArray.length >= 3) {
                        behindCalculateResult = Integer.parseInt(xFirstBehindArray[2]);
                    }
                    String behindCalculate = Integer.toString(behindCalculateResult);

                    /* x앞에 오는 값이 숫자인지 문자인지 확인 */
                    boolean xFront = GraphActivity.numberCheck(xFirstFront);

                    String operatorCheck = functionDeleteEqualY.substring(firstFindXTest + 1, firstFindXTest + 2);
                    Log.v("operatorCheck", "operatorCheck : " + operatorCheck);
                    int xIntFront = 0;
                    if (xFirstFront != "") {
                        xIntFront = Integer.parseInt(xFirstFront);
                    }
                    if (xFront) { /* y = 정수 , x = 정수 , x항과 숫자항이 존재할 때, x항이 뒤로가는 경우는 아직 생각 안함 */
                        if (operatorCheck.equals("+")) {
                            for (float i = START_NUM; i < RANGE; i += STEP) {
                                float y = xIntFront * i + Integer.parseInt(behindCalculate);
                                float x = i;
                                entryList.add(new Entry(x, y));
                            }
                        } else if (operatorCheck.equals("-")) {
                            for (float i = START_NUM; i < RANGE; i += STEP) {
                                float y = xIntFront * i - Integer.parseInt(behindCalculate);
                                float x = i;
                                entryList.add(new Entry(x, y));
                            }
                        } else if (operatorCheck.equals("*")) {
                            for (float i = START_NUM; i < RANGE; i += STEP) {
                                float y = xIntFront * i * Integer.parseInt(behindCalculate);
                                float x = i;
                                entryList.add(new Entry(x, y));
                            }
                        } else if (operatorCheck.equals("/")) {
                            for (float i = START_NUM; i < RANGE; i += STEP) {
                                float y = xIntFront * i / Integer.parseInt(behindCalculate);
                                float x = i;
                                entryList.add(new Entry(x, y));
                            }
                        }
                    } else {
                        if (xFirstFront.charAt(0) == '-') {
                            xStringMinus = xFirstFront.substring(1);
                            if (GraphActivity.numberCheck(xStringMinus)) {
                                if (operatorCheck.equals("+")) {
                                    for (float i = START_NUM; i < RANGE; i += STEP) {
                                        float y = xIntFront * i + 2;
                                        float x = i;
                                        entryList.add(new Entry(x, y));
                                    }
                                } else if (operatorCheck.equals("-")) {
                                    for (float i = START_NUM; i < RANGE; i += STEP) {
                                        float y = xIntFront * i - Integer.parseInt(behindCalculate);
                                        float x = i;
                                        entryList.add(new Entry(x, y));
                                    }
                                } else if (operatorCheck.equals("*")) {
                                    for (float i = START_NUM; i < RANGE; i += STEP) {
                                        float y = xIntFront * i * Integer.parseInt(behindCalculate);
                                        float x = i;
                                        entryList.add(new Entry(x, y));
                                    }
                                } else if (operatorCheck.equals("/")) {
                                    for (float i = START_NUM; i < RANGE; i += STEP) {
                                        float y = xIntFront * i / Integer.parseInt(behindCalculate);
                                        float x = i;
                                        entryList.add(new Entry(x, y));
                                    }
                                } else if ("1" == "2") {
                                    // 부호다음에 cos sin 같은 문자 오는거 생각
                                }
                            } else {
                                if (xFirstFront.charAt(1) == 's') {
                                    if (operatorCheck.equals("+")) {
                                        for (float i = START_NUM; i < RANGE; i += STEP) {
                                            float y = (float) sin(i) + 2;
                                            float x = i;
                                            entryList.add(new Entry(x, y));
                                        }
                                    } else if (operatorCheck.equals("-")) {
                                        for (float i = START_NUM; i < RANGE; i += STEP) {
                                            float y = (float) sin(i) - Integer.parseInt(behindCalculate);
                                            float x = i;
                                            entryList.add(new Entry(x, y));
                                        }
                                    } else if (behindCalculate.equals("*")) {
                                        for (float i = START_NUM; i < RANGE; i += STEP) {
                                            float y = (float) sin(i) * Integer.parseInt(behindCalculate);
                                            float x = i;
                                            entryList.add(new Entry(x, y));
                                        }
                                    } else if (behindCalculate.equals("/")) {
                                        for (float i = START_NUM; i < RANGE; i += STEP) {
                                            float y = (float) sin(i) / Integer.parseInt(xFirstBehindNumber);
                                            float x = i;
                                            entryList.add(new Entry(x, y));
                                        }
                                    }
                                } else if (xFirstFront.charAt(1) == 'c') {
                                    if (operatorCheck.equals("+")) {
                                        for (float i = START_NUM; i < RANGE; i += STEP) {
                                            float y = (float) cos(i) + 2;
                                            float x = i;
                                            entryList.add(new Entry(x, y));
                                        }
                                    } else if (operatorCheck.equals("-")) {
                                        for (float i = START_NUM; i < RANGE; i += STEP) {
                                            float y = (float) cos(i) - Integer.parseInt(xFirstBehindNumber);
                                            float x = i;
                                            entryList.add(new Entry(x, y));
                                        }
                                    } else if (operatorCheck.equals("*")) {
                                        for (float i = START_NUM; i < RANGE; i += STEP) {
                                            float y = (float) cos(i) * Integer.parseInt(xFirstBehindNumber);
                                            float x = i;
                                            entryList.add(new Entry(x, y));
                                        }
                                    } else if (operatorCheck.equals("/")) {
                                        for (float i = START_NUM; i < RANGE; i += STEP) {
                                            float y = (float) cos(i) / Integer.parseInt(behindCalculate);
                                            float x = i;
                                            entryList.add(new Entry(x, y));
                                        }
                                    }
                                } else if (xFirstFront.charAt(1) == 't') {
                                    if (operatorCheck.equals("+")) {
                                        for (float i = START_NUM; i < RANGE; i += STEP) {
                                            float y = (float) tan(i) + 2;
                                            float x = i;
                                            entryList.add(new Entry(x, y));
                                        }
                                    } else if (operatorCheck.equals("-")) {
                                        for (float i = START_NUM; i < RANGE; i += STEP) {
                                            float y = (float) tan(i) - Integer.parseInt(behindCalculate);
                                            float x = i;
                                            entryList.add(new Entry(x, y));
                                        }
                                    } else if (operatorCheck.equals("*")) {
                                        for (float i = START_NUM; i < RANGE; i += STEP) {
                                            float y = (float) tan(i) * Integer.parseInt(behindCalculate);
                                            float x = i;
                                            entryList.add(new Entry(x, y));
                                        }
                                    } else if (operatorCheck.equals("/")) {
                                        for (float i = START_NUM; i < RANGE; i += STEP) {
                                            float y = (float) tan(i) / Integer.parseInt(behindCalculate);
                                            float x = i;
                                            entryList.add(new Entry(x, y));
                                        }
                                    }
                                }
                            }
//                Log.v("xStringMinus", "xStringMinus : " + xStringMinus);

                        } else if (xFirstFront.equals("")) {    //루트랑 스퀘어 추가 생각 필요
                            if (operatorCheck.equals("+")) {
                                for (float i = START_NUM; i < RANGE; i += STEP) {
                                    float y = i + 2;
                                    float x = i;
                                    entryList.add(new Entry(x, y));
                                }
                            } else if (operatorCheck.equals("-")) {
                                for (float i = START_NUM; i < RANGE; i += STEP) {
                                    float y = i - Integer.parseInt(behindCalculate);
                                    float x = i;
                                    entryList.add(new Entry(x, y));
                                }
                            } else if (operatorCheck.equals("*")) {
                                for (float i = START_NUM; i < RANGE; i += STEP) {
                                    float y = i * Integer.parseInt(behindCalculate);
                                    float x = i;
                                    entryList.add(new Entry(x, y));
                                }
                            } else if (operatorCheck.equals("/")) {
                                for (float i = START_NUM; i < RANGE; i += STEP) {
                                    float y = i / Integer.parseInt(behindCalculate);
                                    float x = i;
                                    entryList.add(new Entry(x, y));
                                }
                            }
                        }
                    }
                }
            }

        }
        return entryList;
    }

    @Override
    protected void onPostExecute(ArrayList<Entry> result) {
        if(CHECK_LOG) Log.i(GRAPH_LOG_TAG, "onPostExecute() " + position);

        if(result != null) {
            LineDataSet lineDataSet = new LineDataSet(result, function);

            if(CHECK_LOG) Log.i(GRAPH_LOG_TAG, position + " lineDataSet = " + lineDataSet);
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
            if(setColor != -1) {
                lineDataSet.setColor(setColor);
                lineDataSet.setCircleColor(setColor);
            }
            if(CHECK_LOG) Log.i(GRAPH_LOG_TAG, position + " setColor = " + setColor);
            GraphActivity graphActivity = null;
            if(activity != null) graphActivity = (GraphActivity) activity;
            List<ILineDataSet> dataSets = graphActivity.getDataSets();
            dataSets.add(lineDataSet);
            if(position == Y_LINE) graphActivity.setXYLineDone(true);
            if(position == FUNCTION_3) graphActivity.setDrawPlay(false);
        }
    }
}

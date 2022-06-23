package com.example.ds_calculator;




import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

public class CalculateHelper {
    public static double num1;
    public static double num2;
    public static double resultNumber;
    private String sin = "+";

    //������� input�� ���� �����Ͽ� ArrayList�� �����ϴ� �޼ҵ�
    private ArrayList splitTokens(String equation) {
        String[] inputData = equation.split(" "); //������ ����

        ArrayList inputList = new ArrayList();
        double number = 1;

        boolean flag = false;
        for (String data : inputData) {
            if (data.equals(" ")) {
                continue;
            }
            if (checkNumber(data)) {
                number = number * Double.parseDouble(data);
                flag = true;
            } else {
                if (flag) {
                    inputList.add(number);
                    if(data.equals("-")){
                        number = -1;
                    }else{
                        number = 1;
                    }
                }
                if(data.equals("-")){
                    number = -1;
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

    //���� ǥ������� ����
    private ArrayList infixToPostfix(ArrayList inputData) {
        ArrayList result = new ArrayList();
        HashMap level = new HashMap();
        Stack stack = new Stack();

        //�� ��ȣ�� �켱���� ����. ���ϱ�, ������ > ���ϱ�, ���� > ��Ÿ
        level.put("*", 3);
        level.put("/", 3);
        level.put("+", 2);
        level.put("-", 2);
        level.put("(", 1);

        for (Object object : inputData) {
            if (object.equals("(")) {           //object�� ��ȣ �϶�
                stack.push(object);
            } else if (object.equals(")")) {
                while (!stack.peek().equals("(")) { //�ٷ� �տ� "("�ִ��� Ȯ��
                    Object val = stack.pop();
                    if (!val.equals("(")) {
                        result.add(val);
                    }
                }
                stack.pop();
            } else if (level.containsKey(object)) {     //object�� ��ȣ�̸� put�ѰͰ� ������ �ִ��� Ȯ��
                if (stack.isEmpty()) {                  //��ȣ �켱���� ��
                    stack.push(object);
                } else {
                    if (Double.parseDouble(level.get(stack.peek()).toString()) >= Double.parseDouble(level.get(object).toString())) {
                        result.add(stack.pop());
                        stack.push(object);
                    } else {
                        stack.push(object);
                    }
                }
            } else {                                    //object�� ���ڸ� ����Ʈ�� �߰�
                result.add(object);
            }
        }

        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }

        return result;
    }

    //���� ǥ����� ���
    private Double postFixEval(ArrayList expr) {
        Stack numberStack = new Stack();
        for (Object o : expr) {
            if (o instanceof Double) {
                numberStack.push(o);
            } else if (o.equals("+")) {
                num1 = (Double) numberStack.pop();
                num2 = (Double) numberStack.pop();
                numberStack.push(num2 + num1);
            } else if (o.equals("-")) {
                num1 = (Double) numberStack.pop();
                num2 = (Double) numberStack.pop();
                numberStack.push(num2 + num1);
            } else if (o.equals("*")) {
                num1 = (Double) numberStack.pop();
                num2 = (Double) numberStack.pop();
                numberStack.push(num2 * num1);
            } else if (o.equals("/")) {
                num1 = (Double) numberStack.pop();
                num2 = (Double) numberStack.pop();
                numberStack.push(num2 / num1);
            }
        }

        resultNumber = (Double) numberStack.pop();

        return resultNumber;
    }

    public Double process(String equation) {
        ArrayList postfix = infixToPostfix(splitTokens(equation));      // ����ǥ��� ���� ����
        Double result = postFixEval(postfix);               //����ǥ��� ��� ����
        return result;
    }

    public boolean checkNumber(String str) {        //���ڸ� true ��ȣ�� false
        char check;

        if (str.equals(""))         //����ִ��� üũ
            return false;

        for (int i = 0; i < str.length(); i++) {
            check = str.charAt(i);
            if (check < 48 || check > 58) {
                if (check != '.') {
                    if(str.length()<2){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public boolean checkError(String str) {
        char check;

        if (str.equals(""))         //����ִ��� üũ
            return false;

        for (int i = 0; i < str.length(); i++) {
            check = str.charAt(i);
            if (check < 48 || check > 58) {
                if (check != '.' && check != '+' && check != '-' && check != '*' && check != '/' && check != '(' && check != ')' && check != ' ')
                    return true;
            }
        }
        return false;
    }


    public String sorted(String reNumber) {
        String[] arrresult = reNumber.split(" ");
        ArrayList<String> resultList = new ArrayList<String>();
        ArrayList<String> bracket = new ArrayList<String>();  //��ȣ ����
        ArrayList<String> muldiv = new ArrayList<String>();  //* , /
        ArrayList<String> addsub = new ArrayList<String>();  // + , -
        Collections.addAll(resultList, arrresult);
        String bracketStr = "";
        String muldivStr = "";
        String addsubStr = "";
        String bracketMuldivStr = "";
        String bracketAddsubStr = "";
        int count = -1;
        for (int i = 0; i < resultList.size(); i++) {
            if (resultList.get(i).equals("(")) {
                if (bracket.size() > 0) {
                    bracket.add(resultList.get(i - 1));       //��ȣ�� 2�� �̻��� ��쿡 ���̿� �� ��ȣ�� �߰�      + �� ���÷�>>> ex) ( 2 x 3 ) + ( 3 / 4 )
                }
                bracket.add("(");
                while (!resultList.get(i).equals(")")) {        // ���� ��ȣ ������ ������ ��δ�´�.
                    i++;
                    bracket.add(resultList.get(i));
                }
                i++;
                if (i == resultList.size()) {                     //i�� �ִ�ġ�� ��� break;
                    break;
                }
            }
            if (resultList.get(i).equals("*") || resultList.get(i).equals("/")) {               // * , / �� �и�
                if(i>1){
                    if(resultList.get(i-2).equals("+") || resultList.get(i-2).equals("-")){
                        muldiv.add(resultList.get(i-2));
                    }
                }
                if(!(resultList.get(i - 1).equals("(") || resultList.get(i - 1).equals(")"))){
                    muldiv.add(resultList.get(i - 1));
                }
                boolean vo = true;
                while (vo) {
                    if(i < resultList.size()-1){
                        if(resultList.get(i+1).equals("(")){
                            break;
                        }
                    }
                    if (resultList.get(i).equals("+") || resultList.get(i).equals("-") || resultList.get(i).equals("(")) { //���� ���� ��ȣ���� ��ȣ ���ö����� �� ���ƶ�
                        vo = false;
                        if (resultList.get(i).equals("(")) {
                            i = i-2;
                        }
                    } else {
                        muldiv.add(resultList.get(i));
                        i++;
                    }
                    if (i == resultList.size()) {             //i�� �ִ�ġ�� ��� break;
                        break;
                    }
                }
                if (i == resultList.size()) {                //i�� �ִ�ġ�� ��� break;
                    break;
                }
            }
            if(addsub.size() == 0 && (resultList.get(1).equals("+") || resultList.get(1).equals("-"))){
                addsub.add(resultList.get(0));
                count++;
            }
            if (resultList.get(i).equals("+") || resultList.get(i).equals("-")) {
                boolean bo = true;
                while (bo) {
                    if(i < resultList.size()-1){
                        if(resultList.get(i+1).equals("(")){
                            break;
                        }
                    }
                    if (resultList.get(i).equals("*") || resultList.get(i).equals("/") || resultList.get(i).equals("(")) {  //���� ������ ��ȣ���� ��ȣ ���ö����� �� ���ƶ�
                        bo = false;
                        if (resultList.get(i).equals("(")) {        //��ȣ ���Ⱑ ������ ��ȣ ���� ���� ��ȣ ������  5 (+) (
                            addsub.remove(count);
                            count--;
                        } else {
                            addsub.remove(count);                           // * , / �� ������ �� �� ���ڿ� ��ȣ�� ������ 2 (+ 5) *
                            addsub.remove(count - 1);
                            count = count -2;
                        }
                        i--;
                    } else {
                        addsub.add(resultList.get(i));
                        i++;
                        count++;
                    }
                    if (i == resultList.size()) {                 //i�� �ִ�ġ�� ��� break;
                        break;
                    }
                }
            }
        }
        if (bracket.size() != 0) {                              //��ȣ �迭 ����
            ArrayList<String> resultList5 = new ArrayList<String>();              //��ȣ ���� ������
            ArrayList<String> resultList6 = new ArrayList<String>();              //��ȣ ���� ����
            if(bracket.contains("*") || bracket.contains("/")){                     //��ȣ �ȿ��� �켱���� �����ؼ� ����������, ���� ���� ���� ������
                for(int i = 0; i<bracket.size(); i++){
                    if(bracket.get(i).equals("*") || bracket.get(i).equals("/")){
                        resultList5.add(bracket.get(i));
                        if(checkNumber(bracket.get(i+1))){
                            resultList5.add(bracket.get(i+1));
                        }else{
                            resultList5.add(bracket.get(i+2));
                        }
                    }
                    if(bracket.get(i).equals("+") || bracket.get(i).equals("-")){
                        if(bracket.get(i+2).equals("*") || bracket.get(i+2).equals("/")){
                            resultList5.add(bracket.get(i+2));
                            resultList5.add(bracket.get(i+1));
                        }else if(bracket.get(i+1).equals("(")){
                            resultList6.add(bracket.get(i));
                            resultList6.add(bracket.get(i + 2));
                        }else{
                            resultList6.add(bracket.get(i));
                            resultList6.add(bracket.get(i + 1));
                        }
                    }
                    if(i==1){                       //���� ��ȣ[2]�� ù ����[1]�� ��� �迭�� ������ ���Ѵ�. [0]�� ��ȣ
                        if(bracket.get(2).equals("*") || bracket.get(2).equals("/")){
                            resultList5.add(bracket.get(1));
                        }else{
                            resultList6.add(bracket.get(1));
                        }
                    }
                }
                String test_separ = separation(resultList5);
                if(resultList6.get(0).equals("+") || resultList6.get(0).equals("-")){
                    sin = resultList6.get(0);
                    resultList6.remove(0);
                }
                String test_separa = separation(resultList6);
                bracketMuldivStr = "( "+ test_separ.substring(3);
                bracketAddsubStr = test_separa + " )";
                bracketStr = bracketMuldivStr + bracketAddsubStr;
            }else{
                bracketStr = merge(bracket);
                bracketStr = "( "+ bracketStr.substring(3) + " )";                //��ȣ�ȿ� ���� �������� ���� ��� ���� �켱������ �����Ѵ�.
            }
        }
        if (muldiv.size() != 0) {                       //���� ������ �迭 ����
            if(!checkNumber(muldiv.get(0))){
                sin = muldiv.get(0);
                muldiv.remove(0);
            }
            muldivStr = separation(muldiv);             //�߰��� ������ �����ִ� ���� ������ ��� �з�
        }
        if(addsub.size() != 0){                     //���� ���� �迭 ����
            addsubStr = merge(addsub);
        }
        String result = "";
        result = bracketStr + muldivStr + addsubStr;
        if(!checkNumber(String.valueOf(result.charAt(0)))){
            if(result.charAt(1) == '-'){
                result = result.substring(3);
                result = "-" + result;
            }else if(result.charAt(0) != '('){
                result = result.substring(3);
            }
            result.replace(" * - ", " * -");
            result.replace(" / - ", " / -");
        }
        return result;
    }

    public String separation(ArrayList dm){                 //���� ������ �� ������ ���� ��迭
        ArrayList<String> test1 = new ArrayList<String>();
        ArrayList<String> test_arr = new ArrayList<String>();
        String separation = "";
        if (dm.size() != 0) {               //���� ������ �迭 ����
            int count = 0;
            for(int i =0; i<dm.size(); i++){
                test_arr.add(String.valueOf(dm.get(i)));
                if(dm.get(i).equals("+") || dm.get(i).equals("-")){
                    test_arr.remove(count);
                    test1.add( " "+ sin +" "+ merge(test_arr).substring(3));
                    sin = String.valueOf(dm.get(i));
                    test_arr.clear();
                    count = 0;
                }else if(i == dm.size()-1){
                    test1.add( " "+ sin +" "+ merge(test_arr).substring(3));
                    test_arr.clear();
                }
                count++;
            }

            String[] test_str = new String[test1.size()];   // ���� ������ ����

            for(int i=0; i<test1.size(); i++){
                test_str[i] = test1.get(i);
            }
            int[] test_int = new int[test_str.length];     //�� �������� ���� ū ���� ���� ��Ƽ� ��
            for(int i =0; i<test_str.length; i++){
                if(test_str[i].charAt(1) == '-'){
                    test_int[i] = Integer.parseInt("-" + test_str[i].charAt(3));
                }else{
                    test_int[i] = Integer.parseInt(test_str[i].substring(3,4));
                }
            }
            for(int i =0; i<test_str.length; i++) {
                for (int k = 0; k <test_str.length; k++) {
                    if (test_int[i] > test_int[k]) {
                        int b = test_int[i];
                        test_int[i] = test_int[k];
                        test_int[k] = b;
                        String e = test_str[i];
                        test_str[i] = test_str[k];
                        test_str[k] = e;
                    }
                }
            }
            for(int i =0; i<test_str.length; i++){
                separation += test_str[i];
            }
        }
        sin = "+";
        return separation;
    }


    public String merge(ArrayList value){
        String[] numBerStr = new String[value.size()];        // ��� ���� �Ѵ� ���� string
        Integer[] numBer = new Integer[value.size()];              // numberStr�� ���� ��� ������ int�� �Ѱܹ���
        ArrayList<String> markNum = new ArrayList<String>();        //��ȣ + ���ڰ� �Բ� ����ִ� �迭
        Integer[] index = new Integer[value.size()];          //�켱������ ���� �ٲ� ������� string�� ���� �뵵
        int count =0;
        for(int i =0; i<value.size(); i++){
            if(!(value.get(i).equals("(") || value.get(i).equals(")"))){
                if(!(value.get(i).equals("+") || value.get(i).equals("*") || value.get(i).equals("/"))){
                    if(value.get(i).equals("-")){
                        index[count] = count;
                        numBerStr[count] = "-" + value.get(i+1);
                        count++;
                        i++;
                    }else{
                        index[count] = count;
                        numBerStr[count] = String.valueOf(value.get(i));
                        count++;
                    }
                }
            }
        }
        int count1 = 0;
        if(checkNumber(String.valueOf(value.get(count1)))) {     //ù ��ȣ ���ϱ�
            if (value.size() > 1) {
                if(value.get(count1 + 1).equals("*") || value.get(count1 + 1).equals("/")) {
                    markNum.add(" " + value.get(count1 + 1) + " " + value.get(count1));
                }else{
                    markNum.add(" + " + value.get(count1));
                }
            } else {
                markNum.add(" + " + value.get(count1));
            }
        }
        count1++;
        while(count1 != value.size()){
            if (value.get(count1).equals("(")) {
                markNum.add(" + " + value.get(count1 + 1));
                count1++;
            }else if (checkNumber(String.valueOf(value.get(count1)))) {
                if(value.get(count1 - 1).equals("(")){
                    markNum.add(" + " + value.get(count1));
                }else{
                    markNum.add(" " + value.get(count1 - 1) + " "+value.get(count1));
                }
            }
            count1++;
        }
        for(int i =0; i<count; i++){
            numBer[i] = Integer.parseInt(numBerStr[i]);
        }
        for(int i =0; i<count; i++){
            for(int k =0; k<count; k++){
                if(numBer[i]>numBer[k]) {
                    int a = numBer[i];
                    numBer[i] = numBer[k];
                    numBer[k] = a;
                    int bx = index[i];
                    index[i] = index[k];
                    index[k] = bx;
                }
            }
        }
        String str = "";                        //���� ��
        for(int i =0; i<count; i++){
            str += markNum.get(index[i]);
        }
        return str;
    }
}

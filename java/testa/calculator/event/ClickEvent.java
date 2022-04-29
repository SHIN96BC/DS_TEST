package testa.calculator.event;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.testa.MainActivity;
import com.example.testa.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClickEvent implements View.OnClickListener {

    private MainActivity mainActivity;

    public ClickEvent(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        EditText editText = mainActivity.getTextView();
        switch (view.getId()) {
            case R.id.btnRemove:
                String textTemp1 = editText.getText().toString();
                if(textTemp1.length() < 1) return;
                editText.setText("");
                textTemp1 = textTemp1.substring(0, textTemp1.length()-1);
                editText.append(textTemp1);
                break;
            case R.id.btnRemoveAll:
                editText.setText("");
                break;
            case R.id.btnEquals:
                String text = editText.getText().toString();
                calculation(text);
                break;
            default:
                String btnText = (String) button.getText();
                editText.append(btnText);
        }
    }

    private String calculation(String text) {
//        방법 1 배열로 처리
//        먼저 split으로 모든 연산기호와 숫자들을 잘라서 배열에 넣어준다.
//        String[] textArray = text.split("\\+|-|\\*|/");
//        연산기호로 split을 하면 연산기호가 배열에 담기지 않는 문제가 있다.(공백으로 나누거나 해야할거 같다)

//        방법 2 문자열로 처리
//        indexOf 로 먼저 곱셈 나눗셈을 찾고 둘 중에 어떤게 더 앞에 있는지 구분해서
//        substring 을 사용하여 방금 찾은 곱셈 혹은 나눗셈 기호로 부터 바로 앞과 뒤에 있는 연산자를
//        indexOf, lastIndexOf 로 찾아서 그 값들을 비교해서 제일 작은 값, 큰 값을 찾아서
//        앞뒤로 잘라주고, 변수에 넣어둔다. 그리고 현재 연산해야 할 부분을 연산하고 다시 문자열을
//        합쳐준다. 이 작업을 계산이 끝날때 까지 반복한다.
        String textTemp = text;
        Pattern pattern = Pattern.compile("\\+|-|\\*|/");
        if(textTemp != null) {
            if(textTemp.contains("*") || textTemp.contains("/")) {
                if(textTemp.indexOf("*") < textTemp.indexOf("/")) {
                    String start = textTemp.substring(0, textTemp.indexOf("*"));
                    String end = textTemp.substring(textTemp.indexOf("*")+1, textTemp.length()-1);
                    Matcher matcher = pattern.matcher(end);
                    System.out.println("matcher.start() = " + matcher.start());
//                    end = end.substring(matcher.start());
                    System.out.println("start = " + start);
                    System.out.println("end = " + end);
                }else {

                }
            }
        }

//      방법 3 stack 혹은 Deque로 처리
//      후위 표기식으로 계산

        return null;
    }

    private int plus() {
        return -1;
    }
    private int minus() {
        return -1;
    }
    private int multiply() {
        return -1;
    }
    private int division() {
        return -1;
    }
}

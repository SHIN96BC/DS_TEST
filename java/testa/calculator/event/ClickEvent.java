package testa.calculator.event;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.testa.MainActivity;
import com.example.testa.R;

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
                textTemp1 = textTemp1.substring(0, textTemp1.length()-1);
                editText.append(textTemp1);
            case R.id.btnRemoveAll:
                editText.setText("");
            case R.id.btnEquals:

            case R.id.btnPlus:

            default:
                String btnText = (String) button.getText();
                editText.append(btnText);
        }
    }

    private String calculation(String text) {
        if(text != null) {
            while(text.contains("*") || text.contains("/")) {
                
            }
        }
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

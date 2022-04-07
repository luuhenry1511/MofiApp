package tdmu.edu.vn.mofi.helpers;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;


public class InputValidation {
    private Context context;

    public InputValidation(Context context) {
        this.context = context;
    }
    /* CHECK RỖNG*/
    public boolean isInputEditTextFilled(EditText EditText, String message) {
        String value = EditText.getText().toString().trim();
        if (value.isEmpty()) {
            Toast.makeText(context.getApplicationContext(),message,Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    /* CHECK định dạng mail*/
    public boolean isInputEditTextEmail(EditText EditText, String message) {
        String value = EditText.getText().toString().trim();

        if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            Toast.makeText(context.getApplicationContext(),message,Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    /*CHECK mật khẩu vs nhập lại mk */
    public boolean isInputEditTextMatches(EditText textInputEditText1, EditText textInputEditText2, String message) {
        String value1 = textInputEditText1.getText().toString().trim();
        String value2 = textInputEditText2.getText().toString().trim();
        if (!value1.contentEquals(value2)) {
            Toast.makeText(context.getApplicationContext(),message,Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    /**
     * method to Hide keyboard
     *
     * @param view
     */
    private void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}

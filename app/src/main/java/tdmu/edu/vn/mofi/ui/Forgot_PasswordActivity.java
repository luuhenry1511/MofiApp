package tdmu.edu.vn.mofi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import tdmu.edu.vn.mofi.DangNhap;
import tdmu.edu.vn.mofi.R;
import tdmu.edu.vn.mofi.SQL.DatabaseHelper;
import tdmu.edu.vn.mofi.helpers.InputValidation;
import tdmu.edu.vn.mofi.modals.User;


public class Forgot_PasswordActivity extends AppCompatActivity {

    private final AppCompatActivity activity = Forgot_PasswordActivity.this;


    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextConfirmPassword;
    Button btnRegister;
    TextView txtDangNhap;
    InputValidation inputValidation;
    DatabaseHelper databaseHelper;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().hide();
        initViews();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDataToSQLite();
            }
        });

        txtDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin = new Intent(getApplicationContext(), DangNhap.class);
                startActivity(intentLogin);
            }
        });
        initObjects();
    }

    private void initViews() {

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextConfirmPassword = (EditText) findViewById(R.id.editTextPasswordAgain);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        txtDangNhap = (TextView) findViewById(R.id.txtDangNhap);
    }


    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();
    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite() {

        if (!inputValidation.isInputEditTextFilled(editTextEmail, getString(R.string.error_message_email_filled))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(editTextPassword, getString(R.string.error_message_mk_filled))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(editTextPassword, editTextConfirmPassword, getString(R.string.error_message_match))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(editTextEmail, getString(R.string.error_message_email_format))) {
            return;
        }
        if (databaseHelper.checkUser(editTextEmail.getText().toString().trim())) {
            if(databaseHelper.updatePasswordUser(editTextEmail.getText().toString().trim(),editTextPassword.getText().toString().trim())){
                Toast.makeText(getApplicationContext(),"Cập nhật lại mật khẩu thành công",Toast.LENGTH_SHORT).show();
                emptyInputEditText();
            }

        } else {

            Toast.makeText(getApplicationContext(),"Email chưa đăng ký tài khoản",Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Làm mới các editText
     */
    private void emptyInputEditText() {
        editTextEmail.setText(null);
        editTextPassword.setText(null);
        editTextConfirmPassword.setText(null);
    }
}
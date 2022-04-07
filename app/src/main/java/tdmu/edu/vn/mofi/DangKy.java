package tdmu.edu.vn.mofi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import tdmu.edu.vn.mofi.SQL.DatabaseHelper;
import tdmu.edu.vn.mofi.helpers.InputValidation;
import tdmu.edu.vn.mofi.modals.User;


public class DangKy extends AppCompatActivity {

    private final AppCompatActivity activity = DangKy.this;

    EditText editTextName;
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
        setContentView(R.layout.activity_dang_ky);
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
        editTextName = (EditText) findViewById(R.id.editTextName);
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
        if (!inputValidation.isInputEditTextFilled(editTextName, getString(R.string.error_message_name_filled))){
            return;
        }
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
        if (!databaseHelper.checkUser(editTextEmail.getText().toString().trim())) {
            user.setName(editTextName.getText().toString().trim());
            user.setEmail(editTextEmail.getText().toString().trim());
            user.setPassword(editTextPassword.getText().toString().trim());
            databaseHelper.addUser(user);

            Toast.makeText(getApplicationContext(),"Đăng ký thành công",Toast.LENGTH_SHORT).show();
            emptyInputEditText();
            Intent intentLogin = new Intent(getApplicationContext(), DangNhap.class);
            startActivity(intentLogin);
        } else {

            Toast.makeText(getApplicationContext(),"Email đã được đăng ký tài khoản",Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Làm mới các editText
     */
    private void emptyInputEditText() {
        editTextName.setText(null);
        editTextEmail.setText(null);
        editTextPassword.setText(null);
        editTextConfirmPassword.setText(null);
    }
}
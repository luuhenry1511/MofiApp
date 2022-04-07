package tdmu.edu.vn.mofi;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import tdmu.edu.vn.mofi.SQL.DatabaseHelper;
import tdmu.edu.vn.mofi.helpers.InputValidation;
import tdmu.edu.vn.mofi.ui.Forgot_PasswordActivity;


public class DangNhap extends AppCompatActivity {

    private final AppCompatActivity activity = DangNhap.this;

    EditText textInputEditTextEmail;
    EditText textInputEditTextPassword;
    Button ButtonLogin;
    TextView dangky, quenmk;
    InputValidation inputValidation;
    DatabaseHelper DB;
    CheckBox cbRemember;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String USERNAME = "userNameKey";
    public static final String PASS = "passKey";
    public static final String REMEMBER = "remember";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangnhap);
        getSupportActionBar().hide();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        textInputEditTextEmail = (EditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (EditText) findViewById(R.id.textInputEditTextPassword);
        ButtonLogin = (Button) findViewById(R.id.appCompatButtonLogin);
        dangky = (TextView) findViewById(R.id.txtDangKy);
        quenmk= (TextView) findViewById(R.id.quenmk);
        cbRemember = (CheckBox) findViewById(R.id.cbRemember);
        loadData();//lấy dữ liệu đã lưu nếu có
        quenmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotPass = new Intent(getApplicationContext(), Forgot_PasswordActivity.class);
                startActivity(forgotPass);
            }
        });
        dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegister = new Intent(getApplicationContext(), DangKy.class);
                startActivity(intentRegister);
            }
        });

        ButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyFromSQLite();
            }
        });
        initObjects();

    }

    private void initObjects() {
        DB = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }


    private void verifyFromSQLite() {
        if (cbRemember.isChecked())
            //lưu lại thông tin đăng nhập
            saveData(textInputEditTextEmail.getText().toString(), textInputEditTextPassword.getText().toString());
        else
            clearData();//xóa thông tin đã lưu

        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, getString(R.string.error_message_name_filled))){
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, getString(R.string.error_message_name_filled))){
            return;
        }
        String email= textInputEditTextEmail.getText().toString().trim();
        String pass= textInputEditTextPassword.getText().toString().trim();

            boolean checkuserpass=DB.checkUser(email,pass);
            boolean checkexist = DB.checkUser(email);
            if (!checkexist){
                Toast.makeText(getApplicationContext(),"Tài khoản không tồn tại",Toast.LENGTH_SHORT).show();
            }
            if (checkuserpass)
            {
                Toast.makeText(getApplicationContext(),"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("email",textInputEditTextEmail.getText().toString().trim());
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(),"Sai tên đăng nhập hoặc mật khẩu",Toast.LENGTH_SHORT).show();
            }
//                Intent intent= new Intent(getApplicationContext(),trangchu.class);
//                intent.putExtra("email",textInputEditTextEmail.getText().toString().trim());
//                startActivity(intent);
    }

    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }
    private void clearData() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }

    private void saveData(String username, String Pass) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(USERNAME, username);
        editor.putString(PASS, Pass);
        editor.putBoolean(REMEMBER,cbRemember.isChecked());
        editor.commit();
    }

    private void loadData() {
        if(sharedpreferences.getBoolean(REMEMBER,false)) {
            textInputEditTextEmail.setText(sharedpreferences.getString(USERNAME, ""));
            textInputEditTextPassword.setText(sharedpreferences.getString(PASS, ""));
            cbRemember.setChecked(true);
        }
        else
            cbRemember.setChecked(false);

    }
}

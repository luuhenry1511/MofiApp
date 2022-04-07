package tdmu.edu.vn.mofi.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import tdmu.edu.vn.mofi.DangNhap;
import tdmu.edu.vn.mofi.R;
import tdmu.edu.vn.mofi.SQL.DatabaseHelper;
import tdmu.edu.vn.mofi.modals.Giaodich;

public class InfoActivity extends Activity {
    EditText username, name, pass;
    Button btnSua, btnThoat, btnLuu, btnHuy;
    DatabaseHelper db;
    String user;
    private ArrayList<Giaodich> infomation;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        db=new DatabaseHelper(getApplicationContext());
        laydulieu();
        mapping();
        hienthidulieu();
        thoatapp();
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chinhsua();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hienthidulieu();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().length()<1||pass.getText().length()<1){
                    Toast.makeText(getApplicationContext(), "Không được bỏ trống thông tin", Toast.LENGTH_SHORT).show();
                } else{
                    db.updateUser(username.getText().toString(), user, pass.getText().toString());
                    db.close();
                    Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    hienthidulieu();
                }
            }
        });
    }
    public void laydulieu(){
        Intent intent = getIntent();
        user = intent.getStringExtra("email");
    }
    public void mapping(){
        username = (EditText) findViewById(R.id.UserName);
        name = (EditText) findViewById(R.id.Name);
        pass = (EditText) findViewById(R.id.Password);
        btnHuy = (Button) findViewById(R.id.btnCancel);
        btnLuu = (Button) findViewById(R.id.btnSave);
        btnSua = (Button) findViewById(R.id.btnUpdate);
        btnThoat = (Button) findViewById(R.id.btnExit);
    }
    public void hienthidulieu(){
        btnThoat.setEnabled(true);
        btnSua.setEnabled(true);
        btnLuu.setEnabled(false);
        btnHuy.setEnabled(false);
        btnHuy.setBackgroundResource(R.color.unable);
        btnLuu.setBackgroundResource(R.color.unable);
        btnSua.setBackgroundResource(R.drawable.bg_card);
        btnThoat.setBackgroundResource(R.drawable.bg_card);
        infomation= db.laythongtinUser(user);

        name.setEnabled(false);
        username.setEnabled(false);
        pass.setEnabled(false);
        name.setText(user);
        username.setText(infomation.get(0).getUser());
        pass.setText(infomation.get(0).getPhanloai());
    }
    public void chinhsua(){
        btnThoat.setEnabled(false);
        btnSua.setEnabled(false);
        btnThoat.setBackgroundResource(R.color.unable);
        btnSua.setBackgroundResource(R.color.unable);

        btnLuu.setEnabled(true);
        btnHuy.setEnabled(true);
        btnLuu.setBackgroundResource(R.drawable.bg_card);
        btnHuy.setBackgroundResource(R.drawable.bg_card);

        username.setEnabled(true);
        pass.setEnabled(true);
    }
    public void thoatapp(){
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin = new Intent(getApplicationContext(), DangNhap.class);
                startActivity(intentLogin);

            }
        });
    }
}

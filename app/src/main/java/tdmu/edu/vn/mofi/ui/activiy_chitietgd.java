package tdmu.edu.vn.mofi.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import tdmu.edu.vn.mofi.MainActivity;
import tdmu.edu.vn.mofi.R;
import tdmu.edu.vn.mofi.SQL.DatabaseHelper;

public class activiy_chitietgd extends AppCompatActivity {
    Spinner spinphannhom;
    EditText edsotien;
    EditText edlydo;
    EditText ngaygd;
    Button savegd, xoagd, suagd, huycs;
    ImageView lich;
    TextView khoantien;
    static final int DATE_DIALOG_ID = 0;
    int money, money2;
    String ngay;
    String thang;
    DatabaseHelper db;
    private int mYear,mMonth,mDay;
    Toast mToast;
    String user, sotien, lydo, time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activiy_chitietgd);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        db= new DatabaseHelper(this);

        mapping();
        laydulieu();
        money = Integer.parseInt(sotien);
        hienthidulieu();
        pickTime();


        if (money > 0){

            khoantien.setText("Khoản thu");
        } else {

            khoantien.setText("Khoản chi");
        }

        suagd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chinhsuadl();
            }
        });
        savegd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ngaygd.getText().length()<1||edsotien.getText().length()<1||edlydo.getText().length()<1){
                    Toast.makeText(getApplicationContext(), "Bạn cần nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
//                    edsotien.requestFocus();

                }else{
                    if(mDay<10){
                        ngay="0"+mDay;

                    }else{
                        ngay=mDay+"";

                    }
                    if((mMonth+1)<10){
                        thang="0"+(mMonth+1);

                    }else{
                        thang=(mMonth+1)+"";

                    }
                    try{
                    if (money<0) {

                        db.updategiaodich(spinphannhom.getSelectedItem().toString(),
                                edlydo.getText().toString(), "-" + edsotien.getText().toString(), ngaygd.getText().toString(),
                                ngay + "", "" + thang, mYear + "", user, time, sotien, lydo);
                        db.close();



                    }else{
                        db.updategiaodich(spinphannhom.getSelectedItem().toString(),
                                edlydo.getText().toString(), edsotien.getText().toString(), ngaygd.getText().toString(),
                                ngay + "", "" + thang, mYear + "", user, time, sotien, lydo);
                        db.close();


                    }

                    Toast.makeText(getApplicationContext(), "Cập nhật thành công.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("email", user);
                    startActivityForResult(intent, 8);
                    finish();
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),"Dữ liệu không hợp lệ",Toast.LENGTH_SHORT).show();
                    }
                }
//                hienthidulieu();
            }
        });
        deleteGD();
        huycs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hienthidulieu();
            }
        });
    }
    public void laydulieu(){
        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        sotien= intent.getStringExtra("sotien");
        lydo = intent.getStringExtra("phanloai");
        time = intent.getStringExtra("time");
    }
    public void mapping(){
        spinphannhom = (Spinner) findViewById(R.id.spinphannhom);
        khoantien = (TextView) findViewById(R.id.txtKhoantien);
        edsotien = (EditText) findViewById(R.id.edsotien);
        edlydo = (EditText) findViewById(R.id.edlydo);
        ngaygd = (EditText) findViewById(R.id.etngaygiaodich);
        lich = (ImageView) findViewById(R.id.calendr);
        savegd = (Button) findViewById(R.id.savegd);
        suagd = (Button) findViewById(R.id.button3);
        xoagd = (Button) findViewById(R.id.button);
        huycs =(Button) findViewById(R.id.huycs);
    }
    public void hienthidulieu (){
        edsotien.setEnabled(false);
        edlydo.setEnabled(false);
        //Không cho chỉnh sửa trực tiếp lên ngày gd
        ngaygd.setEnabled(false);
        ngaygd.setFocusable(false);
        lich.setEnabled(false);
        money2= Math.abs(money);
        edsotien.setText(money2+"");
        edlydo.setText(lydo);
        ngaygd.setText(time);
        spinphannhom.setEnabled(false);

        suagd.setEnabled(true);
        suagd.setBackgroundResource(R.drawable.round_bg);
        savegd.setEnabled(false);
        savegd.setBackgroundResource(R.drawable.unable);
        huycs.setEnabled(false);
        huycs.setBackgroundResource(R.drawable.unable);
        xoagd.setEnabled(true);
        xoagd.setBackgroundResource(R.drawable.round_bg);
        ArrayList<String> themthuchi=new ArrayList<>();
        themthuchi.add(db.getloaiGD(lydo,money,time,user).get(0));

        ArrayAdapter adtmacdinh=new ArrayAdapter(activiy_chitietgd.this,android.R.layout.simple_list_item_1,themthuchi);
        spinphannhom.setAdapter(adtmacdinh);
    }
    public void chinhsuadl(){
        edsotien.setEnabled(true);
        edlydo.setEnabled(true);
        savegd.setEnabled(true);
        savegd.setBackgroundResource(R.drawable.round_bg);
        spinphannhom.setEnabled(true);
        lich.setEnabled(true);
        xoagd.setEnabled(false);
        xoagd.setBackgroundResource(R.drawable.unable);
        suagd.setEnabled(false);
        suagd.setBackgroundResource(R.drawable.unable);
        savegd.setEnabled(true);
        huycs.setEnabled(true);
        huycs.setBackgroundResource(R.drawable.round_bg);
        if (money > 0){
            thietlapspinner("THU");
            khoantien.setText("Khoản thu");
        } else {
            thietlapspinner("CHI");
            khoantien.setText("Khoản chi");
        }
    }
    public void pickTime(){
        // set up calendar
        Calendar c=Calendar.getInstance();
        mYear=c.get(Calendar.YEAR);
        mMonth=c.get(Calendar.MONTH);
        mDay=c.get(Calendar.DATE);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        lich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
    }
    public void thietlapspinner(String khoantiens){

        List<String> list = new ArrayList<String>();
        for (int i = 0; i < db.getAllNames(khoantiens, user).size(); i++) {
            list.add(db.getAllNames(khoantiens, user).get(i));

        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        spinphannhom.setAdapter(adapter1);
    }
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);

        }

        return null;

    }
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            ngaygd.setText(new StringBuilder().append(mDay).append("/").append(mMonth+1).append("/").append(mYear));

        }

    };
    public void deleteGD(){
        xoagd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(activiy_chitietgd.this)
                        .setTitle("Chú ý?")
                        .setMessage("Bạn có muốn xóa không?")
                        .setPositiveButton("Có",
                                new DialogInterface.OnClickListener() {
                                    @TargetApi(11)
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        db.DeleteGD(lydo,spinphannhom.getSelectedItem().toString(), time, sotien, user);


                                        db.close();
                                        Toast.makeText(getApplicationContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        intent.putExtra("email",user);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();

            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("email", user);
        startActivityForResult(intent, 8);
        finish();
    }
}
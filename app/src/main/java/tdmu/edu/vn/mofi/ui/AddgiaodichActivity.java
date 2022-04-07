package tdmu.edu.vn.mofi.ui;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import tdmu.edu.vn.mofi.R;
import tdmu.edu.vn.mofi.SQL.DatabaseHelper;
import tdmu.edu.vn.mofi.MainActivity;


public class AddgiaodichActivity extends AppCompatActivity {
    Spinner spinloaigd, spinphannhom;
    EditText edsotien;
    EditText edlydo;
    EditText ngaygd;
    Button savegd;
    ImageView lich;
    static final int DATE_DIALOG_ID = 0;
    String ngay;
    String thang;
    DatabaseHelper db;
    private int mYear,mMonth,mDay;
    Toast mToast;
    String ngdung, sotien;
    int thuorchi;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgiaodich);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        thuorchi= 2;
        mapping();
        //get current user
        Intent intent = getIntent();
         ngdung = intent.getStringExtra("email");

        db= new DatabaseHelper(this);

        ngaygd.setEnabled(false);
        ngaygd.setFocusable(false);
        // set up calendar
        Calendar c=Calendar.getInstance();
        mYear=c.get(Calendar.YEAR);
        mMonth=c.get(Calendar.MONTH);
        mDay=c.get(Calendar.DATE);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ngaygd.setText( sdf.format(c.getTime()));

        lich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        // set up spin loaigd
        final List<String> loaigd=new ArrayList<String>();
        loaigd.add("Khoản Thu");
        loaigd.add("Khoản Chi");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, loaigd);
        spinloaigd.setAdapter(adapter1);
        spinloaigd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sp1= spinloaigd.getSelectedItem().toString();
                if(sp1.contentEquals("Khoản Chi")) {
                    List<String> list = new ArrayList<String>();
                    for (int i = 0; i < db.getAllNames("CHI", ngdung).size(); i++) {
                        list.add(db.getAllNames("CHI", ngdung).get(i));

                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
                    dataAdapter.notifyDataSetChanged();
                    spinphannhom.setAdapter(dataAdapter);
                }
                if(sp1.contentEquals("Khoản Thu")) {
                    List<String> list = new ArrayList<String>();
                    for (int i = 0; i < db.getAllNames("THU", ngdung).size(); i++) {
                        list.add(db.getAllNames("THU", ngdung).get(i));

                    }

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
                    dataAdapter.notifyDataSetChanged();
                    spinphannhom.setAdapter(dataAdapter);


                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                    try {
                        if (spinloaigd.getSelectedItem().equals("Khoản Chi")) {
                            if (db.kiemtragdcotontai(edlydo.getText().toString(), "CHI", ngaygd.getText().toString(), ngdung )){
                                thuorchi =1;
                                toastsangchinhsua(1);
                            }
                            else {
                                db.themgiaodich(spinphannhom.getSelectedItem().toString(),
                                        edlydo.getText().toString(), "-" + edsotien.getText().toString(), ngaygd.getText().toString(),
                                        ngay + "", "" + thang, mYear + "", ngdung);
                                db.close();
                                LayoutInflater inflater = getLayoutInflater();
                                View mToastView = inflater.inflate(R.layout.luuthanhcong,
                                        null);
                                mToast = new Toast(AddgiaodichActivity.this);
                                mToast.setView(mToastView);
                                mToast.show();
                            }

                        } else {
                            if (db.kiemtragdcotontai(edlydo.getText().toString(), "THU", ngaygd.getText().toString(), ngdung)) {
                                thuorchi =0;
                                toastsangchinhsua(0);
                            } else {
                                db.themgiaodich(spinphannhom.getSelectedItem().toString(),
                                        edlydo.getText().toString(), edsotien.getText().toString(), ngaygd.getText().toString(),
                                        ngay + "", "" + thang, mYear + "", ngdung);
                                db.close();
                                LayoutInflater inflater = getLayoutInflater();
                                View mToastView = inflater.inflate(R.layout.luuthanhcong,
                                        null);
                                mToast = new Toast(AddgiaodichActivity.this);
                                mToast.setView(mToastView);
                                mToast.show();

                            }
                        }

                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),"Chưa có phân loại thu chi",Toast.LENGTH_SHORT).show();
                    }

                    if (thuorchi ==2){
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("email", ngdung);
                    startActivityForResult(intent, 8);
                    finish();
                    }
                }
            }
        });

    }
    private void mapping(){
        spinloaigd = (Spinner) findViewById(R.id.spinloaigd);
        spinphannhom = (Spinner) findViewById(R.id.spinphannhom);
        edsotien = (EditText) findViewById(R.id.edsotien);
        edlydo = (EditText) findViewById(R.id.edlydo);
        ngaygd = (EditText) findViewById(R.id.etngaygiaodich);
        savegd = (Button) findViewById(R.id.savegd);
        lich = (ImageView) findViewById(R.id.calendr);
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
    public void toastsangchinhsua(int thuorchi){
        if (thuorchi == 0){
            sotien = edsotien.getText().toString();
        }
        if (thuorchi == 1){
            sotien = "-" + edsotien.getText().toString();
        }
        new AlertDialog.Builder(AddgiaodichActivity.this)
                .setTitle("Chú ý")
                .setMessage("Giao dịch này đã tồn tại, bạn có muốn chỉnh sửa?")
                .setPositiveButton("Có",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                                Intent intent = new Intent(getApplicationContext(), activiy_chitietgd.class);
                                intent.putExtra("user",ngdung);
                                intent.putExtra("sotien", sotien);
                                intent.putExtra("phanloai",edlydo.getText().toString());
                                intent.putExtra("time",ngaygd.getText().toString());

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
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("email", ngdung);
        startActivityForResult(intent, 8);
        finish();
    }
}


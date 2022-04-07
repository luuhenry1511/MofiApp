package tdmu.edu.vn.mofi.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import tdmu.edu.vn.mofi.R;
import tdmu.edu.vn.mofi.SQL.DatabaseHelper;
import tdmu.edu.vn.mofi.adapter.ThongkeAdapter;
import tdmu.edu.vn.mofi.modals.Giaodich;

public class DayActivity extends Activity {

    private ArrayList<Giaodich> listchi;
    private ArrayList<Giaodich> listthu;
    private ArrayList<Giaodich> sum;
    DatabaseHelper db;
    TextView tvTitle, tvSum;
    ListView listView;
    Button btthu,btchi;
    String user, sotien;
    EditText ngaygd;
    ImageView lich;
    static final int DATE_DIALOG_ID = 0;
    private int mYear,mMonth,mDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongke);
        laydulieu();
        mapping();
        db=new DatabaseHelper(getApplicationContext());
        // set up calendar
        Calendar c=Calendar.getInstance();
        mYear=c.get(Calendar.YEAR);
        mMonth=c.get(Calendar.MONTH);
        mDay=c.get(Calendar.DATE);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ngaygd.setText( sdf.format(c.getTime()));


        tvTitle.setText("Thống kê theo ngày");
        //MẶC ĐỊNH LẤY LIST CỦA NGÀY HIỆN TẠI
        thietlapmacdinh();

        lich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        btchi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listchi=db.Thongketheongay("CHI", user,ngaygd.getText().toString());
                final ThongkeAdapter tkChi = new ThongkeAdapter(getParent(),R.layout.custom_rcllist,listchi);
                listView.setAdapter(tkChi);
                sum= db.SumThongketheongay("CHI",user,ngaygd.getText().toString());
                if (sum.get(0).getSotien() == null){
                    sotien = "0";
                }
                else{sotien=sum.get(0).getSotien();}
                tvSum.setText("Tổng cộng: "+sotien);
                btchi.setBackgroundResource(R.drawable.bg_card);
                btthu.setBackgroundResource(R.color.unable);
            }
        });
        btthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listthu=db.Thongketheongay("THU", user,ngaygd.getText().toString());
                final ThongkeAdapter tkThu=new ThongkeAdapter(getParent(),R.layout.custom_rcllist,listthu);
                listView.setAdapter(tkThu);
                sum= db.SumThongketheongay("THU",user,ngaygd.getText().toString());
                if (sum.get(0).getSotien() == null){
                    sotien = "0";
                }
                else{sotien=sum.get(0).getSotien();}
                tvSum.setText("Tổng cộng: "+sotien);
                btthu.setBackgroundResource(R.drawable.bg_card);
                btchi.setBackgroundResource(R.color.unable);
            }
        });

    }

    public void laydulieu(){
        Intent intent = getIntent();
        user = intent.getStringExtra("email");
    }
    public void mapping(){
        tvTitle=(TextView)findViewById(R.id.tvTitle);
        tvSum= (TextView) findViewById(R.id.TextView02);
        listView=(ListView)findViewById(R.id.listViewhienthithongke);
        btthu=(Button)findViewById(R.id.btthu);
        btchi=(Button)findViewById(R.id.btchi);
        ngaygd = (EditText) findViewById(R.id.etngaygiaodich);
        ngaygd.setEnabled(false);
        ngaygd.setFocusable(false);
        lich = (ImageView) findViewById(R.id.calendr);
    }
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(getParent(),
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

            btchi.setBackgroundResource(R.color.unable);
            btthu.setBackgroundResource(R.color.unable);
        }

    };

    public void thietlapmacdinh(){
        listchi=db.Thongketheongay("CHI", user,ngaygd.getText().toString());
        final ThongkeAdapter tkChi = new ThongkeAdapter(getParent(),R.layout.custom_rcllist,listchi);
        listView.setAdapter(tkChi);
        sum= db.SumThongketheongay("CHI",user,ngaygd.getText().toString());
        if (sum.get(0).getSotien() == null){
            sotien = "0";
        }
        else{sotien=sum.get(0).getSotien();}
        tvSum.setText("Tổng cộng: "+sotien);

        btthu.setBackgroundResource(R.color.unable);

    }
}

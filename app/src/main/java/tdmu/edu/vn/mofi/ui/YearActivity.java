package tdmu.edu.vn.mofi.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import tdmu.edu.vn.mofi.R;
import tdmu.edu.vn.mofi.SQL.DatabaseHelper;
import tdmu.edu.vn.mofi.adapter.ThongkeAdapter;
import tdmu.edu.vn.mofi.modals.Giaodich;

public class YearActivity extends Activity {
    private ArrayList<Giaodich> listchi;
    private ArrayList<Giaodich> listthu;
    private ArrayList<Giaodich> sum;
    DatabaseHelper db;
    TextView tvTitle, tvSum;
    ListView listView;
    Button btthu,btchi;
    String user, chonnam, sotien;
    EditText ngaygd;
    Spinner spnnam;
    static final int DATE_DIALOG_ID = 0;
    private int mYear,mMonth,mDay, idnam, check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongkeyear);
        laydulieu();
        mapping();
        db=new DatabaseHelper(getApplicationContext());
        check=0;

        // set up spin năm
        final List<String> nam=new ArrayList<String>();
        nam.add("Năm 2020");
        nam.add("Năm 2021");
        nam.add("Năm 2022");
        nam.add("Năm 2023");

        ArrayAdapter<String> adapternam = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, nam);
        spnnam.setAdapter(adapternam);

        spnnam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                thietlapSpinnernam(spnnam.getSelectedItem().toString());
                if (check!=0){
                    btchi.setBackgroundResource(R.color.unable);
                    btthu.setBackgroundResource(R.color.unable);
                }
                check=1;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        thietlapmacdinh();
        btchi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listchi=db.Thongketheonam("CHI", user, chonnam);
                final ThongkeAdapter tkChi = new ThongkeAdapter(getParent(),R.layout.custom_rcllist,listchi);
                listView.setAdapter(tkChi);
                sum= db.SumThongketheonam("CHI",user, chonnam);
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
                listthu=db.Thongketheonam("THU", user, chonnam);
                final ThongkeAdapter tkThu=new ThongkeAdapter(getParent(),R.layout.custom_rcllist,listthu);
                listView.setAdapter(tkThu);
                sum= db.SumThongketheonam("THU",user, chonnam);
                if (sum.get(0).getSotien() == null){
                    sotien = "0";
                }
                else{sotien=sum.get(0).getSotien();}
                tvSum.setText("Tổng cộng: "+sotien);

                btthu.setBackgroundResource(R.drawable.bg_card);
                btchi.setBackgroundResource(R.color.unable);
            }
        });
        btthu.setBackgroundResource(R.color.unable);
        btchi.setBackgroundResource(R.drawable.bg_card);

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
        spnnam= (Spinner) findViewById(R.id.spnnam);
    }

    public void thietlapSpinnernam( String nam){
        switch (nam){
            case "Năm 2020":
                chonnam="2020";
                break;
            case "Năm 2021":
                chonnam="2021";
                break;
            case "Năm 2022":
                chonnam="2022";
                break;
            case "Năm 2023":
                chonnam="2023";
                break;
        }
    }
    public void setChonnam(int nam){
        switch (nam){
            case 2020:
                idnam=0;
                break;
            case 2021:
                idnam=1;
                break;
            case 2022:
                idnam=2;
                break;
            case 2023:
                idnam=3;
                break;
        }
    }
    public void thietlapmacdinh(){
        // set up calendar
        Calendar c=Calendar.getInstance();
        mYear=c.get(Calendar.YEAR);
        mMonth=c.get(Calendar.MONTH);
        setChonnam(mYear);
        spnnam.setSelection(idnam);
        thietlapSpinnernam(spnnam.getSelectedItem().toString());
        listchi=db.Thongketheonam("CHI", user, chonnam);
        final ThongkeAdapter tkChi = new ThongkeAdapter(getParent(),R.layout.custom_rcllist,listchi);
        listView.setAdapter(tkChi);
        sum= db.SumThongketheonam("CHI",user, chonnam);
        if (sum.get(0).getSotien() == null){
            sotien = "0";
        }
        else{sotien=sum.get(0).getSotien();}
        tvSum.setText("Tổng cộng: "+sotien);


    }
}

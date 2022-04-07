package tdmu.edu.vn.mofi.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tdmu.edu.vn.mofi.R;
import tdmu.edu.vn.mofi.SQL.DatabaseHelper;
import tdmu.edu.vn.mofi.adapter.KhoantienAdapter;
import tdmu.edu.vn.mofi.modals.Khoantien;

public class ThuChi extends Activity {
    ImageView imageView;
    EditText editText;
    RecyclerView listView;
    DatabaseHelper db;
    Spinner spinner1;
    Spinner spinthemthuchi;
    TextView textView;
    private ArrayList<Khoantien> llist;
    RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thuchi);
        Intent intent = getIntent();
        final String user = intent.getStringExtra("email");

        imageView = (ImageView) findViewById(R.id.addcaidat);
        editText = (EditText) findViewById(R.id.themloaithu);
        db = new DatabaseHelper(getApplicationContext());
        listView = (RecyclerView) findViewById(R.id.danhsach);
        spinner1 = (Spinner) findViewById(R.id.spinner);
        spinthemthuchi = (Spinner) findViewById(R.id.spinthemthuchi);
        textView = (TextView) findViewById(R.id.textView);
        ArrayList<String> themthuchi=new ArrayList<>();
        themthuchi.add("KHOẢN CHI");
        themthuchi.add("KHOẢN THU");
        ArrayAdapter themthuchiadapter=new ArrayAdapter(ThuChi.this,android.R.layout.simple_list_item_1,themthuchi);
        spinner1.setAdapter(themthuchiadapter);

        ArrayList<String> arrayList=new ArrayList<>();
        arrayList.add("CHI");
        arrayList.add("THU");
        ArrayAdapter arrayAdapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,arrayList);
        spinthemthuchi.setAdapter(arrayAdapter);



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().length() > 0) {

                    if (db.kiemtra(spinthemthuchi.getSelectedItem().toString(), editText.getText().toString(), user) == true) {
                        db.themkhoanthuchi(spinthemthuchi.getSelectedItem().toString(), editText.getText().toString(), user);
                        Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        recyclerViewKhoantien(spinthemthuchi.getSelectedItem().toString(),user);
//                        Intent intent = new Intent(getApplicationContext(), ThuChi.class);
//                        intent.putExtra("email",user);
//                        startActivity(intent);

                        editText.setText("");
//                        onRestart();
//                        finish();
//                        finish();
//                        startActivity(getIntent());

                    } else {
                        Toast.makeText(getApplicationContext(), "Bạn đã nhập giá trị này rồi", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(),"Bạn chưa nhập gì cả",Toast.LENGTH_SHORT).show();
                    editText.requestFocus();
                }
            }
        });

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){

                    textView.setText("Khoản Chi");
                    recyclerViewKhoantien("CHI",user);
                }if(position==1){

                    textView.setText("Khoản Thu");
                    recyclerViewKhoantien("THU",user);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

    });


//        KhoantienAdapter khoantien=new KhoantienAdapter(llist);
//        listView.setAdapter(khoantien);
    }

    private void recyclerViewKhoantien(String khoantien, String user) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(linearLayoutManager);
        llist=db.getKhoantien(khoantien, user);
        adapter = new KhoantienAdapter(llist);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}

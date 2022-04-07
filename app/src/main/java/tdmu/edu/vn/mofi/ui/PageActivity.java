package tdmu.edu.vn.mofi.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
//import androidx.appcompat.widget.SearchView;
import android.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tdmu.edu.vn.mofi.DangNhap;
import tdmu.edu.vn.mofi.R;
import tdmu.edu.vn.mofi.SQL.DatabaseHelper;
import tdmu.edu.vn.mofi.adapter.GiaodichAdapter;
import tdmu.edu.vn.mofi.adapter.KhoantienAdapter;
import tdmu.edu.vn.mofi.modals.Giaodich;

public class PageActivity extends Activity {
    RecyclerView listView;
    Button btnAddgd;
    String user;
    TextView txtSum,txtthu, txtchi ;
    SearchView txtsearch;
    DatabaseHelper db;
    int sodu, thuthang, chithang;
    private ArrayList<Giaodich> GD;
//    RecyclerView.Adapter adapter;
    GiaodichAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Intent intent = getIntent();
        user = intent.getStringExtra("email");
        mapping();

        btnAddgd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentL = new Intent(PageActivity.this, AddgiaodichActivity.class);
                intentL.putExtra("email", user);
                startActivity(intentL);
            }
        });
        db = new DatabaseHelper(PageActivity.this);
        if (db.Tinhtongtien(user).get(0) == null) {
            sodu = 0;

        } else {
            sodu = Integer.parseInt(db.Tinhtongtien(user).get(0));
        }
        if (db.Sumthuthang(user).get(0) == null) {
            thuthang = 0;

        } else {
            thuthang = Integer.parseInt(db.Sumthuthang(user).get(0));
        }
        if (db.Sumchithang(user).get(0) == null) {
            chithang = 0;

        } else {
            chithang = Integer.parseInt(db.Sumchithang(user).get(0));
        }
        txtSum.setText(sodu + "");
        txtthu.setText(thuthang + "");
        txtchi.setText(chithang + "");
        recyclerView(user);
        txtsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    public void mapping(){
        listView = (RecyclerView) findViewById(R.id.rclrecentgd);
        btnAddgd = (Button) findViewById(R.id.btnAddgd);
        txtSum = (TextView) findViewById(R.id.txtSum);
        txtthu = (TextView) findViewById(R.id.txtThuthang);
        txtchi = (TextView) findViewById(R.id.txtChithang);
        txtsearch = (SearchView) findViewById(R.id.search);
        txtsearch.setFocusable(false);
    }
    private void recyclerView(String user) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(linearLayoutManager);
        GD= (ArrayList<Giaodich>) db.lichsugiaodich(user);
        adapter = new GiaodichAdapter(GD);
        listView.setAdapter(adapter);

    }

}

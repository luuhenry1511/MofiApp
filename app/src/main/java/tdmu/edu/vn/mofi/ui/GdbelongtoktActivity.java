package tdmu.edu.vn.mofi.ui;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import tdmu.edu.vn.mofi.MainActivity;
import tdmu.edu.vn.mofi.R;
import tdmu.edu.vn.mofi.SQL.DatabaseHelper;
import tdmu.edu.vn.mofi.adapter.GiaodichAdapter;
import tdmu.edu.vn.mofi.modals.Giaodich;

public class GdbelongtoktActivity extends AppCompatActivity {
    TextView tieude;
    Spinner spinner;
    RecyclerView danhsach;

    String user, khoantien, phanloai, chonthang;
    private ArrayList<Giaodich> GD;
    GiaodichAdapter adapter;
    DatabaseHelper db;
    SearchView searchView;
    private int thangs;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gdbelongtokt);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        db = new DatabaseHelper(GdbelongtoktActivity.this);
        mapping();
        laydulieu();

        // set up spin tháng
        final List<String> thang=new ArrayList<String>();
        thang.add("Tháng 1");
        thang.add("Tháng 2");
        thang.add("Tháng 3");
        thang.add("Tháng 4");
        thang.add("Tháng 5");
        thang.add("Tháng 6");
        thang.add("Tháng 7");
        thang.add("Tháng 8");
        thang.add("Tháng 9");
        thang.add("Tháng 10");
        thang.add("Tháng 11");
        thang.add("Tháng 12");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, thang);
        spinner.setAdapter(adapter1);
        // set up calendar
        Calendar c=Calendar.getInstance();
        thangs=c.get(Calendar.MONTH);
        spinner.setSelection(thangs);
        thietlapSpinner(spinner.getSelectedItem().toString());
        recyclerView(user,phanloai,chonthang);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                thietlapSpinner(spinner.getSelectedItem().toString());
                recyclerView(user,phanloai,chonthang);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tieude.setText("Các giao dịch "+phanloai);

    }

    public void mapping(){
        tieude = (TextView) findViewById(R.id.textView);
        spinner = (Spinner) findViewById(R.id.spinner);
        danhsach = (RecyclerView) findViewById(R.id.danhsach);
    }

    public void laydulieu(){
        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        khoantien = intent.getStringExtra("khoantien");
        phanloai = intent.getStringExtra("phanloai");

    }


    private void recyclerView(String user, String khoantien, String chonthang) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        danhsach.setLayoutManager(linearLayoutManager);
        GD= db.getGDtuKhoantien(khoantien,user, chonthang);
        adapter = new GiaodichAdapter(GD);
        danhsach.setAdapter(adapter);

    }
    public void thietlapSpinner( String thang){
        switch (thang){
            case "Tháng 1":
                chonthang="1";
                break;
            case "Tháng 2":
                chonthang="2";
                break;
            case "Tháng 3":
                chonthang="3";
                break;
            case "Tháng 4":
                chonthang="4";
                break;
            case "Tháng 5":
                chonthang="5";
                break;
            case "Tháng 6":
                chonthang="6";
                break;
            case "Tháng 7":
                chonthang="7";
                break;
            case "Tháng 8":
                chonthang="8";
                break;
            case "Tháng 9":
                chonthang="9";
                break;
            case "Tháng 10":
                chonthang="10";
                break;
            case "Tháng 11":
                chonthang="11";
                break;
            case "Tháng 12":
                chonthang="12";
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public void onBackPressed() {

        if (!searchView.isIconified()){
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("email", user);
        startActivityForResult(intent, 8);
        finish();
    }
}

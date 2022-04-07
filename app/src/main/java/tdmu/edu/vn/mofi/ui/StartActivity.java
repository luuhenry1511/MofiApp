package tdmu.edu.vn.mofi.ui;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import androidx.appcompat.app.AppCompatActivity;

import tdmu.edu.vn.mofi.R;

public class StartActivity  extends TabActivity {
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Intent intent = getIntent();
        user = intent.getStringExtra("email");
        final TabHost tabHost = getTabHost();

        TabHost.TabSpec homnay = tabHost.newTabSpec("Ngày");
        homnay.setIndicator("Ngày", getResources().getDrawable(R.mipmap.ic_launcher));
        Intent hn = new Intent(StartActivity.this, DayActivity.class);
        hn.putExtra("email", user);
        homnay.setContent(hn);

        TabHost.TabSpec thangnay = tabHost.newTabSpec("Tháng");
        thangnay.setIndicator("Tháng", getResources().getDrawable(R.mipmap.ic_launcher));
        Intent tn = new Intent(this, MonthActivity.class);
        tn.putExtra("email", user);
        thangnay.setContent(tn);

        TabHost.TabSpec namnay = tabHost.newTabSpec("Năm");
        namnay.setIndicator("Năm", getResources().getDrawable(R.mipmap.ic_launcher));
        Intent nn = new Intent(this, YearActivity.class);
        nn.putExtra("email", user);
        namnay.setContent(nn);

        TabHost.TabSpec trucquan = tabHost.newTabSpec("Năm");
        trucquan.setIndicator("Trực quan", getResources().getDrawable(R.mipmap.ic_launcher));
        Intent tq = new Intent(this, activity_thongketrucquan.class);
        tq.putExtra("email", user);
        trucquan.setContent(tq);

        tabHost.addTab(homnay);
        tabHost.addTab(thangnay);
        tabHost.addTab(namnay);
        tabHost.addTab(trucquan);
        tabHost.setCurrentTab(0);
    }
}

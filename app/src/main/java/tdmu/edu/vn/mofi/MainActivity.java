package tdmu.edu.vn.mofi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

import tdmu.edu.vn.mofi.ui.InfoActivity;
import tdmu.edu.vn.mofi.ui.PageActivity;
import tdmu.edu.vn.mofi.ui.StartActivity;
import tdmu.edu.vn.mofi.ui.ThongKeActivity;
import tdmu.edu.vn.mofi.ui.ThuChi;

public class MainActivity extends TabActivity {
    Intent intent;
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        user = intent.getStringExtra("email");
        final TabHost tabHost = getTabHost();

        TabHost.TabSpec thuchi = tabHost.newTabSpec("Giao dịch");
        thuchi.setIndicator("", getResources().getDrawable(R.drawable.ic_home))
                .setContent(intent);
        Intent i = new Intent(this, PageActivity.class);
        i.putExtra("email", user);
        thuchi.setContent(i);

        TabHost.TabSpec thongke = tabHost.newTabSpec("Thu chi");

        thongke.setIndicator("", getResources().getDrawable(R.drawable.ic_thu));
        Intent o = new Intent(this, ThuChi.class);
        o.putExtra("email", user);
        thongke.setContent(o);

        TabHost.TabSpec home = tabHost.newTabSpec("Thống kê");

        home.setIndicator("", getResources().getDrawable(R.drawable.ic_thongke));
        Intent e = new Intent(this, StartActivity.class);
        e.putExtra("email", user);
        home.setContent(e);

        TabHost.TabSpec caidat = tabHost.newTabSpec("Thông tin");
        caidat.setIndicator("", getResources().getDrawable(R.drawable.ic_info));
        Intent p = new Intent(this, InfoActivity.class);
        p.putExtra("email", user);
        caidat.setContent(p);

        tabHost.addTab(thuchi);
        tabHost.addTab(thongke);
        tabHost.addTab(home);
        tabHost.addTab(caidat);
        tabHost.setCurrentTab(0);

    }


}
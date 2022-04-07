package tdmu.edu.vn.mofi.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import tdmu.edu.vn.mofi.R;
import tdmu.edu.vn.mofi.modals.Giaodich;

public class ThongkeAdapter extends ArrayAdapter<Giaodich> {
    private Activity activity;
    private int idLayout;
    private ArrayList<Giaodich> list;
    public ThongkeAdapter(Activity activity, int idLayout, ArrayList<Giaodich> list){
        super(activity,idLayout,list);
        this.activity=activity;
        this.idLayout=idLayout;
        this.list=list;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater=LayoutInflater.from(activity);
        convertView=inflater.inflate(idLayout,null);
        TextView txtphannhom = (TextView)convertView.findViewById(R.id.txtName);
        TextView txtsotien=(TextView)convertView.findViewById(R.id.txtTien);
        TextView txtday = (TextView) convertView.findViewById(R.id.txtDay);

        txtphannhom.setText(list.get(position).getPhanloai());
        txtsotien.setText(list.get(position).getSotien());
        txtday.setText(list.get(position).getTime());
        return convertView;
    }
}

package tdmu.edu.vn.mofi.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.List;

import tdmu.edu.vn.mofi.R;
import tdmu.edu.vn.mofi.modals.Giaodich;
import tdmu.edu.vn.mofi.ui.activiy_chitietgd;
import tdmu.edu.vn.mofi.ui.GdbelongtoktActivity;


public class GiaodichAdapter extends RecyclerView.Adapter<GiaodichAdapter.ViewHolder> implements Filterable {
    ArrayList<Giaodich> gd;
    ArrayList<Giaodich> gdOld;
    public GiaodichAdapter(ArrayList<Giaodich> gd) {

        this.gd = gd;
        this.gdOld = gd;
    }

    @NonNull
    @Override
    public GiaodichAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rclmain, parent, false);

        return new GiaodichAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull GiaodichAdapter.ViewHolder holder, int position) {
        holder.txtName.setText(String.valueOf(gd.get(position).getPhanloai()));
        holder.txtTien.setText(String.valueOf(gd.get(position).getSotien()));
        holder.txtNgay.setText(String.valueOf(gd.get(position).getTime()));
        holder.btnXem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), activiy_chitietgd.class);
                intent.putExtra("user",gd.get(position).getUser());
                intent.putExtra("time",gd.get(position).getTime());
                intent.putExtra("phanloai",gd.get(position).getPhanloai());
                intent.putExtra("sotien",gd.get(position).getSotien());
                holder.itemView.getContext().startActivity(intent);

            }
        });
    }

    public int getItemCount() {
        return gd.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtTien, txtNgay;
        Button btnXem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtTien = itemView.findViewById(R.id.txtTien);
            txtNgay = itemView.findViewById(R.id.txtDay);
            btnXem = itemView.findViewById(R.id.btnView);
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()){
                    gd= gdOld;
                } else{
                    ArrayList<Giaodich> danhsach = new ArrayList<>();
                    for (Giaodich giaodich : gdOld) {
                        if (giaodich.getPhanloai().toLowerCase().contains(strSearch.toLowerCase())){
                            danhsach.add(giaodich);
                        }
                    }
                    gd= danhsach;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values=gd;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                gd= (ArrayList<Giaodich>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}

package tdmu.edu.vn.mofi.adapter;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tdmu.edu.vn.mofi.MainActivity;
import tdmu.edu.vn.mofi.R;
import tdmu.edu.vn.mofi.SQL.DatabaseHelper;
import tdmu.edu.vn.mofi.modals.Khoantien;
import tdmu.edu.vn.mofi.ui.GdbelongtoktActivity;
import tdmu.edu.vn.mofi.ui.PageActivity;

public class KhoantienAdapter extends RecyclerView.Adapter<KhoantienAdapter.ViewHolder> {
    ArrayList<Khoantien> khoantiens;
    DatabaseHelper db;
    public KhoantienAdapter (ArrayList<Khoantien> khoantiens) {
        this.khoantiens =new ArrayList<>(khoantiens);
    }
    @NonNull
    @Override
    public KhoantienAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_tong, parent, false);

        return new KhoantienAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull KhoantienAdapter.ViewHolder holder, int position) {
        holder.txtkhoanthukhoanchi2.setText(String.valueOf(khoantiens.get(position).getKhoanthukhoanchi()));
        holder.txtphannhom2.setText(String.valueOf(khoantiens.get(position).getPhanloai()));
        String user = khoantiens.get(position).getUser();
        db = new DatabaseHelper(holder.itemView.getContext());
        holder.btnXoa.setOnClickListener(v -> {
            if (db.kiemtratrckhixoa(khoantiens.get(position).getKhoanthukhoanchi(),khoantiens.get(position).getPhanloai(), user) == true) {
                new AlertDialog.Builder(holder.itemView.getContext())
                        .setTitle("Chú ý!")
                        .setMessage("Bạn có muốn xóa khoản tiền này không?")
                        .setPositiveButton("Xóa",
                                new DialogInterface.OnClickListener() {
                                    @TargetApi(11)
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        db.Delete(khoantiens.get(position).getPhanloai(), khoantiens.get(position).getKhoanthukhoanchi(), user);

                                        Toast.makeText(holder.itemView.getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();

                                        //cập nhật lại rclview
                                        khoantiens.remove(holder.getAdapterPosition());
                                        notifyDataSetChanged();

                                    }
                                })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();
//                String user = khoantiens.get(position).getUser();

            }else {
                Toast.makeText(holder.itemView.getContext(), "Đã phát sinh giao dịch, không được xóa", Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), GdbelongtoktActivity.class);
                intent.putExtra("user",user);
                intent.putExtra("khoantien",khoantiens.get(position).getKhoanthukhoanchi());
                intent.putExtra("phanloai",khoantiens.get(position).getPhanloai());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }
    public int getItemCount() {
        return khoantiens.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtkhoanthukhoanchi2, txtphannhom2;
        Button btnXoa, btnSua;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtkhoanthukhoanchi2 = itemView.findViewById(R.id.txtkhoanthukhoanchi2);
            txtphannhom2 = itemView.findViewById(R.id.txtphannhom2);
            btnXoa = itemView.findViewById(R.id.btnXoa);
            btnSua = itemView.findViewById(R.id.btnSua);
        }
    }


}

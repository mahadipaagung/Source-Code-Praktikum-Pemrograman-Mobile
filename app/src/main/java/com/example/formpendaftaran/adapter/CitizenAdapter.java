package com.example.formpendaftaran.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.formpendaftaran.R;
import com.example.formpendaftaran.database.CitizenEntity;

import java.util.List;

public class CitizenAdapter extends RecyclerView.Adapter<CitizenAdapter.ViewAdapter> {

    private List<CitizenEntity> list;
    private Context context;
    private Dialog dialog;

    public interface Dialog {
        void onClick(int position);
    }
    public void setDialog(Dialog dialog){
        this.dialog = dialog;
    }

    public CitizenAdapter(Context context, List<CitizenEntity> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new ViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAdapter holder, int position) {
        holder.fullname.setText(list.get(position).fullname);
        holder.gender.setText(list.get(position).jeniskelamin);
        holder.alamat.setText(list.get(position).alamat);
        holder.iuran.setText(list.get(position).iuran);
        holder.familynumber.setText(""+list.get(position).anggota);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewAdapter extends RecyclerView.ViewHolder{
        TextView fullname, gender, alamat, iuran, familynumber;

        public  ViewAdapter(@NonNull View itemView){
            super(itemView);
            fullname = itemView.findViewById(R.id.nama);
            gender = itemView.findViewById(R.id.JK);
            alamat = itemView.findViewById(R.id.alamat);
            iuran = itemView.findViewById(R.id.jenisIuran);
            familynumber =itemView.findViewById(R.id.jumlahkel);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog!=null){
                        dialog.onClick(getLayoutPosition());
                    }
                }
            });

        }

    }
}

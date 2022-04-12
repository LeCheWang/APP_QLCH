package com.tranthimai.qlch.TranMai_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tranthimai.qlch.R;
import com.tranthimai.qlch.TranMai_Model.TranMai_Khach;

import java.util.List;

public class TranMai_KhachAdapter extends RecyclerView.Adapter<TranMai_KhachAdapter.ViewHolder> {
    List<TranMai_Khach> list;
    Context context;
    TranMai_IOnClickKhach iOnClickKhach;

    public TranMai_IOnClickKhach getiOnClickLop() {
        return iOnClickKhach;
    }

    public void setiOnClickLop(TranMai_IOnClickKhach iOnClickKhach) {
        this.iOnClickKhach = iOnClickKhach;
    }

    public TranMai_KhachAdapter(List<TranMai_Khach> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.tranmai_item_khach_hang, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TranMai_KhachAdapter.ViewHolder holder, int position) {
        TranMai_Khach khach= list.get(position);
        holder.tvHoTen.setText(khach.getHoTen());
        holder.tvNamSinh.setText(khach.getNamSinh());
        holder.tvGioiTinh.setText(khach.getGioiTinh());
        holder.tvDiaChi.setText("Địa chỉ: "+ khach.getDiaChi());
        holder.tvSdt.setText("SĐT: "+ khach.getSdt());

        holder.btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iOnClickKhach.iOnClickSua(khach, position);
            }
        });
        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iOnClickKhach.iOnClickXoa(khach, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list!=null)
            return list.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvHoTen, tvNamSinh, tvGioiTinh, tvDiaChi, tvSdt;
        Button btnSua, btnXoa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHoTen= itemView.findViewById(R.id.tvHoTen);
            tvNamSinh= itemView.findViewById(R.id.tvNamSinh);
            tvGioiTinh= itemView.findViewById(R.id.tvGioiTinh);
            tvDiaChi= itemView.findViewById(R.id.tvDiaChi);
            tvSdt= itemView.findViewById(R.id.tvSdt);

            btnSua= itemView.findViewById(R.id.btnSua);
            btnXoa= itemView.findViewById(R.id.btnXoa);
        }
    }

    public void setList(List<TranMai_Khach> l){
        list= l;
    }
}
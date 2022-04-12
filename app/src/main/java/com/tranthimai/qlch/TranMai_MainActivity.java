package com.tranthimai.qlch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tranthimai.qlch.TranMai_Adapter.TranMai_IOnClickKhach;
import com.tranthimai.qlch.TranMai_Adapter.TranMai_KhachAdapter;
import com.tranthimai.qlch.TranMai_Model.TranMai_Khach;

import java.util.ArrayList;
import java.util.List;

public class TranMai_MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton btnAdd;
    List<TranMai_Khach> khachs;
    TranMai_SQLHelper sqlHelper;
    TranMai_KhachAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        sqlHelper= new TranMai_SQLHelper(TranMai_MainActivity.this);
//        khachs= new ArrayList<>();
        khachs= sqlHelper.getAllKhach();
        adapter= new TranMai_KhachAdapter(khachs, TranMai_MainActivity.this);
        layoutManager= new LinearLayoutManager(TranMai_MainActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setiOnClickLop(new TranMai_IOnClickKhach() {
            @Override
            public void iOnClickSua(TranMai_Khach khach, int pos) {
                final Dialog dialog = new Dialog(TranMai_MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.tranmai_dialog_sua);

                Window window = dialog.getWindow();
                if (window == null) {
                    return;
                }
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                WindowManager.LayoutParams windowAtributes = window.getAttributes();
                windowAtributes.gravity = Gravity.CENTER_VERTICAL;
                window.setAttributes(windowAtributes);

                dialog.setCancelable(true);
                EditText edtHoTen= dialog.findViewById(R.id.edtHoTen);
                EditText edtNamSinh= dialog.findViewById(R.id.edtNamSinh);
                EditText edtGioiTinh= dialog.findViewById(R.id.edtGioiTinh);
                EditText edtDiaChi= dialog.findViewById(R.id.edtDiaChi);
                EditText edtSdt= dialog.findViewById(R.id.edtSdt);

                Button btnDialogSua= dialog.findViewById(R.id.btnDiaLogSua);

                edtHoTen.setText(khach.getHoTen());
                edtNamSinh.setText(khach.getNamSinh());
                edtGioiTinh.setText(khach.getGioiTinh());
                edtDiaChi.setText(khach.getDiaChi());
                edtSdt.setText(khach.getSdt());

                String sdtSearch= khach.getSdt();
                btnDialogSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String hoTen= edtHoTen.getText().toString().trim();
                        String namSinh= edtNamSinh.getText().toString().trim();
                        String gioiTinh= edtGioiTinh.getText().toString().trim();
                        String diaChi= edtDiaChi.getText().toString().trim();
                        String sdt= edtSdt.getText().toString().trim();
                        if (hoTen.compareTo("")==0||namSinh.compareTo("")==0||gioiTinh.compareTo("")==0||diaChi.compareTo("")==0||sdt.compareTo("")==0){
                            Toast.makeText(TranMai_MainActivity.this, "Thông tin không được để trống", Toast.LENGTH_SHORT).show();
                        }else {
                            TranMai_Khach khach1= new TranMai_Khach(hoTen, namSinh, gioiTinh, diaChi, sdt);
                            sqlHelper.upDateKhach(khach1, sdtSearch);
                            khachs.get(pos).setHoTen(hoTen);
                            khachs.get(pos).setNamSinh(namSinh);
                            khachs.get(pos).setGioiTinh(gioiTinh);
                            khachs.get(pos).setDiaChi(diaChi);
                            khachs.get(pos).setSdt(sdt);
                            adapter.setList(khachs);
                            recyclerView.setAdapter(adapter);
                            dialog.cancel();
                        }
                    }
                });

                dialog.show();
            }

            @Override
            public void iOnClickXoa(TranMai_Khach khach, int pos) {
                AlertDialog.Builder builder= new AlertDialog.Builder(TranMai_MainActivity.this);
                builder.setTitle("XÁC NHẬN XÓA KHÁCH HÀNG");
                builder.setMessage("Bạn có chắc muốn xóa khách hàng không?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        khachs.remove(pos);
                        sqlHelper.deleteKhach(khach.getSdt());
                        adapter.setList(khachs);
                        recyclerView.setAdapter(adapter);
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(TranMai_MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.tranmai_dialog_them);

                Window window = dialog.getWindow();
                if (window == null) {
                    return;
                }
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                WindowManager.LayoutParams windowAtributes = window.getAttributes();
                windowAtributes.gravity = Gravity.CENTER_VERTICAL;
                window.setAttributes(windowAtributes);

                dialog.setCancelable(true);

                EditText edtHoTen= dialog.findViewById(R.id.edtHoTenThem);
                EditText edtNamSinh= dialog.findViewById(R.id.edtNamSinhThem);
                EditText edtGioiTinh= dialog.findViewById(R.id.edtGioiTinhThem);
                EditText edtDiaChi= dialog.findViewById(R.id.edtDiaChiThem);
                EditText edtSdt= dialog.findViewById(R.id.edtSdtThem);

                Button btnDialogThem= dialog.findViewById(R.id.btnDiaLogThem);

                btnDialogThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String hoTen= edtHoTen.getText().toString().trim();
                        String namSinh= edtNamSinh.getText().toString().trim();
                        String gioiTinh= edtGioiTinh.getText().toString().trim();
                        String diaChi= edtDiaChi.getText().toString().trim();
                        String sdt= edtSdt.getText().toString().trim();
                        if (hoTen.compareTo("")==0||namSinh.compareTo("")==0||gioiTinh.compareTo("")==0||diaChi.compareTo("")==0||sdt.compareTo("")==0){
                            Toast.makeText(TranMai_MainActivity.this, "Thông tin không được để trống", Toast.LENGTH_SHORT).show();
                        }else {
                            TranMai_Khach khach1= new TranMai_Khach(hoTen, namSinh, gioiTinh, diaChi, sdt);
                            sqlHelper.insertKhack(khach1);
                            khachs.add(khach1);
                            adapter.setList(khachs);
                            recyclerView.setAdapter(adapter);
                            dialog.cancel();
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    private void anhXa() {
        recyclerView= findViewById(R.id.revKhach);
        btnAdd= findViewById(R.id.btnAdd);
    }
}
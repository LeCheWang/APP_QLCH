package com.tranthimai.qlch;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.tranthimai.qlch.TranMai_Model.TranMai_Admin;
import com.tranthimai.qlch.TranMai_Model.TranMai_Khach;

import java.util.ArrayList;
import java.util.List;

public class TranMai_SQLHelper extends SQLiteOpenHelper {
    static final String DB_NAME = "CUAHANG";
    static final int DB_VERSION = 1;
    static final String DB_TABLE_KHACH = "khachhang";
    static final String DB_TABLE_ADMIN  = "admin";
    SQLiteDatabase sqLiteDatabase;
    ContentValues contentValues;

    public TranMai_SQLHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "CREATE TABLE " + DB_TABLE_ADMIN + "(ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT, password TEXT)";

        String query2 = "CREATE TABLE " + DB_TABLE_KHACH + "(ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "hoTen TEXT, namSinh TEXT, gioiTinh TEXT, diaChi TEXT, sdt TEXT)";

        db.execSQL(query1);
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_KHACH);
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_ADMIN);
            onCreate(db);
        }
    }

    public void insertAdmin(TranMai_Admin admin) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put("username", admin.getUsername());
        contentValues.put("password", admin.getPassword());
        sqLiteDatabase.insert(DB_TABLE_ADMIN, null, contentValues);
        sqLiteDatabase.close();
    }

    public void insertKhack(TranMai_Khach khach) {
        sqLiteDatabase = this.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put("hoTen", khach.getHoTen());
        contentValues.put("namSinh", khach.getNamSinh());
        contentValues.put("gioiTinh", khach.getGioiTinh());
        contentValues.put("diaChi", khach.getDiaChi());
        contentValues.put("sdt", khach.getSdt());
        sqLiteDatabase.insert(DB_TABLE_KHACH, null, contentValues);
        sqLiteDatabase.close();
    }


    public List<TranMai_Khach> getAllKhach() {
        List<TranMai_Khach> list = new ArrayList<>();
        sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DB_TABLE_KHACH, new String[]{});
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String hoTen = cursor.getString(cursor.getColumnIndex("hoTen"));
            @SuppressLint("Range") String namSinh = cursor.getString(cursor.getColumnIndex("namSinh"));
            @SuppressLint("Range") String gioiTinh = cursor.getString(cursor.getColumnIndex("gioiTinh"));
            @SuppressLint("Range") String diaChi = cursor.getString(cursor.getColumnIndex("diaChi"));
            @SuppressLint("Range") String sdt = cursor.getString(cursor.getColumnIndex("sdt"));

            list.add(new TranMai_Khach(hoTen, namSinh, gioiTinh, diaChi, sdt));
        }
        sqLiteDatabase.close();
        return list;
    }


    public void deleteKhach(String sdt) {
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(DB_TABLE_KHACH, "sdt=?", new String[]{sdt + ""});
        sqLiteDatabase.close();
    }

    public void upDateKhach(TranMai_Khach khach, String sdt) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        if (khach.getHoTen()!=null)
            contentValues.put("hoTen", khach.getHoTen());
        if (khach.getNamSinh() != null)
            contentValues.put("namSinh", khach.getNamSinh());
        if (khach.getNamSinh() != null)
            contentValues.put("gioiTinh", khach.getGioiTinh());
        if (khach.getNamSinh() != null)
            contentValues.put("diaChi", khach.getDiaChi());
        if (khach.getNamSinh() != null)
            contentValues.put("sdt", khach.getSdt());

        sqLiteDatabase.update(DB_TABLE_KHACH, contentValues, "sdt=?", new String[]{sdt + ""});
        sqLiteDatabase.close();
    }

    public boolean checkExistsKhach(String sdt) {
        sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DB_TABLE_KHACH + " WHERE sdt=?", new String[]{sdt + ""});
        if (cursor.getCount() == 1) {
            sqLiteDatabase.close();
            return true;
        }
        sqLiteDatabase.close();
        return false;
    }

    public boolean checkExistsAdmin(String username) {
        sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DB_TABLE_ADMIN + " WHERE username=?", new String[]{username + ""});
        if (cursor.getCount() == 1) {
            sqLiteDatabase.close();
            return true;
        }
        sqLiteDatabase.close();
        return false;
    }

    public boolean checkLogin(TranMai_Admin admin) {
        sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DB_TABLE_ADMIN + " WHERE username=? and password=?", new String[]{admin.getUsername() + "", admin.getPassword() + ""});
        if (cursor.getCount() == 1) {
            sqLiteDatabase.close();
            return true;
        }
        sqLiteDatabase.close();
        return false;
    }

}
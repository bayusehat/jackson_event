package com.example.event;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DataHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "event_final.db";
    private static final int DATABASE_VERSION = 2;
    public DataHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String sql = "create table pesertas (id integer primary key autoincrement, nama text null, jk text null, no_hp text null, email text null, username_ig text null, username_tiktok text null,asal text null, know_jackson text null, others_know text null, user text null, created_at timestamp default current_timestamp)";
        Log.d("Data", "onCreate: "+sql);
        db.execSQL(sql);
        sql = "INSERT INTO pesertas (nama, jk, no_hp, email, username_ig, username_tiktok,asal, know_jackson, others_know,user) VALUES ('ANTONO', 'Laki', '085364791632', 'antono@gmail.om', 'korengajaib','korengajaib' ,'SMK Telkom Malang', 'Online','','Surabaya')";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean userIsFound(String userExist) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select username_ig from pesertas where username_ig = '"+userExist+"'";
        Cursor data = db.rawQuery(query, null);
        if(data.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean tiktokFound(String userExist){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select username_tiktok from pesertas where username_tiktok = '"+userExist+"'";
        Cursor data = db.rawQuery(query, null);
        if(data.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean todayExist(String userExist){
        SQLiteDatabase db = this.getWritableDatabase();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String query = "select username_ig, strftime('%d-%m-%Y', created_at) AS hari_ini from pesertas where username_ig = '"+userExist+"' and hari_ini = '"+currentDate+"'";
        Cursor data = db.rawQuery(query, null);
        if(data.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }
}

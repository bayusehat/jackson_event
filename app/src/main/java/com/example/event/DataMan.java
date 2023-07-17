package com.example.event;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataMan extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "event_peserta.db";
    private static final int DATABASE_VERSION = 1;
    public DataMan(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String sql = "create table pesertas (id integer primary key autoincrement, nama text null, jk text null, no_hp text null, email text null, username_ig text null, asal text null, know_jackson text null, user text null,created_at timestamp default current_timestamp)";
        Log.d("Data", "onCreate: "+sql);
        db.execSQL(sql);
        sql = "INSERT INTO pesertas (nama, jk, no_hp, email, username_ig, asal, know_jackson, user) VALUES ('ANTONO', 'Laki', '085364791632', 'antono@gmail.om', '@korengajaib', 'SMK Telkom Malang', 'Sosial Media','SURABAYA')";
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
}

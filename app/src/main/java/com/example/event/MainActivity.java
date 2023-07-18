package com.example.event;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String[] daftar;
    String[] listNama;
    ListView ListView01;
    Menu menu;
    protected Cursor cursor;
    DataMan dbcenter;

    Session session;
    public static MainActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn  = (Button)findViewById(R.id.button2);
        Button btnLogout = (Button)findViewById(R.id.button3);
        TextView kotaUser = (TextView) findViewById(R.id.welcomeText);
        TextView namaKota = (TextView) findViewById(R.id.welcomeText2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte = new Intent(MainActivity.this, BuatPeserta.class);
                startActivity(inte);
            }
        });

        ma = this;
        dbcenter = new DataMan(this);
        RefreshList();
        session = new Session(this);

        kotaUser.setText("Anda login dalam kawasan");
        namaKota.setText(session.getSPKota());
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.saveSPBoolean(session.SP_SUDAH_LOGIN, false);
                startActivity(new Intent(MainActivity.this, LoginUser.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });
    }

    public void RefreshList(){
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        Session sess = new Session(this);
        cursor = db.rawQuery("SELECT * FROM pesertas where user = '"+sess.getSPKota()+"'",null);
        daftar = new String[cursor.getCount()];
        listNama = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc = 0; cc < cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            daftar[cc] = cursor.getString(0).toString();
            listNama[cc] = cursor.getString(1).toString();
        }
        ListView01 = (ListView)findViewById(R.id.listView1);
        ListView01.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, listNama));
        ListView01.setSelected(true);
        ListView01.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftar[arg2]; //.getItemAtPosition(arg2).toString();
                final CharSequence[] dialogitem = {"Lihat Peserta", "Update Peserta", "Hapus Peserta"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch(item){
                            case 0 :
                                Intent i = new Intent(getApplicationContext(), LihatPeserta.class);
                                i.putExtra("id", selection);
                                startActivity(i);
                                break;
                            case 1 :
                                Intent in = new Intent(getApplicationContext(), UpdatePeserta.class);
                                in.putExtra("id", selection);
                                startActivity(in);
                                break;
                            case 2 :
                                SQLiteDatabase db = dbcenter.getWritableDatabase();
                                db.execSQL("delete from pesertas where id = '"+selection+"'");
                                RefreshList();
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
        ((ArrayAdapter)ListView01.getAdapter()).notifyDataSetInvalidated();
        }
    }
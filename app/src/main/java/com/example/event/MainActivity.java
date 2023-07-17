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
    DataCenter dbcenter;
    public static MainActivity ma;
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String KOTA_INI = "place";

    SharedPreferences sharedPreferences;
    String kota;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // getting data from shared prefs and
        // storing it in our string variable.
        kota = sharedPreferences.getString("KOTA_INI", null);

        TextView welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText("Kamu login di " + kota);

        Button btn  = (Button)findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte = new Intent(MainActivity.this, BuatPeserta.class);
                startActivity(inte);
            }
        });

        ma = this;
        dbcenter = new DataCenter(this);
        RefreshList();
    }

    public void RefreshList(){
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM pesertas",null);
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
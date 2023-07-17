package com.example.event;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class BuatPeserta extends AppCompatActivity {
    protected Cursor cursor;
    DataCenter dbHelper;
    Button ton1, ton2;
    EditText text1, text2, text3, text4, text5, text6, text7;

    boolean isValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_peserta);

        dbHelper = new DataCenter(this);
        text1 = (EditText) findViewById(R.id.editText1);
        text2 = (EditText) findViewById(R.id.editText2);
        text3 = (EditText) findViewById(R.id.editText3);
        text4 = (EditText) findViewById(R.id.editText4);
        text5 = (EditText) findViewById(R.id.editText5);
        text6 = (EditText) findViewById(R.id.editText6);
        text7 = (EditText) findViewById(R.id.editText7);
        ton1 = (Button) findViewById(R.id.button1);
        ton2 = (Button) findViewById(R.id.button2);

        ton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                isValid = checkAllFields();
                if(isValid){
                    if(dbHelper.userIsFound(text5.getText().toString())){
                        text5.setError("Username IG yang anda inputkan sudah terdaftar, silahkan menggunakan username yang lain");
                    }else {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.execSQL("insert into pesertas(nama, jk, no_hp, email, username_ig, asal, know_jackson) values('" +
                                text1.getText().toString() + "','" +
                                text2.getText().toString() + "','" +
                                text3.getText().toString() + "','" +
                                text4.getText().toString() + "','" +
                                text5.getText().toString() + "','" +
                                text6.getText().toString() + "','" +
                                text7.getText().toString() + "')");
                        Toast.makeText(getApplicationContext(), "Berhasil mendaftarkan peserta", Toast.LENGTH_LONG).show();
                        MainActivity.ma.RefreshList();
                        finish();
                    }
                }
            }
        });
        ton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }

    private boolean checkAllFields(){
        if(text1.length() == 0){
            text1.setError("Nama Lengkap harus diisi");
            return false;
        }
        if(text2.length() == 0){
            text2.setError("Gender harus diisi");
            return false;
        }
        if(text3.length() == 0){
            text3.setError("Whatsapp number harus diisi");
            return false;
        }
        if(text4.length() == 0){
            text4.setError("Email harus diisi");
            return false;
        }
        if(text5.length() == 0){
            text5.setError("Username IG harus diisi");
            return false;
        }
        if(text6.length() == 0){
            text6.setError("Nama sekolah dan Koata harus diisi");
            return false;
        }
        if(text7.length() == 0){
            text7.setError("Tentang Jackson field harus diisi");
            return false;
        }

        return true;
    }
}
package com.example.event;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class UpdatePeserta extends AppCompatActivity{
    protected Cursor cursor;
    DataHelper dbHelper;
    Button ton1, ton2;
    EditText text1, text3, text4, text5, text6, text7, text8;
    TextView textView8;
    Spinner spin;
    RadioGroup radioGroup;
    RadioButton radioButton;
    String[] gender = {"Laki","Perempuan"};
    private boolean isValid;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_peserta);

        spin = (Spinner) findViewById(R.id.spinnerGender);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gender);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

        dbHelper = new DataHelper(this);
        text1 = (EditText) findViewById(R.id.editText1);
        text3 = (EditText) findViewById(R.id.editText3);
        text4 = (EditText) findViewById(R.id.editText4);
        text5 = (EditText) findViewById(R.id.editText5);
        text6 = (EditText) findViewById(R.id.editText6);
        text7 = (EditText) findViewById(R.id.editText7);
        text8 = (EditText) findViewById(R.id.editText8);
        textView8 = (TextView) findViewById(R.id.textView8);
        radioGroup = (RadioGroup) findViewById(R.id.radGroup);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM pesertas WHERE id = " +
                getIntent().getStringExtra("id"),null);
        cursor.moveToFirst();
        if (cursor.getCount()>0)
        {
            int selectionPosition= adapter.getPosition(cursor.getString(2).toString());
            cursor.moveToPosition(0);

            text7.setVisibility(View.INVISIBLE);
            textView8.setVisibility(View.INVISIBLE);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                    if(checkedId == R.id.radOthers){
                        textView8.setVisibility(View.VISIBLE);
                        text7.setVisibility(View.VISIBLE);
                    }else{
                        textView8.setVisibility(View.INVISIBLE);
                        text7.setVisibility(View.INVISIBLE);
                    }
                }
            });
            text1.setText(cursor.getString(1).toString());
            spin.setSelection(selectionPosition);
            text3.setText(cursor.getString(3).toString());
            text4.setText(cursor.getString(4).toString());
            text5.setText(cursor.getString(5).toString());
            text6.setText(cursor.getString(7).toString());
            text8.setText(cursor.getString(6).toString());
            text7.setText(cursor.getString(9).toString());
        }
        ton1 = (Button) findViewById(R.id.button1);
        ton2 = (Button) findViewById(R.id.button2);
        // daftarkan even onClick pada btnSimpan
        ton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                isValid = checkAllFields();
                if(isValid) {
//                    if(dbHelper.userIsFound(text5.getText().toString())){
//                        text5.setError("Username IG yang anda inputkan sudah terdaftar, silahkan menggunakan username yang lain");
//                    }else {
                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        radioButton = (RadioButton) findViewById(selectedId);
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.execSQL("update pesertas set " +
                                "nama = '" + text1.getText().toString() + "', " +
                                "jk='" + spin.getSelectedItem().toString() + "', " +
                                "no_hp='" + text3.getText().toString() + "', " +
                                "email='" + text4.getText().toString() + "', " +
                                "username_ig='" + text5.getText().toString() + "', " +
                                "username_tiktok='" + text8.getText().toString() + "', " +
                                "asal='" + text6.getText().toString() + "', " +
                                "know_jackson='" + radioButton.getText().toString() +"', " +
                                "others_know='" + text7.getText().toString() + "' where id = " + getIntent().getStringExtra("id"));
                        Toast.makeText(getApplicationContext(), "Berhasil update data peserta!", Toast.LENGTH_LONG).show();
                        MainActivity.ma.RefreshList();
                        finish();
                    }
                }
//            }
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
        Session sess = new Session(this);
        if(text1.length() == 0){
            text1.setError("Nama Lengkap harus diisi");
            return false;
        }
        if(spin.getSelectedItem().toString() == ""){
            TextView errorTextview = (TextView) spin.getSelectedView();
            errorTextview.setError("Gender harus dipilih");
            return false;
        }
        if(text3.length() == 0){
            text3.setError("Whatsapp number harus diisi");
            return false;
        }
        if(text3.length() < 10){
            text3.setError("Whatsapp number tidak valid");
            return false;
        }
        if(text4.length() == 0){
            text4.setError("Email harus diisi");
            return false;
        }
        if(!sess.validEmail(text4.getText().toString())){
            Toast.makeText(getApplicationContext(), "E-mail tidak sesuai format!", Toast.LENGTH_LONG).show();
            text4.setError("E-mail tidak sesuai format");
            return false;
        }
        if(text5.length() == 0){
            text5.setError("Username Instagram harus diisi");
            return false;
        }
//        if(text8.length() > 0){
//            if(dbHelper.tiktokFound(text8.getText().toString())) {
//                text8.setError("Username TikTok telah digunakan");
//                return false;
//            }
//        }
//        if(text6.length() == 0){
//            text6.setError("Nama sekolah dan kota harus diisi");
//            return false;
//        }
//        if(text7.length() == 0){
//            text7.setError("Tentang Jackson field harus diisi");
//            return false;
//        }

        return true;
    }
}

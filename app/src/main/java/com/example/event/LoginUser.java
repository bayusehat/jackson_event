package com.example.event;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class LoginUser extends AppCompatActivity{
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String KOTA_INI = "place";

    SharedPreferences sharedPreferences;
    String kota;
    String[] users = {"--pilih kota--","SURABAYA","MALANG","KEDIRI"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_login);

        Spinner spin = (Spinner) findViewById(R.id.spinner);
        Button btnLogin = (Button) findViewById(R.id.buttonLogin);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        kota = sharedPreferences.getString("KOTA_INI", null);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spin.getSelectedItem().toString() == "--pilih kota--"){
                    TextView errorTextview = (TextView) spin.getSelectedView();
                    errorTextview.setError("Kota harus dipilih terlebih dahulu");
                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString(KOTA_INI, spin.getSelectedItem().toString());
                    editor.apply();

                    Log.d("ISI",KOTA_INI);
                    Log.d("INPUT",spin.getSelectedItem().toString());

                    Intent i = new Intent(LoginUser.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (kota != null) {
            Intent i = new Intent(LoginUser.this, MainActivity.class);
            startActivity(i);
        }
    }

}

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
    String[] users = {
            "EAST JAVA SERIES - NORTH REGION",
            "CENTRAL JAVA SERIES - SOUTH REGION",
            "BALI SERIES",
            "DI JOGJAKARTA SERIES",
            "EAST JAVA SERIES - SOUTH REGION",
            "CENTRAL JAVA SERIES - NORTH REGION",
            "RIAU SERIES",
            "WEST JAVA SERIES - WEST REGION",
            "EAST KALIMANTAN SERIES",
            "DKI JAKARTA SERIES - WEST REGION",
            "DKI JAKARTA SERIES - EAST REGION",
            "DKI JAKARTA SERIES - SOUTH REGION",
            "DKI JAKARTA SERIES - CHAMPIONSHIP SERIES",
            "WEST JAVA SERIES - EAST REGION",
            "BANTEN SERIES",
            "EAST JAVA SERIES - WEST REGION",
            "EAST JAVA SERIES - EAST REGION",
            "SOUTH SUMATERA SERIES",
            "SOUTH SULAWESI SERIES",
            "NORTH SULAWESI SERIES"};
    Session session;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_login);

        Spinner spin = (Spinner) findViewById(R.id.spinner);
        Button btnLogin = (Button) findViewById(R.id.buttonLogin);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

        session = new Session(this);
        if (session.getSPSudahLogin()){
            startActivity(new Intent(LoginUser.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spin.getSelectedItem().toString() == "--pilih kota--"){
                    TextView errorTextview = (TextView) spin.getSelectedView();
                    errorTextview.setError("Kota harus dipilih terlebih dahulu");
                }else{
                    session.saveSPString(session.SP_KOTA, spin.getSelectedItem().toString());
                    session.saveSPBoolean(session.SP_SUDAH_LOGIN, true);
                    Intent i = new Intent(LoginUser.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

}

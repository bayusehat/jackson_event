package com.example.event;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Patterns;

import java.util.regex.Pattern;

public class Session {
    public static final String SP_JACKSON = "spJacksonApp";

    public static final String SP_KOTA = "spKota";

    public static final String SP_SUDAH_LOGIN = "spSudahLogin";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public Session(Context context){
        sp = context.getSharedPreferences(SP_JACKSON, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSPKota(){
        return sp.getString(SP_KOTA, "");
    }


    public boolean getSPSudahLogin(){
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }

    public boolean validEmail(String email){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public boolean validUsername(String username){
        String valAdd = username.substring(0,1);
        Log.d("subtring",valAdd);
        if(valAdd == "@"){
            return true;
        }else{
            return false;
        }
    }
}

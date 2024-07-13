package com.example.caravoidancegame.Util;


import android.content.Context;
import android.content.SharedPreferences;
public class MySP {
    private static final String DB_FILE = "DB_FILE";
    private static MySP MySP = null;
    private SharedPreferences preferences;

    public static MySP getInstance(){
        return MySP;
    }
    public static void init(Context context){
        if(MySP == null){
            MySP= new MySP(context);
        }

    }

    private MySP(Context context){
        preferences = context.getSharedPreferences(DB_FILE,Context.MODE_PRIVATE);
    }

    public String getStrSP(String key,String def){
        return preferences.getString(key,def);
    }

    public void putString(String key,String value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

}


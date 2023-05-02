package com.example.lks_hotel_mobile;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    private SharedPreferences sharedPreferences;

    public Session(Context ctx){
        sharedPreferences = ctx.getSharedPreferences("my-data", Context.MODE_PRIVATE);
    }

    public void SetEmployee(int id, String username, String name){
        sharedPreferences.edit().putInt("id", id).commit();
        sharedPreferences.edit().putString("username", username).commit();
        sharedPreferences.edit().putString("name", name).commit();
    }

    public int getId(){
        return sharedPreferences.getInt("id", 0);
    }
    public String getUsername(){
        return  sharedPreferences.getString("username", "");
    }
    public String getName(){
        return  sharedPreferences.getString("name", "");
    }
}

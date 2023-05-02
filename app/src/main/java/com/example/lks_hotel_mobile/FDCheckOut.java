package com.example.lks_hotel_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FDCheckOut extends AppCompatActivity {
    Spinner room, item;
    Context ctx;
    RequestQueue queue;
    Session s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fdcheck_out);

        room = findViewById(R.id.room);
        item = findViewById(R.id.item);
        ctx = this;
        queue = Volley.newRequestQueue(ctx);
        s = new Session(ctx);
    }
    void getRoom(){
//        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, RequestApi.getRoomUrl(), null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                try{
//                    for(int i = 0; i < response.length(); i++){
//                        JSONObject obj = response.getJSONObject(i);
//                    }
//                }catch (JSONException ex){
//                    ex.printStackTrace();
//                }
//            }
//        })
    }
    void getItem(){

    }
}
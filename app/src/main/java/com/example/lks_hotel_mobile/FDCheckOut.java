package com.example.lks_hotel_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FDCheckOut extends AppCompatActivity {
    Spinner room, item;
    Context ctx;
    RequestQueue queue;
    Session s;
    List<String> roomNumber;
    List<Integer> roomId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fdcheck_out);
        room = findViewById(R.id.roomNumber);
        item = findViewById(R.id.item);
        ctx = this;
        queue = Volley.newRequestQueue(ctx);
        s = new Session(ctx);
        roomNumber = new ArrayList<>();
        roomId = new ArrayList<>();

        getRoom();
    }
    void getRoom(){
        JsonArrayRequest roomRequest = new JsonArrayRequest(Request.Method.GET, RequestApi.getRoomUrl(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject obj = response.getJSONObject(i);
                        roomNumber.add(obj.getString("RoomNumber"));
                        roomId.add(obj.getInt("ID"));
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, roomNumber);
                room.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(roomRequest);
    }
}
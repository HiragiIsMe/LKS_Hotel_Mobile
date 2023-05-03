package com.example.lks_hotel_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FDCheckOut extends AppCompatActivity {
    EditText price, qty, sub;
    Spinner room, item;
    Button Submit;
    Context ctx;
    RequestQueue queue;
    Session s;
    List<String> roomNumber;
    List<Integer> roomId;
    List<String> fdName;
    List<Integer> fdId;
    List<Integer> fdPrice;
    int RoomID, FDID,  FDQty, FDSub, EmployeeID;
    int quantity, pricee;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fdcheck_out);
        price = findViewById(R.id.price);
        qty = findViewById(R.id.qty);
        sub = findViewById(R.id.subtotal);
        room = findViewById(R.id.roomNumber);
        item = findViewById(R.id.item);
        Submit = findViewById(R.id.btnSub);
        ctx = this;
        queue = Volley.newRequestQueue(ctx);
        s = new Session(ctx);
        roomNumber = new ArrayList<>();
        roomId = new ArrayList<>();
        fdName = new ArrayList<>();
        fdId = new ArrayList<>();
        fdPrice = new ArrayList<>();
        room.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                RoomID = Integer.valueOf(roomId.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        item.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                FDID = Integer.valueOf(fdId.get(i));
                price.setText(String.valueOf(fdPrice.get(i)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() == 0){
                    quantity = 0;
                    pricee = Integer.parseInt(price.getText().toString());
                    sub.setText(String.valueOf(quantity * pricee));
                }else{
                    quantity = Integer.parseInt(qty.getText().toString());
                    pricee = Integer.parseInt(price.getText().toString());
                    sub.setText(String.valueOf(quantity * pricee));
                }

            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    FDQty = Integer.parseInt(qty.getText().toString());
                    FDSub = Integer.parseInt(sub.getText().toString());
                    JSONObject obj = new JSONObject();
                    obj.put("RoomID", RoomID);
                    obj.put("FDID", FDID);
                    obj.put("Qty", FDQty);
                    obj.put("TotalPrice", FDSub);
                    obj.put("EmployeeID", s.getId());
                    JsonObjectRequest push = new JsonObjectRequest(Request.Method.POST, RequestApi.getCheckoutUrl(), obj, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            if(Boolean.valueOf(String.valueOf(response)) ==  true){
                                AlertDialog dialog = new AlertDialog.Builder(ctx).create();
                                dialog.setTitle("Success");
                                dialog.setMessage("Checkout Success");
                                dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(ctx).create();
                            dialog.setTitle("Error");
                            dialog.setMessage(""+error);
                            dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                    });
                    queue.add(push);
                }catch (JSONException ex){
                    ex.printStackTrace();
                }
            }
        });
        getRoom();
        getFD();
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
                AlertDialog dialog = new AlertDialog.Builder(ctx).create();
                dialog.setMessage(error.getMessage());
                dialog.show();
            }
        });
        queue.add(roomRequest);
    }
    void getFD(){
        JsonArrayRequest fdRequest = new JsonArrayRequest(Request.Method.GET, RequestApi.getFd(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject obj = response.getJSONObject(i);
                        fdId.add(obj.getInt("ID"));
                        fdPrice.add(obj.getInt("Price"));
                        fdName.add(obj.getString("Name"));
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, fdName);
                item.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog dialog = new AlertDialog.Builder(ctx).create();
                dialog.setMessage(error.getMessage());
                dialog.show();
            }
        });
        queue.add(fdRequest);
    }
}
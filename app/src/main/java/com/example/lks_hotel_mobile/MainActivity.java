package com.example.lks_hotel_mobile;

import static com.android.volley.VolleyLog.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {
    EditText username, password;
    Button login;
    Context ctx;
    RequestQueue queue;
    Session s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        ctx = this;
        queue = Volley.newRequestQueue(ctx);
        s = new Session(ctx);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().length() == 0 || password.getText().length() == 0){
                    Toast.makeText(getApplicationContext(), "Username And Password Must Be Filled", Toast.LENGTH_LONG).show();
                }else{
                    StringRequest request = new StringRequest(Request.Method.POST, RequestApi.getLoginUrl(), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response != null) {
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    s.SetEmployee(obj.getInt("id"), obj.getString("username"), obj.getString("name"));
                                    Intent main = new Intent(MainActivity.this, FDCheckOut.class);
                                    startActivity(main);
                                    finish();
                                } catch (JSONException ex) {
                                    ex.printStackTrace();
                                    AlertDialog dialog = new AlertDialog.Builder(ctx).create();
                                    dialog.setTitle("Error");
                                    dialog.setMessage("Can't find user");
                                    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog.show();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            AlertDialog dialog = new AlertDialog.Builder(ctx).create();
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
                    }){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            String usernameValue = username.getText().toString();
                            String passwordValue = password.getText().toString();
                            Map<String, String> params = new HashMap<>();
                            params.put("Username", usernameValue);
                            params.put("Password", passwordValue);

                            return  params;
                        }
                    };
                    queue.add(request);
                }
            }
        });
    }
}